package com.emresevinc.mesajtarayici;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class SMS {

	String sahip;
	String icerik;
	String tarih;
	String gondericiAdi;
	
	public String getGondericiAdi() {
		return gondericiAdi;
	}
	public void setGondericiAdi(String gondericiAdi) {
		this.gondericiAdi = gondericiAdi;
	}
	DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public String getTarih() {
		long l = Long.parseLong(tarih);
		Date dt = new Date(l);    // tarih de�i�kenini formatl� hali ile de�i�tirip tarih return  
		return format.format(dt); // edince problem ��kard��� i�in ��z�m� bu �ekilde buldum.
	}
	public void setTarih(String tarih){
		this.tarih = tarih;
	}
	public String getSahip() {
		return sahip;
	}
	public void setSahip(String sahip) {
		this.sahip = sahip;
	}
	public String getIcerik() {
		return icerik;
	}
	public void setIcerik(String icerik) {
		this.icerik = icerik;
	}
}
