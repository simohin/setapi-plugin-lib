package setapi.plugin.lib.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import ru.crystals.pos.spi.IntegrationProperties;
import ru.crystals.pos.spi.ResBundle;
import setapi.plugin.lib.exception.BaseError;

import java.nio.file.Files;
import java.nio.file.Paths;

public final class PluginConfig {

    private static final ObjectMapper MAPPER = ObjectMapperConfig.getInstance();

    private static PluginConfigProperties PROPERTIES;

    private static final Logger LOGGER = LogConfig.getLogger();

    private PluginConfig() {
    }

    public static void init(IntegrationProperties properties, ResBundle resBundle) {
        String os = System.getProperty("os.name");
        String configFilePath = resBundle.getString("config.file.path.linux");
        if (os.toLowerCase().startsWith("windows"))
            configFilePath = resBundle.getString("config.file.path.windows");

        try {
            String configFileContent = new String(Files.readAllBytes(Paths.get(properties.getServiceProperties().get(configFilePath))));
            LOGGER.debug("Config file content {}", configFileContent);

            PROPERTIES = MAPPER.readValue(configFileContent, PluginConfigProperties.class);
            LOGGER.debug("Plugin initialized with {}", PROPERTIES.toString());
        } catch (Throwable e) {
            throw new BaseError("Error on reading configs", e);
        }
    }

    public static PluginConfigProperties getProperties() {
        if (PROPERTIES == null) {
            throw new IllegalStateException("Not yet initialized");
        }
        return PROPERTIES;
    }

    public static boolean isPluginAvailable() {
        if (PROPERTIES == null) return false;
        String ip = PROPERTIES.getIp();
        return ip != null && !ip.isEmpty();
    }

    public static boolean hasDailyReports() {
        if (PROPERTIES == null) return false;
        return PROPERTIES.isDailyReportsEnabled();
    }

}
