package br.unipar.programacaoweb.daos;

import br.unipar.programacaoweb.models.Pizza;
import br.unipar.programacaoweb.utils.EntityManagerUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PizzaDAO {
    EntityManager em = EntityManagerUtil.getEm();


    public void salvar(Pizza pizza) {
        try{
            em.getTransaction().begin();
            em.persist(pizza);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void atualizar(Pizza pizza) {
        try {
            em.getTransaction().begin();
            em.merge(pizza);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    public boolean excluir(Integer id) {
        try {
            em.getTransaction().begin();
            Pizza pizza = em.find(Pizza.class, id);
            em.remove(pizza);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }
    public Pizza buscarPorId(Integer id) {
        try {
            return em.find(Pizza.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Pizza> buscarTodos() {
        try {
            return em.createQuery("from Pizza").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public Pizza buscarPorSabor(String nome) {
        try {
            return em.createQuery("from Pizza where sabor = :sabor", Pizza.class)
                    .setParameter("sabor", nome)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }



}
