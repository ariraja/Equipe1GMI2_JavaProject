package com.cytech.ingredients;

/*utile pour JSON*/
import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;

import java.util.Arrays;

import java.util.HashMap;

public class Test {

    public static void testBoisson() {
        // creer un boisson et afficher un boisson
        //creer un coktail et afficher un cocktail
        Boisson a = new BoissonAlcoolisee("Braja","rouge",0.02,52);
        Boisson b = new BoissonAlcoolisee("Burvoy","bleu",0.05,70);
        Boisson c = new BoissonNonAlcoolisee("BSaid","vert",0.3,24);
       // System.out.println(b);
        //System.out.println(c);

        Boisson[] ListB = {a,b,c};

        HashMap<Boisson, Double> recette = new HashMap<Boisson, Double>();
        recette.put(a,50.0);
        recette.put(b,50.0);
        Cocktail coco1 = new Cocktail("Cocktail One",recette);
        System.out.println(coco1);


    }

    public static void testCocktail() {


    }

    private static void saveCommande(String id, Boisson[] commande) throws IOException {
        JSONObject obj=new JSONObject();
        JSONArray liste_boissons=new JSONArray();
        for(Boisson b:commande){
            JSONObject obj_interne=new JSONObject();
           obj_interne.put("nom",b.nom);
            obj_interne.put("contenance",b.contenance);
            obj_interne.put("couleur",b.getCouleur());
            obj_interne.put("prix",b.getPrix());
            liste_boissons.add(obj_interne);
        }
        obj.put("l_Boissons",liste_boissons);

        try(FileWriter file=new FileWriter("commande_" + id +".json")){
            file.write(obj.toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Commande n°" + id + " sauvegardée");
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

            Boisson a = new BoissonAlcoolisee(nom,couleur,prix,degre);
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

            Boisson a = new BoissonNonAlcoolisee(nom,couleur,prix,degre);
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


    public static void main() throws IOException, ParseException {

        HashMap<Boisson,Integer> LeStock = readJSONBoissonsStock(new FileReader("boisson.json"));
        System.out.println(LeStock);
        System.out.println("###");
        writeJSONBoissonsStock(LeStock);
        System.out.println("###");

        LeStock = readJSONBoissonsStock(new FileReader("boisson.json"));
        System.out.println(LeStock);
        System.out.println("###");
        writeJSONBoissonsStock(LeStock);
        System.out.println("###");
        /*Boisson test =new BoissonNonAlcoolisee("Eau","#1586E0",0.002,50);
        Boisson_liste.add(test);
        writeJSONBoisson(Boisson_liste);*/
        //JSONArray a = (JSONArray) parser.parse(new FileReader("boisson.json"));
       //JSONObject objet = (JSONObject) parser.parse(new FileReader("h:\\Documents\\Cours ING1 GM\\Semestre 2\\APOO\\Java\\Java Projet\\• Projet - Bar à cocktail\\boisson.json"));


    /*Test pour écrire dans un json*/
       /* Boisson rc = new Boisson("Rhum cubain", 100.0D, "#AA3E10", 0.04D);
        Boisson jdc =new Boisson("Jus de citron", 100.0D, "#ECEB2E", 0.005D);
        Boisson eg =new Boisson("Eau gazeuse", 100.0D, "#1586E8", 0.005D);
        Boisson sdsdc =new Boisson("Sirop de sucre de canne", 100.0D, "#CFC1BF", 0.015D);
        Boisson rb = new Boisson("Rhum Blanc", 100.0D, "#EEE9E8", 0.05D);

        Boisson[] c ={rc, rb, eg};
        String id=getDateToday();
        System.out.println("ID commande n°: " + id );
        saveCommande(id,c);*/
    }
}
