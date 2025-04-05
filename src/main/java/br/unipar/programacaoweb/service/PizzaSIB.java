package br.unipar.programacaoweb.service;

import br.unipar.programacaoweb.daos.PizzaDAO;
import br.unipar.programacaoweb.models.Pizza;
import jakarta.jws.WebService;

import java.util.List;

@WebService(endpointInterface = "br.unipar.programacaoweb.service.PizzaSEI")
public class PizzaSIB implements PizzaSEI {


    @Override
    public String boasVindas(String nome) {
        return "Seja bem-vindo(a) " + nome;
    }

    @Override
    public String salvarNovaPizza(String sabor) {
        if(pizzaExiste(sabor)){
            return "Pizza já existe!";
        }
        PizzaDAO pizzaDAO = new PizzaDAO();
        Pizza pizza = new Pizza();
        pizza.setSabor(sabor);
        pizzaDAO.salvar(pizza);
        return "Pizza salva com sucesso!";

    }
    public boolean pizzaExiste(String sabor) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        if(pizzaDAO.buscarPorSabor(sabor) != null){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<Pizza> buscarTodas() {
        PizzaDAO pizzaDAO = new PizzaDAO();
        return pizzaDAO.buscarTodos();
    }

    @Override
    public String excluirPizza(Integer id) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        if (pizzaDAO.excluir(id)) {
            return "Pizza excluída com sucesso!";
        } else {
            return "Pizza não encontrada!";
        }
    }

    @Override
    public String editarPizza(Pizza pizza) {
        PizzaDAO pizzaDAO = new PizzaDAO();
        Pizza pizzaEditada = pizzaDAO.buscarPorId(pizza.getId());


        if(pizzaEditada != null){
            pizzaEditada.setSabor(pizza.getSabor());
            pizzaDAO.atualizar(pizzaEditada);

            PizzaDAO daoEditar = new PizzaDAO();
            daoEditar.atualizar(pizzaEditada);
            return "Pizza editada com sucesso!";
        }else {
            return "Pizza não encontrada!";
        }

    }


}
