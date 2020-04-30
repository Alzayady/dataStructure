package eg.edu.alexu.csd.filestructure.btree;
import org.junit.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchEngine implements ISearchEngine {
    IBTree<String , Map<Integer,Integer>> bTree = new BTree<>(5);
    NodeList nodeList ;
    String match_pattern;
    Pattern pattern ;
    public  SearchEngine(){
        match_pattern="^(id=\")(.*)(\")";
        pattern=Pattern.compile(match_pattern);
    }
    @Override
    public void indexWebPage(String filePath) {
        if (filePath== null) throw new RuntimeErrorException(new Error());
        edit_entry(filePath,1);
    }


    @Override
    public void indexDirectory(String directoryPath) {
        if (directoryPath== null || directoryPath.equals(""))throw new RuntimeErrorException(new Error());
        try {
            File dir = new File(directoryPath);
            File[] directoryListing = dir.listFiles();
            if (directoryListing != null) {
                for (File child : directoryListing) {
                    if(child.isDirectory())indexDirectory(child.getPath());
                    else indexWebPage(child.getPath());
                }
            }
        } catch (Exception e){
            throw new RuntimeErrorException(new Error());
        }
    }

    @Override
    public void deleteWebPage(String filePath) {
        edit_entry(filePath,-1);
    }

    private void edit_entry(String filePath , int append){
        if (filePath== null) throw new RuntimeErrorException(new Error());
        System.out.println(filePath);
        nodeList = getListOfElements(filePath);
        if (nodeList == null) return;
        for (int i = 0; i < nodeList.getLength(); i++){
            Matcher matcher = pattern.matcher(nodeList.item(i).getAttributes().getNamedItem("id").toString());
            Assert.assertTrue(matcher.matches());
            Integer id =  Integer.parseInt(matcher.group(2));
            String[] words =nodeList.item(i).getTextContent().split("[,_$ ?.@\n]+");
            for(String word :words){
                word=word.toLowerCase();

                if(bTree.search(word)==null){
                    bTree.insert(word,new HashMap<>());
                }
                Map<Integer,Integer>hashMap=bTree.search(word);

                int new_value= append;
                if(hashMap.containsKey(id))
                    new_value+=hashMap.get(id);
                if(new_value>=0)
                hashMap.put(id,new_value);
            }
        }
    }


    @Override
    public List<ISearchResult> searchByWordWithRanking(String word) {
        if (word == null) throw new RuntimeErrorException(new Error());
        if (nodeList == null) return new LinkedList<>();
        List<ISearchResult> results = new ArrayList<>();
        Map<Integer,Integer> freq_map = bTree.search(word.toLowerCase());
        if(freq_map==null)return results;
        for(Integer id : freq_map.keySet()){
            if(freq_map.get(id)>0)
            results.add(new SearchResult(id,freq_map.get(id)));
        }
        return results;
    }

    @Override
    public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
        if (sentence==null) throw new RuntimeErrorException(new Error());
        if (nodeList == null) return new LinkedList<>();
        sentence=sentence.trim();
        String[] words = sentence.split("[,_$ ?.@\n]+");
        Map<String,Integer> temp_results=new HashMap<>();
        for(String word :words){
            List<ISearchResult>primary_result=searchByWordWithRanking(word);
            for(ISearchResult searchResult : primary_result){
                String id=searchResult.getId();
                int rank=searchResult.getRank();
                if(temp_results.containsKey(id))
                    rank+=temp_results.get(id);
               temp_results.put(id,rank);
            }
        }
        List<ISearchResult> results = new ArrayList<>();
        for(String id : temp_results.keySet()){
            results.add(new SearchResult(id,temp_results.get(id)));
        }

        return results;
    }

    private NodeList getListOfElements(String filePath){
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
            throw new RuntimeErrorException(new Error());
        }
        return nList; 
    }


}
