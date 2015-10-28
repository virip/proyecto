package com.viridiana.bluetooth;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class Servicio extends Service {
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;

	// Cuando el servicio inicia por primera vez se llama a este método
	@Override
	public void onCreate() {
		HandlerThread thread = new HandlerThread("ServiceStartArguments",
				android.os.Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		// Get the HandlerThread's Looper and use it for our Handler
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}

	//Se manda el dispositivo con quien se va a conectar
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		Bundle paq = intent.getExtras();
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		msg.obj = (BluetoothDevice) paq.get("dispositivo");
		mServiceHandler.sendMessage(msg);
		// If we get killed, after returning from here, restart (START_STICKY)
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		mServiceHandler.terminateActions();
		Toast.makeText(this, "service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// return mBinder;
		return null;
	}
}
