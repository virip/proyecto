package com.viridiana.bluetooth;

import java.util.Queue;

import android.util.Log;

public class Procesamiento {
	// filtro de prueba filtro 3
	private double[] w = { 0.031755227821976, 0.020798176324179, -0.133654926794862, 
			0.253499736151579, 0.694804371235957,0.253499736151579, 
			-0.133654926794862, 0.020798176324179, 0.031755227821976 };
	// El orden dek filtro
	private int orden = 8;
	// condicion de convergencia
	private double mu = 0.01;
	
	public double separarANC(Queue<Integer> ventanaT, int a) {
		// t -> se√±al tor√°cica a -> se√±al abdominal
		Integer[] aux = new Integer[orden];
		Integer[] t = ventanaT.toArray(aux); 
		ventanaT.toArray();
		double y = 0;
		double error;
		for (int k = 0; k < orden; k++) {
			//System.out.println(k);
			y = y + t[orden - (k+1)] * w[k];
		}
		error = a - y;
		// AJUSTE DEL FILTRO
		
		int i = 0;
		for (int k = 0; k < 8; k++) {
			//System.out.println("filtro: " + w[k]);
			w[k] = w[k] + mu * error * t[orden - (i+1)];
			i = i + 1;
		}
		return error;

	}
	public ECGSignal calcularFrecuencia(double muestra, ECGSignal signal ){
		//Log.d("Muestra", "" + muestra);
		//Log.d("Periodos", "" + signal.getPeriodos());
		if(muestra>=signal.getAmpInicial() && muestra<=signal.getAmpFinal()){
			//cuando la muestra actual es mayor a la anterior la cambio
			//Si no, aumento un periodo 
			if(muestra>signal.getAux()){
				signal.setAux(muestra);
				
			}else if(signal.getBandera()){
				signal.setPeriodos(signal.getPeriodos()+1);
				//Controla que se sume el periodo cuando sea necesario 
				signal.setBandera(false);
			}else if (muestra<=signal.getAux()){
				signal.setBandera(true);
			}
		}
		return signal;		
		
	}
	
	public static void freqCheck() {
		Log.d("Sensado de freq seÒal tor·cica", "" + ServiceHandler.toracicaECG.getPeriodos());
		if (ServiceHandler.toracicaECG.getPeriodos() >= ServiceHandler.toracicaECG.getFrecInicial()
				&& ServiceHandler.toracicaECG.getPeriodos() <= ServiceHandler.toracicaECG.getFrecFinal()) {
			// Todo cool
			// Guardar en el archivo
			// Falta implementar los casos del hijo.
		} else if (ServiceHandler.toracicaECG.getPeriodos() < ServiceHandler.toracicaECG.getFrecInicial()) {
			// Bradicardia!
		} else if (ServiceHandler.toracicaECG.getPeriodos() < ServiceHandler.toracicaECG.getFrecFinal()) {
			// Taquicardia
		}
		ServiceHandler.toracicaECG.setPeriodos(0);

	}
}
