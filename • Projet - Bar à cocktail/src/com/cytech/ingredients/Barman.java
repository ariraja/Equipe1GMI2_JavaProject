package com.cytech.ingredients;
import java.util.*;
import java.util.HashMap;


public class Barman {
    private static String prenomBarman = "Paul";
    private static String nomBarman = "Emile";
    private static double VerreCl = 25;
    private static List<Boisson> LeStock = new ArrayList<Boisson>();
    private static List<Cocktail> LesCocktails = new ArrayList<Cocktail>();


    public static void AjouterBoissonAuStock(Boisson b) {
        LeStock.add(b);

    }
    public static void RetirerBoissonDuStock(Boisson b) {
        LeStock.remove(b);
    }
    public static void AjouterCocktailALaListe(Cocktail c) {
        LesCocktails.add(c);

    }
    public static void RetirerCocktailDuStock(Cocktail c) {
        LesCocktails.remove(c);
    }
    public static Cocktail ComposerCocktail(String nom,double contenance, Boisson[] B) {
        Cocktail res = new Cocktail(nom,11,B[0].getCouleur(),B);
        return res;



    }

    public static void SePresenter() {
        System.out.print(" \n Bonjour et bienvenue, je m'appelle " + prenomBarman + " "  + nomBarman);
        System.out.println(" puis-je faire un truc pour vous ? \n");


    }
    public static void TuVeuxQuoi() {


        Map Carte = AfficherCatalogue();
        System.out.println("  ---- \n (1 : Commander)     (2 : Créer mon propre Cocktail)      (0 : Quitter le bar) ");

        int choix = Main.SaisirInt(0,2);
        if(choix == 1) {
            Commande maCommande = new Commande(1);
            Barman.SelectionnerBoisson(Carte, maCommande);
        }
        else if(choix == 0) System.out.println("A bientot !");

    }

    public static Map AfficherCatalogue() {
        Map Dico = new HashMap();
        int i = 1;
        System.out.println("/////////////////");
        System.out.println("//////  -*-* Le BAR *-*- ///////////////////////// \n");
        System.out.println(" #### Nos Cocktails :");
        for (Cocktail c : LesCocktails) {
            System.out.println("    [" +i + "] : * " + c);
            Dico.put(i, c);
            i++;
        }
        System.out.println(" #### Nos Boissons :");

        for (Boisson b : LeStock) {
            System.out.println("    [" +i + "] : * " + b);
            Dico.put(i, b);
            i++;
        }
        //System.out.println();
        return Dico;

    }

    public static void SelectionnerBoisson(Map Carte, Commande maCommande) { // selectionne une boisson parmi la liste de boisson et renvoie la boisson
        AfficherCatalogue();int choix;

        if(maCommande.estVide()) System.out.println("  ---- \n (# : Entrer le numéro de la boisson)             (0 : Quittez)  ");
        else System.out.println("  ---- tu veux quoi ? \n (# : Entrer le numéro de la boisson)          (0 : Voir Commande ~ " + maCommande.CalculPrixTotal() + "€)");
        choix = Main.SaisirInt(0,Carte.size());

        if (choix != 0) {
           // System.out.println("  ---- \n (1 : VERRE)             (0 : BOUTEILLE)  ");
            System.out.println(" ~# " + Carte.get(choix));
            System.out.print(" combien de verre ? > ");int combien = Main.SaisirInt(0,Carte.size());


            maCommande.Ajouter(Carte.get(choix),combien);

            SelectionnerBoisson(Carte,maCommande);
        }
        else {
            if (maCommande.estVide()) {
                System.out.println(" Vous n'avez rien commander.. êtes vous sure ? \n (0 : Annuler  ");
                //choix = Main.SaisirInt(0,Carte.size());
            } else {
                maCommande.Afficher();

                System.out.println(" (0 : Continuer ma commande)     (1 : Valider ma commande)     (2 : Annuler ma commande)");
                choix = Main.SaisirInt(0, 2);
                switch (choix) {
                    case 0 : SelectionnerBoisson(Carte,maCommande); break;
                    case 1 :  Barman.ValiderCommande(maCommande); break;
                    case 2 :   Barman.AnnulerCommande();break;
                }

            }
        }


    }

    public static void AnnulerCommande() {
        System.out.println("SupprimerCoommande");

    }


    public static void ValiderCommande(Commande maCommande) {
        System.out.println(" ***" + LeStock.size());
         if(maCommande.getNbBoissons() > 0) {
             for(Boisson b : maCommande.getListeBoissons()) {

                RetirerBoissonDuStock(b);
             }
             System.out.println(" ***" + LeStock.size());


         }



    }

}
