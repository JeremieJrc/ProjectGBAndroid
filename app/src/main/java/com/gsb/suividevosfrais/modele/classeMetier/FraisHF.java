package com.gsb.suividevosfrais.modele.classeMetier;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremie on 07/12/2016.
 */

public class FraisHF extends Frais{

    ///Declaration des propriete

    private  Integer montant;
    private String motif;

    //table de hastable pour tous les frais

    private ArrayList<FraisHF> listeFraisHf;

    //Contructeur

    public FraisHF(){}

    public FraisHF(String dateFrais, Integer montant,String motif){

        this.dateFrais=dateFrais;
        this.montant=montant;
        this.motif=motif;
    }

    //setter

    public void setMontant(Integer montant){
        this.montant=montant;
    }
    public void setMotif(String motif){
        this.motif=motif;
    }

    //getter

    public Integer getMontant(){
        return montant;
    }

    public String getMotif(){
        return motif;
    }

    //Methoddes array liste FraisH

    public ArrayList<FraisHF> getListFraisH() {
        return listeFraisHf;
    }

    public void addlisteFrais(FraisHF fraisH){
        listeFraisHf.add(fraisH);
    }

    public Integer sizeListeFraisH(){
        return listeFraisHf.size();
    }



    public JSONArray convertFraisHFToJSONArray () {
        // construction d'une liste avec les informations du profil
        List list = new ArrayList() ;

        list.add(dateFrais);
        list.add(montant);
        list.add(motif);

        return new JSONArray(list) ;
    }

}
