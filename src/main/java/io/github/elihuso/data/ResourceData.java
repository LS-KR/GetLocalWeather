package io.github.elihuso.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.elihuso.components.CityCode;
import io.github.elihuso.components.Weather;
import io.github.elihuso.logic.Streaming;

import java.util.ArrayList;
import java.util.List;

public class ResourceData {
    public static CityCode[] getCityCodes() {
        List<CityCode> codes = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(Streaming.ReadInputStream(JavaResources.getResource("CityCode.json")), JsonArray.class);
        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            codes.add(new CityCode(object.get("city").getAsString(), object.get("code").getAsString()));
        }
        return codes.toArray(CityCode[]::new);
    }

    public static Weather[] getWeather() {
        List<Weather> weathers = new ArrayList<>();
        JsonArray jsonArray = new Gson().fromJson(Streaming.ReadInputStream(JavaResources.getResource("Weather.json")), JsonArray.class);
        for (int i = 0; i < jsonArray.size(); ++i) {
            JsonObject object = jsonArray.get(i).getAsJsonObject();
            weathers.add(new Weather(object.get("key").getAsString(), object.get("name").getAsString(), object.get("emoji").getAsString()));
        }
        return weathers.toArray(Weather[]::new);
    }
}
