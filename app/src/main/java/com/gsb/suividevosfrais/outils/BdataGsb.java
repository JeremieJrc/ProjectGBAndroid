package com.gsb.suividevosfrais.outils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;

/**
 * Created by Jeremie on 07/12/2016.
 */

public class BdataGsb extends SQLiteOpenHelper {

    //--- Déclaration des propriétés ---

    //Table frais
    //Table frais qui contiendra les frais forfait


    public static final String TABLE_FRAISF = "table_fraisMois";

    // Description des colonnes frais forfait
    public static final String COL_IDFRAIS = "idFrais";//Colonne pour l'id Frais
    public static final String COL_DATEFRAIS = "dateFrais";//Colonne date jj/MM/aaaa
    public static final String COL_TYPEFRAIS = "type_forfait";//Colonne pour le type de forfait (Km, repas, nuit, etape)
    public static final String COL_QTE = "quantite";//Colonne pour la quantité forfait


    //------------------------------------------------------------------------------------//


    //Table Frais hors forfait
    //Table frais qui contiendra les frais forfait et hors forfait

    public static final String TABLE_FRAISHF = "table_fraisHf";

    // Description des colonnes frais hors forfait
    public static final String COL_IDHF = "idHFrais";//Colonne pour l'id Frais
    public static final String COL_DATEHF = "dateHf";//Colonne date jj/MM/aaaa
    public static final String COL_MONTANT = "montant";//Colonne pour le montant hors forfait
    public static final String COL_MOTIF = "motif";//Colonne pour le commentaire hors forfait



    //------------------------------------------------------------------------------------//


    //Table identifiant
    //Table pour l'identifiant
    public static final String TABLE_VISITOR = "table_visitor";

    // Description des colonnes identifiant
    public static final String COL_IDVISITOR = "idVisitor";//Colonne pour l'id Identifiant
    public static final String COL_LOGIN = "login";//Colonne pour le login Identifiant
    public static final String COL_MDP = "mdp";//Colonne pour le mot de passe Identifiant


    //------------------------------------------------------------------------------------//
    //------------------------------------------------------------------------------------//
    //------------------------------------------------------------------------------------//
    //------------------------------------------------------------------------------------//


    //Requête de création de la table TABLE_FRAISF

    private static final String CREER_TABLEF = "CREATE TABLE " + TABLE_FRAISF
            + " (" + COL_IDFRAIS + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATEFRAIS + " TEXT NOT NULL, "
            + COL_TYPEFRAIS + " TEXT NOT NULL, "
            + COL_QTE + " INTEGER NULL);";

    //------------------------------------------------------------------------------------//

    //Requête de création de la table TABLE_FRAISHF
    private static final String CREER_TABLEHF = "CREATE TABLE " + TABLE_FRAISHF
            + " (" + COL_IDHF + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DATEHF + " TEXT NOT NULL, "
            + COL_MONTANT + " INTEGER NULL, "
            + COL_MOTIF + " TEXT NULL);";


    //------------------------------------------------------------------------------------//


    //Requête de création de la table TABLE_VISIT
    private static final String CREER_TABLEVISIT = "CREATE TABLE " + TABLE_VISITOR
            + " (" + COL_IDVISITOR + " INTEGER PRIMARY KEY , "
            + COL_LOGIN + " TEXT NULL, "
            + COL_MDP + " TEXT NULL);";


    //------------------------------------------------------------------------
    //------------------------------------------------------------------------


    /**
     * Constructeur de la classe BddGsbApp.
     *
     * @param context : est le context.
     * @param nom : est le nom de la base de données.
    //* @param factory : est une fabrique de création de curseur.
     * @param version : est la version de la base de données.
     */
    public  BdataGsb(Context context, String nom, int version) {
        super(context, nom, null, version);
    }

    /**
     * Création de la base de données
     * avec les tables.
     *
     * @param db : est la base de données.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREER_TABLEF);//Création de la table Frais
        db.execSQL(CREER_TABLEHF);//Création de la table Frais
        db.execSQL(CREER_TABLEVISIT);//Création de la table Identifiant.
    }

    /**
     * Procédure de mise à jours de la base de données.
     * Supprime les anciennes tables et les refait.
     *
     * @param db : est la base de données.
     * @param ancienVers : est le numéro de l'ancienne version.
     * @param nouvelVers : est le numéro de la nouvelle version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int ancienVers, int nouvelVers) {
        if (nouvelVers > ancienVers) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAISF);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRAISHF);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_VISITOR);
            onCreate(db);
        }
    }
}