package com.tarakanov.alex.pogoda;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import java.io.IOException;
import java.net.URL;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;



import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView lblTemperature;
    TextView lblTemperatureChange;
    TextView lblPressure;
    TextView lblPressureChange;
    TextView lblLight;
    TextView lblAverageTemperature;
    Button btnShowData;

    public void updateData() {
            Parser parser = new Parser();
            parser.execute();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // соединяемся с экранными элементами
        lblTemperature = (TextView) findViewById(R.id.lblTemperature);
        lblTemperatureChange = (TextView) findViewById(R.id.lblTemperatureChange);
        lblPressure = (TextView) findViewById(R.id.lblPressure);
        lblPressureChange = (TextView) findViewById(R.id.lblPressureChange);
        lblLight = (TextView) findViewById(R.id.lblLight);
        lblAverageTemperature = (TextView) findViewById(R.id.lblAverageTemperature);
        btnShowData = (Button) findViewById(R.id.btnShowData);

        btnShowData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateData();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class Parser extends AsyncTask<Void, Void, Void> {
        protected final String sourceURL = "http://yartemp.com/";
        Document page;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                page = Jsoup.parse(new URL(sourceURL), 3000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            updateDataFromInternet();
        }

        public void updateDataFromInternet() {
            String temperature;
            String temperatureChange;
            String pressure;
            String pressureChange;
            String light;
            String averageTemperature;

            Element tableWeather = page.select("div[class=tempInfoDivInner]").first();

            String temp1 = tableWeather.select("span[id=spTemp1]").text();
            String temp2 = tableWeather.select("span[id=spTemp1a]").text();
            temperature = "Текущая температура: " + temp1 + temp2 + "°C";

            String tempChange1 = tableWeather.select("span[id=spTemp2]").text();
            String tempChange2 = tableWeather.select("span[id=spTemp2a]").text();
            temperatureChange = "Измение температуры: " + tempChange1 + tempChange2 + "°C/час";

            String pressure1 = tableWeather.select("span[id=spPressure]").text();
            pressure = "Атмосферное давление: " + pressure1 + " мм рт.ст.";

            String pressurePerHour = tableWeather.select("span[id=spPressurePerHour]").text();
            pressureChange = "Изменение давления: " + pressurePerHour + " мм рт.ст./час";

            String light1 = tableWeather.select("span[id=spLight]").text();
            light = "Солнечность: " + light1;

            String temperatureLast24Hours = tableWeather.select("span[id=spTemp4]").text();
            averageTemperature = "Средняя температура \nза последние 24 часа: " + temperatureLast24Hours + "C";

            lblTemperature.setText(temperature);
            lblTemperatureChange.setText(temperatureChange);
            lblPressure.setText(pressure);
            lblPressureChange.setText(pressureChange );
            lblLight.setText(light);
            lblAverageTemperature.setText(averageTemperature);
        }
    }

}
