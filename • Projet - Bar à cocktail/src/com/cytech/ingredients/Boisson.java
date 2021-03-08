package com.cytech.ingredients;

import java.util.Objects;

public class Boisson extends BoissonMere {

    private double prixAchat = 10;

    public Boisson(String nom, double contenance, String couleur, double prix) {
        super(nom, contenance, couleur);
        this.prixAchat = prix;
    }


    public Boisson(String nom, double contenance, String couleur) {
        super(nom, contenance, couleur);

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
        return Objects.hash(prixAchat);
    }

    @Override
    public String toString() {
        return "Boisson '" + this.getNom() + '\'' +
                ", (" + this.getContenance() + "ml) ---> " + this.getPrix() +
                "€ ";
    }

    public double getPrix() { // le prix change en fct du contenu
        return this.prixAchat;
    }


}
