package com.cytech.ingredients;
import java.util.*;
import java.util.HashMap;


public class Barman {
    private static String prenomBarman = "Paul";
    private static String nomBarman = "Emile";

    private static HashMap<Boisson, Integer> LeStock = new HashMap<Boisson, Integer>(); // ['nomboisson'] return quntité
    private static List<Cocktail> LesCocktails = new ArrayList<Cocktail>();

    private static int nbCocktailsDispo;
    private static int nbBoissonsDispo;

    public static int getQuantiteBoissonStock(Boisson b) {
        if(LeStock.containsKey(b) ){
            int qte =  LeStock.get(b);
            return qte;
        } else {
            return 0;
        }
    }
    public static void AjouterBoissonAuStock(Boisson b, int cb) {
        if(LeStock.containsKey(b) ){
            int qte = LeStock.get(b);
            qte += cb;

            LeStock.put(b, qte);

        } else {
            LeStock.put(b, cb);
        }


    }
    public static void RetirerBoissonDuStock(Boisson b, int cb) {
        if(LeStock.containsKey(b) ){
            int qte = LeStock.get(b);
            if (qte > 0) {
                qte -= cb;
            }
            LeStock.put(b,qte);
        } else {
            LeStock.put(b, 0);
        }

    }
    public static void AjouterCocktailALaListe(Cocktail c) {
        LesCocktails.add(c);

    }
    public static void RetirerCocktailDeLaListe(Cocktail c) {

         LesCocktails.remove(c);


    }
    /*public static Cocktail ComposerCocktail(String nom,double contenance, Boisson[] B) {
        Cocktail res = new Cocktail(nom,11,B[0].getCouleur(),B);
        return res;
    }*/

    public static void SePresenter() {
        System.out.print(" \n Bonjour et bienvenue, je m'appelle " + prenomBarman + " "  + nomBarman);
        System.out.println(" puis-je faire un truc pour vous ? \n");


    }
    public static void TuVeuxQuoi() {
        Commande maCommande = new Commande(1);
        Map Carte = AfficherCatalogue(maCommande);
        System.out.println("  ---- \n (1 : Commander)     (2 : Créer mon propre Cocktail)      (0 : Quitter le bar) ");

        //int choix = Main.SaisirInt(0,2);
        int choix = 1;
        if(choix == 1) {

            Barman.SelectionnerBoisson(Carte, maCommande);
        }
        else if(choix == 0) System.out.println("A bientot !");

    }

    public static Map AfficherCatalogue(Commande maCommande) {
        MettreAJourDisponibiliteCocktails();
        Map CarteSelec = new HashMap(); int i = 1;
        System.out.println("/////////////////");
        System.out.println("//////  -*-* Le BAR *-*- ///////////////////////// \n");
        if(nbBoissonsDispo + nbCocktailsDispo > 0) {
            System.out.println(" #### Nos Cocktails : " + nbCocktailsDispo + "/" + LesCocktails.size());
            for (Cocktail c : LesCocktails) {
                //System.out.println(c.estDisponible());
                if (c.estDisponible()) {
                    System.out.println("    [" + i + "] : * " + c);
                    CarteSelec.put(i, c);
                    i++;
                }
            }
            System.out.println(" #### Nos Boissons : " + nbBoissonsDispo + "/" + LeStock.size());
            for (Boisson b : LeStock.keySet()) {
                int fois = LeStock.get(b) - maCommande.getQuantiteBoisson(b);
                if (fois > 0) {
                    System.out.println("    [" + i + "] : * " + b + " x " + fois);
                    CarteSelec.put(i, b);
                    i++;
                } else {
                    System.out.println("     étagère vide.. ");
                }
            }

        } else {
            System.out.println("\n                      le bar est vide... revenez plutard\n");
            Main.MENUPRINCIPALE();

        }
        return CarteSelec;

    }

    public static void SelectionnerBoisson(Map Carte, Commande maCommande) { // selectionne une boisson parmi la liste de boisson et renvoie la boisson
        Carte = AfficherCatalogue(maCommande);int choix;

        if(maCommande.estVide()) System.out.println("  ---- \n (# : Entrer le numéro de la boisson)             (0 : Quittez)  ");
        else System.out.println("  ---- \n (# : Entrer le numéro de la boisson)          (0 : Voir Commande ~ " + maCommande.CalculPrixTotal() + "€)");
        choix = Main.SaisirInt(0,Carte.size(), "");

        if (choix != 0) {
           // System.out.println("  ---- \n (1 : VERRE)             (0 : BOUTEILLE)  ");
            System.out.println(" ~# " + Carte.get(choix));
            System.out.print(" combien ? > ");

            int maxCombien= 1000;

            if (choix > nbCocktailsDispo) {
                maxCombien = getQuantiteBoissonStock((Boisson) Carte.get(choix));
                maxCombien -= maCommande.getQuantiteBoisson((Boisson) Carte.get(choix));
            }
            System.out.println(" max*** " + maxCombien);
            if(maxCombien > 0) {
                int combien = Main.SaisirInt(0, maxCombien, "ne pas dépasser le stock");
                maCommande.Ajouter(Carte.get(choix), combien);
            } else {
                System.out.println(" T'as tout pris chakal " );
            }

            SelectionnerBoisson(Carte,maCommande);
        }
        else {
            if (maCommande.estVide()) {
                System.out.println(" Vous n'avez rien commander.. êtes vous sure ? \n (0 : Annuler  ");
                //choix = Main.SaisirInt(0,Carte.size());
            } else {
                maCommande.Afficher();

                System.out.println(" (0 : Continuer ma commande)     (1 : Valider ma commande)     (2 : Annuler ma commande)");
                choix = Main.SaisirInt(0, 2,"");
                switch (choix) {
                    case 0 : SelectionnerBoisson(Carte,maCommande); break;
                    case 1 : Barman.ValiderCommande(maCommande); break;
                    case 2 : Barman.AnnulerCommande(maCommande);break;
                }

            }
        }
    }

    public static void AnnulerCommande(Commande maCommande) {
        maCommande.Supprimer();
        Barman.TuVeuxQuoi();
    }

    public static void ValiderCommande(Commande maCommande) {
         System.out.println(" stock avant validation *** " + LeStock);
        // mettre a jour le stock
         if(maCommande.getNbBoissonsTOTAL() > 0) {
             for(Boisson b : maCommande.getListeBoissons()) {
                RetirerBoissonDuStock(b,maCommande.getQuantiteBoisson(b));
             }
         }
         // mettre à jour La disponibilité des cocktails
        MettreAJourDisponibiliteCocktails();System.out.println(" stock apres validation *** " + LeStock);
        Barman.TuVeuxQuoi();
    }

    public static void MettreAJourDisponibiliteCocktails() {
        nbCocktailsDispo = 0;
        for(Cocktail c : LesCocktails) {
            c.majDispo(LeStock);
            if(c.estDisponible()) nbCocktailsDispo++;
        }
        nbBoissonsDispo = 0;
        for(Boisson b : LeStock.keySet()) {
           // System.out.println(" ---------------------------------" +LeStock.get(b));
            if(LeStock.get(b) > 0) nbBoissonsDispo++;
        }

    }

}
