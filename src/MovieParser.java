//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class MovieParser implements IParser{
//
//    String kinopoisk200 = "https://www.kinopoisk.ru/top/lists/1/filtr/all/sort/order/perpage/200/";
//
//    public HashMap<String, String> getData(String link, String regexp) throws Exception {
//        //кинопоиск:
//        // <i>реж.*?>(.*?)</a -- режиссер
//        //потом .*
//        //data-title="(.*?)"
//        String pageCode = getPageHTMLCode(link);
//        Pattern pattern = Pattern.compile(regexp,  Pattern.DOTALL);
//        HashMap<String, String> moviesAndDirectors = new HashMap<>();
//        Matcher m = pattern.matcher(pageCode);
//        while (m.find()) {
////            String a = m.group();
//            try {
//                moviesAndDirectors.put(m.group("director"), m.group("film"));
//            }
//            catch (Exception){
//                throw new Exception("regexp should be...");
//            }
//
//        }
//        return moviesAndDirectors;
//    }
//
//
//    private String getPageHTMLCode(String link) throws IOException {
//        Document doc = Jsoup.connect(link).get();
//        return doc.body().text();
//    }
//
//
//
//}
