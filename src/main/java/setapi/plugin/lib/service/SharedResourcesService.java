package setapi.plugin.lib.service;

import org.slf4j.Logger;
import ru.crystals.pos.spi.ResBundle;
import ru.crystals.pos.spi.equipment.SetApiPrinter;
import ru.crystals.pos.spi.ui.UIForms;
import setapi.plugin.lib.config.PluginConfigProperties;

import static setapi.plugin.lib.config.LogConfig.getLogger;
import static setapi.plugin.lib.config.PluginConfig.getProperties;
import static setapi.plugin.lib.config.PrinterConfig.getPrinter;
import static setapi.plugin.lib.config.ResBundleConfig.getResBundle;
import static setapi.plugin.lib.config.UIConfig.getUiForms;
import static setapi.plugin.lib.config.UIConfig.getUiService;
import static setapi.plugin.lib.config.BankIntegrationConfig.getInstance;


public abstract class SharedResourcesService {
    protected final UIForms ui = getUiForms();
    protected final SetApiPrinter printer = getPrinter();
    protected final Logger log = getLogger();
    protected final ResBundle res = getResBundle();
    protected final PluginConfigProperties configProperties = getProperties();
    protected final UIService uiService = getUiService();
    protected final BankIntegrationService bankIntegrationService = getInstance();

}
