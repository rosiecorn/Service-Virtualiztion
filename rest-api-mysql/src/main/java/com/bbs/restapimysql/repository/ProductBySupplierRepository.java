package com.bbs.restapimysql.repository;

import com.bbs.restapimysql.model.Product;
import com.bbs.restapimysql.model.ProductBySupplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductBySupplierRepository extends JpaRepository<ProductBySupplier, Long> {

    @Query(value="select distinct numberofproduct,suppliername  from" +
            "(select count(productname) as numberofproduct\n" +
            "\t\t,suppliername\n" +
            "from product_details\n" +
            "group by suppliername) t order by t.numberofproduct asc", nativeQuery = true)

      List<ProductBySupplier> listAll();

    @Query(value="select * from" +
            "(select count(productname) as numberofproduct\n" +
            "\t\t,suppliername\n" +
            "from product_details\n" +
            "where suppliername like %:name%) t order by t.numberofproduct asc", nativeQuery = true)

    List<ProductBySupplier> findByName(@Param("name") String name);

}
