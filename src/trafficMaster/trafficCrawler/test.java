package trafficMaster.trafficCrawler;

import java.io.UnsupportedEncodingException;
import java.sql.Array;
import java.util.HashMap;
import java.util.Map;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a="常規收費 全程收費 $7.0 由上水往聯和墟(和泰街) $3.0 由上水往孔嶺 $4.5 由上水往坪輋 $5.5, 常規收費 全程收費 $7.0 由坪洋往聯和市場 $6.0 由坪輋往聯和市場 $3.5 由坪輋往上水 $5.5 由聯和墟往上水 $3.0";
		String[] b=a.split("常規收費 ");
		for(int i=0;i<a.length();i++){
			System.out.println(b[i]);
		}
	}

}
