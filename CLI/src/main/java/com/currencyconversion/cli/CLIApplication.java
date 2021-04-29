package com.currencyconversion.cli;

import com.currencyconversion.cli.models.Currency;
import com.currencyconversion.cli.services.ApiProxy;
import com.currencyconversion.cli.services.ApiProxyImpl;
import org.apache.commons.cli.*;

import java.util.List;

public class CLIApplication {

    private static ApiProxy apiProxy;

    public static void main(String[] args) {
        try {
            CommandLineParser parser = new PosixParser();
            Options options = new Options();

            options.addOption("f", "file", true, "Set a path of Json file");
            options.addOption("t", "target-currency", true, "Set a currency");
            CommandLine cli = parser.parse(options, args);

            String file = cli.getOptionValue('f');
            String targetCurrency = cli.getOptionValue('t');

            apiProxy = new ApiProxyImpl();
            List<Currency> currencies = apiProxy.send(file, targetCurrency);
            for (Currency currency:currencies){
                System.out.println(currency);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
