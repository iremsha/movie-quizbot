package bot.Data;

import java.io.IOException;
import java.util.HashMap;

public interface IDataGetter {

    HashMap<String, String> getData(String link) throws IOException;

    HashMap<String, String> getData() throws IOException;

    //    static HashMap<String, String> getData(String link) throws IOException {
//        String pageCode = Jsoup.connect(link).get().html();
//        Elements elements = getBlocks(pageCode);
//        var data = new HashMap<String, String>();
//        for (var element : elements){
//            String movieName = element.select("a.name").text();
//            String director = element.select("a.lined").first().text();
//            data.put(movieName, director);
//        }
//        return data;
//}
//    private static Elements getBlocks(String pageCode){
//        Document doc = Jsoup.parse(pageCode);
//        Elements elements = doc.select("div.info");
////        for (var el : elements){
////            System.out.println(el);
////            System.out.println("=============================");
////        }
//        return elements;
//    }
}
