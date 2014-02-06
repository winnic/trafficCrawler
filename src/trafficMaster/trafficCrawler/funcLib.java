package trafficMaster.trafficCrawler;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class funcLib {
	//TODO can more than 2 bus routes
	public Elements getRouteTables(Document doc) {
        Elements tables= doc.select("table");
        Elements routeTables = new Elements();
		for (Element table : tables) {  
			Element td=table.select("tr").first().select("td").first();
		    if(td.html().equals("<u>常規途經街道</u>")){
		    	routeTables.add(table);
		    }
         }
		return routeTables;
	}
	
	public Elements getPriceTables(Document doc) {
		Elements tables= doc.select("table");
        Elements priceTables = new Elements();
		for (Element table : tables) {  
			Element td=table.select("tr").first().select("td").first();
		    if(td.html().equals("<u>常規收費</u>")){
		    	priceTables.add(table);
		    }
         }
		return priceTables;
	}
	
	public Elements getServiceTables(Document doc) {
		Elements tables= doc.select("table");
        Elements serviceTables = new Elements();

		for (Element table : tables) {  
			Element td=table.select("tr").first().select("td").first();
		    if(td.html().equals("<u>服務時間</u>")){
		    	serviceTables.add(table);
		    }
        }
		return serviceTables;
	}

	public List<String> getStops(Element routeTables) {
		List<String> stops = new ArrayList<>();
		Elements tmp = new Elements();
		
		/*if(routeTables.select("td.text_small_white").size()<2){
			System.out.println("Error! missing bus starting/ending point!");
		}*/

		//stops.add(routeTables.select("td.text_small_white").first().text());
		String first=routeTables.select("tr").get(1).select(".text_small_white").text();
		if(first.length()!=0){
			stops.add(first);
		}

		
		//System.out.println("starting position : "+stops.text());
		//tmp=routeTables.select("td[bgcolor=#EEEEEE]");
		tmp=routeTables.select("td:eq(2)");
		for (Element thisTmp : tmp) {  
			if(!thisTmp.text().equals("（沿途均可停車上落）")&&!thisTmp.text().equals("主要上落客點")){
				if((thisTmp.text().length()>1)&&!thisTmp.text().equals("")){
					stops.add(thisTmp.text());}
			}
		}
		
		String last=routeTables.select("tr").last().select(".text_small_white").text();
		if(last.length()!=0){
			stops.add(last);
		}
		
		//System.out.println("ending position : "+stops.last().text());
		//System.out.println("route : "+stops.toString());

		return stops;
	}

	public List<String> getPrice(Elements priceTables) {
		List<String> price =  new ArrayList<>();
		
		for(int i=0; priceTables.size()>i; i++)
			price.add(priceTables.get(i).text());
		
		return price;
	}

	public List<String> getServicePeriod(Elements serviceTables) {
		List<String> servicePeriod =  new ArrayList<>();
		
		for(int i=0; serviceTables.size()>i; i++)
			servicePeriod.add(serviceTables.get(i).text());
		
		return servicePeriod;
	}

	public void printThisInfo(List<String> stops) {
	    Map<String, Object> map = new HashMap<String, Object>();
	    GoogleMap goecoder= new GoogleMap();
	    String location="";
	    
	    for(int i=0; i<stops.size();i++){
		    try {
		    	
	        	if(Global.lastStop){
	        	}else{
	        		if(Global.stopNum==(i)){
	        			Global.lastStop=true;
	        		}else{
	        			continue;
	        		}
	        	}
		    	
		    	location= (stops.get(i).split("　　"))[0];
				map=goecoder.findLocation(location+",香港");
				//{status=OK, longitude=114.1923564, formattedAddress=Ngan Mok Street, Tin Hau, Hong Kong, latitude=22.2836023}
				if(map.get("status").equals("OK")){
					System.out.println(Global.id+",RED_"+Global.busId+","+i+","+map.get("engName")+","+stops.get(i)+","+map.get("district")+","+map.get("latitude")+","+map.get("longitude")+",0,0");
					Global.id++;
				}else{
					System.out.println("Error! ErrMsg:"+map.get("status"));
					if(map.get("status").equals("ZERO_RESULTS")){
						System.out.println("ZERO_RESULTS! processedURL:"+map.get("processedURL"));
						System.out.println(Global.id+",RED_"+Global.id+","+i+","+map.get("engName")+","+stops.get(i)+","+map.get("district")+",0,0,0,0");
					}else{
						try {
						    Thread.sleep((long) (30000*Global.coef));
						} catch(InterruptedException ex) {
						    Thread.currentThread().interrupt();
						}
						map=goecoder.findLocation(stops.get(i));
						//{status=OK, longitude=114.1923564, formattedAddress=Ngan Mok Street, Tin Hau, Hong Kong, latitude=22.2836023}
						if(map.get("status").equals("OK")){
							System.out.println(Global.id+",RED_"+Global.busId+","+i+","+map.get("engName")+","+stops.get(i)+","+map.get("district")+","+map.get("latitude")+","+map.get("longitude")+",0,0");
							Global.id++;
						}else{
							System.out.println("Error!still OVER_QUERY_LIMIT.  ProcessedURL:"+map.get("processedURL"));
							System.out.println("districtLink="+Global.districtLink+" lastBusLine="+Global.lastBusLine);
							System.out.println("id="+Global.id+" busID="+Global.busId+" routeNum="+Global.routeNum+" stopNum="+i);
							System.exit(1);
						}
					}
				}
				//System.out.print(map.toString());
				try {
				    Thread.sleep((long) (500*Global.coef));
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	    }
	    
	}


}
