package com.gsb.suividevosfrais.modele.acces;

import android.content.Context;
import android.content.ContentValues;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gsb.suividevosfrais.outils.BdataGsb;
import com.gsb.suividevosfrais.modele.classeMetier.FraisF;

import java.util.ArrayList;

/**
 * Created by Jeremie on 07/12/2016.
 */

public class AccesLocalFraisF {

    private static String nomBaseLocal = "bdFrais.sqlite";
    private static BdataGsb accesBaseLocal;
    private static SQLiteDatabase bd;
    private Integer versionBaseLocal = 1;
    private Context context;


    //propriete pour manipuler baseData

    // Description des colonnes frais forfait
    public static final String TABLE_FRAISF = "table_fraisMois";

    public static final String COLIDFRAIS = "idFrais";//Colonne pour l'id Frais
    public static final String COLDATEFRAIS = "dateFrais";//Colonne date jj/MM/aaaa
    public static final String COLTYPEFRAIS = "type_forfait";//Colonne pour le type de forfait (Km, repas, nuit, etape)
    public static final String COLQTE = "quantite";//Colonne pour la quantité forfait


    public AccesLocalFraisF(Context context) {
        this.accesBaseLocal = new BdataGsb(context, nomBaseLocal, versionBaseLocal);
    }
    //Inserer un frais

    public static void addFraisF(FraisF fraisF) {

        //Ouverture bd en ecriture
        bd=accesBaseLocal.getWritableDatabase();

        //ContentValue permet inserer de la donnee
        //Tous en sachant la position d

        ContentValues values = new ContentValues();

        values.put(COLDATEFRAIS, fraisF.getDateFrais()); //Renvoi chaine en int
        values.put(COLTYPEFRAIS, fraisF.getTypeFrais());
        values.put(COLQTE , fraisF.getQuantite());

         bd.insert(TABLE_FRAISF, null, values); //Retour de verification insertion
    }

    //Modifier un frais

    public static void updateFraisF(Integer id, FraisF fraisF,Integer qte) {
        //Ouverture bd en ecriture
        bd=accesBaseLocal.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLDATEFRAIS, fraisF.getDateFrais());
        values.put(COLTYPEFRAIS, fraisF.getTypeFrais());
        values.put(COLQTE, qte);

        bd.update(TABLE_FRAISF, values, COLIDFRAIS + " = " + id, null);

    }

    //Suprimer un frais fonction de son id

    public Integer removeFraisF(Integer id) {
        //Ouverture bd en ecriture
        bd=accesBaseLocal.getWritableDatabase();

        return bd.delete(TABLE_FRAISF, COLIDFRAIS + " = " + id, null);
    }

    //recuperer le dernier frais fonction du type un frais

    public FraisF getLastFraisF(String typeFrais) {

        //Ouverture de la bdd en lecture
        bd= accesBaseLocal.getReadableDatabase();

        //Curseur de recherche de donnee fonction de variable

        Cursor curseur = bd.query(TABLE_FRAISF, new String[]{COLIDFRAIS, COLDATEFRAIS, COLTYPEFRAIS , COLQTE},
                COLTYPEFRAIS + " = '" + typeFrais + "'", null, null, null, null);

        if (curseur.getCount() == 0) {
            return null;
        } else {
            //on se positionne sur le dernier frais d'une catégorie.
            curseur.moveToLast();

            FraisF fraisF = new FraisF();
            fraisF.setId(curseur.getInt(0));
            fraisF.setDateFrais(curseur.getString(1));
            fraisF.setTypeFrais(curseur.getString(2));
            fraisF.setQuantite(curseur.getInt(3));

            //fermeture du curseur
            curseur.close();

            return fraisF;
        }
    }

    //Recupere un frais fonction du type et de la date

    public static FraisF getFraisDateFrais(String typeFrais, String dateFrais) {

        //Ouverture de la bdd en lecture
        bd= accesBaseLocal.getReadableDatabase();

        Cursor curseur = bd.query(TABLE_FRAISF, new String[]{COLIDFRAIS , COLDATEFRAIS , COLTYPEFRAIS, COLQTE},
                COLTYPEFRAIS+ " = '" + typeFrais + "' AND " +COLDATEFRAIS + " = '" + dateFrais + "'", null, null, null, null);

        if (curseur.getCount() == 0) {
            return null;
        } else {
            //on se positionne sur le PREMIER OBJET frais d'une catégorie.
            curseur.moveToFirst();

            FraisF fraisF = new FraisF();
            fraisF.setId(curseur.getInt(0));
            fraisF.setDateFrais(curseur.getString(1));
            fraisF.setTypeFrais(curseur.getString(2));
            fraisF.setQuantite(curseur.getInt(3));

            //fermeture du curseur
            curseur.close();
            return fraisF;
        }

    }

    public ArrayList<FraisF> recupFraisForfait(String datefrais){

        //Propriete
        FraisF fraisF;
        ArrayList<FraisF> listFrais=new ArrayList<FraisF>();


        //Ouverture de la bdd en lecture
        bd= accesBaseLocal.getReadableDatabase();


        //Curseur de recherche de donnee fonction de variable

        Cursor curseur = bd.query(TABLE_FRAISF, new String[]{COLIDFRAIS, COLDATEFRAIS, COLTYPEFRAIS , COLQTE},
                COLDATEFRAIS + " = '" + datefrais + "'", null, null, null, null);

            curseur.moveToFirst();

        while(!curseur.isAfterLast()){
            //Mettre doneee du frais dansn l'objet type FraisF
            fraisF=new FraisF();


            fraisF.setDateFrais(curseur.getString(1)); //colonne 1
            fraisF.setDateFrais(curseur.getString(2));
            fraisF.setQuantite(curseur.getInt(3));
            //On ajoute a la liste

            listFrais.add(fraisF);
            fraisF=null;
            curseur.moveToNext();

        }

        // fermeture du curseur
        curseur.close();
        bd.close();
        return listFrais;
    }





}