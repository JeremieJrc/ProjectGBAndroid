package com.gsb.suividevosfrais.vue;

import android.os.Bundle;
import android.app.Activity;
import android.text.InputType;
import android.widget.DatePicker.OnDateChangedListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;


import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisF;
import com.gsb.suividevosfrais.modele.classeMetier.FraisF;
import com.gsb.suividevosfrais.outils.Global;
import com.gsb.suividevosfrais.R;

/**
 * Created by Jeremie on 09/12/2016.
 */

public class EtapeActivity extends Activity {

    // informations affich�es dans l'activite�
    //Proprieté

    private AccesLocalFraisF acsLocalFrais= new AccesLocalFraisF(this);

    private Integer idFrais;
    private String dateMois;
    private Integer annee ;
    private Integer mois ;
    private Integer qte ;
    private String typeF="etape";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etape);

        // modification de l'affichage du DatePicker
        Global.changeAfficheDate((DatePicker) findViewById(R.id.datetp)) ;

        // valorisation des propri�t�s
        valoriseProprietes() ;

        // chargement des m�thodes �v�nementielles
        imgReturn_clic() ;
        cmdValider_clic() ;
        cmdPlus_clic() ;
        cmdMoins_clic() ;
        dat_clic() ;

        //Desactivation de saisie manuel dans la zone de texte
        ((EditText)findViewById(R.id.txtEtape)).setInputType(InputType.TYPE_NULL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.etape, menu);
        return true;
    }

    /**
     * Valorisation des propri�t�s avec les informations affich�es
     */
    private void valoriseProprietes() {

        //Recuperation annee mois datePicker
        annee = ((DatePicker)findViewById(R.id.datetp)).getYear() ;
        mois = ((DatePicker)findViewById(R.id.datetp)).getMonth() + 1 ;

        idFrais=null;
        qte = 0 ;

        //1 recupere la date jj/mm/aaaa
        dateMois="01"+"/"+mois.toString()+"/"+annee.toString();

        //Declaration FraisF pour recuperer objet dant la bdd
        FraisF fraisF =new FraisF();

        //on recupere la quantite get fraisQuantite()

        fraisF=acsLocalFrais.getFraisDateFrais(typeF,dateMois);

        //init fraisF et verifier sil il existe if(fraisF !=null)

        //Si l'objet Frais forfait n'est pas null alors on Initialise les composants d'affichages
        if(fraisF != null){
            idFrais = fraisF.getId();//Initialisation de l'id avec celui trouvé dans la base de données
            qte = fraisF.getQuantite();//Initialisation de la quantité avec celle trouvée dans la base de données.
        }

        ((EditText)findViewById(R.id.txtEtape)).setText(qte.toString()) ;
    }

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        ((ImageView)findViewById(R.id.imgEtapReturn)).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton valider : s�rialisation
     */
    private void cmdValider_clic() {
        ((Button)findViewById(R.id.cmdEtpeValider)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

                //Recuperation dateFrais fonction mois et annee
                //1 recupere la date jj/mm/aaaa
                dateMois="01"+"/"+mois.toString()+"/"+annee.toString();

                //Declaration objet frais
                FraisF fraisF= new FraisF();

                //Propriétés de recupt si existe
                //Recuperation fonction dateFrais et le type
                fraisF=acsLocalFrais.getFraisDateFrais(typeF,dateMois);

                //DEUX possibilité
                //1- Update ou
                //2- Insert

                if(idFrais!=null){

                    acsLocalFrais.updateFraisF(idFrais,fraisF,qte);
                }else{
                    //Declaration objet frais

                    FraisF addFraisF= new FraisF(dateMois,typeF,qte);
                    acsLocalFrais.addFraisF(addFraisF);
                }
                retourActivityPrincipale();
            }
        }) ;
    }

    /**
     * Sur le clic du bouton plus : ajout de 10 dans la quantit�
     */
    private void cmdPlus_clic() {
        ((Button)findViewById(R.id.cmdEtpPlus)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte+=1 ;
                ((EditText)findViewById(R.id.txtEtape)).setText(qte.toString()) ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton moins : enl�ve 10 dans la quantit� si c'est possible
     */
    private void cmdMoins_clic() {
        ((Button)findViewById(R.id.cmdEtpMoins)).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                qte = Math.max(0, qte-1) ; // suppression de 10 si possible
                ((EditText)findViewById(R.id.txtEtape)).setText(qte.toString()) ;
            }
        }) ;
    }

    /**
     * Sur le changement de date : mise � jour de l'affichage de la qte
     */
    private void dat_clic() {
        final DatePicker uneDate = (DatePicker)findViewById(R.id.datetp) ;
        uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                valoriseProprietes() ;
            }
        });
    }

    /**
     * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, � la date choisie
     */
    private void enregNewQte() {
        // enregistrement dans la zone de texte
        ((EditText)findViewById(R.id.txtEtape)).setText(qte.toString()) ;
    }
    /**
     * Retour � l'activit� principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(EtapeActivity.this, MainActivity.class) ;
        startActivity(intent) ;
    }
}
