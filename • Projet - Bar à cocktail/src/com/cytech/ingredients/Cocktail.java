package com.cytech.ingredients;
import java.util.Arrays;


public class Cocktail extends BoissonMere{


    private Boisson[] listeComposantsBoisson; // changer ça en MAP


    public Cocktail(String nom, double contenance, String couleur,Boisson[] listeComposants) {
        super(nom, contenance, couleur);
        this.listeComposantsBoisson = listeComposants;
    }

    @Override
    public String toString() {
        String res = "Cocktail '" + this.getNom() + '\'' +
                ", (" + this.getContenance() + "ml) ---> " + this.getPrix() +
                "€ ";
        res += " (";
        for(Boisson b : this.listeComposantsBoisson)
            res += "'" + b.getNom() +"',";
        res = res.substring(0,res.length()-1);
        res += ")";
        return res;
    }

    public double getPrix() {
        double res = 0;
        for(Boisson b : this.listeComposantsBoisson)
            res += b.getPrix();
        res *= 1.1;
        res = (double) Math.round(res * 100) / 100;
        return res;
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
