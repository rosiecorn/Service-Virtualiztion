package com.bbs.restapimysql.repository;
import com.bbs.restapimysql.model.Category;
import com.bbs.restapimysql.model.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {

    @Query(value="SELECT * FROM shippers  where companyname like %:companyname%", nativeQuery = true)
    List<Shipper> findByName(@Param("companyname") String companyname);

   // List<Shipper> findById(long id);
}
