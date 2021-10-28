package logging;

import util.Constants;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {

    /**
     * Makes Logs into a log file
     *
     * @param info Log information
     */
    public static void makeLog(String info) {

        Logger logger = Logger.getLogger("MyLog");
        FileHandler fileHandler;
        try {
            fileHandler = new FileHandler(Constants.LOGS_FILE_PATH,true);
            logger.addHandler(fileHandler);
            logger.setUseParentHandlers(false);
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.info(info);
            fileHandler.close();
        } catch (SecurityException | IOException e) {
            logger.warning(e.getMessage());
        }
    }
}
