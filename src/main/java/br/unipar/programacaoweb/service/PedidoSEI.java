package br.unipar.programacaoweb.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface PedidoSEI {
    @WebMethod
    String boasVindas(@WebParam(name = "nome") String nome);

    @WebMethod
    String salvarNovoPedido(@WebParam(name = "idCliente") Integer idCliente,
                            @WebParam(name = "idItensPedido") Integer idItensPedido,
                            @WebParam(name = "valorTotal") float valorTotal,
                            @WebParam(name = "observacao") String observacao,
                            @WebParam(name = "status") String status);


    @WebMethod
    String listarPedidos(@WebParam(name = "idCliente") Integer idCliente);

    @WebMethod
    String excluirPedido(@WebParam(name = "id") Integer id);

    @WebMethod
    String editarPedido(@WebParam(name = "id") Integer id,
                         @WebParam(name = "valorTotal") float valorTotal,
                         @WebParam(name = "observacao") String observacao,
                         @WebParam(name = "status") String status);
    @WebMethod
    String buscarPedido(@WebParam(name = "id") Integer id);

    @WebMethod
    String buscarTodosPedidos();


}
