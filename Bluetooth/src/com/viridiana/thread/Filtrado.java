package com.viridiana.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.viridiana.bluetooth.AttendantHandler;
import com.viridiana.bluetooth.Procesamiento;
import com.viridiana.bluetooth.ServiceHandler;
import com.viridiana.bluetooth.Signals;

public class Filtrado extends Thread{
	
	private Signals muestras;
	private Procesamiento proc;
	Handler handler;
	public Filtrado(Signals muestras){
		this.muestras = muestras;
		proc = new Procesamiento();
	}
	
	@Override
	public void run(){
	
		double fetal = proc.separarANC(AttendantHandler.ventanaT1, muestras.getA1());
		ServiceHandler.fetalECG = proc.calcularFrecuencia(fetal, ServiceHandler.fetalECG);
		//Log.d("Señal fetal", "" + fetal);
		//Procesamiento.freqCheck();
		//System.out.println("fetal: "+fetal);
		AttendantHandler.ventanaT1.remove();
	}
}
