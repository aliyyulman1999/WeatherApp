package com.rplgdc.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {

    String CITY = "dhaka,bd";
    String API = "8118ed6ee68db2debfaaa5a44c832918";

    TextView address_tv, date_tv, forecast_tv, temp_tv, tempmin_tv, tempmax_tv, sunrise_tv,
    sunset_tv, wind_tv, pressure_tv, humidity_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        address_tv = findViewById(R.id.location);
        date_tv = findViewById(R.id.date);
        forecast_tv = findViewById(R.id.forecast);
        temp_tv = findViewById(R.id.deg);
        tempmax_tv = findViewById(R.id.temp_max);
        tempmin_tv = findViewById(R.id.temp_min);
        sunrise_tv = findViewById(R.id.sunrise);
        sunset_tv = findViewById(R.id.sunset);
        wind_tv = findViewById(R.id.wind);
        pressure_tv = findViewById(R.id.pressure);
        humidity_tv = findViewById(R.id.humidity);

        new weatherTask().execute();
    }
    class weatherTask extends AsyncTask <String, Void, String>{
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        protected String doInBackground(String... args){
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=\" + CITY + \"&units=metric&appid="+ API);
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");
                JSONObject sys = jsonObject.getJSONObject("sys");
                JSONObject wind = jsonObject.getJSONObject("wind");
                JSONObject weather = jsonObject.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObject.getLong("dt");
                String updatedAtText = "Updated at : " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt*1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: "+ main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: "+ main.getString("temp_max")+ "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");
                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");
                String address = jsonObject.getString("name") + "," + sys.getString("country");

                address_tv.setText(address);
                date_tv.setText(updatedAtText);
                forecast_tv.setText(weatherDescription.toUpperCase());
                temp_tv.setText(temp);
                tempmax_tv.setText(tempMax);
                tempmin_tv.setText(tempMin);
                sunrise_tv.setText(new SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(new Date(sunrise*1000)));
                sunset_tv.setText(new SimpleDateFormat("hh:mm a",Locale.ENGLISH).format(new Date(sunrise*1000)));
                wind_tv.setText(windSpeed);
                pressure_tv.setText(pressure);
                humidity_tv.setText(humidity);

                findViewById(R.id.loader).setVisibility(View.GONE);

            }catch (JSONException e){
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }
    }
}
