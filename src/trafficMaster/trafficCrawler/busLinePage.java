package trafficMaster.trafficCrawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class busLinePage {

	public void printABusLine(String link) throws FileNotFoundException {		
		Elements routeTables = new Elements();
		Elements priceTables = new Elements();
		Elements serviceTables = new Elements();
		List<String> stops =  new ArrayList<>();
		List<String> price =  new ArrayList<>();
		List<String> servicePeriod =  new ArrayList<>();
		
		try {//link="http://www.16seats.net/chi/rmb/r_kh70.html";
	        System.out.println("---going to connect a bus line page ("+link+")");
	        Global.lastBusLine=link;
			Document doc= Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36").ignoreHttpErrors(true).timeout(100000).get();
	        
			funcLib funcHelper = new funcLib();
	        routeTables=funcHelper.getRouteTables(doc);
	        priceTables=funcHelper.getPriceTables(doc);
	        serviceTables=funcHelper.getServiceTables(doc);
	        	        
	        //get routes
	        if(routeTables.size()!=0){
	        	for(int i=0; routeTables.size()>i; i++){
	        		
		        	if(Global.lastRoute){
		        	}else{
		        		if(Global.routeNum==(i+1)){
		        			Global.lastRoute=true;
		        		}else{
		        			continue;
		        		}
		        	}
		        	
	        		stops=funcHelper.getStops(routeTables.get(i));
		        	funcHelper.printThisInfo(stops);
		        	Global.busId++;
	        	}
	        }else{
	        	System.out.println("Invalid page! There are no routes.");
	        }
	        //get prices
	        if(priceTables.size()!=0){
		        price=funcHelper.getPrice(priceTables);
		        //System.out.println(price.toString());
	        }else{
	        	System.out.println("Invalid page! There are no price tables.");
	        }
	        //get service period 
	        if(serviceTables.size()!=0){
	        	servicePeriod=funcHelper.getServicePeriod(serviceTables);
		        //System.out.println(servicePeriod.toString());
	        }else{
	        	System.out.println("Invalid page! There are no service tables.");
	        }
			System.out.println("---close this page");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
