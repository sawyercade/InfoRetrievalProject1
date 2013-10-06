import org.jsoup.select.Elements;

import java.util.Map;

public interface Tokenizer {
    void tokenize(String text);

    void tokenize(Elements elements);

    Map<String, Integer> getFrequencies();
}