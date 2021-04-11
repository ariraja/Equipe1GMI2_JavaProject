package com.cytech.ingredients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.util.Calendar;

import java.util.HashMap;


public class Barman {
    private static String prenomBarman = "Paul";
    private static String nomBarman = "Emile";


    // Initialiser les Boissons en stock a partir du fichier JSON
    public static void initBoissonsJSON() throws IOException, ParseException {
        LeStock.clear();
        LeStock = readJSONBoissonsStock(new FileReader("boisson.json"));

    }
    // initialiser les Coktails a partir du fichier JSON
    public static void initCocktailsJSON() throws IOException, ParseException { // Cette fonction est a modif
        LesCocktails.clear();
        LesCocktails = readJSONCocktails(new FileReader("cocktail.json"));


    }

    private static HashMap<Boisson, Integer> LeStock = new HashMap<Boisson, Integer>(); // ['nomboisson'] return quntité
    private static ArrayList<Cocktail> LesCocktails = new ArrayList<Cocktail>();

    public static void ModifierNomBoissonAuStock(Boisson b, String newN) {
        if(LeStock.containsKey(b) ){
            int qte = LeStock.get(b);
            RemoveBoissonDuStock(b);
            b.setNom(newN);
            AjouterQteBoissonAuStock(b,qte);
        }
    }
    public static void ModifierCouleurBoissonAuStock(Boisson b, String newN) {
        if(LeStock.containsKey(b) ){
            int qte = LeStock.get(b);
            RemoveBoissonDuStock(b);
            b.setCouleur(newN);
            AjouterQteBoissonAuStock(b,qte);
        }
    }
    public static void ModifierPrixMlBoissonAuStock(Boisson b, double newN) {
        if(LeStock.containsKey(b) ){
            int qte = LeStock.get(b);
            String cla = b.getClass().getSimpleName();
            if(cla.equals("BoissonAlcoolisee")) {
               BoissonAlcoolisee newBoisson = new BoissonAlcoolisee(b.getNom(),b.getCouleur(),newN,((BoissonAlcoolisee) b).getDegreAlcool());
               RemoveBoissonDuStock(b);
                AjouterQteBoissonAuStock(newBoisson,qte);
            } else if(cla.equals("BoissonNonAlcoolisee")) {
                BoissonNonAlcoolisee newBoisson = new BoissonNonAlcoolisee(b.getNom(),b.getCouleur(),newN,((BoissonNonAlcoolisee) b).getDegreSucre());
                RemoveBoissonDuStock(b);
                AjouterQteBoissonAuStock(newBoisson,qte);
            }


        }
    }

