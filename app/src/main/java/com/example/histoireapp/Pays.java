package com.example.histoireapp;

public class Pays {
    private int id;
    private String nom;

    // Default constructor
    public Pays() {
    }

    // Constructor without id (for creation)
    public Pays(String nom) {
        this.nom = nom;
    }

    // Constructor with id (for retrieval)
    public Pays(int id, String nom) {
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