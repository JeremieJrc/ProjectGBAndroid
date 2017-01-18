package com.gsb.suividevosfrais.modele.acces;

import android.util.Log;
import com.gsb.suividevosfrais.outils.AccesHTTP;
import org.json.JSONArray;
/**
 * Created by Jeremie on 14/12/2016.
 */

public class AccesDistant {

    /**
     * transfert des infos vers le serveur distant
     * - création de l'acces HTTP avec redéfinition à la volée de onPostExecute
     * - appel de la méthode execute sur l'accès, après avoir créer les paramètres nécessaires
     * @param dataConvertJson
     */
    public void transfertDistant(JSONArray dataConvertJson) {
        // création de l'objet d'accès à distance avec reféfinition à la volée de la méthode onPostExecute
        AccesHTTP accesDonnees = new AccesHTTP(){
            @Override
            protected void onPostExecute(Long result) {
                // ret contient l'information récupérée
                Log.d("retour du serveur", this.ret.toString()) ;
            }
        };
        // ajout des données en paramêtre
        accesDonnees.addParam("op", "enreg");
        accesDonnees.addParam("lesprofils", dataConvertJson.toString());
        // envoi

        //Modifier fonction du serveur
        accesDonnees.execute("http://192.168.0.21/coach/serveurcoach.php");
    }
}
