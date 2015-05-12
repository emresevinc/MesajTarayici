package com.emresevinc.mesajtarayici;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultList extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchresult);
		
		final ListView lw = (ListView) findViewById(R.id.liste);
        lw.setAdapter(new OzelListAdapter(this,MainActivity.smsList));
        if (MainActivity.smsList.size()<1) {
			Toast.makeText(this, "Aradýðýnýz karakter dizisi bulunamadý", Toast.LENGTH_LONG).show();
		}
	}
	@Override
	public void onBackPressed() 
	{
		this.finish();
	}
	
			
		
		
	 
	
}
