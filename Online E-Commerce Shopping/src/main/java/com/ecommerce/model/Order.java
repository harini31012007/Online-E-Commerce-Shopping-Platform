package com.ecommerce.model;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity @Data @Table(name = "orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @ManyToOne private User user;
    private BigDecimal totalAmount;
    private String status = "PENDING";
    private String shippingAddress;
    private LocalDateTime orderDate = LocalDateTime.now();
}
