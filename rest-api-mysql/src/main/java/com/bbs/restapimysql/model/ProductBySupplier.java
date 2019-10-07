package com.bbs.restapimysql.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="t")
public class ProductBySupplier {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name ="numberofproduct")
    private int numberofproduct;
    private String suppliername;

    public int getNumberofproduct() {
        return numberofproduct;
    }

    public void setNumberofproduct(int numberofproduct) {
        this.numberofproduct = numberofproduct;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }
}
