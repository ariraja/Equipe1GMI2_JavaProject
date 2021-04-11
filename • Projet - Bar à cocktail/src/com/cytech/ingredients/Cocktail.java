package com.cytech.ingredients;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.*;

public class Cocktail extends BoissonMere{


    private boolean dispo = false;
    private HashMap<Boisson, Integer> listeComposantsBoisson;


    public Cocktail(String nom, HashMap<Boisson, Integer> listeComposants) {
        super(nom, 200,new ArrayList<Boisson>(listeComposants.keySet()).get(0).getCouleur()); // TODO couleur
        this.listeComposantsBoisson = listeComposants;
    }

    public boolean estDisponible() {
        return this.dispo;
    }
    public void majDispo(HashMap<Boisson, Integer> B_stock) {
        boolean ok = true;
 int qte;

         for(Boisson comp : this.getComposants()) {
             if(B_stock.containsKey(comp) ) { // si le composant est bien présent

                 qte = B_stock.get(comp); // qte en stock
                 if(qte < this.listeComposantsBoisson.get(comp)) { // si ya pas le minimum requis
                     ok = false;
                 }

             }
            // System.out.println("     +" +comp + " ######## " + B_stock + " " +ok);
         }

         this.dispo = ok;
    }

    @Override
    public String toString() {
        String res = "Coktail '"  + Main.printColor("BOLD") + this.getNom() + '\'' + Main.printColor("RESET");
        if(this.getDegreAlcool() > 0) {
            res += " | *DegALCOOL* : " +  this.getDegreAlcool() + "°";
        }
        if(this.getDegreSucre() > 0) {
            res += " | *DegSUCRE* " +  this.getDegreSucre() + "°";
        }
        res += " | (";
        for(Boisson b : this.listeComposantsBoisson.keySet())
            res += "'" + b.getNom() +"',";
        res = res.substring(0,res.length()-1);
        res += ")";

        res += " | *PRIX* : "+ Main.printColor("BOLD") + this.getPrix()+ Main.printColor("RESET")  +
                "€";

        return res;
    }

    // retourner prix en fonctions des composants
    public double getPrix() { // TODO
        double res = 0;
        for(Boisson b : this.listeComposantsBoisson.keySet())
            res += b.getPrix();
        res *= 1.1;
        res = (double) Math.round(res * 100) / 100;
        return res;
    }

    public Set<Boisson> getComposants() {
        return this.listeComposantsBoisson.keySet();

    }

    // retourner degre de alcool du cocktail // TODO
    public double getDegreAlcool() {
        double res = 0.0;
       double qteAlcoolTotal = 0.0; // nb de boisson avec de lalcool
        for(Boisson b : this.listeComposantsBoisson.keySet()) {
            String cla = b.getClass().getSimpleName();

            // si la boisson est un alcoolisee alors
            if(cla.equals("BoissonAlcoolisee")) {
                BoissonAlcoolisee Ba = (BoissonAlcoolisee) b;
                res += Ba.getDegreAlcool()* this.listeComposantsBoisson.get(b); // le degré pondéré par la dose en ml
                qteAlcoolTotal += this.listeComposantsBoisson.get(b);
            }
        }
        if(qteAlcoolTotal > 0.0) {
            res = res / qteAlcoolTotal;
            BigDecimal bd = new BigDecimal(res).setScale(2, RoundingMode.HALF_UP);
 res = bd.doubleValue();
            return res;
        }
        else
            return res;

    }

    // retourner degre de sucre du cocktail // TODO
    public double getDegreSucre() {
        double res = 0.0;
        double qteSucreTotal = 0.0; // nb de boisson avec de lalcool
        for(Boisson b : this.listeComposantsBoisson.keySet()) {
            String cla = b.getClass().getSimpleName();

            // si la boisson est un alcoolisee alors
            if(cla.equals("BoissonNonAlcoolisee")) {
                BoissonNonAlcoolisee Ba = (BoissonNonAlcoolisee) b;
                res += Ba.getDegreSucre()* this.listeComposantsBoisson.get(b); // le degré pondéré par la dose en ml
                qteSucreTotal += this.listeComposantsBoisson.get(b);
            }
        }
        if(qteSucreTotal > 0.0) {
            res = res / qteSucreTotal;
            BigDecimal bd = new BigDecimal(res).setScale(2, RoundingMode.HALF_UP);
            res = bd.doubleValue();
            return res;
        }
        else
            return res;
    }
    public double getContenu() {
        double res = 0.0;
        double qteAlcoolTotal = 0.0; // nb de boisson avec de lalcool
        for(double b : this.listeComposantsBoisson.values())
            res += b;
        return res;
    }

}
