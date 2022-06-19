package at.pmzcraft.game.program.engine.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Properties;

public class PropertyLoader {
    private static final Path propertyPath = Path.of("src", "main", "resources", "application.properties");

    private static final Properties PROPERTIES;

    static {
        try {
            PROPERTIES = new Properties();
            FileInputStream fileInputStream = new FileInputStream(propertyPath.toFile());
            PROPERTIES.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }

    public static Path getPath(String key) {
        String pathString = PROPERTIES.getProperty(key);
        String[] tokens = pathString.split(",");
        Path path = Path.of(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            path = Path.of(path.toString(), tokens[i]);
        }
        return path;
    }
}
