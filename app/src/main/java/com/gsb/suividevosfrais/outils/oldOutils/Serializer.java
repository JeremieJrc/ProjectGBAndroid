package com.gsb.suividevosfrais.outils.oldOutils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.StreamCorruptedException;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * Classe qui permet de s�rialiser et d�s�rialiser des objets
 * @author Emds
 *
 */
public abstract class Serializer {

	/**
	 * S�rialisation d'un objet
	 * @param filename
	 * @param object
	 */
	public static void serialize(String filename, Object object, Context context) {
		try {
			FileOutputStream file = context.openFileOutput(filename, Context.MODE_PRIVATE) ;
			ObjectOutputStream oos;
			try {
				oos = new ObjectOutputStream(file);
				oos.writeObject(object) ;
				oos.flush() ;
				oos.close() ;
			} catch (IOException e) {
				// erreur de s�rialisation
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// fichier non trouv�
			e.printStackTrace();
		}
	}
	
	/**
	 * D�s�rialisation d'un objet
	 * @param filename
	 * @param context
	 * @return
	 */
	public static Object deSerialize(String filename, Context context) {
		try {
			FileInputStream file = context.openFileInput(filename) ;
			ObjectInputStream ois;
			try {
				ois = new ObjectInputStream(file);
				try {
					Object object = ois.readObject() ;
					ois.close() ;
					return object ;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// fichier non trouv�
			e.printStackTrace();
		}
		return null ;		
	}
	
}
