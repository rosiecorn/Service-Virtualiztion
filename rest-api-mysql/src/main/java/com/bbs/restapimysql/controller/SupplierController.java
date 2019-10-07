package com.bbs.restapimysql.controller;

import com.bbs.restapimysql.model.ErrorCode;
import com.bbs.restapimysql.model.Supplier;
import com.bbs.restapimysql.repository.SupplierRepository;
import org.aspectj.bridge.MessageUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/supplier"})
public class SupplierController {

    private SupplierRepository repository;

    SupplierController(SupplierRepository supplierRepository) {
        this.repository = supplierRepository;
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
    public ResponseEntity<List<Supplier>> findByName
            (
                    @RequestParam(name = "city", required = false) String city
                    ,@RequestParam(name = "companyname",required = false) String companyname


            ) {

        List<Supplier> supplier = null;

        if (city != null && companyname != null) {
            supplier = repository.findByCompanyCity(companyname, city);
            return ResponseEntity.ok().body(supplier);
        }
        if (city != null && companyname == null) {
            supplier = repository.findByCity(city);
            return ResponseEntity.ok().body(supplier);
        }
        if (city == null && companyname != null) {
            supplier = repository.findByCompany(companyname);
            return ResponseEntity.ok().body(supplier);
        } else //{
            return ResponseEntity.badRequest().build();
        //}

       // return ResponseEntity.notFound().build();

    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Supplier create(@RequestBody Supplier supplier) {
        return repository.save(supplier);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Supplier> update(@PathVariable("id") long id,
                                          @RequestBody Supplier supplier) {
        return repository.findById(id)
                .map(record -> {
                    record.setAddress(supplier.getAddress());
                    record.setCity(supplier.getCity());
                    record.setCompanyname(supplier.getCompanyname());
                    record.setContactname(supplier.getContactname());
                    record.setContacttitle(supplier.getContacttitle());
                    record.setCountry(supplier.getCountry());
                    record.setFax(supplier.getFax());
                    record.setPhone(supplier.getPhone());
                    record.setPostalcode(supplier.getPostalcode());
                    record.setRegion(supplier.getRegion());

                    Supplier updated = repository.save(record);

                    return ResponseEntity.ok().body(updated);
                }).orElseThrow(NotFoundException::new);
    }


    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    ErrorCode code = new ErrorCode("Supplier.ValidSupplierId","Deleted successfully");


                    return ResponseEntity.ok().body(code);
                }).orElse(
                        new ResponseEntity<ErrorCode>(new ErrorCode("Supplier.InvalidSupplierId","SupplierId does not found"), HttpStatus.NOT_FOUND)
                );
    }
}
