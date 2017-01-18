package com.gsb.suividevosfrais.controleur;


import com.gsb.suividevosfrais.modele.acces.AccesDistant;

import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisF;
import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisH;
import com.gsb.suividevosfrais.modele.acces.AccesLogin;

import com.gsb.suividevosfrais.modele.classeMetier.FraisF;
import com.gsb.suividevosfrais.modele.classeMetier.FraisHF;
import com.gsb.suividevosfrais.modele.classeMetier.LogVisiteur;

import com.gsb.suividevosfrais.vue.MainActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremie on 19/12/2016.
 */

public class Controle {

    //Proprieté  Array liste

    private ArrayList<FraisF> listfraisF= new ArrayList<FraisF>();
    private ArrayList<FraisHF> listfraisFH= new ArrayList<FraisHF>();
    private LogVisiteur visiteur= new LogVisiteur();

    private  AccesDistant accesDistant;

    //Constructeur

    public Controle(){
        this.accesDistant=new AccesDistant();
    }

    /**
     * tramsmission des informations de la base locale vers la base distante
     * - valorisation de la collection de la base donnees en récupération  locale
     * - si la collection n'est pas vide, transfert vers le serveur distant
     */
    public void transmettreLocalVersDistant() {

        // si des informations ont été récupérées
        if (listfraisF!=null && listfraisFH!=null && visiteur!=null) {
            // tentative de transfert des informations vers le serveur distant
            this.accesDistant.transfertDistant(this.dataConvertJson()) ;
        }
    }

    //Methode

    //Rcuperation des Des frais Forfait et Hors Frais

    public void recupFrais( AccesLocalFraisH acsFh , AccesLocalFraisF acsF,AccesLogin acsLog){

        //Popriete

        String dateFrais;

        //Recuperation dernier frais qq pour la date

        dateFrais= acsF.getLastFraisF("km").getDateFrais();

        //Recuperation dans les Liste

        //Recuperation ListeFrais

        listfraisF=acsF.recupFraisForfait(dateFrais);

        //Recuperation Liste FraisH

        listfraisFH=acsFh.recupBddGsb(dateFrais.substring(3));

        // recuperation donnees Log Visiteur

        visiteur=acsLog.recupVisiteur();

    }


    // Conversion des de la liste des donnees recuperer


    private JSONArray dataConvertJson(){

        List list= new ArrayList();

        //On met dans liste les frais

        for (FraisF fraisF : listfraisF ){
            list.add(fraisF.convertFraisFToJSONArray());
        }

        // on met dans la liste les Hors Frais
        for (FraisHF fraisHF : listfraisFH ){
            list.add(fraisHF.convertFraisHFToJSONArray());
        }

        //On met le login visteur

        list.add(visiteur.convertFraisHFToJSONArray());

        return new JSONArray(list);
    }


}
