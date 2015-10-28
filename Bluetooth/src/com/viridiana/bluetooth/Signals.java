package com.viridiana.bluetooth;

public class Signals {
	private String cabecera;
	private int t1;
	private int t2;
	private int a1;
	private int a2;
	private int a3;
	
	/*public Signals(String cabecera, int t1, int t2, int a1, int a2, int a3){
		this.cabecera = cabecera;
		this.t1 = t1;
		this.t2 = t2;
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
	}*/
	
	//Getters y Setters
	public void setCabecera(String cabecera){
		this.cabecera=cabecera;
	}
	
	public String getCabecera(){
		return cabecera;
	}
	
	public void setT1(int t1){
		this.t1=t1;
	}
	
	public int getT1(){
		return t1;
	}
	
	public void setT2(int t2){
		this.t2=t2;
	}
	
	public int getT2(){
		return t2;
	}
	
	public void setA1(int a1){
		this.a1=a1;
	}
	
	public int getA1(){
		return a1;
	}
	
	public void setA2(int a2){
		this.a2=a2;
	}
	
	public int getA2(){
		return a2;
	}
	
	public void setA3(int a3){
		this.a3=a3;
	}
	
	public int getA3(){
		return a3;
	}
}
