package com.cytech.ingredients;
import java.util.*;
import java.util.HashMap;


public class Barman {
    private static String prenomBarman = "Paul";
    private static String nomBarman = "Emile";
    private static List<Boisson> LeStock = new ArrayList<Boisson>();
    private static List<Cocktail> LesCocktails = new ArrayList<Cocktail>();

    public static void AjouterBoissonAuStock(Boisson b) {
        LeStock.add(b);

    }
    public static void AjouterCocktailALaListe(Cocktail b) {
        LesCocktails.add(b);

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
        System.out.println("\n\n (1 : Commencer ma commande)    (2 : Créer mon propre Cocktail)    (0 : Quitter le bar) ");

        int choix = Main.SaisirInt(0,2);
        if(choix == 0) System.out.println("A bientot !");
        else if(choix == 1) Barman.SelectionnerBoisson(Carte);

    }

    public static Map AfficherCatalogue() {
        Map Dico = new HashMap();
        System.out.println("////  ** Le BAR ** //////////////");
        System.out.println(" * Tu veux quoi ? On a tout : \n");
        int i = 1;
        System.out.println(" #### Nos Cocktails :");
        for (Cocktail c : LesCocktails) {
            System.out.println("    [" +i + "] : " + c);
            Dico.put(i, c);
            i++;
        }
        System.out.println(" #### Nos Boissons :");

        for (Boisson b : LeStock) {
            System.out.println("    [" +i + "] : " + b);
            Dico.put(i, b);
            i++;
        }
        return Dico;

    }

    public static void SelectionnerBoisson(Map Carte) { // selectionne une boisson parmi la liste de boisson et renvoie la boisson

        int choix; System.out.println(" (0 : Finaliser)    (# : Entrer le numéro de la boisson) ");
        choix = Main.SaisirInt(0,Carte.size());

        if (choix != 0) {
            System.out.println(" combien ? > ");
            int combien = Main.SaisirInt(0,Carte.size());

            System.out.println(" ajouter " + Carte.get(choix) + " x " + combien + " à la commande");
            System.out.println(" AjouterALaCommande(Boisson b , int combien ) ");
            SelectionnerBoisson(Carte);
        }
        else {
            System.out.println(" (0 : Annuler ma commande)     (1 : Valider ma commande) "); choix = Main.SaisirInt(0,1);
            if (choix == 0) {
                Barman.AnnulerCommande();
            } else {
                Barman.ValiderCommande();
            }
        }


    }
    public static void AnnulerCommande() {
        System.out.println("SupprimerCoommande");


    }

    public static void ValiderCommande() {
        System.out.println("ValideCoommande");


    }

}
