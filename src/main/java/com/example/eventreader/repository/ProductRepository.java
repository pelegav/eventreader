package com.example.eventreader.repository;

import com.example.eventreader.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByEvent_InsuredId(String insuredId);
}
