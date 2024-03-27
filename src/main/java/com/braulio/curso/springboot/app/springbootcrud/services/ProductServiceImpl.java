package com.braulio.curso.springboot.app.springbootcrud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.braulio.curso.springboot.app.springbootcrud.entities.Product;
import com.braulio.curso.springboot.app.springbootcrud.entities.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return (List<Product>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isPresent()) {
            Product productdb = optionalProduct.orElseThrow();
            productdb.setName(product.getName());
            productdb.setDescription(product.getDescription());
            productdb.setPrice(product.getPrice());
            return Optional.of(repository.save(productdb));
        }
        return optionalProduct;
    }
    
    @Transactional
    @Override
    public Optional<Product> delete(Long id) {

        Optional<Product> optionalProduct = repository.findById(id);
        optionalProduct.ifPresent(productDb ->{
            repository.delete(productDb);
        });

        return optionalProduct;
    }
}
