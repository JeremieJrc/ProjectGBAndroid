package com.gsb.suividevosfrais.modele.acces;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.test.PerformanceTestCase;

import com.gsb.suividevosfrais.modele.classeMetier.LogVisiteur;
import com.gsb.suividevosfrais.outils.BdataGsb;


/**
 * Created by Jeremie on 19/12/2016.
 */

public class AccesLogin {

    private static String nomBaseLocal = "bdFrais.sqlite";
    private static BdataGsb accesBaseLocal;
    private static SQLiteDatabase bd;
    private Integer versionBaseLocal = 1;
    private Context context;

    //Constante pour la manipulation  base data
    // elle vont premettent de realiser des requetes
    //Sur la se de donnee

    //Table identifiant
    //Table pour l'identifiant
    public static final String TABLE_VISITOR = "table_visitor";

    // Description des colonnes identifiant
    public static final String COL_IDVISITOR = "idVisitor";//Colonne pour l'id Identifiant
    public static final String COL_LOGIN = "login";//Colonne pour le login Identifiant
    public static final String COL_MDP = "mdp";//Colonne pour le mot de passe Identifiant

    public AccesLogin(Context context){
        this.accesBaseLocal=  new BdataGsb(context,nomBaseLocal,versionBaseLocal);
    }
    //Proprieté et methode de manipulation Login

    public boolean existVisiteur() {

        //Ouveture base en lecture
        bd = accesBaseLocal.getReadableDatabase();
        //Requete

        Cursor curseur = bd.rawQuery("select *  from " + TABLE_VISITOR, null);

        if (curseur.getCount()==0) {
            curseur.close();
            return true;
        } else {

            curseur.close();
            bd.close();
            return false;
        }
    }
    public void addLogVisteur(String nomUser, String passUser){
        //Ouvrir en ecriture
        bd=accesBaseLocal.getWritableDatabase();
        //Insert dans la base
        ContentValues values=new ContentValues();
        values.put(COL_IDVISITOR,0);
        values.put(COL_LOGIN,nomUser);
        values.put(COL_MDP,passUser);

        bd.insert(TABLE_VISITOR,null,values);
        bd.close();
        //Inserrer new visteur
    }
    public boolean enablePasswd(LogVisiteur visiteur) {
        //Ouverture en lecture
        bd = accesBaseLocal.getReadableDatabase();

        //Curesyr requete
        Cursor curseur = bd.query(TABLE_VISITOR, new String[]{COL_IDVISITOR, COL_LOGIN, COL_MDP},
                COL_LOGIN + "='" + visiteur.getNonVisiteur() + "'" + COL_MDP + "='" + visiteur.getpassword() + "'", null, null, null, null);

        if(curseur.getCount()==0){
            curseur.close();
            return false;
        }else{
            curseur.close();
            return true;
        }
    }
    public LogVisiteur recupVisiteur(){

        LogVisiteur visiteur= new LogVisiteur();

        //Ouveture base en lecture
        bd = accesBaseLocal.getReadableDatabase();
        //Requete
        Cursor curseur = bd.rawQuery("select * from "+TABLE_VISITOR, null);
        curseur.moveToFirst();

        visiteur.setidVisiteur(curseur.getInt(0));
        visiteur.setNomVisiteur(curseur.getString(1));
        visiteur.setPaswd(curseur.getString(2));

        curseur.close();
        bd.close();
        return  visiteur;

    }

    public void updateLoginVisiteur(Integer id, String nomUser, String passUser){
        //Ouvrir en ecriture
        bd=accesBaseLocal.getWritableDatabase();
        ContentValues values= new ContentValues();


        values.put(COL_LOGIN,nomUser);
        values.put(COL_MDP,passUser);

        bd.update(TABLE_VISITOR,values, COL_IDVISITOR + " = " + id,null);
        bd.close();

    }


}
