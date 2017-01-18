package com.gsb.suividevosfrais.modele.classeMetier;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremie on 14/12/2016.
 */

public class LogVisiteur {
    private Integer idVisiteur;
    private String nomVisiteur;
    private String paswd;


    public  LogVisiteur(){}

    public LogVisiteur(String nomVisiteur,String paswd){
        this.nomVisiteur=nomVisiteur;
        this.paswd=paswd;
    }

   //setter

    public void setidVisiteur(Integer idVisiteur){
        this.idVisiteur=idVisiteur;
    }
    public void setNomVisiteur(String nomVisiteur){
        this.nomVisiteur=nomVisiteur;
    }
    public void setPaswd(String paswd){
        this.paswd=paswd;
    }

    //getter

    public  Integer getIdVisiteur(){return idVisiteur;}
    public  String getNonVisiteur(){
        return nomVisiteur;
    }
    public  String  getpassword(){return paswd;}


    public JSONArray convertFraisHFToJSONArray () {
        // construction d'une liste avec les informations du profil
        List list = new ArrayList() ;

        list.add(nomVisiteur);
        list.add(paswd);

        return new JSONArray(list);
    }



}
