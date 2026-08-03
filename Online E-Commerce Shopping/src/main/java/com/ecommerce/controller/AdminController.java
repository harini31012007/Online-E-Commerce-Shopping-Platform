package com.ecommerce.controller;
import com.ecommerce.model.Product;
import com.ecommerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired private ProductRepository productRepo;

    // ADMIN DASHBOARD
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("products", productRepo.findAll());
        return "admin";
    }

    // ADD PRODUCT
    @PostMapping("/product/add")
    public String addProduct(@ModelAttribute Product product) {
        productRepo.save(product);
        return "redirect:/admin/dashboard?added";
    }

    // DELETE PRODUCT
    @PostMapping("/product/delete")
    public String deleteProduct(@RequestParam Long id) {
        productRepo.deleteById(id);
        return "redirect:/admin/dashboard?deleted";
    }
}
