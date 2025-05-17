package fotoh.log;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameLogger{

    private final Logger logger;

    public GameLogger(String name, String resourceBundleName) {
        logger = Logger.getLogger(name, resourceBundleName);
    }

    public void info(String msg) {
        logger.logp(Level.INFO, "", "", msg);
    }

    public void warning(String msg) {
        logger.logp(Level.WARNING, "", "", msg);
    }

    public void severe(String msg) {
        logger.logp(Level.SEVERE, "", "", msg);
    }
}
