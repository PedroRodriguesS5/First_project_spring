package br.com.cubosacademy.apirest.repositories;

import br.com.cubosacademy.apirest.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> { }
