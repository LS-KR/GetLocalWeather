package io.github.elihuso.config;

import io.github.elihuso.data.JavaResources;
import io.github.elihuso.data.ResourceData;
import io.github.elihuso.logic.Streaming;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class ConfigManager {
    private final File file;
    private Ini ini;

    public ConfigManager(File file) throws IOException {
        this.file = file;
        init();
    }

    private void init() throws IOException {
        if (!this.file.exists()) {
            file.createNewFile();
            Files.write(file.toPath(), Streaming.ByteInputStream(JavaResources.getResource("config.ini")), StandardOpenOption.SYNC);
            this.ini = new Ini(file);
        }
        else {
            this.ini = new Ini(file);
            if (ini.get("DEFAULT").containsKey("port")) {
                ConfigImplements.port = Integer.parseInt(ini.get("DEFAULT", "port"));
            }
        }
        ConfigImplements.cityCodes = ResourceData.getCityCodes();
        ConfigImplements.weathers = ResourceData.getWeather();
    }

    public boolean contains(String section, String key) {
        return ini.get(section).containsKey(key);
    }

    public Object get(String section, String key) {
        return ini.get(section, key);
    }

    public String getString(String section, String key) {
        return ini.get(section, key);
    }

    public int getInt(String section, String key) {
        return Integer.parseInt(ini.get(section, key));
    }

    public double getDouble(String section, String key) {
        return Double.parseDouble(ini.get(section, key));
    }

    public boolean getBoolean(String section, String key) {
        return Boolean.parseBoolean(ini.get(section, key));
    }
}
