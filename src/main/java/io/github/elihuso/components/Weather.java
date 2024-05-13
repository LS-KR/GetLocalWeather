package io.github.elihuso.components;

public class Weather {
    private final String key;
    private final String name;
    private final String emoji;

    public Weather(String key, String name, String emoji) {
        this.key = key;
        this.name = name;
        this.emoji = emoji;
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

    public String getKey() {
        return key;
    }
}
