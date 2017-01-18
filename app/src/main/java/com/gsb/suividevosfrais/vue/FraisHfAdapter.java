package com.gsb.suividevosfrais.vue;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.gsb.suividevosfrais.modele.acces.AccesLocalFraisH;
import com.gsb.suividevosfrais.modele.classeMetier.FraisHF;
import com.gsb.suividevosfrais.R;

public class FraisHfAdapter extends BaseAdapter {

	ArrayList<FraisHF> lesFrais ; // liste des frais du mois
	LayoutInflater inflater ;
	Context context ; // contexte pour g�rer la s�rialisation

	/**
	 * Constructeur de l'adapter pour valoriser les propri�t�s
	 * @param context
	 * @param lesFrais
	 */
	public FraisHfAdapter(Context context, ArrayList<FraisHF> lesFrais) {
		inflater = LayoutInflater.from(context) ;
		this.lesFrais = lesFrais ;
		this.context = context ;
	}
	
	/**
	 * retourne le nombre d'�l�ments de la listview
	 */
	@Override
	public int getCount() {
		return lesFrais.size() ;
	}

	/**
	 * retourne l'item de la listview � un index pr�cis
	 */
	@Override
	public Object getItem(int index) {
		return lesFrais.get(index) ;
	}

	/**
	 * retourne l'index de l'�l�ment actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les �l�ments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
		ImageButton BtSupp;
	}
	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		if (convertView == null) {
			holder = new ViewHolder() ;
			convertView = inflater.inflate(R.layout.layout_liste, null) ;
			holder.txtListJour = (TextView)convertView.findViewById(R.id.txtListJour) ;
			holder.txtListMontant = (TextView)convertView.findViewById(R.id.txtListMontant) ;
			holder.txtListMotif = (TextView)convertView.findViewById(R.id.txtListMotif) ;

			holder.BtSupp=(ImageButton)convertView.findViewById(R.id.BtnSupp);

			convertView.setTag(holder) ;

		}else{
			holder = (ViewHolder)convertView.getTag();
		}

		holder.txtListJour.setText(lesFrais.get(index).getJour()) ;
		holder.txtListMontant.setText(lesFrais.get(index).getMontant().toString()) ;
		holder.txtListMotif.setText(lesFrais.get(index).getMotif()) ;
		holder.BtSupp.setTag(index);

		//Gestion evenement suppresion frais hors Forfait
		holder.BtSupp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Recuperation de l'index
				 Integer index =(Integer)v.getTag();
				// Gestion de Suptression d'un frais en definsant sa position

				 AccesLocalFraisH.removeFraisH(lesFrais.get(index));

				Toast.makeText(context,"Hors Forfait Supprimer : ",Toast.LENGTH_LONG).show();
				//Rafraichissement de la liste  apres suppression
			}
		});

		return convertView ;
	}
	
}
