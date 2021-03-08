package com.cytech.ingredients;

import java.util.*;

public class Commande {
    private int idc;
    private List<Boisson> listeBoissons = new ArrayList<Boisson>();
    private List<Cocktail> listeCocktails = new ArrayList<Cocktail>();

    public Commande(int idc) {
        this.idc = idc;
    }

    public void Ajouter(Object o, int cb ) {
        String cla = o.getClass().getSimpleName();
        String[] lesBoiss = {"Boisson","BoissonAlcoolisee","BoissonNonAlcoolisee"};
        List lb = Arrays.asList(lesBoiss);

        if(lb.contains(cla)) {
            Boisson b = (Boisson) o;
            for(int i =0; i < cb; i++) {

                this.listeBoissons.add(b);

            }
            System.out.println(" ... " + cla + " '" + b.getNom() + "' x " + cb + " a été ajouté à la commande");
        } else if (cla.compareTo("Cocktail") == 0) {
            Cocktail c = (Cocktail) o;
            for(int i =0; i < cb; i++) {

                this.listeCocktails.add(c);

            }
            System.out.println(" ... " + cla + " '" + c.getNom() + "' x " + cb + " a été ajouté à la commande");
        }



    }

    public List<Boisson> getListeBoissons() {
        return this.listeBoissons;
    }
    public List<Cocktail> getListeCocktails() {
        return this.listeCocktails;
    }
    public int getNbCocktails() {
        return this.listeCocktails.size();
    }
    public int getNbBoissons() {
        return this.listeBoissons.size();
    }
    public boolean estVide() {
        return ((this.getNbBoissons() + this.getNbCocktails()) == 0);
    }


    public Map Afficher() {
        Map Dico = new HashMap();
        int i = 0;
        System.out.println(" //////////////");
        System.out.println(" ///// * VOTRE COMMANDE * ////////////////////////");
        if(this.getNbCocktails() > 0) System.out.println(" ## " + this.getNbCocktails() + " Cocktails");
        for (Cocktail c : this.listeCocktails) {
            System.out.println("     [" +i + "] : | " + c);
            Dico.put(i, c);
            i++;
        }
        if(this.getNbBoissons() > 0) System.out.println(" ## " + this.getNbBoissons() + " Boissons");

        for (Boisson b : this.listeBoissons) {
            System.out.println("     [" + i + "] : | " + b);
            Dico.put(i, b);
            i++;
        }
        System.out.println(" ///// PRIX TOTAL : " + this.CalculPrixTotal() + "€");
        return Dico;
    }

    public double CalculPrixTotal() {
        double res = 0.0;
        if(this.getNbCocktails() > 0) {
            for (Cocktail c : this.getListeCocktails()) {
                res += c.getPrix();
            }
        }
        if(this.getNbBoissons() > 0) {
            for (Boisson b : this.getListeBoissons()) {
                res += b.getPrix();
            }
        }
        res = (double) Math.round(res * 100) / 100;
        return res;
    }
}
