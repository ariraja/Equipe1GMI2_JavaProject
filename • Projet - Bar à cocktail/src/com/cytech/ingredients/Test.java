package com.cytech.ingredients;

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
}
