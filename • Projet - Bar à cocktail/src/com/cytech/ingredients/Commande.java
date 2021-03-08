package com.cytech.ingredients;

import java.util.*;

public class Commande {
    private int idc;
    private HashMap<Boisson, Integer> commandeBoissons = new HashMap<Boisson, Integer>();
    private HashMap<Cocktail, Integer> commandeCocktails = new HashMap<Cocktail, Integer>();

    public Commande(int idc) {
        this.idc = idc;
    }

    public void Ajouter(Object o, int cb ) {
        String cla = o.getClass().getSimpleName();
        List lb = Arrays.asList(new String[] {"Boisson","BoissonAlcoolisee","BoissonNonAlcoolisee"});

        if(lb.contains(cla)) {
            Boisson b = (Boisson) o;

            if(this.commandeBoissons.containsKey(b)){
                int qte = this.commandeBoissons.get(b);
                qte += cb;
                this.commandeBoissons.put(b,qte);
            } else {
                this.commandeBoissons.put(b, cb);
            }

        System.out.println(" ... " + cla + " '" + b.getNom() + "' x " + cb + " a été ajouté à la commande");
        } else if (cla.compareTo("Cocktail") == 0) {
            Cocktail c = (Cocktail) o;

            if(this.commandeCocktails.containsKey(c)){
                int qte = this.commandeCocktails.get(c);
                qte += cb;
                this.commandeCocktails.put(c,qte);
            } else {
                this.commandeCocktails.put(c, cb);
            }

            System.out.println(" ... " + cla + " '" + c.getNom() + "' x " + cb + " a été ajouté à la commande");
        }
    }

    public Set<Boisson> getListeBoissons() {
        return this.commandeBoissons.keySet();
    }
    public Set<Cocktail> getListeCocktails() {
        return this.commandeCocktails.keySet();
    }

    public int getNbBoissonsTOTAL() {
        return this.commandeBoissons.size();
    }
    public int getNbCocktailsTOTAL() {
        return this.commandeCocktails.size();
    }
    public boolean estVide() {
        return ((this.getNbBoissonsTOTAL() + this.getNbCocktailsTOTAL()) == 0);
    }
    public int getQuantiteBoisson(Boisson b) {
        if(this.commandeBoissons.containsKey(b) ){
            int qte = this.commandeBoissons.get(b);
            return qte;
        } else {
            return 0;
        }
    }
    public int getQuantiteCocktail(Cocktail c) {
        if(this.commandeCocktails.containsKey(c) ){
            int qte = this.commandeCocktails.get(c);
            return qte;
        } else {
            return 0;
        }
    }

    public Map Afficher() {
        Map Dico = new HashMap();
        int i = 0;
        System.out.println(" //////////////");
        System.out.println(" ///// *** VOTRE COMMANDE *** ////////////////////////");
        if(this.getNbCocktailsTOTAL() > 0) System.out.println(" ## " + this.getNbCocktailsTOTAL() + " Cocktails");
        for (Cocktail c : getListeCocktails()) {
            System.out.println("     [" +i + "] : | " + c + " x " + this.commandeCocktails.get(c));
            Dico.put(i, c);
            i++;
        }
        if(this.getNbBoissonsTOTAL() > 0) System.out.println(" ## " + this.getNbBoissonsTOTAL() + " Boissons");

        for (Boisson b : getListeBoissons()) {
            System.out.println("     [" + i + "] : | " + b + " x " + this.commandeBoissons.get(b));
            Dico.put(i, b);
            i++;
        }

        System.out.println(" ///// *** PRIX TOTAL : " + this.CalculPrixTotal() + "€ ***");
        System.out.println(" //////////////");
        return Dico;
    }

    public double CalculPrixTotal() {
        double res = 0.0;
        if(this.getNbCocktailsTOTAL() > 0) {
            for (Cocktail c : this.getListeCocktails()) {
                int qte = this.commandeCocktails.get(c);
                res += c.getPrix()*qte;
            }
        }
        if(this.getNbBoissonsTOTAL() > 0) {
            for (Boisson b : this.getListeBoissons()) {
                int qte = this.commandeBoissons.get(b);
                res += b.getPrix()*qte;
            }
        }
        res = (double) Math.round(res * 100) / 100;
        return res;
    }

    public void Supprimer() {
        this.commandeCocktails.clear();
        this.commandeBoissons.clear();
    }
}
