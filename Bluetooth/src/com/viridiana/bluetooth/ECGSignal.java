package com.viridiana.bluetooth;

public class ECGSignal {
	private int frecInicial;
	private int frecFinal;
	private double ampInicial;
	private double ampFinal;
	private int periodos;
	private double aux;
	//a ver si la uso 
	private boolean bandera;
	
	public ECGSignal(int frecInicial, int frecFinal, double ampInicial, double ampFinal, int periodos, double aux, boolean bandera){
		this.frecInicial = frecInicial;
		this.frecFinal = frecFinal; 
		this.ampInicial = ampInicial;
		this.ampFinal = ampFinal;		
		this.periodos = periodos;
		this.aux = aux;
		this.bandera = bandera;
	}
	
	public void setFrecInicial(int frecInicial){
		this.frecInicial = frecInicial;
	}
	
	public int getFrecInicial(){
		return frecInicial;
	}
	
	public void setFrecFinal(int frecFinal){
		this.frecFinal = frecFinal;
	}
	
	public int getFrecFinal(){
		return frecFinal;
	}
	
	public void setAmpInicial(double ampInicial){
		this.ampInicial = ampInicial;
	}
	
	public double getAmpInicial(){
		return ampInicial;
	}
	
	public void setAmpFinal(double ampFinal){
		this.ampFinal = ampFinal;
	}
	
	public double getAmpFinal(){
		return ampFinal;
	}
	
	public void setPeriodos(int periodos){
		this.periodos = periodos;
	}
	
	public int getPeriodos(){
		return periodos;
	}
	
	public void setAux(double aux){
		this.aux = aux;
	}
	
	public double getAux(){
		return aux;
	}
	
	public void setBandera(boolean bandera){
		this.bandera = bandera;
	}
	
	public boolean getBandera(){
		return bandera;
	}
}
