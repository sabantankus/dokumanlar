package com.sabantankus.havadurumu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.BatchUpdateException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    EditText sehir;
    ImageView resim;
    TextView sicakliktext,endüsüksicakliktext,enyukseksicakliktext,nemtext,gokyuzutext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sehir=findViewById(R.id.editText);
        sicakliktext=findViewById(R.id.textView2);
        endüsüksicakliktext=findViewById(R.id.textView4);
        enyukseksicakliktext=findViewById(R.id.textView6);
        nemtext=findViewById(R.id.textView8);
        gokyuzutext=findViewById(R.id.textView10);
        resim=findViewById(R.id.imageView);

    }
    public void sorgula(View view){
        VerileriGetir verileriGetir=new VerileriGetir();
        try{

            String url=("https://openweathermap.org/data/2.5/weather?q=Ankara&appid=b6907d289e10d714a6e88b30761fae22");
            verileriGetir.execute(url);
        }
        catch (Exception e)
        {

        }

    }

    private class VerileriGetir extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings) {
            String sonuc="";
            URL url;



            HttpsURLConnection httpsURLConnection;
            try{
                url=new URL("https://openweathermap.org/data/2.5/weather?q=Ankara&appid=b6907d289e10d714a6e88b30761fae22");;

                httpsURLConnection=(HttpsURLConnection)url.openConnection();
                InputStream ınputStream=httpsURLConnection.getInputStream();
                InputStreamReader ınputStreamReader=new InputStreamReader(ınputStream);

                int veri=ınputStreamReader.read();
                while (veri>0)
                {
                    char karakter=(char)veri;
                    sonuc+=karakter;
                    veri=ınputStreamReader.read();
                }

                return sonuc;
            }
            catch (Exception e)
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try
            {
                JSONObject jsonObject=new JSONObject(s);
                String havadurumu=jsonObject.getString("main");
                JSONObject jsonObject1=new JSONObject(havadurumu);
                String sicaklik=jsonObject1.getString("temp");
                sicakliktext.setText(sicaklik+" C");
                String endusuk=jsonObject1.getString("temp_min");
                endüsüksicakliktext.setText(endusuk+" C");
                String enyuksek=jsonObject1.getString("temp_max");
                enyukseksicakliktext.setText(enyuksek+" C");
                String nem=jsonObject1.getString("humidity");
                nemtext.setText("%"+nem);
               String bulut=jsonObject.getString("clouds");
               JSONObject jsonObject2=new JSONObject(bulut);
                String bulutdurumu=jsonObject2.getString("all");
                int deger= Integer.valueOf(bulutdurumu);

                if(deger>70)
                {
                    gokyuzutext.setText("Bulutlu");
                    resim.setImageResource(R.drawable.bulutlu);
                }
                else if(deger>40)
                {
                    gokyuzutext.setText("Parçalı Bulutlu");
                    resim.setImageResource(R.drawable.parcali_bulut);
                }
                else
                {
                    gokyuzutext.setText("Gökyüzü Açık");
                    resim.setImageResource(R.drawable.gunesli);
                }

            }
            catch (Exception e)
            {

            }
        }
    }

}
