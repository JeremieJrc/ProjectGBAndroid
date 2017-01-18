package com.gsb.suividevosfrais.modele.classeMetier;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremie on 07/12/2016.
 */

public class FraisF extends Frais {

    private String typeFrais;
    private Integer quantite;

    //Constructeur

    public FraisF(){}

    public FraisF(String dateFrais,String typeFrais,Integer quantite){
        this.dateFrais=dateFrais; //format jj/mm/aaaa
        this.typeFrais=typeFrais;
        this.quantite=quantite;
    }

    //setter

    public void setTypeFrais(String typeFrais){
        this.typeFrais=typeFrais;
    }

    public void setQuantite(Integer quantite){
        this.quantite=quantite;
    }

    //getter

    public String getTypeFrais(){
        return typeFrais;
    }

    public Integer getQuantite(){
        return quantite;
    }



    public JSONArray convertFraisFToJSONArray () {
        // construction d'une liste avec les informations du profil
        List list = new ArrayList() ;

        list.add(dateFrais);
        list.add(typeFrais);
        list.add(quantite);

        return new JSONArray(list) ;
    }


}
