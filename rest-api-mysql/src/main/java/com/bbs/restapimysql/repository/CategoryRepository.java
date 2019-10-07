package com.bbs.restapimysql.repository;

import com.bbs.restapimysql.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value="SELECT categoryid, categoryname,description FROM categories  where categoryname like %:name%", nativeQuery = true)
    List<Category> findByCategoryName( @Param("name") String name);

}
