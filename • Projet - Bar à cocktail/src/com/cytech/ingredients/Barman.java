package com.cytech.ingredients;
import java.util.*;
import java.util.HashMap;


public class Barman {
    private static String prenomBarman = "Paul";
    private static String nomBarman = "Emile";


    // Initialiser les Boissons en stock a partir du fichier JSON
    public static void initBoissonsJSON() {
        LeStock.clear();
        //**** GET LES BOISSONS
        Boisson a = new BoissonAlcoolisee("Braja","rouge",0.02,52);
        Boisson b = new BoissonAlcoolisee("Burvoy","bleu",0.05,70);
        Boisson c = new BoissonNonAlcoolisee("BSaid","vert",0.3,24);

        Barman.AjouterBoissonAuStock(a,2);
        Barman.AjouterBoissonAuStock(b,5);
        Barman.AjouterBoissonAuStock(c,10);

    }
    // initialiser les Coktails a partir du fichier JSON
    public static void initCocktailsJSON() { // Cette fonction est a modif
        LesCocktails.clear();
        Boisson a = new BoissonAlcoolisee("Braja","rouge",0.02,52);
        Boisson b = new BoissonAlcoolisee("Burvoy","bleu",0.05,70);
        Boisson c = new BoissonNonAlcoolisee("BSaid","vert",0.3,24);

        //**** GET LES COCKTAILS
        HashMap<Boisson, Double> recette = new HashMap<Boisson, Double>();
        recette.put(a,5.0);
        recette.put(b,22.5);
        Cocktail coco1 = new Cocktail("coco1",recette);
        Barman.AjouterCocktailALaListe(coco1);

        HashMap<Boisson, Double> recette2 = new HashMap<Boisson, Double>();
        recette2.put(a,150.0);
        recette2.put(c,50.0);
        Cocktail coco2 = new Cocktail("coco2",recette2);
        Barman.AjouterCocktailALaListe(coco2);

    }

    private static HashMap<Boisson, Integer> LeStock = new HashMap<Boisson, Integer>(); // ['nomboisson'] return quntité
    private static ArrayList<Cocktail> LesCocktails = new ArrayList<Cocktail>();


    public static int getQuantiteDUneBoissonStock(Boisson b) {
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
        System.out.println("  ---- \n ( 1 : *COMMANDER* )     ( 2 : *CREER MON COCKTAIL* )      (0 : *QUITTER LE BAR* ) ");

        int choix = Main.SaisirInt(0,2,"");
        if(choix == 1) Barman.SelectionnerBoisson(maCommande);
        else if(choix == 2) Barman.ComposerCocktail(maCommande);
        else if(choix == 0) Barman.Abientot(maCommande); // TODO fonction a bientot

    }
    private static Double SommeArray(Collection<Double> t) {
        double res = 0.0;

        for( double d : t)
            res += d;

        return res;

    }

    private static void AfficherRecette(HashMap<Boisson, Double> recette) {
    String res = " ~# Vos Ingrédients : ";
    ArrayList<Boisson> Bs = new ArrayList<Boisson>(recette.keySet());
    ArrayList<Double> Qs = new ArrayList<Double>(recette.values());
    int n = Bs.size();
    for(int i = 0; i < n-1; i++) {
        res += Bs.get(i).getNom() + " (" + Qs.get(i) + "%) - ";
    }
    res += Bs.get(n-1).getNom()+ " (" + Qs.get(n-1) + "%)";;
    System.out.println(res);

}

