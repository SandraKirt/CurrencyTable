package CurrencyExhange;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;


public class Main {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect("https://www.ecb.europa.eu/stats/policy_and_exchange_rates/euro_reference_exchange_rates/html/index.en.html").get();
        Map<String, Currency> currencyTable = loadCurrencies(doc);
        for (String key: currencyTable.keySet()) {
            System.out.println(key  + " - " + currencyTable.get(key).getName() + " - " + currencyTable.get(key).getPrice());
        }
    }

    private static Map<String, Currency> loadCurrencies(Document doc) {
        Map <String,Currency> currency = new HashMap<String, Currency>();
        for (int i = 0; i < doc.select("td.currency").size(); i++) {
            Currency currencyLine = new Currency();
            Double priceDouble = new Double(doc.select("span.rate").get(i).text());
            currencyLine.setCode(doc.select("td.currency").get(i).text());
            currencyLine.setName(doc.select("td.alignLeft").get(i).text());
            currencyLine.setPrice(BigDecimal.valueOf(priceDouble));
            currency.put(doc.select("td.currency").get(i).text(),currencyLine);
        }
        return currency;
    }
}
