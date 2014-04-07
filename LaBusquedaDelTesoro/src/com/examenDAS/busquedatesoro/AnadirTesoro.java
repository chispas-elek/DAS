package com.examenDAS.busquedatesoro;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class AnadirTesoro extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_anadir_tesoro);
		
		//Se programa el botón de posicionamiento GPS y recogemos los datos para
		//Generar una sentencia SQL que mandaremos a MainActivity para que introduzca todo en la BD.
		//Después cerramos la interfaz.
		
	}
}
