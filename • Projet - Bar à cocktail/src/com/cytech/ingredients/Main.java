package com.cytech.ingredients;
import org.json.simple.parser.ParseException;

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
    public static String printColor(String c) {
        switch (c) {
            case "RESET" : return "\033[0m";
            case "BOLD" : return "\033[1m";
            case "BLACK" : return "\033[30m";
            case "RED" : return "\033[31m";
            case "GREEN" : return "\033[32m";
            case "YELLOW" : return "\033[33m";
            case "BLUE" : return "\033[34m";
            case "MAGENTA" : return "\033[35m";
            case "CYAN" : return "\033[36m";
            case "WHITE" : return "\033[37m";
            default: return "\033[0m";
        }

    }


    public static void MENUPRINCIPALE() throws IOException, ParseException {
        System.out.println(printColor("YELLOW") + "//////////////////////////////////////////////");
        System.out.println("/////////////"+ printColor("BOLD") +" Mlamali SAIDSALIMO "+ printColor("YELLOW") +"/////////////");
        System.out.println("////////////"+ printColor("BOLD") +"   Ari RAJAOEFERA   "+ printColor("YELLOW") +"//////////////");
        System.out.println("///////////"+ printColor("BOLD") +"  Guillaume URVOY   "+ printColor("YELLOW") +"///////////////");
        System.out.println("//////////"+ printColor("BOLD") +" Jonathan LOUAMBA   "+ printColor("YELLOW") +"////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("////////"+ printColor("RESET") +" * BAR A COCKTAIL * "+ printColor("YELLOW") +"//////////////////");
        System.out.println("//////////////////////////////////////////////");
        System.out.println("//////"+ printColor("RESET") + "  1 : BIENVENUE (CLIENT)   "+ printColor("YELLOW") +"/////////////");
        System.out.println("/////"+ printColor("RESET") +"  2 : GESTION (BARMAN)     "+ printColor("YELLOW") +"//////////////");
        System.out.println("////  ---                      ///////////////");
        System.out.println("///"+ printColor("RESET") +"  0 : QUITTER              "+ printColor("YELLOW") +"////////////////");
        System.out.print("//"+ printColor("RESET") +" > ");
        int choix = SaisirInt(0,2,"");

        if(choix == 1) {
            Barman.initBoissonsJSON();
            Barman.initCocktailsJSON();
            Barman.SePresenter();
            Barman.TuVeuxQuoi();
        } else if (choix == 2) {
            Barman.initBoissonsJSON();
            Barman.initCocktailsJSON();
            Barman.JeGere();
        } else {
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException, ParseException {

        //Test.
        MENUPRINCIPALE();

    }
}