    private static HashMap<Boisson, Double> NormaliserRecette(HashMap<Boisson, Double> recette) {
        double Total = 0.0;
        for(Boisson b : recette.keySet()) {
            Total += recette.get(b);
        }
        if(Total != 0) {
            for (Boisson b : recette.keySet()) {
                recette.put(b, recette.get(b) / Total * 100);
            }
            return recette;
        } else {
            return recette;
        }

    }
    private static void ComposerCocktail(Commande maCommande) {
        Map Carte;
        int choix;
        double Combien;
        double maxPourc = 100;
        double PourcTotalDeja = 0.0;
        boolean encore = true;
    //    ArrayList<Boisson> IngredientBoissons = new  ArrayList<Boisson>(); // Listes des ingrédient
     //   ArrayList<Double> IngredientQuantityMl = new  ArrayList<Double>(); // Listes des Ml
        HashMap<Boisson, Double> recette = new HashMap<Boisson, Double>();

        while(encore) {
            Carte = AfficherCatalogue(maCommande,"INGREDIENTS","De quoi sera composer votre cocktail ? (2 boissons minimum)",false,false);
            if(recette.size() > 0) {
                System.out.println("  ----"); AfficherRecette(recette);
            }

            /*** Saisie de la Boisson ***/
            System.out.println("  ---- \n (# : Entrer le numéro de la boisson)             (0 : RETOUR)  ");
            System.out.print("  > ");
            choix = Main.SaisirInt(0, Carte.size(), "");

            // si il n'a pas cliquer sur retour
            if (choix != 0) {
                PourcTotalDeja = SommeArray( recette.values());
                /*** Saisie de la Qté ***/
                maxPourc = (100.0-PourcTotalDeja);
                System.out.println("  ---- \n Combien de % ? [0-" + maxPourc + "]");
                System.out.print("  > ");

                Combien = (double) Main.SaisirDouble(0, maxPourc, "entre 0 et " + maxPourc + "%");

                // ajouter a la recette
                if(recette.containsKey((Boisson) Carte.get(choix))) {
                    recette.put((Boisson) Carte.get(choix),  recette.get((Boisson) Carte.get(choix)) + Combien );
                } else {
                    recette.put((Boisson) Carte.get(choix),Combien);
                }

                PourcTotalDeja = SommeArray( recette.values());


                // ** il faut au moins 2 Ingrédient
                if ( recette.size() > 1) {


                    // 4 ingrédient max et 200ml maximum
                    if ( recette.size() < 4 && PourcTotalDeja < 100 ) {
                        AfficherRecette(recette);
                        System.out.println("  ---- \n (1 : CONTINUER AJOUTER)   (2 : CREER MON COCKTAIL)         (0 : ANNULER)  ");
                        choix = Main.SaisirInt(0, 2, "");
                    }
                    // On ne propose plus de ajouter
                    else {
                        AfficherRecette(recette);
                        System.out.println("  ---- \n                           (2 : CREER MON COCKTAIL)         (0 : ANNULER)  ");
                        choix = Main.SaisirInt(0, 2, "");
                        if (choix == 1) choix = 2;
                    }

                    /*** CREATION ET AJOUT DU COCKTAIL ***/
                    if (choix == 2) {
                        /*** Saisie de la Qté ***/
                        System.out.println("  ---- \n Donner un nom à votre cocktail  ");
                        System.out.print("  > ");
                        String Nom = Main.SaisirString("veuillez saisir un prénom correct");
                        recette = NormaliserRecette(recette);
                        Cocktail newC = new Cocktail(Nom, recette);
                        AfficherRecette(recette);
                        System.out.println(newC);
                        AjouterCocktailALaListe(newC); // ajouter le nouveau cocktail créer a la liste
                        Barman.TuVeuxQuoi(); // Retour au menu
                    } else if (choix == 0) {
                        Barman.TuVeuxQuoi(); // Retour au menu
                    }

                }


            }
        }
    }

    // Afficher le contenu du bar, retourne une carte pour selectionné
    public static Map AfficherCatalogue(Commande maCommande,String Titre,String Notice, boolean okAffCocktail, boolean okAffQuantite) {
        Map CarteSelec = new HashMap(); int i = 1;
        System.out.println("///////////////// " + Notice);
        System.out.println(String.format("//////  -*-* %s *-*- ///////////////////////// \n",Titre));
        if(Barman.getNbBoissonsDispo() + Barman.getNbCocktailsDispo() > 0) {
            if(okAffCocktail) {
                System.out.println(" #### Nos Cocktails : " + Barman.getNbCocktailsDispo()+ "/" + LesCocktails.size());
                for (Cocktail c : LesCocktails) {
                    //System.out.println(c.estDisponible());
                    if (c.estDisponible()) {
                        System.out.println("    [" + i + "] : * " + c);
                        CarteSelec.put(i, c);
                        i++;
                    }
                }
            }
            System.out.println(" #### Nos Boissons : " + Barman.getNbBoissonsDispo() + "/" + LeStock.size());
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

            int maxCombien= 100;

            if (choix > Barman.getNbCocktailsDispo()) {
                maxCombien = getQuantiteDUneBoissonStock((Boisson) Carte.get(choix));
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
                    case 0 : Barman.SelectionnerBoisson(maCommande); break;
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
        System.out.println(" stock apres validation *** " + LeStock);
        Barman.TuVeuxQuoi();
    }


    public static int getNbCocktailsDispo() {
        int nbCocktailsDispo = 0;
        for(Cocktail c : LesCocktails) {
            c.majDispo(LeStock);
            if(c.estDisponible()) nbCocktailsDispo++;
        }
        return nbCocktailsDispo;
    }
    public static int getNbBoissonsDispo() {
        int nbBoissonsDispo = 0;
        for(Boisson b : LeStock.keySet()) {
            // System.out.println(" ---------------------------------" +LeStock.get(b));
            if(LeStock.get(b) > 0) nbBoissonsDispo++;
        }
        return nbBoissonsDispo;
    }

    public static void Abientot( Commande maCommande) {
        Main.MENUPRINCIPALE();

        // sauvegarder le stock

        //
    }


}
