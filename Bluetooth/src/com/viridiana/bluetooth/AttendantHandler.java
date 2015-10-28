package com.viridiana.bluetooth;

import java.util.LinkedList;
import java.util.Queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.viridiana.thread.Filtrado;

public class AttendantHandler extends Thread {
	public static Queue<Integer> ventanaT1 = new LinkedList<Integer>();
	public Handler attendantThreadHandler;
	Signals muestras;
	Procesamiento proc = new Procesamiento();
	@Override
	public void run() {
		Looper.prepare();
		attendantThreadHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				muestras = (Signals) msg.obj;
				//C�lculo de la frecuencia
				ServiceHandler.toracicaECG = proc.calcularFrecuencia(muestras.getT1(), ServiceHandler.toracicaECG);
				//Dependiendo del orden del filtro
				Log.d("window size", "" + ventanaT1.size());

				synchronized(ventanaT1){
					ventanaT1.add(muestras.getT1());
					if(ventanaT1.size()==8){
						Queue<Integer> nVentanT1 = new LinkedList<Integer>();
							for(Integer sample : ventanaT1){
								nVentanT1.add(sample);
							}
						ventanaT1.clear();
						/*****************************************************************************************
						 * 
						 * 	Debes revisar que estás pasando el paquete de 8 muestras junto con el paquete
						 * actual de 5 muestras (actuales), que incluye a la 8va muestra (con la que acompletas
						 * lo requerido para empezar a filtrar).
						 * 
						 *  
						 ******************************************************************************************/
						new Filtrado(muestras,nVentanT1).start();
					}
				}
			}
		};
		Looper.loop();
	}
}