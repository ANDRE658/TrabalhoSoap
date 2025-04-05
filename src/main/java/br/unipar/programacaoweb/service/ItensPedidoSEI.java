package br.unipar.programacaoweb.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService
public interface ItensPedidoSEI {
    @WebMethod
    String boasVindas(@WebParam(name = "nome") String nome);

    @WebMethod
    String salvarNovoItem(@WebParam(name = "tamanho") String tamanho,
                          @WebParam(name = "quantidade") Integer quantidade,
                          @WebParam(name = "valorUnitario") float valorUnitario,
                          @WebParam(name = "idPizza") Integer idPizza,
                          @WebParam(name = "idBorda") Integer idBorda,
                          @WebParam(name = "idPedido") Integer idPedido);

    @WebMethod
    String buscarTodos();

    @WebMethod
    String excluirItem(@WebParam(name = "id") Integer id);

    @WebMethod
    String editarItem(@WebParam(name = "id") Integer id,
                      @WebParam(name = "idPedido") Integer idPedido,
                      @WebParam(name = "idPizza") Integer idPizza,
                      @WebParam(name = "idBorda") Integer idBorda,
                      @WebParam(name = "quantidade") Integer quantidade);
}
