/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.dht.repository.impl;

import com.dht.pojo.Category;
import com.dht.pojo.HibernateUtils;
import com.dht.pojo.Product;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Admin
 */
public class ProductRepositoryImpl {
    public List<Product> getProducts(Map<String, String> params) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            CriteriaBuilder b = s.getCriteriaBuilder();
            CriteriaQuery<Product> q = b.createQuery(Product.class);
            Root r = q.from(Product.class);
            q.select(r);
            
            List<Predicate> predicates = new ArrayList<>();
            
            if (params != null) {
                String kw = params.get("kw");
                if (kw != null && !kw.isEmpty())
                    predicates.add(b.like(r.get("name"), String.format("%%%s%%", kw)));
                
                String fromPrice = params.get("fromePrice");
                if (fromPrice != null && !fromPrice.isEmpty()) 
                    predicates.add(b.greaterThanOrEqualTo(r.get("price"), Double.parseDouble(fromPrice)));
                
                String toPrice = params.get("toPrice");
                if (toPrice != null && !toPrice.isEmpty())
                    predicates.add(b.lessThanOrEqualTo(r.get("price"), Double.parseDouble(toPrice)));
                
                String cateId = params.get("cateId");
                if (cateId != null && !cateId.isEmpty())
                    predicates.add(b.equal(r.get("category"), Integer.parseInt(cateId)));
            }
            
            q.where(predicates.toArray(new Predicate[]{}));
            q.orderBy(b.desc(r.get("id")));
            
            Query query = s.createQuery(q);
            List<Product> products = query.getResultList();
            
            return products;
        }
    }
    
    public Product getProductById(int id) {
        try (Session session = HibernateUtils.getFactory().openSession()) {
            return session.get(Product.class, id);
            
        }
    }
    
    public Category getCateById(int id) {
        try (Session session = HibernateUtils.getFactory().openSession()) {
            return session.get(Category.class, id);
            
        }
    }
    
    public void addOrUpdate(Product p) {
        try (Session s = HibernateUtils.getFactory().openSession()) {
            s.persist(p);
        }
    }
}
