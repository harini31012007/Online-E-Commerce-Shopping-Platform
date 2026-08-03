package com.ecommerce.repository;
import com.ecommerce.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface CartItemRepository extends JpaRepository<CartItem, Long> { List<CartItem> findByUser(User user); }
