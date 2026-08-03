package com.ecommerce.controller;
import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class MainController {
    @Autowired private UserRepository userRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private CartItemRepository cartRepo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    private User getCurrentUser(Authentication auth) {
        if (auth == null) return null;
        return userRepo.findByEmail(auth.getName());
    }

    @GetMapping("/") public String home() { return "index"; }
    @GetMapping("/login") public String login() { return "login"; }
    @GetMapping("/register") public String register() { return "register"; }
    
    @PostMapping("/register") 
    public String doRegister(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // If email contains "admin", automatically make them an admin (for testing purposes)
        if (user.getEmail().toLowerCase().contains("admin")) {
            user.setRole("ROLE_ADMIN");
        }
        userRepo.save(user);
        return "redirect:/login?registered";
    }

    // SEARCH & FILTERING INCLUDED
    @GetMapping("/products") 
    public String products(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.isEmpty()) {
            model.addAttribute("products", productRepo.findByCategoryContainingIgnoreCaseOrNameContainingIgnoreCase(search, search));
            model.addAttribute("searchQuery", search);
        } else {
            model.addAttribute("products", productRepo.findAll());
        }
        return "products";
    }

    @GetMapping("/cart") public String cart(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        List<CartItem> cart = cartRepo.findByUser(user);
        double total = cart.stream().mapToDouble(c -> c.getProduct().getPrice().doubleValue() * c.getQuantity()).sum();
        model.addAttribute("cart", cart);
        model.addAttribute("total", total);
        return "cart";
    }

    @PostMapping("/cart/add") public String addToCart(@RequestParam Long productId, Authentication auth) {
        User user = getCurrentUser(auth);
        Product p = productRepo.findById(productId).orElse(null);
        if(p != null) {
            CartItem item = new CartItem();
            item.setUser(user);
            item.setProduct(p);
            item.setQuantity(1);
            cartRepo.save(item);
        }
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove") public String removeFromCart(@RequestParam Long itemId) {
        cartRepo.deleteById(itemId);
        return "redirect:/cart";
    }

    @PostMapping("/checkout") public String checkout(@RequestParam String address, Authentication auth) {
        User user = getCurrentUser(auth);
        List<CartItem> cart = cartRepo.findByUser(user);
        if(!cart.isEmpty()) {
            double total = cart.stream().mapToDouble(c -> c.getProduct().getPrice().doubleValue() * c.getQuantity()).sum();
            Order order = new Order();
            order.setUser(user);
            order.setTotalAmount(BigDecimal.valueOf(total));
            order.setShippingAddress(address);
            order.setStatus("PENDING PAYMENT");
            order = orderRepo.save(order);
            return "redirect:/payment?orderId=" + order.getId();
        }
        return "redirect:/cart";
    }

    // PAYMENT SIMULATION
    @GetMapping("/payment")
    public String paymentPage(@RequestParam Long orderId, Model model) {
        model.addAttribute("order", orderRepo.findById(orderId).orElse(null));
        return "payment";
    }

    @PostMapping("/payment/process")
    public String processPayment(@RequestParam Long orderId, Authentication auth) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (order != null) {
            order.setStatus("PAID & CONFIRMED");
            orderRepo.save(order);
            cartRepo.deleteAll(cartRepo.findByUser(getCurrentUser(auth)));
        }
        return "redirect:/?orderSuccess";
    }

    // USER PROFILE & ORDER HISTORY
    @GetMapping("/profile")
    public String profile(Model model, Authentication auth) {
        User user = getCurrentUser(auth);
        model.addAttribute("user", user);
        model.addAttribute("orders", orderRepo.findByUserOrderByOrderDateDesc(user));
        return "profile";
    }
}
