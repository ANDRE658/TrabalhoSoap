package br.unipar.programacaoweb.daos;

import br.unipar.programacaoweb.utils.EntityManagerUtil;
import jakarta.persistence.EntityManager;
import br.unipar.programacaoweb.models.Pedido;

import java.util.List;

public class PedidoDAO {
    EntityManager em = EntityManagerUtil.getEm();

    public void salvar(Pedido pedido) {
        try {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void atualizar(Pedido pedido) {
        try {
            em.getTransaction().begin();
            em.merge(pedido);
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
            Pedido pedido = em.find(Pedido.class, id);
            em.remove(pedido);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public Pedido buscarPorId(Integer id) {
        try {
            return em.find(Pedido.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarTodos() {
        try {
            return em.createQuery("SELECT p FROM Pedido p", Pedido.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarPorClienteId(Integer clienteId) {
        try {
            return em.createQuery("SELECT p FROM Pedido p WHERE p.cliente.id = :clienteId", Pedido.class)
                     .setParameter("clienteId", clienteId)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<Pedido> buscarPorStatus(String status) {
        try {
            return em.createQuery("SELECT p FROM Pedido p WHERE p.status = :status", Pedido.class)
                     .setParameter("status", status)
                     .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
