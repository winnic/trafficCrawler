package trafficMaster.trafficCrawler;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GoogleMap goecoder= new GoogleMap();
		Map<String, Object> map = new HashMap<String, Object>();
		String text="�F���D�@�@�@�@�@�@�@�@�@[ �R���ʳf ]";
		
		String[] a= text.split("�@�@");
		System.out.println(a[0]);
		System.exit(0);
		
		try {
			map=goecoder.findLocation(a[0]+",����");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
