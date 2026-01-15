package com.app.ecom_application.repository;

import com.app.ecom_application.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository <Order, Long> {
}
