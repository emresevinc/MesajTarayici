package com.emresevinc.mesajtarayici;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;




public class MainActivity extends Activity implements AnimationListener{

	LinearLayout lL= null;
	Button donbuyut=null;
	Animation animat=null;
	Spinner spinner = null;
	EditText aramaEditText = null;
    ProgressDialog progressDialog = null;
	static final List<SMS> smsList = new ArrayList<SMS>();
	Cursor c = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lL = (LinearLayout) findViewById(R.id.linearLayout1);
            
        animat = AnimationUtils.loadAnimation(getApplicationContext(),
        R.anim.btndondurbuyut);
        animat.setAnimationListener((AnimationListener) this);
        
        lL.startAnimation(animat);
        spinner = (Spinner)findViewById(R.id.sampleSpinner);
        ArrayAdapter<String> arrAdap = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, new String[]{"inbox","sendbox"});
        arrAdap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrAdap);
        aramaEditText = (EditText)findViewById(R.id.aramaEditText);
    }
    
 
    Intent searchResultIntent = null;
    public void donBuyut(View v)
    {
    	searchResultIntent = new Intent(this,SearchResultList.class);
    	new Background().execute((Void)null);
    }

    
    public Map<String,String> getAllContacts()
    {
    	Map<String,String> kisiListesi = new HashMap<String,String>();
    	ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        if (cur.getCount() > 0) {
        	String id=null,name=null,phoneNo=null;
            while (cur.moveToNext()) {
                  id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                  name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                  if (Integer.parseInt(cur.getString(  						//Eðer kiþiye kayýtlbir numara varsa 
                        cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                     Cursor pCur = cr.query(
                               ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                               null,
                               ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                               new String[]{id}, null);
                     while (pCur.moveToNext()) {
                         phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                     }
                    kisiListesi.put(name, phoneNo);
                    pCur.close();
                }
            }
        }
        else
        {
        	Toast.makeText(this, "Rehberinizde mesaj gönderici bilgisi alabileceðiniz bir kaydýnýz yok.", Toast.LENGTH_LONG).show();
        	return null;
        }
		return kisiListesi;
    }
    
    
    private class Background extends AsyncTask<Void, Void, Void>
    {
    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
    		progressDialog =ProgressDialog.show(MainActivity.this, "Lütfen Bekleyin", "Mesaj içerikleri taranýyor...");
    	}
    	
		@Override
		protected Void doInBackground(Void... params) {
	    	String aranan = aramaEditText.getText().toString();
	    	int secim = spinner.getSelectedItemPosition();
	    	Uri uri = null;
	    	if (secim == 0) {
	    		uri = Uri.parse("content://sms/inbox");
			}else {
				uri = Uri.parse("content://sms/sent");
			}
	        c= getContentResolver().query(uri, null, null ,null,null);
	        startManagingCursor(c);
	        smsList.clear();
	        int toplamSMS=c.getCount();
	        progressDialog.setMax(toplamSMS);
	        progressDialog.show();
	        if(c.moveToFirst()) {
	           
	        	Map <String,String> m = getAllContacts();
	            for(int i=0; i < toplamSMS; i++) {
	            	//publishProgress(i);
	                SMS sms = new SMS();
	                sms.setIcerik(c.getString(c.getColumnIndexOrThrow("body")).toString());
	                
	                if (sms.getIcerik().contains(aranan)) {
	                	String adr = c.getString(c.getColumnIndexOrThrow("address")).toString();
	                	sms.setSahip(adr);
	                    sms.setTarih(c.getString(c.getColumnIndexOrThrow("date")).trim());
	                    for(Entry<String, String> entry: m.entrySet()) {
							if (adr.contains(entry.getValue()) || entry.getValue().contains(adr)) {
								sms.setGondericiAdi(entry.getKey());
								break;
							}
						}
	                	smsList.add(sms);
					}
	                c.moveToNext();
	            }
	        }
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			progressDialog.dismiss();
			startActivity(searchResultIntent);
		}
    }


	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (c != null) {
			c.close();
		}
	}


	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onAnimationStart(Animation animation) {

	}
	
	

}
