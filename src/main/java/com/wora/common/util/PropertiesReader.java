package com.wora.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertiesReader {

    private final static Map<String, String> properties = new HashMap<>();
    private final static String filePath = ".env";

    static {
        synchronized (PropertiesReader.class) {
            if (properties.isEmpty()) {

                try (final BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if ((line.trim().startsWith("#"))) {
                            continue;
                        }

                        final String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            final String key = parts[0].trim();
                            final String value = parts[1].trim();
                            properties.put(key, value);
                        }

                    }
                } catch (IOException e) {
                    throw new RuntimeException("======== Error while reading the .env file ========");
                }
            }
        }
    }

    public static String get(String key) {
        return properties.get(key);
    }
}
