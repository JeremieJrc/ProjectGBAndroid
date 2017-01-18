package com.gsb.suividevosfrais.vue;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisH;
import com.gsb.suividevosfrais.modele.classeMetier.FraisHF;
import com.gsb.suividevosfrais.R;

public class HfActivity extends Activity {

	//Proprietes
	private AccesLocalFraisH acsLocalFrais= new AccesLocalFraisH(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf);
		// mise � 0 du montant
		((EditText)findViewById(R.id.txtHf)).setText("0") ;
        // chargement des m�thodes �v�nementielles
		imgReturn_clic() ;
		cmdAjouter_clic() ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hf, menu);
		return true;
	}

	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgHfReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton ajouter : enregistrement dans la liste et s�rialisation
     */
    private void cmdAjouter_clic() {
    	((Button)findViewById(R.id.cmdHfAjouter)).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {

				//A modifier
				enregListe() ;
				retourActivityPrincipale() ;
    		}
    	}) ;    	
    }
    
	/**
	 * Enregistrement dans la liste du nouveau frais hors forfait
	 */
	private void enregListe() {

		// r�cup�ration des informations saisies
		String dateMois;
		Integer annee = ((DatePicker)findViewById(R.id.datHf)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHf)).getMonth() + 1 ;
		Integer jour = ((DatePicker)findViewById(R.id.datHf)).getDayOfMonth() ;

		dateMois=jour.toString()+"/"+mois.toString()+"/"+annee.toString();

		Integer montant =Integer.parseInt(((EditText)findViewById(R.id.txtHf)).getText().toString()) ;
		String motif = ((EditText)findViewById(R.id.txtHfMotif)).getText().toString() ;

		//A init
		FraisHF fraisHF=new FraisHF(dateMois,montant,motif);

		// AddFraas HorsFROFAIT  au jour donnees
		acsLocalFrais.addFraisH(fraisHF);
	}
	/**
	 * Retour � l'activit� principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfActivity.this, MainActivity.class) ;
		startActivity(intent) ;   					
	}
}
