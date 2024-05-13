package io.github.elihuso.logic;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Streaming {
    public static String ReadInputStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine()).append(String.format("%n"));
        }
        scanner.close();
        return builder.toString();
    }

    public static byte[] ByteInputStream(InputStream inputStream) throws IOException {
        return IOUtils.toByteArray(inputStream);
    }
}

