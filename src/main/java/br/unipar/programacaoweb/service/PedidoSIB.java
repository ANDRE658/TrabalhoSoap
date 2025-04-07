package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.daos.*;
import br.unipar.programacaoweb.models.*;
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
    public String salvarNovoPedido(String cpf,
                                   String sabor,
                                   String bordas,
                                   String tamanho,
                                   int quantidade,
                                   String observacao) {


        if(cpf == null || cpf.isEmpty()) {
            return "CPF inválido!";
        }
        if(clienteExiste(cpf) == false){
            return "Cliente não cadastrado!";
        }
        if (sabor == null || sabor.isEmpty()) {
            return "Sabor inválido!";
        }
        if (bordas == null || bordas.isEmpty()) {
            return "Borda inválida!";
        }
        if (tamanho == null || tamanho.isEmpty() || (!tamanho.equals("P") && !tamanho.equals("M") && !tamanho.equals("G"))) {
            return "Tamanho inválido!\n" +
                    "Para o tamanho da pizza, utilize P, M ou G.";
        }
        if (quantidade <= 0) {
            return "Quantidade inválida!";
        }


        ItensPedido novoItem = new ItensPedido();
        novoItem.setTamanho(tamanho);
        novoItem.setQuantidade(quantidade);
        novoItem.setValorUnitario(valorUnitario(tamanho));
        novoItem.setValorTotal(quantidade * valorUnitario(tamanho));

        Pedido novoPedido = new Pedido();
        novoPedido.setValorTotal(novoItem.getValorTotal());
        novoPedido.setObservacao(observacao);
        novoPedido.setStatus("Recebido");



        PizzaDAO pizzaDAO = new PizzaDAO();
        BordaDAO bordaDAO = new BordaDAO();
        PedidoDAO pedidoDAO = new PedidoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();


        Pizza pizza = pizzaDAO.buscarPorSabor(sabor);
        Borda borda = bordaDAO.buscarPorSabor(bordas);
        Cliente cliente = clienteDAO.buscarPorCpf(cpf);

        if (pizza == null || borda == null || cliente == null) {
            return "Erro ao salvar o item: Pizza, Borda ou Cliente não encontrado!";
        }

        novoItem.setPizza(pizza);
        novoItem.setBorda(borda);
        novoItem.setPedido(novoPedido);

        List<ItensPedido> itens = new ArrayList<>();
        itens.add(novoItem);
        novoPedido.setItensPedido(itens);
        novoPedido.setCliente(cliente);

        boolean sucesso = pedidoDAO.salvar(novoPedido);

        if (sucesso) {
            iniciarAtualizacaoAutomaticaStatus(novoPedido.getId());
            return "Pedido salvo com sucesso!";
        } else {
            return "Erro ao salvar o pedido no banco de dados.";
        }
    }

    @Override
    public String editarPedido(int idPedido,
                               String sabor,
                               String bordaS,
                               String tamanho,
                               int quantidade,
                               String observacao) {

        PedidoDAO pedidoDAO = new PedidoDAO();
        PizzaDAO pizzaDAO = new PizzaDAO();
        BordaDAO bordaDAO = new BordaDAO();

        Pedido pedidoExistente = pedidoDAO.buscarPorId(idPedido);
        if (pedidoExistente == null) {
            return "Pedido não encontrado com ID: " + idPedido;
        }

        Pizza pizza = pizzaDAO.buscarPorSabor(sabor);
        Borda borda = bordaDAO.buscarPorSabor(bordaS);

        if (pizza == null || borda == null) {
            return "Erro: Pizza ou Borda não encontrada!";
        }

        float valorUnitario = valorUnitario(tamanho);
        float valorTotal = valorUnitario * quantidade;

        pedidoExistente.setObservacao(observacao);
        pedidoExistente.setValorTotal(valorTotal);

        List<ItensPedido> itens = pedidoExistente.getItensPedido();
        if (itens != null && !itens.isEmpty()) {

            ItensPedido item = itens.get(0);
            item.setTamanho(tamanho);
            item.setQuantidade(quantidade);
            item.setValorUnitario(valorUnitario);
            item.setValorTotal(valorTotal);
            item.setPizza(pizza);
            item.setBorda(borda);
        }

        try {
            pedidoDAO.atualizar(pedidoExistente);
            return "Pedido editado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao editar o pedido: " + e.getMessage();
        }
    }

    public float valorUnitario(String tamanho){
        float valorUnitario = 0;
        if(tamanho.equals("P")){
            valorUnitario = 20;
        }else if(tamanho.equals("M")){
            valorUnitario = 30;
        }else if(tamanho.equals("G")){
            valorUnitario = 40;
        }
        return valorUnitario;
    }

    @Override
    public String excluirPedido(Integer id) {
        PedidoDAO pedidoDAO = new PedidoDAO();

        try {
            boolean sucesso = pedidoDAO.excluir(id);
            if (sucesso) {
                return "Pedido com ID " + id + " excluído com sucesso.";
            } else {
                return "Não foi possível excluir o pedido com ID " + id + ". Verifique se ele existe.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao excluir o pedido: " + e.getMessage();
        }
    }

    @Override
    public String buscarPedido(Integer id) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.buscarPorId(id);

        if (pedido == null) {
            return "Pedido não encontrado com o ID: " + id;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID do Pedido: ").append(pedido.getId()).append("\n");
        sb.append("Valor Total: R$ ").append(String.format("%.2f", pedido.getValorTotal())).append("\n");
        sb.append("Status: ").append(pedido.getStatus()).append("\n");
        sb.append("Observação: ").append(pedido.getObservacao()).append("\n");

        sb.append("Itens:\n");
        for (ItensPedido item : pedido.getItensPedido()) {
            sb.append("Pizza: ").append(item.getPizza().getSabor()).append("\n");
            sb.append("Borda: ").append(item.getBorda().getSabor()).append("\n");
            sb.append("Tamanho: ").append(item.getTamanho()).append("\n");
            sb.append("Quantidade: ").append(item.getQuantidade()).append("\n");
            sb.append("Valor Unitário: R$ ").append(String.format("%.2f", item.getValorUnitario())).append("\n");
            sb.append("Valor Total do Item: R$ ").append(String.format("%.2f", item.getValorTotal())).append("\n");
            sb.append("-----------------------------\n");
        }

        return sb.toString();
    }

    @Override
    public List<Pedido> buscarTodosPedidos() {
        PedidoDAO pedidoDAO = new PedidoDAO();
        return pedidoDAO.buscarTodos();
    }

    @Override
    public String buscarPedidosPorCliente(String cpf) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Pedido> pedidos = pedidoDAO.buscarPorClienteId(clienteDAO.buscarPorCpf(cpf).getId());

        if (pedidos == null || pedidos.isEmpty()) {
            return "Nenhum pedido encontrado para o CPF: " + cpf;
        }

        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : pedidos) {
            sb.append("ID do Pedido: ").append(pedido.getId()).append("\n");
            sb.append("Valor Total: R$ ").append(String.format("%.2f", pedido.getValorTotal())).append("\n");
            sb.append("Status: ").append(pedido.getStatus()).append("\n");
            sb.append("Observação: ").append(pedido.getObservacao()).append("\n");

            sb.append("Itens:\n");
            for (ItensPedido item : pedido.getItensPedido()) {
                sb.append("Pizza: ").append(item.getPizza().getSabor()).append("\n");
                sb.append("Borda: ").append(item.getBorda().getSabor()).append("\n");
                sb.append("Tamanho: ").append(item.getTamanho()).append("\n");
                sb.append("Quantidade: ").append(item.getQuantidade()).append("\n");
                sb.append("Valor Unitário: R$ ").append(String.format("%.2f", item.getValorUnitario())).append("\n");
                sb.append("Valor Total do Item: R$ ").append(String.format("%.2f", item.getValorTotal())).append("\n");
                sb.append("-----------------------------\n");
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    @Override
    public String acompanharPedido(Integer id) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        Pedido pedido = pedidoDAO.buscarPorId(id);

        if (pedido == null) {
            return "Pedido não encontrado com o ID: " + id;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("ID do Pedido: ").append(pedido.getId()).append("\n");
        sb.append("Status: ").append(pedido.getStatus()).append("\n");

        return sb.toString();
    }

    public void iniciarAtualizacaoAutomaticaStatus(int idPedido) {
        new Thread(() -> {
            try {
                PedidoDAO pedidoDAO = new PedidoDAO();
                String[] statusList = {
                        "Recebido",
                        "Em Preparo",
                        "Pronto para Retirada",
                        "Saiu para Entrega",
                        "Entregue"
                };

                int index = 0;
                while (index < statusList.length) {
                    Pedido pedido = pedidoDAO.buscarPorId(idPedido);
                    if (pedido == null) {
                        System.out.println("Pedido com ID " + idPedido + " não encontrado.");
                        break;
                    }

                    pedido.setStatus(statusList[index]);
                    pedidoDAO.atualizar(pedido);

                    System.out.println("Status atualizado para: " + statusList[index]);
                    index++;

                    Thread.sleep(60000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public boolean clienteExiste(String cpf){
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.buscarPorCpf(cpf) != null){
            return true;
        }else{
            return false;
        }
    }


}
