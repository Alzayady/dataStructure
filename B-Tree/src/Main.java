import eg.edu.alexu.csd.filestructure.btree.ISearchEngine;
import eg.edu.alexu.csd.filestructure.btree.ISearchResult;
import eg.edu.alexu.csd.filestructure.btree.SearchEngine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        ISearchEngine engine =new SearchEngine();
        engine.indexWebPage("res\\wiki_00");
        engine.indexWebPage("res\\subfolder\\wiki_02");
        engine.deleteWebPage("res\\wiki_01");
//       while (true){
//           Scanner scanner=new Scanner(System.in);
//           String ss =scanner.next();

        List<ISearchResult> results = engine.searchByMultipleWordWithRanking("     query       ");
        for(ISearchResult searchResult : results){
            System.out.println(searchResult.getRank()+" "+searchResult.getId());
        }
//        System.out.println("===========");
//        results = engine.searchByWordWithRanking("194");
//        for(ISearchResult searchResult : results){
//            System.out.println(searchResult.getRank()+" "+searchResult.getId());
//        }
//        System.out.println("===========");
//
//        results = engine.searchByWordWithRanking("wow");
//        for(ISearchResult searchResult : results){
//            System.out.println(searchResult.getRank()+" "+searchResult.getId());
//        }  System.out.println("===========");
//
//        results = engine.searchByWordWithRanking("rec");
//        for(ISearchResult searchResult : results){
//            System.out.println(searchResult.getRank()+" "+searchResult.getId());
//        }
//      System.out.println("===========");
//
//    results = engine.searchByWordWithRanking("fulltime");
//        for(ISearchResult searchResult : results){
//            System.out.println(searchResult.getRank()+" "+searchResult.getId());
//        }
//        results = engine.searchByWordWithRanking("die");
//        for(ISearchResult searchResult : results){
//            System.out.println(searchResult.getRank()+" "+searchResult.getId());
//        }
     //  }


}
    private  static NodeList getListOfElements(String filePath){
        NodeList nList;

        try {
            File inputFile = new File(filePath);

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            nList = doc.getElementsByTagName("doc");
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeErrorException(new Error());
        }
        return nList;
    }

}