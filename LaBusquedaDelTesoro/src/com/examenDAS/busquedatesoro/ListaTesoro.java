package com.examenDAS.busquedatesoro;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaTesoro extends Activity {

	private ArrayAdapter<String> adaptador;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_tesoro);
		
		//Recogemos el intent que nos manda los datos de la BD
		ArrayList<String> datos = new ArrayList<String>();
		Bundle extras = getIntent().getExtras();
		if(extras != null) {
			datos = extras.getStringArrayList("datosTesoros");
		}
		
		//inicializo el adaptador
		adaptador= new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1,datos);
		
		//añadirmos el adaptar a la lista
		ListView laLista = (ListView) findViewById(R.id.listatesoro);
		laLista.setAdapter(adaptador);
	}
}
