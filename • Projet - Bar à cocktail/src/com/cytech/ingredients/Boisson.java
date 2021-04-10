package com.cytech.ingredients;

import java.util.Objects;

public class Boisson extends BoissonMere {

    private double prixB = 10; // prix d'achat

    // Contructeur
    public Boisson(String nom, double contenance, String couleur, double prix) {
        super(nom, contenance, couleur);
        this.prixB = prix;
    }

    // Contructeur 2, si il met pas le prix (à supprimer en vrai)
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
        return Objects.hash(prixB);
    }

    @Override
    public String toString() {
        return "Boisson '" + this.getNom() + '\'' +
                ", (" + this.getContenance() + "ml) ---> " + this.getPrix() +
                "€ ";
    }

    // retouner prix de la boisson
    public double getPrix() { // le prix change en fct du contenu
        return this.prixB;
    }

    //


}
