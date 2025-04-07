package br.unipar.programacaoweb.daos;

import br.unipar.programacaoweb.models.ItensPedido;
import br.unipar.programacaoweb.utils.EntityManagerUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ItensPedidoDAO {
    EntityManager em = EntityManagerUtil.getEm();

    public void salvar(ItensPedido itensPedido) {
        try {
            em.getTransaction().begin();
            em.persist(itensPedido);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void atualizar(ItensPedido itensPedido) {
        EntityManager em = EntityManagerUtil.getEm();  // CRIAR AQUI
        try {
            em.getTransaction().begin();
            em.merge(itensPedido);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace(); // <-- sempre bom para debug
        } finally {
            em.close();
        }
    }

    public boolean excluir(Integer id) {
        try {
            em.getTransaction().begin();
            ItensPedido itensPedido = em.find(ItensPedido.class, id);
            em.remove(itensPedido);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        } finally {
            em.close();
        }
    }

    public ItensPedido buscarPorId(Integer id) {
        try {
            return em.find(ItensPedido.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<ItensPedido> buscarTodos() {
        try {
            return em.createQuery("SELECT i FROM ItensPedido i", ItensPedido.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    public List<ItensPedido> buscarPorPedidoId(Integer pedidoId) {
        try {
            return em.createQuery("SELECT i FROM ItensPedido i WHERE i.pedido.id = :pedidoId", ItensPedido.class)
                    .setParameter("pedidoId", pedidoId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }
}
