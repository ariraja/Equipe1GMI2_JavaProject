package com.cytech.ingredients;

public class BoissonAlcoolisee extends Boisson {

    private double degreAlcool;

    public BoissonAlcoolisee(String nom, String couleur, double prix, double contenance, double alcool) {
        super(nom, couleur, prix, contenance);
        this.degreAlcool = alcool;
    }
    // contenance pas rensgner
    public BoissonAlcoolisee(String nom, String couleur, double prix, double alcool) {
        super(nom, couleur, prix);
        this.degreAlcool = alcool;
    }

    public double getDegreAlcool() {
        return degreAlcool;
    }

    public String toString() {
        return "Boisson '" + Main.printColor("BOLD") + this.getNom() + Main.printColor("RESET")  +  '\'' +
                " | *DegreAlcool* : " + this.getDegreAlcool() +
                "° | *PRIX* : "+ Main.printColor("BOLD")  + this.getPrix() + "€ " + Main.printColor("RESET") ;
    }

    public String toString2() {
        return "Boisson '"  + this.getNom()  +  '\'' +
                " | *DegreAlcool* : " + this.getDegreAlcool() +
                "° | *PRIX* : " + this.getPrix() + "€ " ;
    }

}
