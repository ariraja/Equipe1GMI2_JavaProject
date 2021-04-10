package com.cytech.ingredients;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public abstract class Boisson extends BoissonMere {

    private double prixMl = 10;


    public Boisson(String nom, String couleur, double prixml, double contenance) {
        super(nom, contenance, couleur);
        this.prixMl = prixml;
    }
    // si la contenance n'est pas précisé
    public Boisson(String nom, String couleur, double prixml) {
        super(nom, 1000, couleur);
        this.prixMl = prixml;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Boisson)) return false;
        Boisson boisson = (Boisson) o;
        return Objects.equals(boisson.getNom(),this.getNom())
                && Objects.equals(boisson.getCouleur(),this.getCouleur());
    }

    @Override
    public int hashCode() {
        return Objects.hash(prixMl);
    }

    @Override
    public String toString() {
        return "Boisson '" + this.getNom() + '\'' +
                " | *CONTENU* : " + this.getContenance() + "ml | *PRIX* : " + this.getPrix() +
                "€ ";
    }

    public double getPrix() { // le prix change en fct du contenu
        return this.prixMl*this.getContenance();
    }

    public void Vider(double ml) {
        this.contenance -= ml;
        if (this.contenance <=0)
            System.out.println("Oh c'est vide");

    }



}
