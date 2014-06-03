package trafficMaster.trafficCrawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class busLinePage {
	Elements priceTables = new Elements();
	Elements serviceTables = new Elements();
	List<String> price =  new ArrayList<>();
	List<String> servicePeriod =  new ArrayList<>();
	
	funcLib funcHelper = new funcLib();
	
	public void printABusLine(String link) throws FileNotFoundException {		
		Elements routeTables = new Elements();
		List<String> stops =  new ArrayList<>();

		try {//link="http://www.16seats.net/chi/rmb/r_kh70.html";
	        System.out.println("---going to connect a bus line page ("+link+")");
	        Global.lastBusLine=link;
			Document doc= Jsoup.connect(link).userAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1468.0 Safari/537.36").ignoreHttpErrors(true).timeout(100000).get();
	        
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
		        	
		        	get_Price_Serv();
	        		stops=funcHelper.getStops(routeTables.get(i));
		        	funcHelper.printThisInfo(stops,price);
		        	Global.busId++;
	        	}
	        }else{
	        	System.out.println("Invalid page! There are no routes.");
	        }
	        
			System.out.println("---close this page");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void get_Price_Serv() {
		//get prices
        if(priceTables.size()!=0){
	        price=funcHelper.getPrice(priceTables);
	        //(td.text_small.toText())(td.text_small_b.<div align="center">)
//	        System.out.println(price.toString());
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
	}
	
	
	
}
