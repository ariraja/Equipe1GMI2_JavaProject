package com.cytech.ingredients;

public class Verre {


    private double taille = 25; // en CL
    private double prix; // en CL
    private Boisson boisson;


    public double getTaille() {
        return taille;
    }

    public Boisson getBoisson() {
        return boisson;
    }

    public Verre(Boisson boisson, double prix) {
        this.boisson = boisson;
        this.prix = prix;
    }

    public double getPrix() {
        return prix;
    }
}
