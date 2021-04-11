package com.cytech.ingredients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        else if(choix == 2) Barman.ComposerCocktail(maCommande);
        else if(choix == 0) Barman.Abientot(maCommande);

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
    private static void ComposerCocktail(Commande maCommande) throws IOException, ParseException {
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
                        if(SommeArray( recette.values()) < 100) {
                            System.out.println("  ---- Hmm.. les taux ont été réajusté. ");
                            recette = NormaliserRecette(recette);
                            AfficherRecette(recette);

                        }
                        /*** Saisie de la Qté ***/
                        System.out.print("  ---- \n * Donner mtn un nom à votre cocktail > ");

                        String Nom = Main.SaisirString("veuillez saisir un prénom correct");

                        Cocktail newC = new Cocktail(Nom, recette);
                        AjouterCocktailALaListe(newC); // ajouter le nouveau cocktail créer a la liste
                        Barman.TuVeuxQuoi(); // Retour au menu
                    } else if (choix == 0) {
                        Barman.TuVeuxQuoi(); // Retour au menu
                    }

                }


            } else {
                Barman.TuVeuxQuoi(); // Retour au menu
            }
        }
    }

    // Afficher le contenu du bar, retourne une carte pour selectionné
    public static Map AfficherCatalogue(Commande maCommande,String Titre,String Notice, boolean okAffCocktail, boolean okAffQuantite) throws IOException, ParseException {
        Map CarteSelec = new HashMap(); int i = 1;
        System.out.println(Main.printColor("YELLOW") + "///////////////// " + Notice );
        System.out.println(String.format( "//////"+ Main.printColor("RED") + Main.printColor("BOLD") +"  -*-* %s *-*- "+ Main.printColor("RESET") + Main.printColor("YELLOW") +" ///////////////////////// \n"+ Main.printColor("RESET") ,Titre));
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

    public static Map AfficherCatalogue(String Titre,String Notice) throws IOException, ParseException {
        Map CarteSelec = new HashMap(); int i = 1;
        System.out.println(Main.printColor("YELLOW") + "///////////////// " + Notice );
        System.out.println(String.format( "//////"+ Main.printColor("RED") + Main.printColor("BOLD") +"  -*-* %s *-*- "+ Main.printColor("RESET") + Main.printColor("YELLOW") +" ///////////////////////// \n"+ Main.printColor("RESET") ,Titre));
        if(Barman.getNbBoissonsDispo() + Barman.getNbCocktailsDispo() > 0) {

                System.out.println(" #### Nos Cocktails : " + Barman.getNbCocktailsDispo()+ "/" + LesCocktails.size());
                for (Cocktail c : LesCocktails) {
                    //System.out.println(c.estDisponible());
                    if (c.estDisponible()) {
                        System.out.println("    [" + i + "] : * " + c);
                        CarteSelec.put(i, c);
                        i++;
                    }
                }

            System.out.println(" #### Nos Boissons : " + Barman.getNbBoissonsDispo() + "/" + LeStock.size());
            for (Boisson b : LeStock.keySet()) {
                int fois = LeStock.get(b);
                if (fois > 0) {
                        System.out.println("    [" + i + "] : * " + b + " x " + fois);

                } else {
                    System.out.println(Main.printColor("RED") + "    [" + i + "] : * " + b + " x " + fois + Main.printColor("RESET"));
                }
                CarteSelec.put(i, b);
                i++;
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
        else System.out.println("  ---- \n (# : Entrer le numéro de la boisson)          (0 : Voir Commande ~ " + maCommande.CalculPrixTotal() + "€)");
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

    private static void writeJSONBoissonsStock(HashMap<Boisson,Integer> StockStock) throws IOException {
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
            obj_interne.put("prixMl",b.getPrixMl());
            obj_interne.put("degreAlcool",b.getDegreAlcool());
            obj_interne.put("quantite",StockStock.get(b));
            liste_BoissonsAlcoolisee.add(obj_interne);
        }
        obj.put("BoissonAlcoolisee",liste_BoissonsAlcoolisee);

        JSONArray liste_BoissonsNonAlcoolisee=new JSONArray();
        for(BoissonNonAlcoolisee b: Bs){
            JSONObject obj_interne=new JSONObject();
            obj_interne.put("nom",b.getNom());
            obj_interne.put("couleur",b.getCouleur());
            obj_interne.put("prixMl",b.getPrixMl());
            obj_interne.put("degreSucre",b.getDegreSucre());
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

    public static void Abientot( Commande maCommande) throws IOException, ParseException {


        // sauvegarder le stock
        writeJSONBoissonsStock(LeStock);
        // sauvegarder les cocktails crée ?

        // retoour au menu
        Main.MENUPRINCIPALE();
    }
    public static void JeGere() throws IOException, ParseException {
        Commande maCommande = new Commande(getDateToday());
        Map Carte = Barman.AfficherCatalogue("GESTION","ici tu peux gèrer le bar");
        System.out.println(Main.printColor("BOLD") +"  ---- \n ( 1 : *GERER-STOCK-DE-BOISSONS* )     ( 2 : *GERER-LES-COCKTAILS* )      (0 : *ENREGISTER-&-RETOUR* ) " + Main.printColor("RESET"));

        int choix = Main.SaisirInt(0,2,"");
        while (choix == 1) {
            Carte = Barman.AfficherCatalogue("GESTION","ici tu peux gèrer le bar");
            //if (choix == 1) {
                System.out.println("  ---- \n (1 : AUGMENTER QUANTITE d'une boisson)   (2 : AJOUTER NOUVELLE boisson au Stock)   (3 : SUPPRIMER une boisson)   (0 : *RETOUR* )");
                choix = Main.SaisirInt(0, 3, "");
                //** Augmenter Quantité d'une boisson
                if (choix == 1) {
                    System.out.println(" (# : Entrer le numéro de la boisson à augmenter)          (0 : Retour)");
                    choix = Main.SaisirInt(0, Carte.size(), "une bonne chiffre pour selectionner une boisson.. ou saisissez 0 pour faire retour");
                    System.out.println(" ~# " + ((Boisson) Carte.get(choix)).getNom() );
                    System.out.print(" * En ajouter combien > "); int cb = Main.SaisirInt(0, 1000, "n'abuse pas..");
                    Barman.AjouterQteBoissonAuStock((Boisson) Carte.get(choix),cb);
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
                    choix = Main.SaisirInt(1,000,"");
                    choix = 1;
                }
                //** Supprimer une boisson
                else if (choix == 3) {
                    System.out.println(" (# : Entrer le numéro de la boisson à supprimer)          (0 : Retour)");
                    choix = Main.SaisirInt(0, Carte.size(), "une bonne chiffre pour selectionner une boisson.. ou saisissez 0 pour faire retour");
                    System.out.println(" ~# " + ((Boisson) Carte.get(choix)).getNom() );
                    System.out.print(" * (1 : OUI, SUPPRIMER)   (0 : ANNULER)  > "); choix = Main.SaisirInt(0, 1, "");

                    if(choix == 1) {
                        // delete
                        RemoveBoissonDuStock((Boisson) Carte.get(choix));
                    }
                    choix = 1;
                }
                //** Retour
                else if (choix == 0) {
                    JeGere();
                }
          //  }
        }
        if(choix == 2) {
            //** Creer un cocktails
            //** Supprimer cocktails

        }
        else if(choix == 0) Barman.Abientot(maCommande);

    }

}
