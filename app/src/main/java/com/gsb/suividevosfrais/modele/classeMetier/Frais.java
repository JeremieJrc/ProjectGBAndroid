package com.gsb.suividevosfrais.modele.classeMetier;

/**
 * Created by Jeremie on 07/12/2016.
 */

public class Frais {
    private Integer id;
    protected String dateFrais; //pour la partager
    private String jour;
    private String mois;
    private String annee;

    //Constructeur

    public Frais(){}

    // les setter

    public void setId(Integer id){
        this.id=id;
    }
/**
 * initialiser la datefrais du frais forfait.
 *
 * @param dateFrais : est la date du frais forfait en jj/MM/aaaa
 */


    public void setDateFrais(String dateFrais){
        this.dateFrais=dateFrais;
    }

    /**
     * initialisation du jour
     * avec la date.
     *
     * @param dateFrais
     */


    public void setJour(String dateFrais){
        this.jour=dateFrais.substring(0,2); //extraction init 0:2
    }

    public void setMois(String dateFrais){
        this.mois=dateFrais.substring(3,2); //init 3:2
    }

    public void setAnnee(String dateFrais){
        this.annee=dateFrais.substring(6,4);
    }

    //Getter


    public Integer getId(){
        return id;
    }

    public String getDateFrais(){
        return dateFrais;
    }
    public String getJour(){
        return jour;
    }

    public String getMois(){
        return mois;
    }

    public String getAnnee(){
        return annee;
    }

}
