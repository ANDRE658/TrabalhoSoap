    package br.unipar.programacaoweb.daos;

    import br.unipar.programacaoweb.utils.EntityManagerUtil;
    import jakarta.persistence.EntityManager;
    import br.unipar.programacaoweb.models.Pedido;

    import java.util.List;

    public class PedidoDAO {
        EntityManager em = EntityManagerUtil.getEm();

        public boolean salvar(Pedido pedido) {
            EntityManager em = EntityManagerUtil.getEm();
            try {
                em.getTransaction().begin();
                em.persist(pedido);
                em.getTransaction().commit();
                return true;
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                return false;
            } finally {
                em.close();
            }
        }

        public boolean atualizar(Pedido pedido) {
            EntityManager em = EntityManagerUtil.getEm(); // Cria aqui
            try {
                em.getTransaction().begin();
                em.merge(pedido);
                em.getTransaction().commit();
                return true;
            } catch (Exception e) {
                em.getTransaction().rollback();
                e.printStackTrace();
                return false;
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
            EntityManager em = EntityManagerUtil.getEm(); // CRIA aqui dentro
            try {
                return em.createQuery(
                                "SELECT p FROM Pedido p LEFT JOIN FETCH p.itensPedido WHERE p.id = :id", Pedido.class)
                        .setParameter("id", id)
                        .getSingleResult();
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
            EntityManager em = EntityManagerUtil.getEm();
            try {
                List<Pedido> pedidos = em.createQuery("SELECT p FROM Pedido p WHERE p.cliente.id = :id", Pedido.class)
                        .setParameter("id", clienteId)
                        .getResultList();

                for (Pedido p : pedidos) {
                    p.getItensPedido().size();
                }

                return pedidos;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            } finally {
                em.close();
            }
        }

    }
