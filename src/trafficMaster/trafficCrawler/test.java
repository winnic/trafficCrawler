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
		String a="�`�W���O ���{���O $7.0 �ѤW�����p�M�V(�M����) $3.0 �ѤW�������� $4.5 �ѤW�����W�| $5.5, �`�W���O ���{���O $7.0 �ѩW�v���p�M���� $6.0 �ѩW�|���p�M���� $3.5 �ѩW�|���W�� $5.5 ���p�M�V���W�� $3.0";
		String[] b=a.split("�`�W���O ");
		for(int i=0;i<a.length();i++){
			System.out.println(b[i]);
		}
	}

}
