package com.viridiana.bluetooth;

import java.util.LinkedList;
import java.util.Queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.viridiana.thread.Filtrado;

public class AttendantHandler extends Thread {
	public static volatile Queue<Integer> ventanaT1 = new  LinkedList<Integer>();
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
				ventanaT1.add(muestras.getT1());
				//Cálculo de la frecuencia
				ServiceHandler.toracicaECG = proc.calcularFrecuencia(muestras.getT1(), ServiceHandler.toracicaECG);
				//Dependiendo del orden del filtro
				Log.d("window size", "" + ventanaT1.size());
				if(ventanaT1.size()==8){
				    new Filtrado(muestras).start();
				}
			}
		};
		Looper.loop();
	}
}