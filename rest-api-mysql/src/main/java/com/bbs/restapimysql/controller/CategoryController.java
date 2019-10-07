package com.bbs.restapimysql.controller;


import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.ErrorCode;
import com.bbs.restapimysql.repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/category"})
public class CategoryController {

    private CategoryRepository repository;

    CategoryController(CategoryRepository categoryRepository) {
        this.repository = categoryRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }


    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Category> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping(path = {"/search"})
    public ResponseEntity<List<Category>> findByName
            (
                    @RequestParam(name ="name") String name

            ){

        List<Category> categories = repository.findByCategoryName(name);

        return  ResponseEntity.ok().body(categories);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category){
        return repository.save(category);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Category> update(@PathVariable("id") long id,
                                           @RequestBody Category category){
        return repository.findById(id)
                .map(record -> {
                    record.setCategoryname(category.getCategoryname());
                    record.setDescription(category.getDescription());

                    Category updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElseThrow(NotFoundException::new);
    }


    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    ErrorCode code = new ErrorCode("Category.ValidCategoryId","Deleted successfully");

                    return ResponseEntity.ok().body(code);
                }).orElse(
                        new ResponseEntity<ErrorCode>(new ErrorCode("Category.InvalidCategoryId","CategoryId does not found"), HttpStatus.NOT_FOUND)

                );
    }
}
