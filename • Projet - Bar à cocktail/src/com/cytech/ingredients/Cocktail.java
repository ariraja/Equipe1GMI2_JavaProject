package com.cytech.ingredients;
import java.util.Arrays;

public class Cocktail extends BoissonMere{


    private Boisson[] listeComposantsBoisson;


    public Cocktail(String nom, double contenance, String couleur,Boisson[] listeComposants) {
        super(nom, contenance, couleur);
        this.listeComposantsBoisson = listeComposants;
    }

    @Override
    public String toString() {
        return "Boisson{" +
                "nom='" + this.getNom() + '\'' +
                ", contenance=" + this.getContenance() +
                ", prix=" + this.getPrix() +
                ", Compo=" + Arrays.toString(this.listeComposantsBoisson) +
                '}';
    }

    public double getPrix() {
        double total = 0;
        for(Boisson b : this.listeComposantsBoisson)
            total += b.getPrix();
        return total*1.1;
    }
    public Boisson[] getComposants() {
        return this.listeComposantsBoisson;
    }

    public double getDegreAlcool() {
        return 0.0;
    }
    public double getDegreSucre() {
        return 0.0;
    }


}
