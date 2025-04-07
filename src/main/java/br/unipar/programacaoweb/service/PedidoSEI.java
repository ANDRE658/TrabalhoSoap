package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.models.Pedido;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface PedidoSEI {
    @WebMethod
    String boasVindas(@WebParam(name = "nome") String nome);

    @WebMethod
    String salvarNovoPedido(@WebParam(name = "cpf") String cpf,
                            @WebParam(name = "sabor") String sabor,
                            @WebParam(name = "borda") String borda,
                            @WebParam(name = "tamanho") String tamanho,
                            @WebParam(name = "quantidade") int quantidade,
                            @WebParam(name = "observacao") String observacao);

    @WebMethod
    String excluirPedido(@WebParam(name = "id") Integer id);

    @WebMethod
    String editarPedido(@WebParam(name = "IdPedido") int idPedido,
                        @WebParam(name = "sabor") String sabor,
                        @WebParam(name = "borda") String borda,
                        @WebParam(name = "tamanho") String tamanho,
                        @WebParam(name = "quantidade") int quantidade,
                        @WebParam(name = "observacao") String observacao);


    @WebMethod
    String buscarPedido(@WebParam(name = "id") Integer id);

    @WebMethod
    List<Pedido> buscarTodosPedidos();

    @WebMethod
    String buscarPedidosPorCliente(@WebParam(name = "cpf") String cpf);

    @WebMethod
    String acompanharPedido(@WebParam(name = "id") Integer id);

}
