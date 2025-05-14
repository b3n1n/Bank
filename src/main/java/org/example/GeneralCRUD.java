package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class GeneralCRUD implements CRUD {
    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Bank");
    private final EntityManager em = emf.createEntityManager();

    @Override
    public <T> void save(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    @Override
    public <T> void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    @Override
    public <T> void delete(T entity) {
        em.getTransaction().begin();
        em.remove(em.merge(entity));
        em.getTransaction().commit();
    }

    @Override
    public <T> List<T> findAll(Class<T> entityClass) {
        return em.createQuery("SELECT c FROM " + entityClass.getSimpleName() + " c", entityClass).getResultList();
    }

    @Override
    public <T> T findById(Class<T> entityClass, Object id) {
        return em.find(entityClass, id);
    }
}
