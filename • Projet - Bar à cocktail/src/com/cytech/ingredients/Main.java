package com.cytech.ingredients;
import java.util.Scanner;

public class Main {

    public static int SaisirInt(int a, int b) { // saisir un entier entre a et b inclus
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();

        while((value < a) || (value > b)) {
            if ((value < a) || (value > b)) System.out.print(" /!\\ Veuillez resaisir..");
            value = sc.nextInt();

        }
        return value;
    }


    public static void main(String[] args) {
	System.out.println("/////////////////////////////////////////////");
	System.out.println("/////////// Mlamali SAIDSALIMO //////////////");
	System.out.println("//////////   Ari RAJAOEFERA   ///////////////");
	System.out.println("/////////  Guillaume URVOY   ////////////////");
	System.out.println("//////// Jonathan LOUAMBA   /////////////////");
	System.out.println("/////////////////////////////////////////////");
	System.out.println("////// * BAR A COCKTAIL * ///////////////////");
	System.out.println("/////////////////////////////////////////////");
	System.out.println("////  1 :  COMMENCER           //////////////");
	System.out.println("/////////////////////////////////////////////");
	System.out.println("/// >"); int choix = SaisirInt(1,1);


    }
}
