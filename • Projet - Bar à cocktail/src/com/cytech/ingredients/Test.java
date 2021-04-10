package com.cytech.ingredients;

import java.io.*;
import java.util.Calendar;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    public static void testBoisson() {
        // creer un boisson et afficher un boisson
        //creer un coktail et afficher un cocktail
        Boisson a = new Boisson("raja",1000,"rouge",15);
        System.out.println(a);
        Boisson b = new BoissonAlcoolisee("urvoy",5,"vert",15,5);
        System.out.println(b);
        Boisson c = new BoissonNonAlcoolisee("said",100,"rouge",15,1);
        System.out.println(c);
Boisson[] l = {a,c};
//Cocktail cc = new Cocktail("coco",0,"bleu",l);
//System.out.println(cc);
    }

    public static void testCocktail() {


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
        String minute=Integer.toString(cal.get(Calendar.MINUTE));
        String seconde=Integer.toString(cal.get(Calendar.SECOND));

        return annee+mois+jour+"-"+heure+minute+seconde;
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

    public static ArrayList<Boisson> readJSONBoisson(FileReader file) throws IOException, ParseException {
        ArrayList<Boisson> listeBoissons = new  ArrayList<Boisson>();
        JSONParser jsonparser = new JSONParser();
        Object obj= jsonparser.parse(file);
        JSONObject jsonobject=(JSONObject)obj;

        JSONArray array=(JSONArray)jsonobject.get("boisson");
        for(int i=0;i<array.size();i++){
            JSONObject boisson=(JSONObject) array.get(i);

            String nom = (String) boisson.get("nom");
            Long contenance = (Long) boisson.get("contenance");//pb ici à la base c un double!!!
            String couleur = (String) boisson.get("couleur");
            Double prix = (Double) boisson.get("prix");

            Boisson a = new Boisson(nom,contenance,couleur,prix);
            System.out.println(a.toString());
            listeBoissons.add(a);
        /*System.out.println("Nom boisson :"+nom);
            System.out.println("Volume boisson :"+contenance);
            System.out.println("Couleur boisson :"+couleur);
            System.out.println("Prix boisson :"+prix);*/
        }
        return listeBoissons;
    }

    private static void writeJSONBoisson(ArrayList<Boisson> arrayB) throws IOException {
        JSONObject obj=new JSONObject();
        JSONArray liste_boissons=new JSONArray();
        for(Boisson b:arrayB){
            JSONObject obj_interne=new JSONObject();
            obj_interne.put("nom",b.nom);
            obj_interne.put("contenance",b.contenance);
            obj_interne.put("couleur",b.getCouleur());
            obj_interne.put("prix",b.getPrix());
            liste_boissons.add(obj_interne);
        }
        obj.put("l_Boissons",liste_boissons);

        try(FileWriter file=new FileWriter("màj_boisson.json")){
            file.write(obj.toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("Stock de boisson màj");
    }


    public static void main(String[] args) throws IOException, ParseException {
        FileReader file=new FileReader("boisson.json");
        ArrayList<Boisson> Boisson_liste=readJSONBoisson(file);
        System.out.println(Boisson_liste);


        //TODO fonction write json et qui écrit nouveau arraylist
        Boisson test =new Boisson("Eau",100,"#1586E0",0.0001);
        Boisson_liste.add(test);
        writeJSONBoisson(Boisson_liste);
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
