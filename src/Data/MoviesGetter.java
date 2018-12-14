package Data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;

public class MoviesGetter implements IDataGetter {
    String link;
    public MoviesGetter(String link){
        this.link = link;
    }

    public HashMap<String, String> getData() throws IOException {
        return getData(this.link);
    }
    public HashMap<String, String> getData(String link) throws IOException {
        String pageCode = Jsoup.connect(link).get().html();
        Elements elements = getBlocks(pageCode);
        var data = new HashMap<String, String>();
        for (var element : elements){
            String movieName = element.select("a.name").text();
            String director = element.select("a.lined").first().text();
            data.put(movieName, director);
        }
        return data;
    }
    private Elements getBlocks(String pageCode){
        Document doc = Jsoup.parse(pageCode);
        Elements elements = doc.select("div.info");
        return elements;
    }



}
