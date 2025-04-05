package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.models.Pizza;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;
@WebService
public interface PizzaSEI {
    @WebMethod
    String boasVindas(@WebParam(name= "nome") String nome);

    @WebMethod
    String salvarNovaPizza(
            @WebParam(name = "sabor") String sabor);

    @WebMethod
    List<Pizza> buscarTodas();

    @WebMethod
    String excluirPizza(@WebParam(name = "id") Integer id);

    @WebMethod
    String editarPizza(Pizza pizza);
}
