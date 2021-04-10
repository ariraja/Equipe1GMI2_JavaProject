package com.cytech.ingredients;

public class BoissonNonAlcoolisee extends Boisson {

    private double degreSucre;

    public BoissonNonAlcoolisee(String nom, String couleur, double prix,  double contenance,double sucre) {
        super(nom, couleur, prix, contenance);
        this.degreSucre = sucre;
    }

    public BoissonNonAlcoolisee(String nom, String couleur, double prix, double sucre) {
        super(nom, couleur, prix);
        this.degreSucre = sucre;
    }

    public double getDegreSucre() {
        return degreSucre;
    }
    public String toString() {
        return "Boisson '" + this.getNom() + '\'' +
                " | *DegSucre* : " + this.getDegreSucre() +
                "° | *PRIX* : " + this.getPrix() +

                "€ ";
    }

}
