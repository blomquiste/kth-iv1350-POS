package se.kth.iv1350.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * This class is responsible for the exception log.
 */
public class LogHandler {
    private static final String FILE_PATH = "src/main/se/kth/iv1350/data/";
    private static final String LOG_FILE_NAME = "pos-log.txt";
    private Locale locale = new Locale("sv", "SE");
    private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).localizedBy(locale);
    private PrintWriter logFile;

    public LogHandler() throws IOException {
        logFile = new PrintWriter(new FileWriter(FILE_PATH + LOG_FILE_NAME, true), true);
    }

    /**
     * Writes a log entry describing a thrown exception.
     * @param exception The exception that shall be logged.
     */
    public void logException(Exception exception) {
        StringBuilder logMsgBuilder = new StringBuilder();
        logMsgBuilder.append(createTime());
        logMsgBuilder.append(", Exception was thrown: ");
        logMsgBuilder.append(exception.getMessage());
        logFile.println(logMsgBuilder);
        exception.printStackTrace(logFile);
        logFile.println("\n");
    }

    private String createTime() {
        LocalDateTime now = LocalDateTime.now();
        return now.format(formatter);
    }
}