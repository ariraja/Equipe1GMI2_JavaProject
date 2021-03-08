package com.cytech.ingredients;

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
}
