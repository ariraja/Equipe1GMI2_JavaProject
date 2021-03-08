package com.cytech.ingredients;
import java.util.Scanner;
import java.io.IOException;

public class Main {

    public static int SaisirInt(int a, int b,String error_mess) { // saisir un entier entre a et b inclus
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();

        while((value < a) || (value > b)) {
            if ((value < a) || (value > b)) System.out.print(" /!\\ Veuillez resaisir.. " + error_mess);
            value = sc.nextInt();

        }
        return value;
    }

    public static void MENUPRINCIPALE() {


    }
    public static void main(String[] args) {/*
        System.out.println("/////////////////////////////////////////////");
        System.out.println("/////////// Mlamali SAIDSALIMO //////////////");
        System.out.println("//////////   Ari RAJAOEFERA   ///////////////");
        System.out.println("/////////  Guillaume URVOY   ////////////////");
        System.out.println("//////// Jonathan LOUAMBA   /////////////////");
        System.out.println("/////////////////////////////////////////////");
        System.out.println("////// * BAR A COCKTAIL * ///////////////////");
        System.out.println("/////////////////////////////////////////////");
        System.out.println("////  1 :  ENTRER           //////////////");
        System.out.println("/////////////////////////////////////////////");
        System.out.println("// >");
        // int choix = SaisirInt(1,1);*/

        Boisson a = new Boisson("raja",100,"rouge",15);   Barman.AjouterBoissonAuStock(a,2);
        Boisson b = new BoissonAlcoolisee("urvoy",250,"vert",15,5); Barman.AjouterBoissonAuStock(b,5);
        Boisson c = new BoissonNonAlcoolisee("said",72,"rouge",15,1); Barman.AjouterBoissonAuStock(c,10);

        Cocktail coco1 = new Cocktail("coco1",11,"bleu",new Boisson[] {a,b,c}); Barman.AjouterCocktailALaListe(coco1);
        Cocktail coco2 = new Cocktail("coco2",22,"bleu",new Boisson[] {a,b});Barman.AjouterCocktailALaListe(coco2);
/*
        Barman.MettreAJourDisponibiliteCocktails();
        System.out.println(coco1.estDisponible());
        Barman.AfficherCatalogue();

        Barman.RetirerBoissonDuStock(b,5);

        System.out.println(coco1.estDisponible());

        Barman.AfficherCatalogue();
*/
        Barman.SePresenter();
        Barman.TuVeuxQuoi();


    }
}
