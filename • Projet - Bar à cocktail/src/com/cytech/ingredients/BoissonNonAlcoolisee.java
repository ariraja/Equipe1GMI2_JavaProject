package com.cytech.ingredients;

public class BoissonNonAlcoolisee extends Boisson {


    private double degreSucre;

    public BoissonNonAlcoolisee(String nom, double contenance, String couleur, double prix, double sucre) {
        super(nom, contenance, couleur, prix);
        this.degreSucre = sucre;
    }

    public double getDegreSucre() {
        return degreSucre;
    }/*
    public String toString() {
        return "Boisson{" +
                "nom='" + this.getNom() + '\'' +
                ", contenance=" + this.getContenance() +
                ", prix=" + this.getPrix() +
                ", sucre=" + this.getDegreSucre() +
                '}';
    }*/

}