    public static void AjouterQteBoissonAuStock(Boisson b, int cb) {
        if(LeStock.containsKey(b) ){
            int qte = LeStock.get(b);
            qte += cb;

            LeStock.put(b, qte);

        } else {
            LeStock.put(b, cb);
        }


    }
    public static void RetirerQteBoissonDuStock(Boisson b, int cb) {
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
    public static void RemoveBoissonDuStock(Boisson b) {
        if(LeStock.containsKey(b) ){
            LeStock.remove(b);
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
    public static String getDateToday(){//récupérer la date et heure sous forme YYYYMMDD-HHMMSS
        Calendar cal = Calendar.getInstance( );  // date du jour
        String today="";
        String annee=Integer.toString(cal.get(Calendar.YEAR));
        String mois=Integer.toString(cal.get(Calendar.MONTH)+1);
        if(cal.get(Calendar.MONTH)<10){
            mois="0"+mois;
        }
        String jour=Integer.toString(cal.get(Calendar.DAY_OF_MONTH));

        String heure=Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        if(cal.get(Calendar.HOUR_OF_DAY)<10){
            heure="0"+heure;
        }
        String minute=Integer.toString(cal.get(Calendar.MINUTE));
        if(cal.get(Calendar.MINUTE)<10){
            minute="0"+ minute;
        }
        String seconde=Integer.toString(cal.get(Calendar.SECOND));
        if(cal.get(Calendar.SECOND)<10){
            seconde="0"+ seconde;
        }

        return annee+mois+jour+"-"+heure+minute+seconde;
    }

    public static void TuVeuxQuoi() throws IOException, ParseException {
        Commande maCommande = new Commande(getDateToday());
        AfficherCatalogue(maCommande,"Le BAR","Qu'est ce qui vous ferai plaisir ?",true,true);
        System.out.println(Main.printColor("BOLD") +"  ---- \n ( 1 : *COMMANDER* )     ( 2 : *CREER MON COCKTAIL* )      (0 : *QUITTER LE BAR* ) " + Main.printColor("RESET"));

        int choix = Main.SaisirInt(0,2,"");
        if(choix == 1) Barman.SelectionnerBoisson(maCommande);
        else if(choix == 2) {
            Barman.ComposerCocktail();
            Barman.TuVeuxQuoi(); // Retour au menu
        }
        else if(choix == 0) Barman.Abientot(true,false);

    }
    private static int SommeArray(Collection<Integer> t) {
        int res = 0;

        for( double d : t)
            res += d;

        return res;

    }

    private static void AfficherRecette(HashMap<Boisson, Integer> recette) {
        String res = " ~# Vos Ingrédients : ";
        ArrayList<Boisson> Bs = new ArrayList<Boisson>(recette.keySet());
        ArrayList<Integer> Qs = new ArrayList<Integer>(recette.values());
        int n = Bs.size();
        for(int i = 0; i < n-1; i++) {
            res += Main.printColor("MAGENTA") + Bs.get(i).getNom() + Main.printColor("RESET") + " (" +Main.printColor("BOLD") + Qs.get(i) + Main.printColor("RESET") + ") - ";
        }
        res += Main.printColor("MAGENTA") + Bs.get(n-1).getNom()+ Main.printColor("RESET") + " (" +Main.printColor("BOLD") + Qs.get(n-1) + Main.printColor("RESET") + ")";;
        System.out.println(res);

    }

   private static void ComposerCocktail() throws IOException, ParseException {
        Map Carte;
        int choix;
        int Combien;
        int maxPourc = 20;
        int NbCannetteTotalDeja = 0;
        boolean encore = true;
    //    ArrayList<Boisson> IngredientBoissons = new  ArrayList<Boisson>(); // Listes des ingrédient
     //   ArrayList<Double> IngredientQuantityMl = new  ArrayList<Double>(); // Listes des Ml
        HashMap<Boisson, Integer> recette = new HashMap<Boisson, Integer>();

        while(encore) {
            Carte = AfficherCatalogue("CREATION DE COCKTAIL * INGREDIENTS","De quoi sera composer votre cocktail ? (2 boissons minimum)",false,true,false,false);
            if(recette.size() > 0) {
                System.out.println("  ----"); AfficherRecette(recette);
            }

            /*** Saisie de la Boisson ***/
            System.out.println(Main.printColor("BOLD") + "  ---- \n (# : Entrer le numéro de la boisson à ajouter au Cocktail)             (0 : RETOUR)  "+Main.printColor("RESET"));
            System.out.print("  > ");
            choix = Main.SaisirInt(0, Carte.size(), "");

            // si il n'a pas cliquer sur retour
            if (choix != 0) {
                NbCannetteTotalDeja = SommeArray( recette.values());
                /*** Saisie de la Qté ***/
                maxPourc = 20 - NbCannetteTotalDeja;
                if(maxPourc < 1) maxPourc = 1;
                System.out.println("  ---- \n Combien de unité de '" + ((Boisson) Carte.get(choix)).getNom() + "' ? [1-" + maxPourc + "]");
                System.out.print("  > ");

                boolean okYenAEnStock;
                do {
                    okYenAEnStock = true;
                    Combien = Main.SaisirInt(1, maxPourc, "entre 0 et " + maxPourc + " unité");
                    if(Combien > LeStock.get((Boisson) Carte.get(choix) )) {
                        okYenAEnStock = false;
                        System.out.println("Il n'y a seulement que " + LeStock.get((Boisson) Carte.get(choix) ) + " unité de " + ((Boisson) Carte.get(choix)).getNom() + "...");
                        System.out.println(" Resaisir > ");
                    }
                } while (! okYenAEnStock );

                // ajouter a la recette
                if(recette.containsKey((Boisson) Carte.get(choix))) {
                    recette.put((Boisson) Carte.get(choix),  recette.get((Boisson) Carte.get(choix)) + Combien );
                } else {
                    recette.put((Boisson) Carte.get(choix),Combien);
                }

                NbCannetteTotalDeja = SommeArray(recette.values());


                // ** il faut au moins 2 Ingrédient
                if ( recette.size() > 1) {


                    // 4 ingrédient max et 20 canette maximum
                    if ( recette.size() < 4 && NbCannetteTotalDeja < 20 ) {
                        AfficherRecette(recette);
                        System.out.println(Main.printColor("BOLD") +"  ---- \n (1 : CONTINUER AJOUTER)      (2 : C'EST BON, CREER MON COCKTAIL)         (0 : ANNULER)  "+Main.printColor("RESET") );
                        choix = Main.SaisirInt(0, 2, "");
                    }
                    // On ne propose plus de ajouter
                    else {
                        AfficherRecette(recette);
                        System.out.println(Main.printColor("BOLD") +"  ---- \n                            (2 : C'EST BON, CREER MON COCKTAIL)         (0 : ANNULER)  " + Main.printColor("RESET"));
                        choix = Main.SaisirInt(0, 2, "");
                        if (choix == 1) choix = 2;
                    }

                    /*** CREATION ET AJOUT DU COCKTAIL ***/
                    if (choix == 2) {

                        /*** Saisie de la Qté ***/
                        System.out.print("  ---- \n * Donner mtn un nom à votre cocktail > ");

                        String Nom = Main.SaisirString("veuillez saisir un prénom correct");

                        Cocktail newC = new Cocktail(Nom, recette);
                        System.out.println("  ~~* NOUVEAU COCKTAIL ! : " + Main.printColor("BOLD") + Main.printColor("GREEN") + newC.toString() + Main.printColor("RESET"));
                       // System.out.println("  ! Remarque importante.. votre cocktail ne sera pas affiché dans le Bar si les ingrédients manquent. \n");
                        System.out.print("  ---- Confirmation : \n * (1 : CREER MON COCKTAIL)   (0 : ANNULER & RETOUR)  > "); int valide = Main.SaisirInt(0, 1, "");

                        if(valide == 1) {
                            AjouterCocktailALaListe(newC); // ajouter le nouveau cocktail créer a la liste

                        }
                        encore = false;

                        //Barman.TuVeuxQuoi(); // Retour au menu
                    } else if (choix == 0) {   encore = false;
                       // Barman.TuVeuxQuoi(); // Retour au menu
                    }

                }


            } else {
                encore = false;
            }
        }
    }

    // Afficher le contenu du bar, retourne une carte pour selectionné
    public static Map AfficherCatalogue(Commande maCommande,String Titre,String Notice, boolean okAffCocktail, boolean okAffQuantite) throws IOException, ParseException {
        Map CarteSelec = new HashMap(); int i = 1;
        System.out.println(Main.printColor("YELLOW") + "///////////////// " + Notice );
        System.out.println(String.format( "//////"+ Main.printColor("RED") + Main.printColor("BOLD") +"  -*-* %s *-*- "+ Main.printColor("RESET") + Main.printColor("YELLOW") +" ///////////////////////// \n"+ Main.printColor("RESET") ,Titre));

        ArrayList<BoissonAlcoolisee> Ba = new ArrayList<BoissonAlcoolisee>();
        ArrayList<BoissonNonAlcoolisee> Bs = new ArrayList<BoissonNonAlcoolisee>();
        for(Boisson b: LeStock.keySet()){
            String cla = b.getClass().getSimpleName();
            if(cla.equals("BoissonAlcoolisee")) {
                Ba.add((BoissonAlcoolisee) b);
            } else if(cla.equals("BoissonNonAlcoolisee")) {
                Bs.add((BoissonNonAlcoolisee) b);
            }
        }


        if(Barman.getNbBoissonsDispo() + Barman.getNbCocktailsDispo() > 0) {
            if(okAffCocktail) {
                System.out.println(Main.printColor("YELLOW") + " #### " + Main.printColor("GREEN") +"Nos COCKTAILS : "+ Main.printColor("RESET") + "(" +  Barman.getNbCocktailsDispo() + "/" + LesCocktails.size()+")");
                for (Cocktail c : LesCocktails) {
                    //System.out.println(c.estDisponible());
                    if (c.estDisponible()) {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("") + "] : * " + c);
                        CarteSelec.put(i, c);
                        i++;
                    }
                }
            }
            System.out.println(Main.printColor("YELLOW") +" #### "+ Main.printColor("GREEN") +"Nos BOISSONS : "+ Main.printColor("RESET") + "(" + Barman.getNbBoissonsDispo() + "/" + LeStock.size()+")");

            /** Boisson Alcoolisee **/
            System.out.println(Main.printColor("YELLOW") +"   ### ○ AVEC ALCOOL : " + Main.printColor("RESET"));
            for (Boisson b : Ba)  {
                int fois = LeStock.get(b) - maCommande.getQuantiteBoisson(b);
                if (fois > 0) {
                    if(okAffQuantite) {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") +i + Main.printColor("RESET") + "] : * " + b  +  Main.printColor("YELLOW") + " x " + fois  + Main.printColor("RESET"));
                    } else {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") +i + Main.printColor("RESET") + "] : * " + b);
                    }
                    CarteSelec.put(i, b);
                    i++;
                } else {
                    System.out.println("     étagère vide.. ");
                }
            }

            /** Boisson Non Alcoolisee **/
            System.out.println(Main.printColor("YELLOW") +"   ### ○ SANS ALCOOL : "+ Main.printColor("RESET"));
            for (Boisson b : Bs) {
                int fois = LeStock.get(b) - maCommande.getQuantiteBoisson(b);
                if (fois > 0) {
                    if(okAffQuantite) {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") +i + Main.printColor("RESET") + "] : * " + b  +  Main.printColor("YELLOW") + " x " + fois  + Main.printColor("RESET"));
                    } else {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") +i + Main.printColor("RESET") + "] : * " + b);
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

    public static Map AfficherCatalogue(String Titre,String Notice,  boolean okAffCocktail, boolean okAffBoisson, boolean okAffQuantite,boolean okGestion) throws IOException, ParseException {
        Map CarteSelec = new HashMap(); int i = 1;
        ArrayList<BoissonAlcoolisee> Ba = new ArrayList<BoissonAlcoolisee>();
        ArrayList<BoissonNonAlcoolisee> Bs = new ArrayList<BoissonNonAlcoolisee>();
        for(Boisson b: LeStock.keySet()){
            String cla = b.getClass().getSimpleName();
            if(cla.equals("BoissonAlcoolisee")) {
                Ba.add((BoissonAlcoolisee) b);
            } else if(cla.equals("BoissonNonAlcoolisee")) {
                Bs.add((BoissonNonAlcoolisee) b);
            }
        }


        System.out.println(Main.printColor("YELLOW") + "///////////////// " + Notice );
        System.out.println(String.format( "//////"+ Main.printColor("RED") + Main.printColor("BOLD") +"  -*-* %s *-*- "+ Main.printColor("RESET") + Main.printColor("YELLOW") +" ///////////////////////// \n"+ Main.printColor("RESET") ,Titre));
        if(Barman.getNbBoissonsDispo() + Barman.getNbCocktailsDispo() > 0) {
            if(okAffCocktail) {
                System.out.println(Main.printColor("YELLOW") + " #### " + Main.printColor("GREEN") +"Nos COCKTAILS : "+ Main.printColor("RESET"));
                for (Cocktail c : LesCocktails) {
                    //System.out.println(c.estDisponible());
                    if (c.estDisponible() || okGestion) {
                        System.out.println("    [" + i + "] : * " + c);
                        CarteSelec.put(i, c);
                        i++;
                    }
                }
                System.out.println(" ");
            }
            if(okAffBoisson) {
                System.out.println(Main.printColor("YELLOW") +" #### "+ Main.printColor("GREEN") +"Nos BOISSONS : "+ Main.printColor("RESET") );

                /** Boisson Alcoolisee **/
                System.out.println(Main.printColor("YELLOW") +"   ### ○ AVEC ALCOOL : " + Main.printColor("RESET"));
                for (Boisson b : Ba) {

                    int fois = LeStock.get(b);
                    if (fois > 0) { // si quantité supérieur
                        if (okGestion || okAffQuantite) {
                            System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("RESET") + "] : * " + b  +  Main.printColor("YELLOW") + " x " + fois  + Main.printColor("RESET"));
                        } else {
                            System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("RESET") + "] : * " + b);
                        }
                        CarteSelec.put(i, b);
                        i++;

                    } else if (okGestion) {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("RESET") + "] : * " + b + Main.printColor("RED") + Main.printColor("BOLD") + " x " + fois + Main.printColor("RESET"));
                        CarteSelec.put(i, b);
                        i++;
                    }

                }
                /** Boisson Non Alcoolisee **/
                System.out.println(Main.printColor("YELLOW") +"   ### ○ SANS ALCOOL : "+ Main.printColor("RESET"));

                for (Boisson b : Bs) {

                    int fois = LeStock.get(b);
                    if (fois > 0) { // si quantité supérieur
                        if (okGestion || okAffQuantite) {
                            System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("RESET") + "] : * " + b  +  Main.printColor("YELLOW") + " x " + fois  + Main.printColor("RESET"));
                        } else {
                            System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("RESET") + "] : * " + b);
                        }
                        CarteSelec.put(i, b);
                        i++;

                    } else if (okGestion) {
                        System.out.println("    [" + Main.printColor("BOLD") + Main.printColor("MAGENTA") + i + Main.printColor("RESET") + "] : * " + b + Main.printColor("RED") +   Main.printColor("BOLD") + " x " + fois + Main.printColor("RESET"));
                        CarteSelec.put(i, b);
                        i++;
                    }

                }
            }

        }
        else {
            System.out.println("\n                      le bar est vide... revenez plutard\n");
            Main.MENUPRINCIPALE();

        }
        return CarteSelec;

    }
    //
    public static void SelectionnerBoisson( Commande maCommande) throws IOException, ParseException { // selectionne une boisson parmi la liste de boisson et renvoie la boisson
        Map Carte = AfficherCatalogue(maCommande,"Le BAR","",true,true);
        int choix;

        if(maCommande.estVide()) System.out.println("  ---- \n (# : Entrer le numéro de la boisson)             (0 : Quittez)  ");
        else System.out.println(Main.printColor("BOLD") +"  ---- \n (# : Entrer le numéro de la boisson)          (0 : "+ Main.printColor("CYAN") + "Voir Commande ~ "+ Main.printColor("RESET") + Main.printColor("BOLD") + maCommande.CalculPrixTotal() + "€)"+ Main.printColor("RESET"));
        choix = Main.SaisirInt(0,Carte.size(), "");

        if (choix != 0) {
           // System.out.println("  ---- \n (1 : VERRE)             (0 : BOUTEILLE)  ");
            System.out.println(" ~# " + Carte.get(choix));
            System.out.print(" combien ? > ");

            int maxCombien= 100;

            if (choix > Barman.getNbCocktailsDispo()) {
                maxCombien = LeStock.get((Boisson) Carte.get(choix));
                maxCombien -= maCommande.getQuantiteBoisson((Boisson) Carte.get(choix));
            }
            if(maxCombien > 0) {
                int combien = Main.SaisirInt(0, maxCombien, "il y en a seulement " + maxCombien + "...");
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

                System.out.println("  ---- \n (0 : Continuer ma commande)     (1 : Valider ma commande)      (2 : Annuler ma commande)");
                choix = Main.SaisirInt(0, 2,"");
                switch (choix) {
                    case 0 : Barman.SelectionnerBoisson(maCommande); break;
                    case 1 : Barman.ValiderCommande(maCommande); break;
                    case 2 : Barman.AnnulerCommande(maCommande);break;
                }

            }
        }
    }

    public static void AnnulerCommande(Commande maCommande) throws IOException, ParseException {
        maCommande.Supprimer();
        Barman.TuVeuxQuoi();
    }


    /*** Valider Commander et sauvegarde ***/
    public static void ValiderCommande(Commande maCommande) throws IOException, ParseException {
         System.out.println(" stock avant validation *** " + LeStock);
        // mettre a jour le stock
         if(maCommande.getNbBoissonsTOTAL() > 0) {
             for(Boisson b : maCommande.getListeBoissons()) {
                RetirerQteBoissonDuStock(b,maCommande.getQuantiteBoisson(b));
             }
         }
         maCommande.save();
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

    public static  ArrayList<Cocktail> readJSONCocktails(FileReader file) throws IOException, ParseException {
        ArrayList<Cocktail> COCKTAILS = new ArrayList<Cocktail>();

        JSONParser jsonparser = new JSONParser();

        Object obj= jsonparser.parse(file);

        JSONObject jsonobject=(JSONObject)obj;

        JSONArray array = (JSONArray) jsonobject.get("Cocktails");
        // Pour chaque Cocktails
        for(int i=0;i<array.size();i++){
            HashMap<Boisson,Integer> recetteC = new HashMap<Boisson,Integer>();
            JSONObject cockt=(JSONObject) array.get(i);

            String nomC = (String)  cockt.get("nom");
            String couleurC = (String)  cockt.get("couleur");


            JSONArray arrayIngredient = (JSONArray) cockt.get("ingredients");
            //Pour chaq ingrédient
            for(int k = 0; k < arrayIngredient.size(); k++) {
                JSONObject boiss = (JSONObject) arrayIngredient.get(k);
                String nomB = (String) boiss.get("nom");
                String couleurB = (String) boiss.get("couleur");

                Integer contenanceB = Integer.parseInt(boiss.get("contenance").toString());

                double prixMlB = (double) boiss.get("prixMl");

                if(null == boiss.get("degreSucre")) // si c'est un alcool
                {
                    double degre = (double) boiss.get("degreAlcool");
                    BoissonAlcoolisee x = new BoissonAlcoolisee(nomB,couleurB,prixMlB,degre);
                    recetteC.put(x,contenanceB); // ajout
                } else {
                    double degre = (double) boiss.get("degreSucre");
                    BoissonNonAlcoolisee x = new BoissonNonAlcoolisee(nomB,couleurB,prixMlB,degre);
                    recetteC.put(x,contenanceB); // ajout
                }
            }

            Cocktail coco = new Cocktail(nomC,recetteC);
            COCKTAILS.add(coco);

        }

        return COCKTAILS;
    }

    public static void writeJSONCocktails(ArrayList<Cocktail> COCKTAILS) throws IOException {
        JSONObject obj=new JSONObject();


        JSONArray liste_Cocktails=new JSONArray(); // JSON liste
        for(Cocktail c: COCKTAILS){
            JSONObject obj_Cock=new JSONObject();
            obj_Cock.put("nom",c.getNom());
            obj_Cock.put("couleur",c.getCouleur());

            JSONArray l_ingredient=new JSONArray();
            for(Boisson b : c.getComposants()) {
                JSONObject obj_interne=new JSONObject();
                obj_interne.put("nom",b.getNom());
                obj_interne.put("couleur",b.getCouleur());
                obj_interne.put("prixMl",(double) b.getPrixMl());

                String cla = b.getClass().getSimpleName();
                if(cla.equals("BoissonAlcoolisee")) { // si alcolisee
                    obj_interne.put("degreAlcool",(double) ((BoissonAlcoolisee) b).getDegreAlcool());

                } else if(cla.equals("BoissonNonAlcoolisee")) { // si sucré
                    obj_interne.put("degreSucre",(double) ((BoissonNonAlcoolisee) b).getDegreSucre());
                }

                obj_interne.put("contenance",(int) c.getRecetteNB(b));
                l_ingredient.add(obj_interne); // Ajout de la boisson au cocktail

            } // end for ingredients

            obj_Cock.put("ingredients",l_ingredient);
            liste_Cocktails.add(obj_Cock); // ajout du cocktail AU JSON
        } // end for cocktail c
        obj.put("Cocktails",liste_Cocktails);


        // Go
        try(FileWriter file=new FileWriter("cocktail.json")){
            file.write(obj.toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }


    public static  HashMap<Boisson,Integer> readJSONBoissonsStock(FileReader file) throws IOException, ParseException {
        HashMap<Boisson,Integer> StockStock = new HashMap<Boisson,Integer>();
        JSONParser jsonparser = new JSONParser();
        Object obj= jsonparser.parse(file);
        JSONObject jsonobject=(JSONObject)obj;

        JSONArray array = (JSONArray) jsonobject.get("BoissonAlcoolisee");
        for(int i=0;i<array.size();i++){
            JSONObject boisson=(JSONObject) array.get(i);
            String nom = (String) boisson.get("nom");

            String couleur = (String) boisson.get("couleur");
            Double prix = (Double) boisson.get("prixMl");
            Double degre = (Double) boisson.get("degreAlcool");
            Integer q = Integer.parseInt(boisson.get("quantite").toString());

            BoissonAlcoolisee a = new BoissonAlcoolisee(nom,couleur,prix,degre);
            StockStock.put(a,q);

        }
        JSONArray array2 = (JSONArray) jsonobject.get("BoissonNonAlcoolisee");
        for(int i=0;i<array.size();i++){

            JSONObject boisson=(JSONObject) array2.get(i);
            String nom = (String) boisson.get("nom");

            String couleur = (String) boisson.get("couleur");
            Double prix = (Double) boisson.get("prixMl");
            Double degre = (Double) boisson.get("degreSucre");
            Integer q = Integer.parseInt(boisson.get("quantite").toString());

            BoissonNonAlcoolisee a = new BoissonNonAlcoolisee(nom,couleur,prix,degre);
            StockStock.put(a,q);

        }

        return StockStock;
    }

    public static void writeJSONBoissonsStock(HashMap<Boisson,Integer> StockStock) throws IOException {
        JSONObject obj=new JSONObject();


        //** Séparé les alccol et les sucré
        ArrayList<BoissonAlcoolisee> Ba = new ArrayList<BoissonAlcoolisee>();
        ArrayList<BoissonNonAlcoolisee> Bs = new ArrayList<BoissonNonAlcoolisee>();
        for(Boisson b: StockStock.keySet()){
            String cla = b.getClass().getSimpleName();
            if(cla.equals("BoissonAlcoolisee")) {
                Ba.add((BoissonAlcoolisee) b);
            } else if(cla.equals("BoissonNonAlcoolisee")) {
                Bs.add((BoissonNonAlcoolisee) b);
            }
        }


        JSONArray liste_BoissonsAlcoolisee=new JSONArray();
        for(BoissonAlcoolisee b: Ba){
            JSONObject obj_interne=new JSONObject();
            obj_interne.put("nom",b.getNom());
            obj_interne.put("couleur",b.getCouleur());
            obj_interne.put("prixMl",(double) b.getPrixMl());
            obj_interne.put("degreAlcool",(double) b.getDegreAlcool());
            obj_interne.put("quantite",StockStock.get(b));
            liste_BoissonsAlcoolisee.add(obj_interne);
        }
        obj.put("BoissonAlcoolisee",liste_BoissonsAlcoolisee);

        JSONArray liste_BoissonsNonAlcoolisee=new JSONArray();
        for(BoissonNonAlcoolisee b: Bs){
            JSONObject obj_interne=new JSONObject();
            obj_interne.put("nom",b.getNom());
            obj_interne.put("couleur",b.getCouleur());
            obj_interne.put("prixMl",(double) b.getPrixMl());
            obj_interne.put("degreSucre",(double) b.getDegreSucre());
            obj_interne.put("quantite",StockStock.get(b));
            liste_BoissonsNonAlcoolisee.add(obj_interne);
        }
        obj.put("BoissonNonAlcoolisee",liste_BoissonsNonAlcoolisee);

        // Go
        try(FileWriter file=new FileWriter("boisson.json")){
            file.write(obj.toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void Abientot(Boolean saveStock,Boolean saveCocktail ) throws IOException, ParseException {

        if(saveStock) {
            // sauvegarder le stock
            writeJSONBoissonsStock(LeStock);
        }
        if(saveCocktail) {
            // sauvegarder les Cocktail
            writeJSONCocktails(LesCocktails);
        }
        // retoour au menu
        Main.MENUPRINCIPALE();
    }



    public static void JeGere() throws IOException, ParseException {
        Map Carte = Barman.AfficherCatalogue("GESTION","ici tu peux gèrer le bar",true,true,true,true);
        System.out.println(Main.printColor("BOLD") +"  ---- \n ( 1 : *GERER-STOCK-DE-BOISSONS* )     ( 2 : *GERER-LES-COCKTAILS* )      (0 : *ENREGISTER-&-RETOUR* ) " + Main.printColor("RESET"));

        int choix = Main.SaisirInt(0,2,"");
        while (choix == 1) {
            Carte = Barman.AfficherCatalogue("GESTION DES BOISSONS","mise à jour des boissons",false,true,true,true);

                System.out.println(Main.printColor("BOLD") + "  ---- \n (1 : AUGMENTER QUANTITE d'une boisson)   (2 : AJOUTER NOUVELLE boisson au Stock)   (3 : MODIFIER Infos boisson)   (4 : SUPPRIMER une boisson)   (0 : *RETOUR* )" + Main.printColor("RESET"));
                choix = Main.SaisirInt(0, 3, "");
                //** Augmenter Quantité d'une boisson
                if (choix == 1) {
                    System.out.println(" (# : Entrer le numéro de la boisson à augmenter)          (0 : Retour)");
                    choix = Main.SaisirInt(0, Carte.size(), "une bonne chiffre pour selectionner une boisson.. ou saisissez 0 pour faire retour");
                    if(choix != 0) {
                        System.out.println(" ~# " + ((Boisson) Carte.get(choix)).getNom());
                        System.out.print(" * En ajouter combien > ");
                        int cb = Main.SaisirInt(0, 1000, "n'abuse pas..");
                        Barman.AjouterQteBoissonAuStock((Boisson) Carte.get(choix), cb);
                    }
                    choix = 1;
                }
                //** Ajouter nouvelle boisson, avec quantité
                else if (choix == 2) {
                    // creer l object boisson
                    System.out.print(" * NOM > "); String newNom = Main.SaisirString("");
                    System.out.print(" * COULEUR > "); String newCouleur = Main.SaisirString("");
                    System.out.print(" * PRIX au Ml > "); double newPrixMl = Main.SaisirDouble(0.0,1.0,"n'oubliez pas que c'est le prix au Ml, n'abusez pas. ");
                    System.out.print(" * [1 : BOISSON-ALCOOLISEE] ou [2: BOISSON-NON-ALCOOLISEE] > "); int typeInt = Main.SaisirInt(1,2,"");

                    Boisson newB = null;
                    if(typeInt == 1) {
                        System.out.print(" * DEGRE Alcool > "); double newDegre = Main.SaisirDouble(0.0,150,"");
                         newB = new BoissonAlcoolisee(newNom,newCouleur,newPrixMl,newDegre);
                    } else if(typeInt == 2) {
                        System.out.print(" * DEGRE Sucre > "); double newDegre = Main.SaisirDouble(0.0,150,"");
                        newB = new BoissonNonAlcoolisee(newNom,newCouleur,newPrixMl,newDegre);
                    }
                    // la quantité
                    System.out.print(" * En ajouter combien > "); int cb = Main.SaisirInt(0, 1000, "n'abuse pas..");
                    System.out.print(" *** NOUVELLE BOISSON ! :  " + newB.toString());

                    Barman.AjouterQteBoissonAuStock(newB,cb);
                    System.out.println("                                                  (0 : Retour)");
                    String valide = Main.SaisirString("");
                    choix = 1;
                }
                //** Modifier une boisson
                else if(choix == 3) {
                    System.out.println(" (# : Entrer le numéro de la boisson à modifier)          (0 : Retour)");
                    choix = Main.SaisirInt(0, Carte.size(), "une bonne chiffre pour selectionner une boisson.. ou saisissez 0 pour faire retour");
                    if(choix != 0) {
                        System.out.println(" ~# " + ((Boisson) Carte.get(choix)).getNom() + " | couleur=" +  ((Boisson) Carte.get(choix)).getCouleur()+ " | PrixMl=" +  ((Boisson) Carte.get(choix)).getPrixMl());
                        System.out.println(" (1 : Le nom)     ( 2 : Couleur)     (3 : Prix au Ml)          (0 : Retour)");
                        int choixAttr = Main.SaisirInt(0, 4, "");
                        if(choixAttr == 1) {
                            System.out.print(" nouveau nom > ");
                            String newName = Main.SaisirString("");
                            Barman.ModifierNomBoissonAuStock((Boisson) Carte.get(choix), newName);
                        } else if(choixAttr == 2) {
                            System.out.print(" nouvelle couleur (en hexadecimal svp) > ");
                            String  newCouleur = Main.SaisirString("");
                            Barman.ModifierCouleurBoissonAuStock((Boisson) Carte.get(choix), newCouleur);
                        } else if(choixAttr == 3){
                            System.out.print(" nouvelle valeur du prix au ml > ");
                            double newPrixMl = Main.SaisirDouble(0,10,"n'abusons pas");
                            Barman.ModifierPrixMlBoissonAuStock((Boisson) Carte.get(choix), newPrixMl);
                        }

                    }
                    choix = 1;


                }
                //** Supprimer une boisson
                else if (choix == 4) {
                    System.out.println(" (# : Entrer le numéro de la boisson à supprimer)          (0 : Retour)");
                    int choixSuppr = Main.SaisirInt(0, Carte.size(), "une bonne chiffre pour selectionner une boisson.. ou saisissez 0 pour faire retour");
                    if(choix != 0) {
                        System.out.println(" ~# " + ((Boisson) Carte.get(choixSuppr)).getNom());
                        System.out.print(" * (1 : OUI, SUPPRIMER)   (0 : ANNULER)  > ");
                        choix = Main.SaisirInt(0, 1, "");

                        if (choix == 1) {
                            // delete
                            RemoveBoissonDuStock((Boisson) Carte.get(choixSuppr));
                        }
                    }
                    choix = 1;
                }
                //** Retour
                else if (choix == 0) {
                    JeGere();
                }

        }
        while (choix == 2){
            Carte = Barman.AfficherCatalogue("GESTION DES COCKTAILS","Mise à jour des cocktails",true,false,true,true);

            System.out.println(Main.printColor("BOLD") + "  ---- \n (1 : CREER un Cocktail )     (2 : SUPPRIMER un Cocktail )     (0 : *RETOUR* )" + Main.printColor("RESET"));
            choix = Main.SaisirInt(0, 3, "");

            if(choix == 1) {
                //** Creer un cocktails
                Barman.ComposerCocktail();
                choix = 2;

            } else if (choix == 2) {
                //** Supprimer cocktails
                System.out.println(" (# : Entrer le numéro du Cocktails à supprimer)          (0 : Retour)");
                choix = Main.SaisirInt(0, Carte.size(), "une bonn chiffre pour selectionner le cocktail.. ou saisissez 0 pour faire retour");
                if(choix != 0) {
                    System.out.println(" ~# " + ((Cocktail) Carte.get(choix)).getNom());
                    System.out.print(" * (1 : OUI, SUPPRIMER)   (0 : ANNULER)  > ");
                    choix = Main.SaisirInt(0, 1, "");

                    if (choix == 1) {
                        // delete
                        RetirerCocktailDeLaListe((Cocktail) Carte.get(choix));
                    }
                }
                choix = 2;


            }  //** Retour
            else if (choix == 0) {
                JeGere();
            }



        }
        if(choix == 0) {
            Barman.Abientot(true,true);
        }

    }

}
