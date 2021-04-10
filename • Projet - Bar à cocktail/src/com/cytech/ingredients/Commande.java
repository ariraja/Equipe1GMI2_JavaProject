package com.cytech.ingredients;

import java.util.*;

public class Commande {
    private int idc; // identifiant de la commande // TODO date heure de ajd
    private HashMap<Boisson, Integer> commandeBoissons = new HashMap<Boisson, Integer>();
    private HashMap<Cocktail, Integer> commandeCocktails = new HashMap<Cocktail, Integer>();

    // Contructeur
    public Commande(int idc) {
        this.idc = idc;
    }

    // Ajouter un object dans la commande
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

    // Retourner liste Boisson de la commande
    public Set<Boisson> getListeBoissons() {
        return this.commandeBoissons.keySet();
    }
    // Retourner liste Cocktails commande
    public Set<Cocktail> getListeCocktails() {
        return this.commandeCocktails.keySet();
    }

    // Returner Nombre Boissons
    public int getNbBoissonsTOTAL() {
        return this.commandeBoissons.size();
    }

    // Returner Nombre de Cocktails
    public int getNbCocktailsTOTAL() {
        return this.commandeCocktails.size();
    }

    // Est ce que la commande est vide
    public boolean estVide() {
        return ((this.getNbBoissonsTOTAL() + this.getNbCocktailsTOTAL()) == 0);
    }

    // Retourne la Quantité d'une boisson dans la commande
    public int getQuantiteBoisson(Boisson b) {
        if(this.commandeBoissons.containsKey(b) ){
            int qte = this.commandeBoissons.get(b);
            return qte;
        } else {
            return 0;
        }
    }

    // Retourne la Quantité d'un cocktails dans la commande
    public int getQuantiteCocktail(Cocktail c) {
        if(this.commandeCocktails.containsKey(c) ){
            int qte = this.commandeCocktails.get(c);
            return qte;
        } else {
            return 0;
        }
    }

    // Afficher la Commande a lecran
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

        System.out.println("\n ///// *** PRIX TOTAL : " + this.CalculPrixTotal() + "€ ***");
        System.out.println(" //////////////");
        return Dico;
    }

    // Calculer prix totale
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


    // Supprimer la commande
    public void Supprimer() {
        this.commandeCocktails.clear();
        this.commandeBoissons.clear();
    }
}
