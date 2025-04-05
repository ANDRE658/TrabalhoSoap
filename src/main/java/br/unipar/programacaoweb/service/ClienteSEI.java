package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.models.Cliente;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface ClienteSEI {

    @WebMethod
    String boasVindas(@WebParam(name = "nome") String nome);

    @WebMethod
    String salvarNovoCliente(
            @WebParam(name = "nome") String nome,
            @WebParam(name = "cpf") String cpf,
            @WebParam(name = "telefone") String telefone,
            @WebParam(name = "endereco") String endereco,
            @WebParam(name = "DataNascimento") String dataNascimento);

    @WebMethod
    List<Cliente> buscarTodos();

    @WebMethod
    String excluirCliente(@WebParam(name = "id") Integer id);

    @WebMethod
    String editarCliente(Cliente cliente);


}
