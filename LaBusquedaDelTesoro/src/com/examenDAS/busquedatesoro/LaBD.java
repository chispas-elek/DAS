package com.examenDAS.busquedatesoro;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class LaBD extends SQLiteOpenHelper {
	SQLiteDatabase db=getWritableDatabase();

	public LaBD(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Tesoros ('Codigo' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Descripcion' TEXT, 'Longitud' TEXT, 'Latitud' TEXT, 'Reclamado' BOOL, 'FechaRec' TEXT)");
		db.execSQL("INSERT INTO Tesoros ('Descripcion','Longitud','Latitud','Reclamado','FechaRec')  VALUES('Tesoro repleto de gemas','43.256944','-2.923611','false',NULL)");
		db.execSQL("INSERT INTO Tesoros ('Descripcion','Longitud','Latitud','Reclamado','FechaRec') VALUES('diamantes','43.256944','-2.923611','false',NULL)");
		db.execSQL("INSERT INTO Tesoros ('Descripcion','Longitud','Latitud','Reclamado','FechaRec')  VALUES('piedras preciosas','43.256944','-2.923611','false',NULL)");
		db.execSQL("INSERT INTO Tesoros ('Descripcion','Longitud','Latitud','Reclamado','FechaRec') VALUES('Tesoro con  monedas de oro','43.256944','-2.923611','true','24/03/2014 15:52')");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public Cursor  tesorosSinReclamar(){

		Cursor c = db.rawQuery("SELECT * FROM Tesoros Where Reclamado='false'", null);
		return c;


	}
	
	public Cursor  tesorosReclamados(){

		Cursor c = db.rawQuery("SELECT * FROM Tesoros Where Reclamado='true'", null);
		return c;


	}
	
	public Cursor  datosTesoro(int Codigo){

		Cursor c = db.rawQuery("SELECT * FROM Tesoros Where Codigo="+Codigo, null);
		return c;


	}
	

	public void  addTesoro(String desc, String longitud, String latitud, boolean reclamado, String fecha){
					
		db.execSQL("INSERT INTO Tesoros ('Descripcion','Longitud','Latitud','Reclamado','FechaRec')  VALUES('"+desc+"','"+longitud+"','"+latitud+"','"+reclamado+"','"+fecha+"')");
		
	}

	public void  reclamarTesoro(int Codigo, String fecha){
		
		db.execSQL("UPDATE Tesoros SET 'Reclamado'='true' AND 'FechaRec'='"+fecha+"' WHERE Codigo="+Codigo);
		
	}

}
