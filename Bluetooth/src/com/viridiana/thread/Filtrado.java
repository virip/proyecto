package com.viridiana.thread;

import java.util.Queue;

import android.os.Handler;

import com.viridiana.bluetooth.AttendantHandler;
import com.viridiana.bluetooth.Procesamiento;
import com.viridiana.bluetooth.ServiceHandler;
import com.viridiana.bluetooth.Signals;

public class Filtrado extends Thread{
	
	private Signals muestras;
	private Queue<Integer> ventanaT1;
	private Procesamiento proc;
	Handler handler;
	public Filtrado(Signals muestras, Queue<Integer> ventanaT1){
		this.muestras = muestras;
		this.ventanaT1 = ventanaT1;
		proc = new Procesamiento();
	}
	
	@Override
	public void run(){
		double fetal = proc.separarANC(ventanaT1, muestras.getA1());
		ServiceHandler.fetalECG = proc.calcularFrecuencia(fetal, ServiceHandler.fetalECG);
		ventanaT1.clear();
		//Log.d("Se�al fetal", "" + fetal);
		//Procesamiento.freqCheck();
		//System.out.println("fetal: "+fetal);
	}
}
