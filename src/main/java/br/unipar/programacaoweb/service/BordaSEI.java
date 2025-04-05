package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.models.Borda;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface BordaSEI {

    @WebMethod
    String boasVindas(@WebParam(name = "nome") String nome);

    @WebMethod
    String salvarNovaBorda(
            @WebParam(name = "Borda") String sabor);

    @WebMethod
    List<Borda> buscarTodas();

    @WebMethod
    String excluirBorda(@WebParam(name = "id") Integer id);

    @WebMethod
    String editarBorda(Borda borda);

}
