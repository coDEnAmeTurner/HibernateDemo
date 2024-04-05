package com.dht.demo;


import com.dht.pojo.Product;
import com.dht.repository.impl.ProductRepositoryImpl;
import java.util.HashMap;
import java.util.Map;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
public class Demo {
    public static void main(String[] args) {
        ProductRepositoryImpl pr = new ProductRepositoryImpl();
        Product p = new Product(4);
        p.setName("axforce 80 chenlong");
        pr.addOrUpdate(p);
    }
}
