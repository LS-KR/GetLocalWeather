package io.github.elihuso.module;

import io.github.elihuso.style.LoggerColor;
import io.github.elihuso.style.LoggerGround;
import io.github.elihuso.style.LoggerLevel;

public class Logger {

    static String[] levelTeg = {"[+] ", "[-] ", "[*] ", "[!] "};

    public static void SetLoggerColor(LoggerGround ground, LoggerColor color) {
        int v = ground.getValue() + color.getValue();
        System.out.print("\033[");
        System.out.print(v);
        System.out.print("m");
    }

    public static void SetRGBLoggerColor(LoggerGround ground, int R, int G, int B) {
        if ((R < 0) || (R > 255) || (G < 0) || (G > 255) || (B < 0) || (B > 255))
            throw new IllegalArgumentException("Invalid Color: (" + R + "," + G + "," + B + ")");
        int v = ground.getValue() + 8;
        System.out.print("\033[" + v + ";2;" + R + ";" + G + ";" + B + "m");
    }

    public static void SetNormalLogger() {
        System.out.print("\033[0m");
    }

    public static void Log(LoggerLevel level, String message) {
        switch (level) {
            case NEGATIVE:
                SetLoggerColor(LoggerGround.FOREGROUND, LoggerColor.BRIGHT_RED);
                break;
            case POSITIVE:
                SetLoggerColor(LoggerGround.FOREGROUND, LoggerColor.BRIGHT_GREEN);
                break;
            case NOTIFICATION:
                SetLoggerColor(LoggerGround.FOREGROUND, LoggerColor.BRIGHT_BLUE);
                break;
            case WARNING:
                SetLoggerColor(LoggerGround.FOREGROUND, LoggerColor.YELLOW);
        }
        System.out.print(levelTeg[level.getValue()]);
        SetNormalLogger();
        System.out.println(message);
    }
}

