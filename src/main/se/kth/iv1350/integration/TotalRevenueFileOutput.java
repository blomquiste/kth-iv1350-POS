package se.kth.iv1350.integration;

import se.kth.iv1350.model.Amount;
import se.kth.iv1350.model.RegisterObserverOutput;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TotalRevenueFileOutput extends RegisterObserverOutput {

    private PrintWriter fileStream;
    private static final String FILE_PATH = "src/main/se/kth/iv1350/data/";
    private static final String LOG_FILE_NAME = "revenue.txt";
    private Locale locale = new Locale("sv", "SE");
    private DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).localizedBy(locale);
    private PrintWriter totalRevenueFile;

    /**
     * Creates a new instance.
     */
    public TotalRevenueFileOutput() throws IOException {
        totalRevenueFile = new PrintWriter(new FileWriter(FILE_PATH + LOG_FILE_NAME, true), true);
    }

        // TODO: comment
    @Override
    protected void doShowTotalRevenue(Amount totalRevenue) throws Exception {
        StringBuilder totalRevenueBuilder = new StringBuilder();
        totalRevenueBuilder.append(LocalDateTime.now().format(formatter));
        totalRevenueBuilder.append(", Total revenue: ");
        totalRevenueBuilder.append(totalRevenue + "\n");
        totalRevenueFile.println(totalRevenueBuilder);
    }

    // TODO: comment
    @Override
    protected void handleErrors(Exception ex) {
        ex.printStackTrace();
    }
}
