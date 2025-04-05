package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.daos.ClienteDAO;
import br.unipar.programacaoweb.daos.ItensPedidoDAO;
import br.unipar.programacaoweb.daos.PedidoDAO;
import br.unipar.programacaoweb.models.Cliente;
import br.unipar.programacaoweb.models.ItensPedido;
import br.unipar.programacaoweb.models.Pedido;
import jakarta.jws.WebService;

import java.util.ArrayList;
import java.util.List;
@WebService(endpointInterface = "br.unipar.programacaoweb.service.PedidoSEI")
public class PedidoSIB implements PedidoSEI{
    @Override
    public String boasVindas(String nome) {
        return "Boas vindas " + nome + " ao sistema de pedidos!";
    }

    @Override
    public String salvarNovoPedido(Integer idCliente,
                                   Integer idItensPedido,
                                   float valorTotal,
                                   String observacao,
                                   String status) {
        Pedido pedido = new Pedido();
        pedido.setValorTotal(valorTotal);
        pedido.setObservacao(observacao);
        pedido.setStatus(status);

        ClienteDAO clienteDAO = new ClienteDAO();
        ItensPedidoDAO itensPedidoDAO = new ItensPedidoDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();

        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        ItensPedido item = itensPedidoDAO.buscarPorId(idItensPedido);

        if (cliente == null || item == null) {
            return "Erro ao salvar o pedido: Cliente ou ItensPedido não encontrado!";
        }

        pedido.setCliente(cliente);

        item.setPedido(pedido);

        List<ItensPedido> listaItens = new ArrayList<>();
        listaItens.add(item);
        pedido.setItensPedido(listaItens);

        try {
            pedidoDAO.salvar(pedido);
            return "Pedido salvo com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao salvar o pedido: " + e.getMessage();
        }

    }


    @Override
    public String listarPedidos(Integer idCliente) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        List<Pedido> pedidos = pedidoDAO.buscarPorClienteId(idCliente);
        StringBuilder sb = new StringBuilder();

        if (pedidos.isEmpty()) {
            return "Nenhum pedido encontrado para o cliente com ID: " + idCliente;
        }

        for (Pedido pedido : pedidos) {
            sb.append("ID do Pedido: ").append(pedido.getId()).append("\n");
            sb.append("Valor Total: ").append(pedido.getValorTotal()).append("\n");
            sb.append("Observação: ").append(pedido.getObservacao()).append("\n");
            sb.append("Status: ").append(pedido.getStatus()).append("\n");
            sb.append("-----------------------------\n");
        }
        return sb.toString();
    }

    @Override
    public String excluirPedido(Integer id) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        try {
            if (pedidoDAO.excluir(id)) {
                return "Pedido excluído com sucesso!";
            } else {
                return "Erro ao excluir o pedido: Pedido não encontrado!";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir o pedido: " + e.getMessage();
        }
    }

    @Override
    public String editarPedido(Integer id, float valorTotal, String observacao, String status) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedidoEditado = pedidoDAO.buscarPorId(id);

        if(pedidoEditado != null) {
            pedidoEditado.setValorTotal(valorTotal);
            pedidoEditado.setObservacao(observacao);
            pedidoEditado.setStatus(status);

            try {
                pedidoDAO.atualizar(pedidoEditado);
                return "Pedido editado com sucesso!";
            } catch (Exception e) {
                e.printStackTrace();
                return "Erro ao editar o pedido: " + e.getMessage();
            }
        }

        return "";
    }

    @Override
    public String buscarPedido(Integer id) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.buscarPorId(id);
        if (pedido != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID do Pedido: ").append(pedido.getId()).append("\n");
            sb.append("Valor Total: ").append(pedido.getValorTotal()).append("\n");
            sb.append("Observação: ").append(pedido.getObservacao()).append("\n");
            sb.append("Status: ").append(pedido.getStatus()).append("\n");
            return sb.toString();
        }
        return "Pedido não encontrado!";
    }

    @Override
    public String buscarTodosPedidos() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        return pedidoDAO.buscarTodos().toString();
    }
}
