package com.examenDAS.busquedatesoro;

import java.util.Timer;
import java.util.TimerTask;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ElServicio extends Service {
	
	Timer mTimer;
	public ElServicio() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO: Return the communication channel to the service.
		throw new UnsupportedOperationException("Not yet implemented");
	}
	
	@Override
	 public void onCreate(){
	  super.onCreate();
	  this.mTimer = new Timer();
	  this.mTimer.scheduleAtFixedRate(
	    new TimerTask(){
	     @Override
	     public void run() {
	      ejecutarTarea();
	     }      
	    }
	    , 0, 1000 * 180);
	 }
	 
	 
	private void ejecutarTarea(){
	  Thread t = new Thread(new Runnable() {
	   public void run() {
		 
		 //Qué se tiene que ejecutar
	       	  
	   }
	  });  
	  t.start();
	 }

}
