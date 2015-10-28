package com.viridiana.bluetooth;

import java.util.Set;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class Bluetooth {
	private BluetoothAdapter adaptadorBt;  
	Context context;
	Activity activity;
	ArrayAdapter<String> encontrados ;
	BluetoothDevice dispositivo;
	
	//Constructores
	public Bluetooth(Context context, Activity activity)
	{
		this.activity = activity;
	    this.context = context;
	    adaptadorBt =  BluetoothAdapter.getDefaultAdapter();
	}
	public Bluetooth() {
	}

	//Getters y Setters
	public ArrayAdapter<String> getEncontrados(){
		return encontrados;
	}
	public BluetoothAdapter getAdaptadorBt(){
		return BluetoothAdapter.getDefaultAdapter();
	}
	public BluetoothDevice getDispositivo(){
		return dispositivo;
	}
	public void setDispositivo(BluetoothDevice dispositivo){
		this.dispositivo =  dispositivo;
	}
	
	//¿El Bluetooth está activado?
	public boolean verificarBluetooth(){
		//adaptadorBt = BluetoothAdapter.getDefaultAdapter();
		if(adaptadorBt.isEnabled())
			return true;
		else 
			return false;
	}
	//Activa el Bluetooth
	public boolean activarBluetooth(){ 
		//adaptadorBt = BluetoothAdapter.getDefaultAdapter();
		if(adaptadorBt==null){
			String msj="Bluetooth no soportado en este dispositivo";
			Toast toast = Toast.makeText(context, msj, Toast.LENGTH_SHORT);
			toast.show();
			return false;
		}else{
			if(!adaptadorBt.isEnabled()){
				final int REQUEST_ENABLE_BT = 1;
				Intent activarBt= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			    activity.startActivityForResult(activarBt, REQUEST_ENABLE_BT);
			}
			return true;
		}
	}
	//Encontrar dispositivos sincronnizados
	public Set<BluetoothDevice> obtenerConectados(){
		Set<BluetoothDevice> dispositivos = adaptadorBt.getBondedDevices();
		encontrados = new ArrayAdapter<String>(context, R.layout.elemento_lista);
		if(dispositivos.size()>0){
			for (BluetoothDevice dispositivo: dispositivos){
				encontrados.add(dispositivo.getName() + " " + dispositivo.getAddress());
			}
		}
		else{
			encontrados.add("No se encontraron dispositivos");
		}
		return dispositivos;
	}
	
	//Buscar dispositivos cercanos
	public void buscarDispositivos(){
		if(adaptadorBt.isDiscovering())
			adaptadorBt.cancelDiscovery();
		else{
			encontrados = new ArrayAdapter<String>(context, R.layout.elemento_lista);
			encontrados.clear();
			adaptadorBt.startDiscovery();
			activity.registerReceiver(mRecibe, new IntentFilter(BluetoothDevice.ACTION_FOUND));
		}
	}
	
		// Create a BroadcastReceiver for ACTION_FOUND		
		final BroadcastReceiver mRecibe = new BroadcastReceiver() {
			public void onReceive(Context context, Intent intent) {
				String accion = intent.getAction();
				//ArrayAdapter<String> encontrados = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);
				// When discovery finds a device				 
				if (BluetoothDevice.ACTION_FOUND.equals(accion)) {
					// Get the BluetoothDevice object from the Intent
					dispositivo = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					// Add the name and address to an array adapter to show in a ListView
					
					encontrados.add(dispositivo.getName() + " " + dispositivo.getAddress());
				}
				else{
					encontrados.add("No se encontraron dispositivos");
				}
			}
		};
		
	
	
}
