package com.example.histoireapp;

public class Categorie {
    private int id;
    private String nom;

    // Default constructor
    public Categorie() {
    }

    // Constructor without id (for creation)
    public Categorie(String nom) {
        this.nom = nom;
    }

    // Constructor with id (for retrieval)
    public Categorie(int id, String nom) {
        this.id = id;
        this.nom = nom;
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

    @Override
    public String toString() {
        return nom;
    }
}