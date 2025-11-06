package com.benchmark.rest.dao;

import jakarta.persistence.*;
import java.util.List;

public class GenericDAO<T> {
    private Class<T> type;
    private EntityManagerFactory emf;

    public GenericDAO(Class<T> type, EntityManagerFactory emf) {
        this.type = type;
        this.emf = emf;
    }

    public T find(Long id) {
        EntityManager em = emf.createEntityManager();
        T obj = em.find(type, id);
        em.close();
        return obj;
    }

    public List<T> findAll(int page, int size) {
        EntityManager em = emf.createEntityManager();
        List<T> list = em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type)
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        em.close();
        return list;
    }

    public void save(T obj) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(obj);
        tx.commit();
        em.close();
    }

    public T update(T obj) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        T merged = em.merge(obj);
        tx.commit();
        em.close();
        return merged;
    }

    public void delete(T obj) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(obj) ? obj : em.merge(obj));
        tx.commit();
        em.close();
    }
}
