package com.cytech.ingredients;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    public static String SaisirString(String error_mess) {
        Scanner sc = new Scanner(System.in);
        String value = sc.nextLine();

        return value;
    }
    public static int SaisirInt(int a, int b,String error_mess) { // saisir un entier entre a et b inclus
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();

        while((value < a) || (value > b)) {
            if ((value < a) || (value > b)) System.out.print(" /!\\ Veuillez resaisir.. " + error_mess + " > ");
            value = sc.nextInt();

        }

        return value;
    }
    public static double SaisirDouble(double a, double b,String error_mess) { // saisir un entier entre a et b inclus
        Scanner sc = new Scanner(System.in);
        double value = sc.nextDouble();

        while((value < a) || (value > b)) {
            if ((value < a) || (value > b)) System.out.print(" /!\\ Veuillez resaisir.. " + error_mess+ " > ");
            value = sc.nextDouble();

        }
        return value;
    }

    public static void MENUPRINCIPALE() {
        System.out.println("//////////////////////////////////////////////");
        System.out.println("///////////// Mlamali SAIDSALIMO /////////////");
        System.out.println("////////////   Ari RAJAOEFERA   //////////////");
        System.out.println("///////////  Guillaume URVOY   ///////////////");
        System.out.println("////////// Jonathan LOUAMBA   ////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////// * BAR A COCKTAIL * //////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////  1 : BIENVENUE (CLIENT)   /////////////");
        System.out.println("/////  ---                      //////////////");
        System.out.println("////  2 : GESTION (BARMAN)     ///////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.print("// > ");
      //int choix = SaisirInt(1,2,"");

        int choix = 1;
        ClearConsole();
        Barman.initBoissonsJSON();
        Barman.initCocktailsJSON();
        Barman.SePresenter();
        Barman.TuVeuxQuoi();

    }
    public static void ClearConsole() {


    }
    public static void main(String[] args) {


      MENUPRINCIPALE();

    }
}
