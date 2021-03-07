package com.cytech.ingredients;

public class Barman {
    public static void BienvenueTuVeuxQuoi() {
        AfficherCatalogue();
        System.out.println("\n\n (1 : Commencer ma commande)    (2 : Créer mon propre Cocktail)    (0 : Quitter le bar) ");

        int choix = Main.SaisirInt(0,2);
        if(choix == 0) System.out.println("A bientot !");
        else if(choix == 1) Barman.SelectionnerBoisson();

    }

    public static void AfficherCatalogue() {
        System.out.println("////  ** Le BAR ** //////////////");
        System.out.println("Tu veux quoi ? On a tout :");
        System.out.println(" #### Nos Cocktails :");
        System.out.println(" #### Nos Boissons :");

    }
    public static void PrendreCommande() {



    }

    public static void SelectionnerBoisson() { // selectionne une boisson parmi la liste de boisson et renvoie la boisson
        System.out.println("SelectionnerBoisson()");int choix;
        System.out.println(" (0 : Finaliser)    (# : Entrer le numéro de la boisson) "); choix = Main.SaisirInt(0,5);
        // retourn listBoissons[choix]
        if (choix != 0)
            SelectionnerBoisson();
        else {
            System.out.println(" (0 : Annuler ma commande)     (1 : Valider ma commande) "); choix = Main.SaisirInt(0,1);
            if (choix == 0) {
                Barman.AnnulerCommande();
            } else {
                Barman.ValiderCommande();
            }
        }


    }
    public static void AnnulerCommande() {
        System.out.println("SupprimerCoommande");


    }

    public static void ValiderCommande() {
        System.out.println("ValideCoommande");


    }

}
