package com.benchmark.rest.dao;

import com.benchmark.rest.model.Category;
import jakarta.persistence.EntityManagerFactory;

public class CategoryDAO extends GenericDAO<Category> {
    public CategoryDAO(EntityManagerFactory emf) {
        super(Category.class, emf);
    }
}
