package com.cytech.ingredients;

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
    public String toString() {
        return "Boisson '" + this.getNom() + '\'' +
                ", (" + this.getContenance() + "ml) ---> " + this.getPrix() +
                "€ ";
    }

    public double getPrix() { // le prix change en fct du contenu
        return this.prixAchat;
    }


}
