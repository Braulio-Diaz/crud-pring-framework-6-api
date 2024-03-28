package com.braulio.curso.springboot.app.springbootcrud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.braulio.curso.springboot.app.springbootcrud.entities.Product;
import com.braulio.curso.springboot.app.springbootcrud.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/list")
    public List<Product> list(){
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> viewById(@PathVariable Long id){

        Optional<Product> optionalProduct = service.findById(id);

        if(optionalProduct.isPresent()){
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        
        } return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<Product> save(@Valid @RequestBody Product product){
        Product newProduct = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody  Product product){

        Optional<Product> optionalProduct = service.update(id, product);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(optionalProduct.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        
        //Product product = new Product();
        //product.setId(id);
        
        Optional<Product> optionalProduct = service.delete(id);
        if (optionalProduct.isPresent()) {
            return ResponseEntity.ok(optionalProduct.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
