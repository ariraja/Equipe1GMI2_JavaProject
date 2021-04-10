package com.cytech.ingredients;
import java.util.*;
import java.util.HashMap;


public class Barman {
    private static String prenomBarman = "Paul";
    private static String nomBarman = "Emile";

    // LE STOCK, de Boissons
    private static HashMap<Boisson, Integer> LeStock = new HashMap<Boisson, Integer>(); // ['nomboisson'] return quantité
    // LES Cocktails
    private static List<Cocktail> LesCocktails = new ArrayList<Cocktail>();

    private static int nbCocktailsDispo; // nb de Cocktails dispo // TODO LesCocktails.nbCocktailsDispo()
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
        Commande maCommande = new Commande(1); // TODO date de ajd
        AfficherCatalogue(maCommande,"Le BAR","Qu'est ce qui vous ferai plaisir ?",true,true);
        System.out.println("  ---- \n (1 : Commander)     (2 : Créer mon propre Cocktail)      (0 : Quitter le bar) ");

        int choix = Main.SaisirInt(0,2,"");
        //int choix = 2;
        if(choix == 1) Barman.SelectionnerBoisson(maCommande);
        else if(choix == 2) Barman.ComposerCocktail(maCommande);
        else if(choix == 0) System.out.println("A bientot !"); // TODO fonction a bientot

    }

    private static void ComposerCocktail(Commande maCommande) {
        Map Carte = AfficherCatalogue(maCommande,"INGREDIENTS","De quoi sera composer votre cocktail ? (2 boissons minimum)",false,false);int choix;boolean encore = true;
        List<Boisson> listeIngredient = new ArrayList<>();

            while( listeIngredient.size() < nbBoissonsDispo ) {

                 do {
                    System.out.println("  ---- \n (# : Entrer le numéro de la boisson)             (0 : RETOUR)  ");
                    System.out.print("  > ");
                    choix = Main.SaisirInt(0, Carte.size(), "");
                     if(listeIngredient.contains((Boisson) Carte.get(choix))){
                         System.out.print(" /!\\ Vous avez déjà choisi cette boisson.. ");
                         //AfficherCatalogue(maCommande,"INGREDIENTS","De quoi sera composer votre cocktail ? (2 boissons minimum)",false,false);
                     }
                } while(listeIngredient.contains((Boisson) Carte.get(choix)));

                if (choix != 0) {




                    listeIngredient.add((Boisson) Carte.get(choix));
                    System.out.println(" ~# Vos Ingrédients  " + listeIngredient);
                    // ** quand on a saisie moins de 1
                    if(listeIngredient.size() > 1)  {
                        if(!(listeIngredient.size() == nbBoissonsDispo)) {
                            System.out.println("  ---- \n (1 : CONTINUER AJOUTER)   (2 : CREER MON COCKTAIL)         (0 : ANNULER)  ");
                            choix = Main.SaisirInt(0, 2, "");
                        }
                        else {
                            System.out.println("  ---- \n                           (2 : CREER MON COCKTAIL)         (0 : ANNULER)  ");
                            choix = Main.SaisirInt(0, 2, "");
                            if(choix== 1)choix = 2;
                        }
                        if (choix == 0) TuVeuxQuoi();
                        else if (choix == 2) {
                            System.out.print(" un nom > ");
                            String unnom = Main.SaisirString("");
                            Cocktail newCoco = new Cocktail(unnom, 25, listeIngredient);
                            Barman.AjouterCocktailALaListe(newCoco);
                            TuVeuxQuoi(); // retour au menu principale quoi
                        }
                    }

                } else {
                    TuVeuxQuoi();
                }
            }
            System.out.println("c trop");
            TuVeuxQuoi();
    }


    // Afficher le contenu du bar, retourne une carte pour selectionné
    public static Map AfficherCatalogue(Commande maCommande,String Titre,String Notice, boolean okAffCocktail, boolean okAffQuantite) {
        MettreAJourDisponibiliteCocktails();
        Map CarteSelec = new HashMap(); int i = 1;
        System.out.println("///////////////// " + Notice);
        System.out.println(String.format("//////  -*-* %s *-*- ///////////////////////// \n",Titre));
        if(nbBoissonsDispo + nbCocktailsDispo > 0) {
            if(okAffCocktail) {
                System.out.println(" #### Nos Cocktails : " + nbCocktailsDispo + "/" + LesCocktails.size());
                for (Cocktail c : LesCocktails) {
                    //System.out.println(c.estDisponible());
                    if (c.estDisponible()) {
                        System.out.println("    [" + i + "] : * " + c);
                        CarteSelec.put(i, c);
                        i++;
                    }
                }
            }
            System.out.println(" #### Nos Boissons : " + nbBoissonsDispo + "/" + LeStock.size());
            for (Boisson b : LeStock.keySet()) {
                int fois = LeStock.get(b) - maCommande.getQuantiteBoisson(b);
                if (fois > 0) {
                    if(okAffQuantite) {
                        System.out.println("    [" + i + "] : * " + b + " x " + fois);
                    } else {
                        System.out.println("    [" + i + "] : * " + b);
                    }
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

    //
    public static void SelectionnerBoisson( Commande maCommande) { // selectionne une boisson parmi la liste de boisson et renvoie la boisson
        Map Carte = AfficherCatalogue(maCommande,"Le BAR","",true,true);
        int choix;

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

            SelectionnerBoisson(maCommande);
        }
        else {
            if (maCommande.estVide()) {
                Main.MENUPRINCIPALE();
            } else {
                maCommande.Afficher();

                System.out.println(" (0 : Continuer ma commande)     (1 : Valider ma commande)     (2 : Annuler ma commande)");
                choix = Main.SaisirInt(0, 2,"");
                switch (choix) {
                    case 0 : SelectionnerBoisson(maCommande); break;
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

    // Valider Commander et sauvegarde
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
