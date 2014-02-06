package trafficMaster.trafficCrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class GoogleMap {
	private static final String url = "http://maps.googleapis.com/maps/api/geocode/xml?address={ADDRESS}&sensor=true";
	
	public static Map<String, Object> getDisassembledAddress(String address) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String street1 = null;
		String state = null;
		
		String processedURL;
		String path;
		
		processedURL = url;
		processedURL = processedURL.replace("{ADDRESS}", URLEncoder.encode(address, "UTF-8"));
		
		path = "GeocodeResponse/result[1]/address_component[type='route']/long_name";
		street1 = XML.get(processedURL, path, XML.RETURN_VALUE).toString();
		
		path = "GeocodeResponse/result[1]/address_component[type='administrative_area_level_1']/short_name";
		state = XML.get(processedURL, path, XML.RETURN_VALUE).toString();
		
		map.put("street1", street1);
		map.put("state", state);	
		return map;
	}	
	
	public static Map<String, Object> findLocation(String address) throws UnsupportedEncodingException {
		Map<String, Object> map = new HashMap<String, Object>();
		String formattedAddress = null;
		String longitude = null;
		String latitude = null;
		
		String processedURL;
		String path;
		
		processedURL = url;
		processedURL = processedURL.replace("{ADDRESS}", URLEncoder.encode(address, "UTF-8"));
		
		path = "GeocodeResponse/status";
		String status = XML.get(processedURL, path, XML.RETURN_VALUE).toString();
		//System.out.println(processedURL);
		//System.out.println(status);
		if(status=="ZERO_RESULTS"){
			map.put("status", "ZERO_RESULTS");
			map.put("processedURL", processedURL);
			return map;
		}

		path = "GeocodeResponse/result[1]/formatted_address";
		formattedAddress = XML.get(processedURL, path, XML.RETURN_VALUE).toString();
		
		path = "GeocodeResponse/result[1]/geometry/location/lng";
		longitude = XML.get(processedURL, path, XML.RETURN_VALUE).toString();
		
		path = "GeocodeResponse/result[1]/geometry/location/lat";
		latitude = XML.get(processedURL, path, XML.RETURN_VALUE).toString();
		
		map.put("status", status);
		map.put("processedURL", processedURL);
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		
		String[] tmp= formattedAddress.split(",");
		if(tmp.length>2){
			if(tmp[0].equals("Hong Kong")){
				map.put("engName", tmp[tmp.length-2]);
				map.put("district", tmp[0]);
			}else{
				map.put("engName", tmp[0]);
				map.put("district", tmp[tmp.length-2]);
			}

		}else if(tmp.length==2){
			if(tmp[0].equals("Hong Kong")){
				map.put("engName", tmp[1]);
				map.put("district", "");
			}else{
				map.put("engName", tmp[0]);
				map.put("district", "");
			}
		}else{
			map.put("engName", "");
			map.put("district", "");
		}
		
		return map;
	}
	

	//FIND THE STATE USING LATITUDE AND LONGITUDE
	public static String getUserLocation(String lat, String lon) {
		   String userlocation = null;
		   System.out.println(lat + " and long is "+ lon);
		   String readUserFeed = readUserLocationFeed(lat.trim() + "," + lon.trim());
		   try {
		      JSONObject Strjson = new JSONObject(readUserFeed);
		      JSONArray jsonArray=(JSONArray)Strjson.get("results");
//		      System.out.println(jsonArray.toString());
//		      userlocation = jsonArray.getJSONObject(1).getString("formatted_address").toString();
		      
		      JSONObject Caddress = (JSONObject)jsonArray.getJSONObject(1);
		      JSONArray cadd = (JSONArray) Caddress.get("address_components");
		      
		      for(int i=0;1<cadd.length();i++){
		    	  JSONObject Saddress = (JSONObject)cadd.getJSONObject(i);
			      String location = Saddress.getString("short_name");
			      if(location.length()==2){
			    	  userlocation=location;
			    	  break;
			      }
		      }
		   } catch (Exception e) {
		      e.printStackTrace();
		   }
		   System.out.println("user's location is: "+userlocation);
		   
		   return userlocation;
		}
	
		public static String readUserLocationFeed(String address) {
		   StringBuilder builder = new StringBuilder();
		   DefaultHttpClient client = new DefaultHttpClient();
		   HttpGet httpGet = new HttpGet(
		         "http://maps.google.com/maps/api/geocode/json?latlng=" + address
		               + "&sensor=false");
		   try {
		      HttpResponse response = client.execute(httpGet);
		      StatusLine statusLine = response.getStatusLine();
		      int statusCode = statusLine.getStatusCode();
		      if (statusCode == 200) {
		         HttpEntity entity = response.getEntity();
		         InputStream content = entity.getContent();
		         BufferedReader reader = new BufferedReader(new InputStreamReader(
		               content));
		         String line;
		         while ((line = reader.readLine()) != null) {
		            builder.append(line);
		         }
//		         System.out.println("Inside Advertisement Locator " +builder.toString());
		      } else {
		    	  System.out.println("Failed to download");
		      }
		   } catch (ClientProtocolException e) {
			   System.out.println("Outside Advertisement Locator "+e.getMessage());
		      e.printStackTrace();
		   } catch (IOException e) {
			   System.out.println(e.getMessage());
		      e.printStackTrace();
		   }
		   return builder.toString();
		}
}
