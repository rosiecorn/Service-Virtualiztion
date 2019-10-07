package com.bbs.restapimysql.repository;

import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value="SELECT `employees`.`empid`,\n" +
            "    `employees`.`lastname`,\n" +
            "    `employees`.`firstname`,\n" +
            "    `employees`.`title`,\n" +
            "    `employees`.`titleofcourtesy`,\n" +
            "    `employees`.`birthdate`,\n" +
            "    `employees`.`hiredate`,\n" +
            "    `employees`.`address`,\n" +
            "    `employees`.`city`,\n" +
            "    `employees`.`region`,\n" +
            "    `employees`.`postalcode`,\n" +
            "    `employees`.`country`,\n" +
            "    `employees`.`phone`,\n" +
            "    `employees`.`mgrid`\n" +
            "FROM `tsql2012`.`employees` where (firstname like %:name% or lastname like %:name%) ORDER BY lastname asc, firstname asc" , nativeQuery = true)
    List<Employee> findByName(@Param("name") String name);


    List<Employee> findByHiredateBetween (Date from, Date to);
}
