package com.cytech.ingredients;

public class Test {

    public static void testBoisson() {
        // creer un boisson et afficher un boisson
        //creer un coktail et afficher un cocktail
        Boisson a = new Boisson("no",1000,"rouge",15);
        System.out.println(a);
        Boisson c = new BoissonNonAlcoolisee("naaaa",100,"r",15,5);
        System.out.println(c);
Boisson[] l = {a,c};
Cocktail cc = new Cocktail("coco",0,"bleu",l);
System.out.println(cc);
    }

    public static void testCocktail() {


    }
}
