package com.benchmark.rest.dao;

import com.benchmark.rest.model.Item;
import java.util.List;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;

public class ItemDAO extends GenericDAO<Item> {
    private EntityManagerFactory emf;

    public ItemDAO(EntityManagerFactory emf) {
        super(Item.class, emf);
        this.emf = emf;
    }

    public List<Item> findByCategoryId(Long categoryId, int page, int size) {
        EntityManager em = emf.createEntityManager();
        List<Item> items = em.createQuery("SELECT i FROM Item i WHERE i.category.id = :cid", Item.class)
                .setParameter("cid", categoryId)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        em.close();
        return items;
    }
}
