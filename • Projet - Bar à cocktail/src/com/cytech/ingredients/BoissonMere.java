package com.cytech.ingredients;

public abstract class BoissonMere {
    private String nom; // nom du breuvage
    private double contenance; // contenance du breuvage
    private double contenu; // contenu du breuvage

    private String couleur; // en hexadecimal

    public BoissonMere(String nom, double contenance, String couleur) {
        this.nom = nom;
        this.contenance = contenance;
        this.couleur = couleur;
    }

    public void Vider(double ml) {
        this.contenance -= ml;
        if (this.contenance <=0)
            System.out.println("Oh c'est vide");

    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public double getContenance() {
        return contenance;
    }


    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
