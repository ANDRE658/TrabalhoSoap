package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.daos.BordaDAO;
import br.unipar.programacaoweb.models.Borda;
import jakarta.jws.WebService;

import java.util.List;

@WebService(endpointInterface = "br.unipar.programacaoweb.service.BordaSEI")
public class BordaSIB implements BordaSEI {


    @Override
    public String boasVindas(String nome) {
        return "Seja bem-vindo(a) " + nome + " ao sistema de bordas!";
    }

    @Override
    public String salvarNovaBorda(String sabor) {
        try{
            Borda novaBorda = new Borda();
            novaBorda.setSabor(sabor);
            BordaDAO bordaDAO = new BordaDAO();
            bordaDAO.salvar(novaBorda);
            return "Borda salva com sucesso!";
        }catch (Exception e){
            return "Erro ao salvar a borda: " + e.getMessage();
        }
    }

    @Override
    public List<Borda> buscarTodas(){
        BordaDAO bordaDAO = new BordaDAO();
        return bordaDAO.buscarTodos();
    }

    @Override
    public String excluirBorda(Integer id) {
        BordaDAO dao = new BordaDAO();
        if(dao.excluir(id)){
            return "Borda excluída com sucesso!";
        }else{
            return "Borda não encontrada!";
        }
    }

    @Override
    public String editarBorda(Borda borda) {
        BordaDAO bordaDAO = new BordaDAO();
        Borda bordaEditada = bordaDAO.buscarPorId(borda.getId());

        if (bordaEditada != null) {
            bordaEditada.setSabor(borda.getSabor());
            bordaDAO.atualizar(bordaEditada);

            BordaDAO dao = new BordaDAO();
            dao.atualizar(bordaEditada);
            return "Borda editada com sucesso!";
        } else {
            return "Borda não encontrada!";
        }
    }
}
