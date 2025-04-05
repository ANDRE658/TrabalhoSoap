package br.unipar.programacaoweb.daos;

import br.unipar.programacaoweb.utils.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import br.unipar.programacaoweb.models.Borda;

import java.util.List;

public class BordaDAO {
    EntityManager em = EntityManagerUtil.getEm();

    public void salvar(Borda borda) {
        try {
            em.getTransaction().begin();
            em.persist(borda);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void atualizar(Borda borda) {
        try {
            em.getTransaction().begin();
            em.merge(borda);
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
            Borda borda = em.find(Borda.class, id);
            em.remove(borda);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public Borda buscarPorId(Integer id) {
        try {
            return em.find(Borda.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Borda> buscarTodos() {
        try {
            return em.createQuery("from Borda ").getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public Borda buscarPorSabor(String sabor) {
        try {
            return em.createQuery("SELECT b FROM Borda b WHERE b.Sabor = :sabor", Borda.class)
                    .setParameter("sabor", sabor)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

}
