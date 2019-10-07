package com.bbs.restapimysql.repository;

import com.bbs.restapimysql.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    List<Customer> findByCountry(String country);

    List<Customer> findByCountryAndCompanyname(String coungtry,String companyname);

    List<Customer> findByCountryAndCompanynameAndContactname(String country, String companyname, String contactname);

    List<Customer> findByCountryAndCompanynameAndContactnameAndPostalcode(String country, String companyname, String contactname, String postalcode);

    List<Customer> findByCountryAndContactname(String country, String contactname);

    List<Customer> findByCountryAndContactnameAndPostalcode(String country, String contactname, String postalcode);

    List<Customer> findByCountryAndPostalcode(String country, String postalcode);




}
