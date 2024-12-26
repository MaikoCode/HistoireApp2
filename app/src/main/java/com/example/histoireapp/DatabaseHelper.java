
package com.example.histoireapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.histoireapp.Histoire;
import com.example.histoireapp.Auteur;
import com.example.histoireapp.Pays;
import com.example.histoireapp.Categorie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "HistoireDB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_HISTOIRE = "histoire";
    private static final String TABLE_AUTEUR = "auteur";
    private static final String TABLE_PAYS = "pays";
    private static final String TABLE_CATEGORIE = "categorie";

    // Common column names
    private static final String KEY_ID = "id";

    // HISTOIRE Table columns
    private static final String KEY_TITRE = "titre";
    private static final String KEY_CONTENU = "contenu";
    private static final String KEY_AUTEUR_ID = "auteur_id";
    private static final String KEY_CATEGORIE_ID = "categorie_id";

    // AUTEUR Table columns
    private static final String KEY_NOM = "nom";
    private static final String KEY_PRENOM = "prenom";
    private static final String KEY_PAYS_ID = "pays_id";

    // Table Create Statements
    private static final String CREATE_TABLE_PAYS = "CREATE TABLE " + TABLE_PAYS +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NOM + " TEXT NOT NULL" +
            ")";

    private static final String CREATE_TABLE_CATEGORIE = "CREATE TABLE " + TABLE_CATEGORIE +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NOM + " TEXT NOT NULL" +
            ")";

    private static final String CREATE_TABLE_AUTEUR = "CREATE TABLE " + TABLE_AUTEUR +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_NOM + " TEXT NOT NULL," +
            KEY_PRENOM + " TEXT NOT NULL," +
            KEY_PAYS_ID + " INTEGER," +
            "FOREIGN KEY(" + KEY_PAYS_ID + ") REFERENCES " + TABLE_PAYS + "(" + KEY_ID + ")" +
            ")";

    private static final String CREATE_TABLE_HISTOIRE = "CREATE TABLE " + TABLE_HISTOIRE +
            "(" +
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_TITRE + " TEXT NOT NULL," +
            KEY_CONTENU + " TEXT NOT NULL," +
            KEY_AUTEUR_ID + " INTEGER," +
            KEY_CATEGORIE_ID + " INTEGER," +
            "FOREIGN KEY(" + KEY_AUTEUR_ID + ") REFERENCES " + TABLE_AUTEUR + "(" + KEY_ID + ")," +
            "FOREIGN KEY(" + KEY_CATEGORIE_ID + ") REFERENCES " + TABLE_CATEGORIE + "(" + KEY_ID + ")" +
            ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creating required tables
        db.execSQL(CREATE_TABLE_PAYS);
        db.execSQL(CREATE_TABLE_CATEGORIE);
        db.execSQL(CREATE_TABLE_AUTEUR);
        db.execSQL(CREATE_TABLE_HISTOIRE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTOIRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTEUR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYS);

        // Create tables again
        onCreate(db);
    }

    // PAYS CRUD Operations
    public long insertPays(Pays pays) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, pays.getNom());
        return db.insert(TABLE_PAYS, null, values);
    }

    public Pays getPays(long pays_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PAYS, null, KEY_ID + "=?",
                new String[]{String.valueOf(pays_id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Pays pays = new Pays(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOM)));
            cursor.close();
            return pays;
        }
        return null;
    }

    @SuppressLint("Range")
    public List<Pays> getAllPays() {
        List<Pays> paysList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PAYS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pays pays = new Pays();
                pays.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                pays.setNom(cursor.getString(cursor.getColumnIndex(KEY_NOM)));
                paysList.add(pays);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return paysList;
    }

    public int updatePays(Pays pays) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, pays.getNom());
        return db.update(TABLE_PAYS, values, KEY_ID + "=?",
                new String[]{String.valueOf(pays.getId())});
    }

    public void deletePays(long pays_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PAYS, KEY_ID + "=?",
                new String[]{String.valueOf(pays_id)});
    }

    // CATEGORIE CRUD Operations
    public long insertCategorie(Categorie categorie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, categorie.getNom());
        return db.insert(TABLE_CATEGORIE, null, values);
    }

    public Categorie getCategorie(long categorie_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CATEGORIE, null, KEY_ID + "=?",
                new String[]{String.valueOf(categorie_id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Categorie categorie = new Categorie(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOM)));
            cursor.close();
            return categorie;
        }
        return null;
    }

    @SuppressLint("Range")
    public List<Categorie> getAllCategories() {
        List<Categorie> categorieList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEGORIE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Categorie categorie = new Categorie();
                categorie.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                categorie.setNom(cursor.getString(cursor.getColumnIndex(KEY_NOM)));
                categorieList.add(categorie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categorieList;
    }

    public int updateCategorie(Categorie categorie) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, categorie.getNom());
        return db.update(TABLE_CATEGORIE, values, KEY_ID + "=?",
                new String[]{String.valueOf(categorie.getId())});
    }

    public void deleteCategorie(long categorie_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORIE, KEY_ID + "=?",
                new String[]{String.valueOf(categorie_id)});
    }

    // AUTEUR CRUD Operations
    public long insertAuteur(Auteur auteur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, auteur.getNom());
        values.put(KEY_PRENOM, auteur.getPrenom());
        values.put(KEY_PAYS_ID, auteur.getPaysId());
        return db.insert(TABLE_AUTEUR, null, values);
    }

    public Auteur getAuteur(long auteur_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_AUTEUR, null, KEY_ID + "=?",
                new String[]{String.valueOf(auteur_id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Auteur auteur = new Auteur(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_NOM)),
                    cursor.getString(cursor.getColumnIndex(KEY_PRENOM)),
                    cursor.getInt(cursor.getColumnIndex(KEY_PAYS_ID)));
            cursor.close();
            return auteur;
        }
        return null;
    }

    @SuppressLint("Range")
    public List<Auteur> getAllAuteurs() {
        List<Auteur> auteurList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_AUTEUR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Auteur auteur = new Auteur();
                auteur.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                auteur.setNom(cursor.getString(cursor.getColumnIndex(KEY_NOM)));
                auteur.setPrenom(cursor.getString(cursor.getColumnIndex(KEY_PRENOM)));
                auteur.setPaysId(cursor.getInt(cursor.getColumnIndex(KEY_PAYS_ID)));
                auteurList.add(auteur);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return auteurList;
    }

    public int updateAuteur(Auteur auteur) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NOM, auteur.getNom());
        values.put(KEY_PRENOM, auteur.getPrenom());
        values.put(KEY_PAYS_ID, auteur.getPaysId());
        return db.update(TABLE_AUTEUR, values, KEY_ID + "=?",
                new String[]{String.valueOf(auteur.getId())});
    }

    public void deleteAuteur(long auteur_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AUTEUR, KEY_ID + "=?",
                new String[]{String.valueOf(auteur_id)});
    }

    // HISTOIRE CRUD Operations
    public long insertHistoire(Histoire histoire) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITRE, histoire.getTitre());
        values.put(KEY_CONTENU, histoire.getContenu());
        values.put(KEY_AUTEUR_ID, histoire.getAuteurId());
        values.put(KEY_CATEGORIE_ID, histoire.getCategorieId());
        return db.insert(TABLE_HISTOIRE, null, values);
    }

    public Histoire getHistoire(long histoire_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_HISTOIRE, null, KEY_ID + "=?",
                new String[]{String.valueOf(histoire_id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Histoire histoire = new Histoire(
                    cursor.getInt(cursor.getColumnIndex(KEY_ID)),
                    cursor.getString(cursor.getColumnIndex(KEY_TITRE)),
                    cursor.getString(cursor.getColumnIndex(KEY_CONTENU)),
                    cursor.getInt(cursor.getColumnIndex(KEY_AUTEUR_ID)),
                    cursor.getInt(cursor.getColumnIndex(KEY_CATEGORIE_ID)));
            cursor.close();
            return histoire;
        }
        return null;
    }

    @SuppressLint("Range")
    public List<Histoire> getAllHistoires() {
        List<Histoire> histoireList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_HISTOIRE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Histoire histoire = new Histoire();
                histoire.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                histoire.setTitre(cursor.getString(cursor.getColumnIndex(KEY_TITRE)));
                histoire.setContenu(cursor.getString(cursor.getColumnIndex(KEY_CONTENU)));
                histoire.setAuteurId(cursor.getInt(cursor.getColumnIndex(KEY_AUTEUR_ID)));
                histoire.setCategorieId(cursor.getInt(cursor.getColumnIndex(KEY_CATEGORIE_ID)));
                histoireList.add(histoire);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return histoireList;
    }

    public int updateHistoire(Histoire histoire) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITRE, histoire.getTitre());
        values.put(KEY_CONTENU, histoire.getContenu());
        values.put(KEY_AUTEUR_ID, histoire.getAuteurId());
        values.put(KEY_CATEGORIE_ID, histoire.getCategorieId());
        return db.update(TABLE_HISTOIRE, values, KEY_ID + "=?",
                new String[]{String.valueOf(histoire.getId())});
    }

    public void deleteHistoire(long histoire_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_HISTOIRE, KEY_ID + "=?",
                new String[]{String.valueOf(histoire_id)});
    }
}