package com.example.histoireapp;

public class Auteur {
    private int id;
    private String nom;
    private String prenom;
    private int paysId;

    // Default constructor
    public Auteur() {
    }

    // Constructor without id (for creation)
    public Auteur(String nom, String prenom, int paysId) {
        this.nom = nom;
        this.prenom = prenom;
        this.paysId = paysId;
    }

    // Constructor with id (for retrieval)
    public Auteur(int id, String nom, String prenom, int paysId) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.paysId = paysId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getPaysId() {
        return paysId;
    }

    public void setPaysId(int paysId) {
        this.paysId = paysId;
    }

    @Override
    public String toString() {
        return prenom + " " + nom;
    }
}