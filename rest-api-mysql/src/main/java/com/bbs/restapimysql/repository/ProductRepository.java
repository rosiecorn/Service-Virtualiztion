package com.bbs.restapimysql.repository;

import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long> {

    @Query(value="SELECT * FROM product_details  where categoryname like %:name% order by suppliername desc", nativeQuery = true)
    List<Product> findByCategoryName(@Param("name") String name);



}
