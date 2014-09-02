package database.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapTest {
	public static void main(String args[]){
	
		Map<String,String> map = new HashMap<String,String>();
		map.put("123", "45667");
		System.out.println(map);
		map.put("123","asss");
		System.out.print(map);
		List   a = new ArrayList();
		a.iterator();
		Set set = new HashSet();
	}
}
