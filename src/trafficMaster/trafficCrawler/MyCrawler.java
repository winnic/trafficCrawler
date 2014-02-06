package trafficMaster.trafficCrawler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyCrawler {//TODO if [lat,lng]=[22.3964280,114.1094970] wrong location, put human input
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
        FileOutputStream log = new FileOutputStream("C:/trafficCrawler/log.txt");      
        System.setOut(new PrintStream(log));
		System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36");	
//        System.setProperty("http.proxyHost", "212.144.254.124");
//        System.setProperty("http.proxyPort", "3128");
        
        String link="http://www.16seats.net/chi/rmb/rmb.html";
        busLineSearcher searcher=new busLineSearcher();
        
        try {
			Document doc= Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36").ignoreHttpErrors(true).timeout(100000).get();
	        Elements districts=doc.select("table[width=900] table a");
		    //System.exit(0);
	        		
	        for(Element district : districts){
	        	link=district.attr("abs:href");
	        	if(Global.lastDistrict){
	        	}else{
	        		if(Global.districtLink.equals(link)){
	        			Global.lastDistrict=true;
	        		}else{
	        			continue;
	        		}
	        	}
	            searcher.search(link);
				try {
				    Thread.sleep((long) (10000*Global.coef));
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
	        }
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
