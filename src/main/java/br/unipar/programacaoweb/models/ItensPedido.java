package br.unipar.programacaoweb.models;

import jakarta.persistence.*;
import br.unipar.programacaoweb.models.Pizza;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ItensPedido {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    private String tamanho;
    private int quantidade;
    private float valorUnitario;
    private float valorTotal;
    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private Pizza pizza;

    @ManyToOne
    @JoinColumn(name = "borda_id")
    private Borda borda;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
