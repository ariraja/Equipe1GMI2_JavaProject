package com.cytech.ingredients;

public class Boisson extends BoissonMere {

    private double prix = 10;

    public Boisson(String nom, double contenance, String couleur, double prix) {
        super(nom, contenance, couleur);
        this.prix = prix;
    }


    public Boisson(String nom, double contenance, String couleur) {
        super(nom, contenance, couleur);

    }

    @Override
    public String toString() {
        return "| Boisson '" + this.getNom() + '\'' +
                ", (" + this.getContenance() + "ml) ---> " + this.getPrix() +
                "€ ";
    }

    public double getPrix() {
        return this.prix;
    }


}
