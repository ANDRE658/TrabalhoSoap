package br.unipar.programacaoweb;

import br.unipar.programacaoweb.models.ItensPedido;
import br.unipar.programacaoweb.service.*;
import jakarta.xml.ws.Endpoint;

public class ServicePizzaria {
    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/pizzaria/borda", new BordaSIB());
        System.out.println("Serviço de Borda rodando em: http://localhost:8080/pizzaria/borda");

        Endpoint.publish("http://localhost:8080/pizzaria/cliente", new ClienteSIB());
        System.out.println("Serviço de Cliente rodando em: http://localhost:8080/pizzaria/cliente");


        Endpoint.publish("http://localhost:8080/pizzaria/pedido", new PedidoSIB());
        System.out.println("Serviço de Pedido rodando em: http://localhost:8080/pizzaria/pedido");

        Endpoint.publish("http://localhost:8080/pizzaria/pizza", new PizzaSIB());
        System.out.println("Serviço de Pizza rodando em: http://localhost:8080/pizzaria/pizza");
    }
}

