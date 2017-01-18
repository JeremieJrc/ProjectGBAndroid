package com.gsb.suividevosfrais.vue;

import java.util.ArrayList;
import android.os.Bundle;
import android.app.Activity;
import android.widget.DatePicker.OnDateChangedListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;

import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisH;
import com.gsb.suividevosfrais.modele.classeMetier.FraisHF;

import com.gsb.suividevosfrais.outils.Global;
import com.gsb.suividevosfrais.R;

public class HfRecapActivity extends Activity {

	//Propriete
	AccesLocalFraisH  acsLocalFraisH= new AccesLocalFraisH(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf_recap);

		// modification de l'affichage du DatePicker
		Global.changeAfficheDate((DatePicker) findViewById(R.id.datHfRecap)) ;

		// valorisation des propri�t�s
		afficheListe() ;

		// chargement des m�thodes �v�nementielles
		imgReturn_clic() ;
		dat_clic() ;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.hf_recap, menu);
		return true;
	}

	/**
	 * Affiche la liste des frais hors forfaits de la date s�lectionn�e
	 */
	void afficheListe() {

		//Liste Adaptater
		ListView listView = (ListView)findViewById(R.id.lstHfRecap) ;

		Integer annee = ((DatePicker)findViewById(R.id.datHfRecap)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHfRecap)).getMonth() + 1 ;

		String dateFraisH;

		dateFraisH=mois.toString()+"/"+annee.toString();


		// r�cup�ration des frais HF pour cette date
		ArrayList<FraisHF> liste=null ;

		liste=acsLocalFraisH.recupBddGsb(dateFraisH);

		if (liste == null) {
			listView.setAdapter(null);
		}else{

			FraisHfAdapter adapter = new FraisHfAdapter(HfRecapActivity.this, liste) ;
			listView.setAdapter(adapter);
		}
	}
	
	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	((ImageView)findViewById(R.id.imgHfRecapReturn)).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le changement de date : mise � jour de l'affichage de la qte
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker)findViewById(R.id.datHfRecap) ;
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				afficheListe() ;				
			}
    	});       	
    }

	/**
	 * Retour � l'activit� principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfRecapActivity.this, MainActivity.class) ;
		startActivity(intent) ;   					
	}
}
