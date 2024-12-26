package com.example.histoireapp;

public class Histoire {
    private int id;
    private String titre;
    private String contenu;
    private int auteurId;
    private int categorieId;

    // Default constructor
    public Histoire() {
    }

    // Constructor without id (for creation)
    public Histoire(String titre, String contenu, int auteurId, int categorieId) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteurId = auteurId;
        this.categorieId = categorieId;
    }

    // Constructor with id (for retrieval)
    public Histoire(int id, String titre, String contenu, int auteurId, int categorieId) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.auteurId = auteurId;
        this.categorieId = categorieId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public int getAuteurId() {
        return auteurId;
    }

    public void setAuteurId(int auteurId) {
        this.auteurId = auteurId;
    }

    public int getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(int categorieId) {
        this.categorieId = categorieId;
    }

    @Override
    public String toString() {
        return titre;
    }
}