package com.emresevinc.mesajtarayici;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OzelListAdapter extends BaseAdapter {
	

    private final List<SMS> smsList;
    LayoutInflater inflater= null;
    
   public OzelListAdapter(Activity activity, List<SMS> smslist) { 
       this.smsList = MainActivity.smsList;
       inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {        
       View rowView = inflater.inflate(R.layout.itemss, null);  
       SMS sms = smsList.get(position);
       TextView isimVeSoyisim = (TextView) rowView.findViewById(R.id.isimsoyisim);
       TextView tarih = (TextView) rowView.findViewById(R.id.tarih);
       TextView mesajicerik = (TextView) rowView.findViewById(R.id.mesajicerik);
       isimVeSoyisim.setText(sms.getSahip()+"  "+sms.getGondericiAdi());
       tarih.setText(sms.getTarih());
       mesajicerik.setText(sms.getIcerik());
       return rowView;
   }

@Override
public int getCount() {

	return smsList.size();
}

@Override
public Object getItem(int location) {

	return smsList.get(location);
}

@Override
public long getItemId(int location) {
	return location;
}
}
