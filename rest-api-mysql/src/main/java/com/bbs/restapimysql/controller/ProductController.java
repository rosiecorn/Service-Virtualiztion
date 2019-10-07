package com.bbs.restapimysql.controller;

import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.Product;
import com.bbs.restapimysql.repository.CategoryRepository;
import com.bbs.restapimysql.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/product"})
public class ProductController {

    private ProductRepository repository;

    ProductController(ProductRepository productRepository) {
        this.repository = productRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }


    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Product> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElseThrow(NotFoundException::new);
    }


    @GetMapping(path = {"/search"})
    public ResponseEntity<List<Product>> findByName
            (
                    @RequestParam(name ="categoryname") String name

            ){

        List<Product> product = repository.findByCategoryName(name);

        return  ResponseEntity.ok().body(product);

    }
}
