package com.cytech.ingredients;

public class BoissonAlcoolisee extends Boisson {

    private double degreAlcool;

    public BoissonAlcoolisee(String nom, double contenance, String couleur, double prix, double alcool) {
        super(nom, contenance, couleur, prix);
        this.degreAlcool = alcool;
    }

    public double getDegreAlcool() {
        return degreAlcool;
    }

}
