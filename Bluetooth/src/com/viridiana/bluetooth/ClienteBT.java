package com.viridiana.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.AsyncTask;

public class ClienteBT extends AsyncTask<String,String,Boolean>{
	private final BluetoothSocket socket;
    private final BluetoothDevice dispositivo;
    private Activity activity;
    boolean resultado = false;
 
    public ClienteBT(BluetoothDevice device, Activity activity) {
    	this.activity = activity;
        BluetoothSocket socketTmp = null;
        dispositivo = device;
        try {
            socketTmp = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"));
          
        } catch (IOException e) { }
        socket = socketTmp;
    }
 
    @Override
    protected Boolean doInBackground(String... args) {
    	
    	//Cuando lo encuentra cancela el descubrimiento
        Bluetooth bt = new Bluetooth();
        bt.getAdaptadorBt().cancelDiscovery();
        try {
        	//Conexión
            socket.connect();
            resultado =true;
        } catch (IOException connectException) {
        	connectException.printStackTrace();
            try {
                socket.close();
            } catch (IOException closeException) { 
            	closeException.printStackTrace();
            }
        }
        //Correr otro hilo para recibir los datos 
        Conectado comunicacion = new Conectado(socket,activity);
        comunicacion.execute();
        return resultado;
        
    }
    public void onPostExecute(boolean resultado){
    	
    }
    //Cerrar conexión
    public void cancel() {
        try {
            socket.close();
        } catch (IOException e) { }
    }

}
