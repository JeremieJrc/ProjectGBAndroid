package com.gsb.suividevosfrais.vue;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gsb.suividevosfrais.R;
import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisF;
import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisH;
import com.gsb.suividevosfrais.modele.acces.AccesLogin;

import com.gsb.suividevosfrais.controleur.Controle;
import com.gsb.suividevosfrais.modele.classeMetier.LogVisiteur;

/**
 * Created by Jeremie on 14/12/2016.
 */
public class LoginActivity  extends  Activity {

    //Proprieté
    //Besoin pour connection login et donnees BDD Acces Frais Et FRaisH
    private AccesLogin acsLogVisiteur = new AccesLogin(this);
    private AccesLocalFraisF acsFraisF= new AccesLocalFraisF(this);
    private AccesLocalFraisH acsFraisH= new AccesLocalFraisH(this);

    private LogVisiteur visiteur= new LogVisiteur();
    private Controle controleur=new Controle();

    Button btnConnexion;
    Button btnUpdateLog;
    EditText edName;
    EditText edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Handler

        btnConnexion = ((Button) findViewById(R.id.btnconct));
        btnUpdateLog = ((Button) findViewById(R.id.btnmodif));

        edName = ((EditText) findViewById(R.id.cmdName));
        edPass = ((EditText) findViewById(R.id.cmdPass));

        if (acsLogVisiteur.existVisiteur()) {
            Toast.makeText(LoginActivity.this, "Tapez vos Identifiants", Toast.LENGTH_LONG).show();
        } else {
            visiteur=acsLogVisiteur.recupVisiteur();

            Toast.makeText(LoginActivity.this, visiteur.getNonVisiteur(), Toast.LENGTH_LONG).show();

            edName.setText(visiteur.getNonVisiteur());
            edPass.setText(visiteur.getpassword());
        }

        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Envoi des données

                String nomUser;
                String passUser;

                nomUser=edName.getText().toString();
                passUser=edPass.getText().toString();

                if (acsLogVisiteur.existVisiteur()) {
                    //Ajoute dans la base de donnee
                    acsLogVisiteur.addLogVisteur(nomUser,passUser);
                    Toast.makeText(LoginActivity.this, "Ajout bdd", Toast.LENGTH_LONG).show();
                }
                  //Connexion
                Toast.makeText(LoginActivity.this, "Connexion", Toast.LENGTH_LONG).show();
                //Recuperation desz Donnees en Acces Local

                controleur.recupFrais(acsFraisH,acsFraisF,acsLogVisiteur);
                //Transmimtrre Local base data vers distant
                controleur.transmettreLocalVersDistant();

            }
        });

        btnUpdateLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(acsLogVisiteur.existVisiteur()){
                    Toast.makeText(LoginActivity.this, "Tapez vos Identifiants", Toast.LENGTH_LONG).show();
                }else{

                    acsLogVisiteur.updateLoginVisiteur(visiteur.getIdVisiteur(),edName.getText().toString(),edPass.getText().toString());
                    Toast.makeText(LoginActivity.this, "Update BDD", Toast.LENGTH_LONG).show();
                }
            }
        });




    }
}