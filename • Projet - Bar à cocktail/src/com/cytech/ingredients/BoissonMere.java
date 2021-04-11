package com.cytech.ingredients;

public abstract class BoissonMere {
    protected String nom; // nom du breuvage
    protected double contenance; // contenance du breuvage, capacité en ml
    protected double contenu; // contenu du breuvage, en ml

    private String couleur; // en hexadecimal

    public BoissonMere(String nom, double contenance, String couleur) {
        this.nom = nom;
        this.contenance = contenance;
        this.contenu = contenance;
        this.couleur = couleur;
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
    public double getContenu() {
        return contenu;
    }
    public String getCouleur() {
        return couleur;
    }
    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }
}
