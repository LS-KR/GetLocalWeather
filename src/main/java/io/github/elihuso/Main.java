package io.github.elihuso;

import com.sun.net.httpserver.HttpServer;
import io.github.elihuso.config.ConfigImplements;
import io.github.elihuso.config.ConfigManager;
import io.github.elihuso.module.Logger;
import io.github.elihuso.server.GetCode;
import io.github.elihuso.server.GetWeather;
import io.github.elihuso.style.LoggerLevel;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {

    @Option(name = "-c", usage = "Config Path")
    String configPath = "config.ini";

    @Argument
    private List<String> arguments = new ArrayList<String>();

    public static ClassLoader loader;
    public static HttpServer server;
    public static ConfigManager configManager;
    public static boolean running = true;

    public static void main(String[] args) throws InterruptedException {
        loader = Thread.currentThread().getContextClassLoader();
        new Main().doMain(args);
    }

    public void doMain(String[] args) throws InterruptedException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);
        }
        catch (Exception ex) {
            Logger.Log(LoggerLevel.NEGATIVE, "Error in parse arguments");
            Logger.Log(LoggerLevel.WARNING, "usage: UABoomServer [-c config]");
            return;
        }

        try {
            configManager = new ConfigManager(new File(configPath));
        }
        catch (IOException ex) {
            Logger.Log(LoggerLevel.NEGATIVE, "Error in initial config: " + ex.getLocalizedMessage());
            return;
        }

        try {
            server = HttpServer.create(new InetSocketAddress(ConfigImplements.port), 0);
        }
        catch (IOException ex) {
            Logger.Log(LoggerLevel.NEGATIVE, "Error on creating server: " + ex.getLocalizedMessage());
            return;
        }

        server.createContext("/citycode.json", new GetCode());
        server.createContext("/weather.json", new GetWeather());
        server.start();

        Logger.Log(LoggerLevel.NOTIFICATION, "Server Started.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println();
            Logger.Log(LoggerLevel.WARNING, "Closing Server...");
            Main.server.stop(0);
            Logger.Log(LoggerLevel.NOTIFICATION, "Goodbye!");
            Main.running = false;
        }));
        while (running)
            Thread.sleep(250);
    }
}