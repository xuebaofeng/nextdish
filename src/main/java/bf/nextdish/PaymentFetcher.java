package bf.nextdish;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentFetcher {

    public static void main(String[] args) throws IOException {
        urlToDoc();
    }

    static Document urlToDoc() throws IOException {

        String folder = "cache/";
        Files.createDirectories(Paths.get(folder));
        String fileName = folder + "payment_" + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".html";

        String url = "https://nextdish.com/driver/155ZI4EEG0Q1H/payment";

        Document doc;
        Path file = Path.of(fileName);
        if (file.toFile().exists()) {
            String content = Files.readString(file);
            doc = Jsoup.parse(content);
        } else {
            doc = Jsoup.connect(url).get();
            Files.writeString(file, doc.html());
        }
        return doc;
    }
}
