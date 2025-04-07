package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.daos.ClienteDAO;
import br.unipar.programacaoweb.models.Cliente;
import jakarta.jws.WebService;

import java.util.List;

@WebService(endpointInterface = "br.unipar.programacaoweb.service.ClienteSEI")
public class ClienteSIB implements ClienteSEI {
    @Override
    public String boasVindas(String nome){
        return "Bem vindo " + nome;
    }
    @Override
    public String salvarNovoCliente(String nome,
                                    String cpf,
                                    String telefone,
                                    String endereco,
                                    String dataNascimento){
        if(clienteExiste(cpf)){
            return "Cliente já cadastrado!";
        }
        if(nome == null || nome.isEmpty()){
            return "Nome inválido!";
        }
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setTelefone(telefone);
        cliente.setEndereco(endereco);
        cliente.setDataNascimento(dataNascimento);
        clienteDAO.salvar(cliente);
        return "Cliente salvo com sucesso!";
    }

    public boolean clienteExiste(String nome){
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.buscarPorCpf(nome) != null){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public List<Cliente> buscarTodos(){
        ClienteDAO clienteDAO = new ClienteDAO();
        return clienteDAO.buscarTodos();
    }

    @Override
    public String excluirCliente(Integer id){
        ClienteDAO clienteDAO = new ClienteDAO();
        if(clienteDAO.excluir(id)){
            return "Cliente excluído com sucesso!";
        }else{
            return "Cliente não encontrado!";
        }
    }
    @Override
    public String editarCliente(Cliente cliente){
        ClienteDAO clienteDAO = new ClienteDAO();
        Cliente clienteEditado = clienteDAO.buscarPorId(cliente.getId());

        if(clienteEditado != null){
            clienteEditado.setNome(cliente.getNome());
            clienteEditado.setCpf(cliente.getCpf());
            clienteEditado.setTelefone(cliente.getTelefone());
            clienteEditado.setEndereco(cliente.getEndereco());
            clienteEditado.setDataNascimento(cliente.getDataNascimento());

            ClienteDAO daoEditar = new ClienteDAO();
            daoEditar.atualizar(clienteEditado);
        }
        return "Cliente editado com sucesso!";
    }
}
