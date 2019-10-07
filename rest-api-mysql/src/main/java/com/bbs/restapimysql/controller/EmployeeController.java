package com.bbs.restapimysql.controller;

import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.Employee;
import com.bbs.restapimysql.model.ErrorCode;
import com.bbs.restapimysql.repository.CategoryRepository;
import com.bbs.restapimysql.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping({"/employee"})
public class EmployeeController {

    private EmployeeRepository repository;

    EmployeeController(EmployeeRepository employeeRepository) {
        this.repository = employeeRepository;
    }

    @GetMapping
    public List findAll() {
        return repository.findAll();
    }


    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Employee> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElseThrow(NotFoundException::new);
    }


    @GetMapping(path = {"/searchname"})
    public ResponseEntity<List<Employee>> findByName
            (
                    @RequestParam(name = "name", required = false) String name

            ) {

        List<Employee> employees = repository.findByName(name);

        return ResponseEntity.ok().body(employees);

    }

    @GetMapping(path = {"/hireddate"})
    public ResponseEntity<List<Employee>> findBHiredate
            (
                     @RequestParam(name = "from") String from
                     ,@RequestParam(name = "to") String to

            ) {


        try {

            SimpleDateFormat formatedate = new SimpleDateFormat("yyyy-MM-dd");

            List<Employee> employees = repository.findByHiredateBetween(formatedate.parse(from), formatedate.parse(to));

            return ResponseEntity.ok().body(employees);
        }
        catch(ParseException pe)
        {
            return ResponseEntity.badRequest().build();
        }

    }

}