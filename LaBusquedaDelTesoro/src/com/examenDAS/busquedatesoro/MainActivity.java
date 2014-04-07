package com.examenDAS.busquedatesoro;



import java.util.ArrayList;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private SQLiteDatabase db = null;
	private ArrayList<String> datos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//inicializamos la lista y el adaptador
		datos = new ArrayList<String>();
						
		//Creamos una base de datos nueva y nos conectamos a ella
		LaBD GestorBD= new LaBD(this,"Tesoros",null,1);
		this.db = GestorBD.getWritableDatabase();
		
		
		
		//Codificamos el primer botón que listará todos los tesoros existentes
		Button boton1 = (Button) findViewById(R.id.button1);
		boton1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Hacemos el select a la base de datos y con el resultado lo mandamos con un intent a la siguiente clase
				Cursor c = db.rawQuery("SELECT * FROM Tesoros", null);
				while (c.moveToNext()){
					datos.add("Tesoro: "+c.getInt(0)+"\n"
							+ "Descripcion: "+c.getString(1)+"\n"
							+ "Localización: "+c.getString(2)+" "+c.getString(3));
				}
				//Generamos el intent que nos permite lanzar la actividad de listatesoro
				Intent i = new Intent(MainActivity.this,ListaTesoro.class);
				i.putExtra("datosTesoros",datos);
				startActivity(i);
			}
		});
		
		//Codificamos el segundo boton que nos indicará si estamos cerca de algún tesoro.
		Button boton2 = (Button) findViewById(R.id.button2);
		boton2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Seleccionamos el mejor criterio de posicionamiento
				LocationManager elManager= (LocationManager) getSystemService(LOCATION_SERVICE); 
				Criteria losCriterios = new Criteria();
				losCriterios.setAccuracy(Criteria.ACCURACY_FINE);
				String mejorProveedor = elManager.getBestProvider(losCriterios, true);
				Toast.makeText(getApplicationContext(), "El mejor proveedor es: "+mejorProveedor, Toast.LENGTH_LONG).show();
				//Una vez obtenido el mejor proveedor, comprobamos si está activado. En caso negativo, pedimos su activación
				if (!elManager.isProviderEnabled(mejorProveedor)) {
					Intent i= new Intent (android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
					startActivity(i);
				} 
				//Obtenemos las coordenadas de la posición actual
				Location pos = elManager.getLastKnownLocation(mejorProveedor);
				Location posDEstino = elManager.getLastKnownLocation(mejorProveedor); 
				float distancia = 0;
				int codTesoro;
				String descT = new String();
				//Llamamos a la BD
				Cursor c = db.rawQuery("SELECT Codigo,Descripcion,Longitud,Latitud FROM Tesoros WHERE Reclamado='false'", null);
				while(c.moveToNext() && distancia > 500) {
					//Seteamos la longitud y latitud y calculamos la distancia
					codTesoro = c.getInt(0);
					descT = c.getString(1);
					posDEstino.setLongitude(Float.parseFloat(c.getString(2)));
					posDEstino.setLatitude(Float.parseFloat(c.getString(3)));
					distancia = pos.distanceTo(posDEstino);
				}
				if(distancia < 500) {
					//Se ha encontrado un tesoro cerca, Mostramos mensaje de advertencia
					AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
					dialog.setTitle("¡Tesoro Avistado!");
					dialog.setMessage("El tesoro con la descripción: "+descT+" se encuentra a "+distancia+" metros \n ¿Desea reclamarlo?");
					dialog.setCancelable(false);
					//Definimos las acciones de positivo y negativo
					dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
						//Si pulsa si, reclamamos el tesoro
						@Override
						public void onClick(DialogInterface dialog, int which) {
							/*#############################################
							#############################################*/
							db.execSQL("Update .....");
						}
					});
					dialog.setNegativeButton("No",new DialogInterface.OnClickListener() {
						//Si pulsa no, cerramos el dialogo
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							
						}
					} );
				}else {
					//No se ha llegado a encontrar nada, mostramos un mensaje simple
					AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
					dialog.setTitle("Advertencia");
					dialog.setMessage("No hay ningún tesoro cerca de tu posición");
					dialog.setCancelable(false);
					dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
							
						}
					});
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//	Aquí se programa la elección del menú añadir
		switch (item.getItemId()) {
			case R.id.anadir:
				//Ejecutamos un intent que espera un valor de la otra interfaz para introducir los datos en la BD
				Intent i = new Intent(MainActivity.this,AnadirTesoro.class);
				startActivityForResult(i, 1);
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//Éste método recogerá el resultado de AnadirTesoro y meterá los datos a la base de datos.
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1 && resultCode == 2) {
			Bundle extras = data.getExtras();
			if(extras != null) {
				String sentenciaSQL = extras.getString("sentencia");
				if(sentenciaSQL.length() > 0) {
					//Hay datos introducimos todo en la bD
					Toast.makeText(getApplicationContext(), "Inserto los datos en la BD", Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	
}
