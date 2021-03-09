package com.cytech.ingredients;

import java.util.*;


public class Cocktail extends BoissonMere{

    private boolean dispo = false;
    private List<Boisson> listeComposantsBoisson; // changer ça en MAP


    public Cocktail(String nom, double contenance, List<Boisson> listeComposants) {

        super(nom, contenance, listeComposants.get(0).getCouleur());
        this.listeComposantsBoisson = listeComposants;
    }

    public boolean estDisponible() {
        return this.dispo;
    }
    public void majDispo(HashMap<Boisson, Integer> B_stock) {
        boolean ok = true;
        //System.out.println(Arrays.toString(this.listeComposantsBoisson));
         for(Boisson comp : this.listeComposantsBoisson) {
             if(!B_stock.containsKey(comp)  ) { // si le composant n'est pas présent
                 ok = false;
             } else if( B_stock.get(comp) <= 0 ){
                 ok = false;

             }
            // System.out.println("     +" +comp + " ######## " + B_stock + " " +ok);
         }

         this.dispo = ok;
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
    public List<Boisson> getComposants() {
        return this.listeComposantsBoisson;
    }
    public double getDegreAlcool() {
        return 0.0;
    }
    public double getDegreSucre() {
        return 0.0;
    }


}
