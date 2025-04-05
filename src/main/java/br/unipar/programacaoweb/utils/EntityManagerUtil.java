package br.unipar.programacaoweb.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerUtil {
    private static EntityManagerFactory emf;
    public static EntityManager em;

    public EntityManagerUtil() {}

    public static EntityManager getEm() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("servicePizzaria");
            System.out.println("Criando EntityManagerFactory");
        }
        return emf.createEntityManager(); // CORREÇÃO AQUI
    }

    public static EntityManager geTEm() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("servicePizzaria");
            System.out.println("Criando EntityManagerFactory");
        }
        if (em == null || !em.isOpen()) {
            em = emf.createEntityManager();
            System.out.println("Criando EntityManager");
        }
        return em;
    }

}
