package com.bbs.restapimysql.controller;

import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.ErrorCode;
import com.bbs.restapimysql.model.Shipper;
import com.bbs.restapimysql.repository.CategoryRepository;
import com.bbs.restapimysql.repository.ShipperRepository;
import org.aspectj.bridge.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.MessageUtils;

import java.util.List;

@RestController
@RequestMapping({"/shipper"})
public class ShipperController {

    private ShipperRepository repository;

    ShipperController(ShipperRepository shipperRepository) {
        this.repository = shipperRepository;
    }

    @GetMapping
    public List findAll() {
        return repository.findAll();
    }


    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable long id) {

        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElseThrow(NotFoundException::new);

    }


    @GetMapping(path = {"/search"})
    public ResponseEntity<List<Shipper>> findByName
            (
                    @RequestParam(name = "companyname") String name

            ) {

        List<Shipper> shippers = repository.findByName(name);

        return ResponseEntity.ok().body(shippers);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Shipper create(@RequestBody Shipper shipper) {
        return repository.save(shipper);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Shipper> update(@PathVariable("id") long id,
                                          @RequestBody Shipper shipper) {
        return repository.findById(id)
                .map(record -> {
                    record.setCompanyname(shipper.getCompanyname());
                    record.setPhone(shipper.getPhone());

                    Shipper updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElseThrow(RuntimeException::new);


    }


    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    ErrorCode code = new ErrorCode("Shipper.ValidShipperId","Deleted successfully");


                    return ResponseEntity.ok().body(code);
                }).orElse(

                        //ResponseEntity.notFound().build()
                        new ResponseEntity<ErrorCode>(new ErrorCode("Shipper.InvalidShipperId","ShipperId does not found"),HttpStatus.NOT_FOUND)


                );
    }
}
