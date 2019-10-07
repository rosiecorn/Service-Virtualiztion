package com.bbs.restapimysql.controller;

import com.bbs.restapimysql.model.Employee;
import com.bbs.restapimysql.model.ProductBySupplier;
import com.bbs.restapimysql.repository.ProductBySupplierRepository;
import com.bbs.restapimysql.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/productBySupplier"})
public class ProductBySupplierController {

    private ProductBySupplierRepository repository;

    ProductBySupplierController(ProductBySupplierRepository productRepository) {
        this.repository = productRepository;
    }

    @GetMapping()
    public ResponseEntity<List<ProductBySupplier>> listall() {
        List<ProductBySupplier> employees = repository.listAll();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping(path = {"/search"})
    public ResponseEntity<List<ProductBySupplier>> findByName
            (
                    @RequestParam(name = "suppliername") String name

            ) {

        List<ProductBySupplier> employees = repository.findByName(name);

        return ResponseEntity.ok().body(employees);

    }
}
