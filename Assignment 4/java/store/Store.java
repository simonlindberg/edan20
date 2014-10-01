package store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Store {
	final private Map<Storable, Integer> map = new HashMap<>();
	private int count = 0;
	
	public void store(final Storable s) {
		if (map.containsKey(s)) {
			map.put(s, map.get(s) + 1);
		} else {
			map.put(s, 1);
		}
		count++;
	}

	public int count() {
		return count;
				
	}

	public String toString() {
		return map.toString();
	}

	public List<Storable> getFiveBest() {
		final List<Storable> returnList = new ArrayList<>();
		final List<Entry<Storable, Integer>> tempList = new ArrayList<>(map.entrySet());
		
		Collections.sort(tempList, new Comparator<Entry<Storable, Integer>>() {
			public int compare(Entry<Storable, Integer> e1, Entry<Storable, Integer> e2) {
				return e2.getValue() - e1.getValue();
			}
		});
		
		for(int i = 0; i < 5; i++) {
			returnList.add(tempList.get(i).getKey());
		}
		return returnList;
	}
}
