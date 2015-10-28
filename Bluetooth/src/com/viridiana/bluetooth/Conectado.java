package com.viridiana.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class Conectado extends AsyncTask<String, String, String> {
	private Activity activity;
	private final BluetoothSocket socket;
	private final InputStream inStream;
	private final OutputStream outStream;

	public Conectado(BluetoothSocket socket, Activity activity) {
		this.activity = activity;
		this.socket = socket;
		InputStream tmpIn = null;
		OutputStream tmpOut = null;
		try {
			tmpIn = socket.getInputStream();
			tmpOut = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		inStream = tmpIn;
		outStream = tmpOut;
	}

	@Override
	protected String doInBackground(String... ags) {
		boolean flag=true; //cuando recibo el primer dato inicio el servicio
		int[] signals = null; //separar las diferentes señales
		int nsignal=0;
		String result = null;
		byte[] buffer = new byte[1024]; // buffer store for the stream
		int bufpos=0;
		String datos;
		try {			
			//datos = "Leyendo...";
			//publishProgress(datos);
			while (true) {
				int bytesDisponibles;
			    bytesDisponibles = inStream.available();
			    if(bytesDisponibles >0){
			    	byte[] recibido = new byte[bytesDisponibles];
			    	inStream.read(recibido);
			    	//datos = String.valueOf("leimos: " + bytesDisponibles);
			    	//publishProgress(datos);
			    	//System.out.println(bytesDisponibles);
			    	for(int i=0;i<bytesDisponibles;i++)
                    {
                        byte b = recibido[i];
			    				    	
				    	if(b == 10)
	                    {
							byte[] codificar = new byte[bufpos];
							System.arraycopy(buffer, 0, codificar, 0, codificar.length);
							datos = new String(codificar, "US-ASCII");
							//Número de señales
							if (datos.matches(".*M5.*")){
							//if (datos.matches(".*M.*")){
								String[] separar = datos.split("M");
								System.out.println(separar);
							}
							//Quitar el salto de línea
							datos =datos.trim();
							if(flag){
								flag=false;
								//Intent intent = new Intent(activity.getApplicationContext(), Servicio.class);
								//intent.putExtra("dato", datos);
								//activity.startService(intent);
							}
							//Signals dato = new Signals();
							//dato.setDato(datos);
							Looper.prepare();
							Handler mHandler = new Handler();
							new Message();
							/*Message msg = mHandler.obtainMessage();
							Bundle bun = new Bundle();
							bun.putString("dato", datos);
							msg.setData(bun);
							mHandler.sendMessage(msg);*/
							Message msg = Message.obtain();
							msg.obj = datos;
							mHandler.sendMessage(msg);
							Looper.loop();
							/*if(flag){
								nsignal++;
									//signals[j]=Integer.parseInt(datos);
									//System.out.println("Señal: " +j+ " " +signals[j]);
								
								publishProgress("Señal: " +nsignal+ " " + datos);
								if(nsignal==5){
									flag=false;
									nsignal=0;
								}
								
							}
							//.* cualquier caracter cero o más veces
							if(datos.matches(".*T.*")){
								//System.out.println("data matches");
								flag=true;
								
							}*/
							publishProgress(datos);
							buffer = new byte[1024];
							bufpos = 0;
	                    }
				    	else
                        {
                            buffer[bufpos++] = b;
                        }
                    }
			    }
				
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
		
	}
	//Agrega los datos recibidos a la lista 
	@Override
	protected void onProgressUpdate(String... args) {
		MainActivity mActivity = (MainActivity) activity;
		if (mActivity != null) {
			mActivity.addRowToList(args[0]);
		}
	}

	@Override
	protected void onPostExecute(String result) {
		Toast.makeText(activity, result, Toast.LENGTH_SHORT).show();
	}

	/* Call this from the main activity to send data to the remote device */
	public void write(byte[] bytes) {
		try {
			outStream.write(bytes);
		} catch (IOException e) {
		}
	}

	/* Call this from the main activity to shutdown the connection */
	public void cancel() {
		try {
			socket.close();
		} catch (IOException e) {
		}
	}
}
