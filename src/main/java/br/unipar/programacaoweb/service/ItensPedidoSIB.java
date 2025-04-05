package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.daos.BordaDAO;
import br.unipar.programacaoweb.daos.ItensPedidoDAO;
import br.unipar.programacaoweb.daos.PedidoDAO;
import br.unipar.programacaoweb.daos.PizzaDAO;
import br.unipar.programacaoweb.models.Borda;
import br.unipar.programacaoweb.models.ItensPedido;
import br.unipar.programacaoweb.models.Pedido;
import br.unipar.programacaoweb.models.Pizza;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(endpointInterface = "br.unipar.programacaoweb.service.ItensPedidoSEI")
public class ItensPedidoSIB implements ItensPedidoSEI{

    public String boasVindas(String nome) {
        return "Bem-vindo ao sistema de pedidos, " + nome + "!";
    }

    @Override
    public String salvarNovoItem(String tamanho,
                                 Integer quantidade,
                                 float valorUnitario,
                                 Integer idPizza,
                                 Integer idBorda,
                                 Integer idPedido) {
        ItensPedido novoItem = new ItensPedido();
        novoItem.setTamanho(tamanho);
        novoItem.setQuantidade(quantidade);
        novoItem.setValorUnitario(valorUnitario);
        novoItem.setValorTotal(quantidade * valorUnitario);

        PizzaDAO pizzaDAO = new PizzaDAO();
        BordaDAO bordaDAO = new BordaDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        Pizza pizza = pizzaDAO.buscarPorId(idPizza);
        Borda borda = bordaDAO.buscarPorId(idBorda);
        Pedido pedido = pedidoDAO.buscarPorId(idPedido);

        if(pizza == null || borda == null || pedido == null){
            return "Erro ao salvar o item: Pizza, Borda ou Pedido não encontrado!";
        }

        novoItem.setPizza(pizza);
        novoItem.setBorda(borda);
        novoItem.setPedido(pedido);

        ItensPedidoDAO itensPedidoDAO = new ItensPedidoDAO();

        try {
            itensPedidoDAO.salvar(novoItem);
            return "Item salvo com sucesso!";
        } catch (Exception e) {
            return "Erro ao salvar o item: " + e.getMessage();
        }
    }


    @Override
    public String buscarTodos() {
        ItensPedidoDAO itensPedidoDAO = new ItensPedidoDAO();
        try {
            return itensPedidoDAO.buscarTodos().toString();
        } catch (Exception e) {
            return "Erro ao buscar os itens do pedido: " + e.getMessage();
        }
    }

    @Override
    public String excluirItem(Integer id) {
        ItensPedidoDAO itensPedidoDAO = new ItensPedidoDAO();
        if (itensPedidoDAO.excluir(id)) {
            return "Item excluído com sucesso!";
        } else {
            return "Item não encontrado!";
        }
    }

    @Override
    public String editarItem(Integer id, Integer idPedido, Integer idPizza, Integer idBorda, Integer quantidade) {
        ItensPedidoDAO itensPedidoDAO = new ItensPedidoDAO();
        ItensPedido itemEditado = itensPedidoDAO.buscarPorId(id);

        if (itemEditado != null) {
            itemEditado.setQuantidade(quantidade);
            itemEditado.setValorTotal(quantidade * itemEditado.getValorUnitario());

            PizzaDAO pizzaDAO = new PizzaDAO();
            BordaDAO bordaDAO = new BordaDAO();
            PedidoDAO pedidoDAO = new PedidoDAO();

            Pizza pizza = pizzaDAO.buscarPorId(idPizza);
            Borda borda = bordaDAO.buscarPorId(idBorda);
            Pedido pedido = pedidoDAO.buscarPorId(idPedido);

            if (pizza == null || borda == null || pedido == null) {
                return "Erro ao editar o item: Pizza, Borda ou Pedido não encontrado!";
            }

            itemEditado.setPizza(pizza);
            itemEditado.setBorda(borda);
            itemEditado.setPedido(pedido);

            try {
                itensPedidoDAO.atualizar(itemEditado);
                return "Item editado com sucesso!";
            } catch (Exception e) {
                e.printStackTrace(); // pode trocar por logger, se estiver usando
                return "Erro ao atualizar o item: " + e.getMessage();
            }

        } else {
            return "Item não encontrado!";
        }
    }
}
