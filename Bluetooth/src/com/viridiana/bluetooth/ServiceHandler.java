package com.viridiana.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class ServiceHandler extends Handler {
	private AttendantHandler attendantHandler;
	double fetal;
	int contv = 0;
	private BluetoothSocket socket;
	private InputStream tmpIn;

	public static volatile ECGSignal toracicaECG = new ECGSignal(60, 100, 225,
			255, 0, 0, false);
	public static volatile ECGSignal fetalECG = new ECGSignal(120, 140, 225,
			255, 0, 0, false); // DEFINIR AMPLITUDES

	public ServiceHandler(Looper looper) {
		super(looper);
	}
	
	//Conexión con el bluetooth en segundo plano 
	@Override
	public void handleMessage(Message msg) {
		try {
			BluetoothDevice dispositivo = (BluetoothDevice) msg.obj;
			try {
				socket = dispositivo.createRfcommSocketToServiceRecord(UUID
						.fromString("00001101-0000-1000-8000-00805f9b34fb"));
				socket.connect();
				tmpIn = socket.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException closeException) {
					closeException.printStackTrace();
				}
				return;
			}
			// Los buffers
			attendantHandler = new AttendantHandler();
			attendantHandler.start();
			InputStream inS = tmpIn;
			Message newMsg;
			Signals signals = new Signals();
			byte[] buffer = new byte[1024]; // buffer store for the stream
			int bufpos = 0;
			String datos;
			int contador = 0;
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					Procesamiento.freqCheck();
				}
			};
			Timer timer = new Timer();
			timer.schedule(task, 60000, 60000);
			
			// empieza la recepción...
			while (true) {
				int bytesDisponibles;
				bytesDisponibles = inS.available();
				if (bytesDisponibles > 0) {
					byte[] recibido = new byte[bytesDisponibles];
					inS.read(recibido);
					for (int i = 0; i < bytesDisponibles; i++) {
						byte b = recibido[i];
						if (b == 10) {
							byte[] codificar = new byte[bufpos];
							System.arraycopy(buffer, 0, codificar, 0,
									codificar.length);
							datos = new String(codificar, "US-ASCII");
							// Quitar el salto de línea
							datos = datos.trim();

							// Clasificación de muestras
							switch (contador) {
							case 1:
								signals.setT1(Integer.parseInt(datos));
								contador++;
								break;
							case 2:
								signals.setT2(Integer.parseInt(datos));
								contador++;
								break;
							case 3:
								signals.setA1(Integer.parseInt(datos));
								contador++;
								break;
							case 4:
								signals.setA2(Integer.parseInt(datos));
								contador++;
								break;
							case 5:
								signals.setA3(Integer.parseInt(datos));
								// Mando el dato a procesar solo una vez
								newMsg = attendantHandler.attendantThreadHandler
										.obtainMessage();
								newMsg.obj = signals;
								attendantHandler.attendantThreadHandler
										.sendMessage(newMsg);
								
								contador = 0;
								break;
							default:
								break;
							}
							// Esto es la cabecera
							if (datos.matches(".*T.*")) {
								contador = 0;
								signals.setCabecera(datos);
								contador++;
							}
							buffer = new byte[1024];
							bufpos = 0;

						} else {
							buffer[bufpos++] = b;
						}
					}
				}
			}

		} catch (IOException e) {
			Log.d("ServiceHandler: ", "Server down!\n" + e.getMessage());
		}
	}
	
	public void terminateActions(){
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
