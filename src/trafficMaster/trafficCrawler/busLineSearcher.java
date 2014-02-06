package trafficMaster.trafficCrawler;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class busLineSearcher {

	public void search(String link) {
        busLinePage busLinePrinter= new busLinePage();
        
        try {
	        System.out.println("---going to connect a district page ("+link+")");
	        Global.districtLink=link;
			Document doc= Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36").ignoreHttpErrors(true).timeout(100000).get();
	        Elements busLineLinks=doc.select("table[width=900] a[href]");
	        		
	        for(Element busLineLink : busLineLinks){
	        	link=busLineLink.attr("abs:href");
	        	
	        	if(Global.lastBusLink){
	        	}else{
	        		if(Global.lastBusLine.equals(link)){
	        			Global.lastBusLink=true;
	        		}else{
	        			continue;
	        		}
	        	}

		        busLinePrinter.printABusLine(link);
				try {
				    Thread.sleep((long) (3000*Global.coef));
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
	        }
	        // http://www.16seats.net/chi/rmb/rmb.html->getlink(http://www.16seats.net/chi/rmb/r_eas.html)->get(http://www.16seats.net/chi/rmb/r_h85.html)
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
