package base.properties;


import lombok.NonNull;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class PropertiesHandler {
    private final Properties properties;
    private String md5Hex;

    public PropertiesHandler() {
        properties = new Properties();
    }

    public synchronized void load(@NonNull String inStream) throws IOException {
        String md5Hex;
        try (FileInputStream fileInputStream = new FileInputStream(inStream)) {
            md5Hex = DigestUtils.md5Hex(fileInputStream);
        }
        if (!md5Hex.equals(this.md5Hex)) {
            this.md5Hex = md5Hex;
            try (FileInputStream fileInputStream1 = new FileInputStream(inStream)) {
                properties.load(fileInputStream1);
            }
        }
    }

    public int size() {
        return properties.size();
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
