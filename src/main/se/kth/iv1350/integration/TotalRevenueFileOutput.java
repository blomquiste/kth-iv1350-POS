package se.kth.iv1350.integration;

import se.kth.iv1350.model.Amount;
import se.kth.iv1350.model.RegisterObserverOutput;
import se.kth.iv1350.util.LogHandler;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * A Singleton that creates an instance responsible for
 * logging the total revenue to a specific file, each time it is updated.
 */
public class TotalRevenueFileOutput extends RegisterObserverOutput {

    private static volatile TotalRevenueFileOutput instance;
    private static final String FILE_PATH = "src/main/se/kth/iv1350/data/";
    private static final String LOG_FILE_NAME = "revenue.txt";
    private Locale locale = new Locale("sv", "SE");
    private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).localizedBy(locale);
    private PrintWriter totalRevenueFile;
    private LogHandler logger;

    /**
     * Creates a new instance of revenue file output.
     */
    private TotalRevenueFileOutput() throws IOException {
        totalRevenueFile = new PrintWriter(new FileWriter(FILE_PATH
                + LOG_FILE_NAME, true), true);
        this.logger = new LogHandler();
    }
    /**
     * @return The only instance of this singleton.
     * @throws IOException
     */
    public static TotalRevenueFileOutput getInstance() throws IOException{
        TotalRevenueFileOutput result = instance;
        if (result == null) {
            synchronized (TotalRevenueFileOutput.class) {
                result = instance;
                if (result == null) {
                    instance = result = new TotalRevenueFileOutput();
                }
            }
        }
        return result;
    }

    /**
     * Creates a string builder with total revenue, that is written to a file.
     * @param totalRevenue the new total revenue
     */
    @Override
    protected void doShowTotalRevenue(Amount totalRevenue) {
        StringBuilder totalRevenueBuilder = new StringBuilder();
        totalRevenueBuilder.append(LocalDateTime.now().format(formatter));
        totalRevenueBuilder.append(", Total revenue: ");
        totalRevenueBuilder.append(totalRevenue + "\n");
        totalRevenueFile.println(totalRevenueBuilder);
    }

    /**
     * Handles errors that are thrown.
     * @param ex the exception thrown
     */
    @Override
    protected void handleErrors(Exception ex) {
        logger.logException(ex);
    }
}