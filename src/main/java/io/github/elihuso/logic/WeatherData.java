package io.github.elihuso.logic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.elihuso.config.ConfigImplements;
import io.github.elihuso.module.HTTPSend;
import io.github.elihuso.module.Logger;
import io.github.elihuso.style.LoggerLevel;

import java.io.IOException;
import java.util.Scanner;

public class WeatherData {
    public static String getUrl(String code) {
        return "http://www.weather.com.cn/weather1d/" + code + ".shtml";
    }

    public static String getWeather(String code) throws IOException {
        String raw = HTTPSend.getHTML(getUrl(code));
        String city = "";
        String weather = "";
        String emoji = "";
        int temperature = -2147483648;
        for (var v : ConfigImplements.cityCodes) {
            if (v.getCode().equalsIgnoreCase(code))
                city = v.getCity();
        }
        Scanner scanner = new Scanner(raw);
        while (scanner.hasNext()) {
            String p = scanner.nextLine();
            if (p.contains("var hour3data=")) {
                Logger.Log(LoggerLevel.NOTIFICATION, String.valueOf(p.indexOf("var hour3data=")));
                p = p.replace("var hour3data=", "");
                Logger.Log(LoggerLevel.NOTIFICATION, p);
                JsonObject jsonObject = new Gson().fromJson(p, JsonObject.class);
                JsonArray jsonArray = jsonObject.get("1d").getAsJsonArray();
                String s = jsonArray.get(0).getAsString();
                for (var u : s.split(",")) {
                    for (var w : ConfigImplements.weathers) {
                        if (u.contains(w.getKey())) {
                            if (weather.isEmpty()) {
                                weather = w.getName();
                                Logger.Log(LoggerLevel.POSITIVE, weather);
                            }
                            if (emoji.isEmpty()) {
                                emoji = w.getEmoji();
                                Logger.Log(LoggerLevel.POSITIVE, emoji);
                            }
                        }
                    }
                    if (u.contains("℃")) {
                        Logger.Log(LoggerLevel.POSITIVE, u.replace('℃', '\0'));
                        temperature = Integer.parseInt(u.replace("℃", ""));
                    }
                }
            }
        }
        return String.format("{\"city\":\"%s\",\"weather\":\"%s\",\"emoji\":\"%s\",\"temperature\":\"%d\"}", city, weather, emoji, temperature);
    }
}
