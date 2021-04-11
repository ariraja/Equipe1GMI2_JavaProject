package com.cytech.ingredients;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Commande {
    public String getIdc() {
        return idc;
    }

    private String idc; // identifiant de la commande // TODO date heure de ajd
    private HashMap<Boisson, Integer> commandeBoissons = new HashMap<Boisson, Integer>();
    private HashMap<Cocktail, Integer> commandeCocktails = new HashMap<Cocktail, Integer>();

    // Contructeur
    public Commande(String idc) {
        this.idc = idc;
    }

    // Ajouter un object dans la commande
    public void Ajouter(Object o, int cb ) {
        if(cb > 0) {

            String cla = o.getClass().getSimpleName();
            List lb = Arrays.asList(new String[]{"Boisson", "BoissonAlcoolisee", "BoissonNonAlcoolisee"});

            if (lb.contains(cla)) {
                Boisson b = (Boisson) o;

                if (this.commandeBoissons.containsKey(b)) {
                    int qte = this.commandeBoissons.get(b);
                    qte += cb;
                    this.commandeBoissons.put(b, qte);
                } else {
                    this.commandeBoissons.put(b, cb);
                }


            } else if (cla.compareTo("Cocktail") == 0) {
                Cocktail c = (Cocktail) o;

                if (this.commandeCocktails.containsKey(c)) {
                    int qte = this.commandeCocktails.get(c);
                    qte += cb;
                    this.commandeCocktails.put(c, qte);
                } else {
                    this.commandeCocktails.put(c, cb);
                }


            }
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
        int i = 1;
        System.out.println(Main.printColor("YELLOW") +" ////////////// " + this.getIdc());
        System.out.println(Main.printColor("YELLOW") +" ///// "+Main.printColor("CYAN") + Main.printColor("BOLD") + " *** VOTRE COMMANDE *** "+Main.printColor("RESET")+Main.printColor("YELLOW") +"////////////////////////"+Main.printColor("RESET"));
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

        System.out.println(Main.printColor("YELLOW") +"\n ///// *** PRIX TOTAL : "+ Main.printColor("RED") + + this.CalculPrixTotal() + "€ "+Main.printColor("YELLOW") + "***"+Main.printColor("RESET") );
        System.out.println(Main.printColor("YELLOW") +" //////////////"+Main.printColor("RESET"));
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

    // Enregistrer la commandes
    public void save() throws IOException {
        JSONObject obj=new JSONObject();

        JSONArray liste_Boissons=new JSONArray();
        for(Boisson b: this.getListeBoissons()){
            JSONObject obj_interne=new JSONObject();
            obj_interne.put("nom",b.getNom());
            obj_interne.put("prix",b.getPrix());
            obj_interne.put("combien",this.commandeBoissons.get(b));
            liste_Boissons.add(obj_interne);
        }
        obj.put("Boissons", liste_Boissons);

        JSONArray liste_Cocktails=new JSONArray();
        for(Cocktail b: this.getListeCocktails()){
            JSONObject obj_interne=new JSONObject();
            obj_interne.put("nom",b.getNom());
            obj_interne.put("prix",b.getPrix());
            obj_interne.put("combien",this.commandeCocktails.get(b));
            liste_Cocktails.add(obj_interne);
        }
        obj.put("Cocktails", liste_Cocktails);
        obj.put("PrixTotal", this.CalculPrixTotal());
        try(FileWriter file=new FileWriter("commandes_backups/commande_" + this.getIdc() +".json")){
            file.write(obj.toString());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }



    // Supprimer la commande
    public void Supprimer() {
        this.commandeCocktails.clear();
        this.commandeBoissons.clear();
    }
}
