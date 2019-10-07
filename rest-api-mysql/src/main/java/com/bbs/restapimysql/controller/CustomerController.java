package com.bbs.restapimysql.controller;

import com.bbs.restapimysql.model.Customer;
import com.bbs.restapimysql.model.ErrorCode;
import com.bbs.restapimysql.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/customer"})
public class CustomerController {

    private CustomerRepository repository;

    CustomerController(CustomerRepository customerRepository) {
        this.repository = customerRepository;
    }

    @GetMapping
    public List findAll(){
        return repository.findAll();
    }


    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Customer> findById(@PathVariable long id){
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElseThrow(NotFoundException::new);
    }


    @GetMapping(path = {"/search"})
    public ResponseEntity<List<Customer>> findByName
                (
                        @RequestParam(name ="country") String country
                        , @RequestParam(name ="companyname",required = false) String companyname
                        , @RequestParam(name ="contactname",required = false) String contactname
                        , @RequestParam(name ="postalcode",required = false) String postalcode
                ){

        List<Customer> customers = null;

        if (postalcode != null && contactname !=null && companyname !=null )
        {
            customers = repository.findByCountryAndCompanynameAndContactnameAndPostalcode(country,companyname,contactname,postalcode);
            return  ResponseEntity.ok().body(customers);
        }
        if (postalcode != null && contactname !=null && companyname == null)
        {
            customers = repository.findByCountryAndContactnameAndPostalcode(country,contactname,postalcode);
            return  ResponseEntity.ok().body(customers);
        }
        if (postalcode != null && contactname ==null && companyname == null)
        {
            customers = repository.findByCountryAndPostalcode(country,postalcode);
            return  ResponseEntity.ok().body(customers);
        }
        if (postalcode == null && contactname ==null && companyname == null)
        {
            customers = repository.findByCountry(country);
            return  ResponseEntity.ok().body(customers);
        }
        if (postalcode == null && contactname !=null && companyname != null)
        {
            customers = repository.findByCountryAndCompanynameAndContactname(country,companyname,contactname);
            return  ResponseEntity.ok().body(customers);
        }
        if (postalcode == null && contactname ==null && companyname != null)
        {
            customers = repository.findByCountryAndCompanyname(country,companyname);
            return  ResponseEntity.ok().body(customers);
        }
        if (postalcode == null && contactname !=null && companyname == null)
        {
            customers = repository.findByCountryAndContactname(country,contactname);
            return  ResponseEntity.ok().body(customers);
        }

        else
            return ResponseEntity.badRequest().build();
                    //(ResponseEntity.notFound().build());

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer create(@RequestBody Customer customer){
        return repository.save(customer);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<Customer> update(@PathVariable("id") long id,
                                          @RequestBody Customer customer){
        return repository.findById(id)
                .map(record -> {
                    record.setAddress(customer.getAddress());
                    record.setCity(customer.getCity());
                    record.setCompanyname(customer.getCompanyname());
                    record.setContactname(customer.getContactname());
                    record.setContacttitle(customer.getContacttitle());
                    record.setCountry(customer.getCountry());
                    record.setFax(customer.getFax());
                    record.setPhone(customer.getPhone());
                    record.setPostalcode(customer.getPostalcode());
                    record.setRegion(customer.getRegion());
                    Customer updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElseThrow(NotFoundException::new);
    }


    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    ErrorCode code = new ErrorCode("Customer.ValidCustomerId","Deleted successfully");

                    return ResponseEntity.ok().body(code);
                }).orElse(
                        new ResponseEntity<ErrorCode>(new ErrorCode("Customer.InvalidCustomerId","CustomerId does not found"), HttpStatus.NOT_FOUND)

                );
    }
}
