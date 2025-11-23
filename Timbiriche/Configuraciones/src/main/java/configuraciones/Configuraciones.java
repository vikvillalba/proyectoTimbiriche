package configuraciones;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author victoria
 */
public class Configuraciones {

    private final Properties props = new Properties();

    public Configuraciones (String configFile) throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(configFile);
        if (input == null) {
            throw new IOException("No se encontró el archivo de configuración: " + configFile);
        }
        props.load(input);
    }

    public void cargarConfiguraciones(){}

    public String getString(String key) {
        return props.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}
