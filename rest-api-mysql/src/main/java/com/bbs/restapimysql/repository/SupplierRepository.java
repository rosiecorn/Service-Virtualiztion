package com.bbs.restapimysql.repository;
import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRepository   extends JpaRepository<Supplier, Long> {

    @Query(value="SELECT * FROM suppliers  where city like %:city%", nativeQuery = true)
    List<Supplier> findByCity(@Param("city") String city);

    @Query(value="SELECT * FROM suppliers  where companyname like %:companyname%", nativeQuery = true)
    List<Supplier> findByCompany(@Param("companyname") String companyname);

    @Query(value="SELECT * FROM suppliers  where city like %:city% OR companyname like %:companyname%", nativeQuery = true)
    List<Supplier> findByCompanyCity(@Param("companyname") String companyname, @Param("city") String city);
}
