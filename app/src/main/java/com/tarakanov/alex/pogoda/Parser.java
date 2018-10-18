package com.tarakanov.alex.pogoda;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;

public class Parser extends AsyncTask<Void, Void, Void> {
    protected final String sourceURL = "http://yartemp.com/";
    protected String temperature;
    protected String temperatureChange;
    protected String pressure;
    protected String pressureChange;
    protected String light;
    protected String averageTemperature;
    protected Document page;

    public Parser() {

    }

    String getTemperature(){
        return temperature;
    }

    String getTemperatureChange(){
        return temperatureChange;
    }

    String getPressure() {
        return pressure;
    }

    String getPressureChange() {
        return pressureChange;
    }

    String getLight() {
        return light;
    }

    String getAverageTemperature() {
        return averageTemperature;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            page = Jsoup.parse(new URL(sourceURL), 5000);
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
        averageTemperature = "Средняя температура за последние 24 часа: " + temperatureLast24Hours + "C";

    }
}
