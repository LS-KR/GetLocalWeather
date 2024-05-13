package io.github.elihuso.server;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import io.github.elihuso.logic.Streaming;
import io.github.elihuso.logic.WeatherData;
import io.github.elihuso.module.Logger;
import io.github.elihuso.style.LoggerLevel;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class GetWeather implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        byte[] response = {};

        if (!httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            httpExchange.sendResponseHeaders(400, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
            return;
        }

        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, authentication");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true");
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        httpExchange.getResponseHeaders().add("Content-Type", "application/json");

        String body = Streaming.ReadInputStream(httpExchange.getRequestBody());
        JsonObject object = new Gson().fromJson(body, JsonObject.class);

        Logger.Log(LoggerLevel.NOTIFICATION, "Body: " + body);

        if (!object.has("code")) {
            httpExchange.sendResponseHeaders(400, response.length);
            OutputStream outputStream = httpExchange.getResponseBody();
            outputStream.write(response);
            outputStream.close();
            return;
        }

        String code = object.get("code").getAsString();
        response = WeatherData.getWeather(code).getBytes(StandardCharsets.UTF_8);
        Logger.Log(LoggerLevel.POSITIVE, code + ": " + WeatherData.getWeather(code));

        httpExchange.sendResponseHeaders(200, response.length);
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response);
        outputStream.close();
    }
}
