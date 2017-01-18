package com.gsb.suividevosfrais.modele.acces;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gsb.suividevosfrais.outils.BdataGsb;
import com.gsb.suividevosfrais.modele.classeMetier.FraisHF;
import java.util.ArrayList;

/**
 * Created by Jeremie on 07/12/2016.
 */

public class AccesLocalFraisH {

    //Propriete pour acces base donnee

    private String nomBaseLocal = "bdFrais.sqlite";
    private Integer versionBaseLocal = 1;
    private static BdataGsb accesBaseLocal;
    private static SQLiteDatabase bd;
    private Context context;

    //Propriete pour manipule la base de donnee
    ArrayList<FraisHF> listFraisH;


    // Description des colonnes frais forfait
    public static final String TABLE_FRAISHF = "table_fraisHf";

    public static final String COLIDFRAIS = "idHFrais";//Colonne pour l'id Frais
    public static final String COLDATEHF = "dateHf";//Colonne date jj/MM/aaaa
    public static final String COLMOTANT = "montant";//Colonne pour le type de forfait (Km, repas, nuit, etape)
    public static final String COLMOTIF = "motif";//Colonne pour la quantité forfait


    public AccesLocalFraisH(Context context) {
        this.accesBaseLocal = new BdataGsb(context, nomBaseLocal, versionBaseLocal);
    }

    public void addFraisH(FraisHF fraisHF) {
        bd = accesBaseLocal.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLDATEHF, fraisHF.getDateFrais());
        values.put(COLMOTANT, fraisHF.getMontant());
        values.put(COLMOTIF, fraisHF.getMotif());

        bd.insert(TABLE_FRAISHF, null, values);

        bd.close();

    }

    //Recupereation de tous les FraisH

    public ArrayList<FraisHF> recupBddGsb(String dateFrais) {
        //Propirete
        FraisHF fraisH;

        //Ouverture de la base lecture
        bd = accesBaseLocal.getReadableDatabase();
        //REQUETE demande de recupere les fraisH a la date en parametre

        Cursor curseur = bd.query(TABLE_FRAISHF, new String[]{COLIDFRAIS, COLDATEHF, COLMOTANT, COLMOTIF},
               COLDATEHF+ " LIKE '%" + dateFrais + "'", null, null, null, null);

        //Cursor curseur=bd.rawQuery("select * from table_fraisHf where dateHf LIKE %'"+dateFrais+"';",null);

        //Traitement
        //Verification de l'existen d'un fraiSh DANS LA BASE DATA

        if (curseur.getCount() == 0) {
            return null;
        }else{
            ArrayList<FraisHF> listFrais=new ArrayList<FraisHF>();

            //Recuperationn des donnees
            curseur.moveToFirst();

            while(!curseur.isAfterLast()){
                //Mettre doneee du frais dansn l'objet type FraisH
                fraisH=new FraisHF();

                fraisH.setId(curseur.getInt(0)); // Colonne 0 dans base de donnee
                fraisH.setDateFrais(curseur.getString(1)); //colonne 1
                fraisH.setMontant(curseur.getInt(2));
                fraisH.setMotif(curseur.getString(3));

                //Ajout des jour et mois
                fraisH.setJour(fraisH.getDateFrais().substring(0,2));
                //On ajoute a la liste

                listFrais.add(fraisH);
                fraisH=null;
                curseur.moveToNext();

            }

            // fermeture du curseur
            curseur.close();
            bd.close();
            return listFrais;
        }
    }

    public static void removeFraisH(FraisHF fraisH){
        //Ouverture de Base data
        bd=accesBaseLocal.getWritableDatabase();
        bd.delete(TABLE_FRAISHF,COLIDFRAIS + "="+fraisH.getId(),null);
    }


}
