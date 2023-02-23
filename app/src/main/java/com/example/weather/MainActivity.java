package com.example.weather;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button button;
    TextView coutry_yt,city_yt,temp_yt ,time;

    TextView lat_t, long_t , hum_t , pre_t , win_t ;

    Switch e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.editTextTextPersonName);
        button=findViewById(R.id.button);
        coutry_yt=findViewById(R.id.textView3);
        city_yt=findViewById(R.id.textView4);
        temp_yt=findViewById(R.id.textView5);
        time=findViewById(R.id.textView2);
        lat_t=findViewById(R.id.textView9);
        long_t=findViewById(R.id.textView7);
        hum_t=findViewById(R.id.textView11);
        pre_t=findViewById(R.id.textView13);
        win_t=findViewById(R.id.textView15);

        e.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findweather();
                Toast.makeText(MainActivity.this, "Temperature might vary !!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void findweather(){
        String city=editText.getText().toString();
        String url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=7cb8829935e22d38fa72278244d520ef";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                //basically we r cally an API here
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject object1=jsonObject.getJSONObject("sys");
                    String country_find=object1.getString("country");
                    coutry_yt.setText(country_find);

                    String city_find=jsonObject.getString("name");
                    city_yt.setText(city_find);

                    JSONObject Object2=jsonObject.getJSONObject("main");
                    Integer temp_find=Object2.getInt("temp");
                        temp_yt.setText((temp_find-273) +"°C");



                    // Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);

                    Calendar calendar=Calendar.getInstance();
                    SimpleDateFormat std=new SimpleDateFormat("dd/MM/yyyy \n HH:mm:ss");
                    String date=std.format(calendar.getTime());
                    time.setText(date);

                    JSONObject object2=jsonObject.getJSONObject("coord");
                    double lat_find=object2.getDouble("lat");
                    lat_t.setText(lat_find+"N");

                    JSONObject object3=jsonObject.getJSONObject("coord");
                    double long_find=object2.getDouble("lon");
                    long_t.setText(lat_find+"E");

                    JSONObject object4=jsonObject.getJSONObject("main");
                    int humad=object4.getInt("humidity");
                    hum_t.setText(humad+"%");



                    JSONObject object7=jsonObject.getJSONObject("main");
                    String preassure_t = object7.getString("pressure");
                    pre_t.setText(preassure_t+" hpa");

                    JSONObject object8=jsonObject.getJSONObject("wind");
                    String wdy=object8.getString("speed");
                    win_t.setText(wdy+"km/per hour");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }
}