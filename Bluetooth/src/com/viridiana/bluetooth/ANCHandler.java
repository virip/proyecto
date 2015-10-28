package com.viridiana.bluetooth;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class ANCHandler extends Handler {
	private Queue<Integer> ventanaT = new LinkedList<Integer>();
	public Handler handler;
	//public ANCHandler ancHandler;
	public ANCHandler(Looper looper) {
		super(looper);
	}
	
	/*public class ANCThread extends Thread {
		// Está clase servirá para el algoritmo ANC
		private Queue<Integer> ventanaT1 = new LinkedList<Integer>();
		// filtro de prueba filtro 3
		private double[] w = { 0.031755227821976, 0.020798176324179,
				-0.133654926794862, 0.253499736151579, 0.694804371235957,
				0.253499736151579, -0.133654926794862, 0.020798176324179,
				0.031755227821976 };
		// El orden dek filtro
		private int orden = 8;
		// condicion de convergencia
		private double mu = 0.01;
		private int contador;

		
		public Handler handler;
		

		@Override
		public void run() {
			Looper.prepare();
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					ventanaT1 = (Queue) msg.obj;
					System.out.println(ventanaT1.toString());
				}
					
			};
			Looper.loop();

		}

		public double separaANC(int[] t, int a) {
			// t -> señal torácica a -> señal abdominal
			double y = 0;
			double error;
			for (int k = 0; k < orden; k++) {
				y = y + t[orden - k] * w[k];
			}
			error = a - y;
			// AJUSTE DEL FILTRO
			int i = 0;
			for (int k = 0; k < 8; k++) {
				w[k] = w[k] + mu * error * t[orden - i];
				i = i + 1;
			}
			return error;
		}

	}*/
	
	@Override
	public void handleMessage(Message msg) {
		ventanaT =(Queue<Integer>)msg.obj;
		//ancHandler.sendMessage(msg);
		//System.out.println(ventanaT);
		new Thread(new Runnable() {
		    public void run() {
		    	System.out.println(ventanaT);
		    }
		}).start();
		
		
	}
}
