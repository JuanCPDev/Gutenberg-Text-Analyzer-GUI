package com.example;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.scene.control.ListView;

public class App {
	static String rawUrl;

	public App(String url) {
		rawUrl = url;

	}

	// returns a sorted map as entries
	public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
		SortedSet<Map.Entry<K, V>> sortedEntries = new TreeSet<Map.Entry<K, V>>(new Comparator<Map.Entry<K, V>>() {
			@Override
			public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
				int res = e2.getValue().compareTo(e1.getValue());
				return res != 0 ? res : 1;
			}
		});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	// gets the url and parses it using jsoup plugin
	// removes any non letter character
	// then it prints the map after it was sorted

	public ListView Main() {
		ListView wordView = new ListView<>();
		Map<String, Integer> map = new TreeMap<>();
		String[] wordList;
		String rawString = "Blank";
		Object[] sortList;

		try {
			Document doc;
			doc = Jsoup.connect(rawUrl).get();
			rawString = doc.select("p").text();

		} catch (IOException e) {

			e.printStackTrace();
		}

		// removing any non letter character
		rawString = rawString.replaceAll("’", "");
		rawString = rawString.replaceAll("[—  \\[“,(”.!?;:-]", " ");

		// after removing all non letter chars split the single string above into
		// individual string in a list of strings
		wordList = rawString.toLowerCase().split(" ");

		// After that, go through each string and make it into a map entry and count
		// each occurrences
		for (String initialWord : wordList) {
			map.put(initialWord, 0);

		}
		for (String initialWord : wordList) {
			if (map.containsKey(initialWord)) {
				map.replace(initialWord, map.get(initialWord) + 1);
			}
		}
		// remove any empty keys that would have been a space
		map.remove("");

		// get a sorted list back
		sortList = entriesSortedByValues(map).toArray();

		// print top 20
		for (int index = 0; index <= 20; index++) {
			wordView.getItems().add(sortList[index].toString());

		}
		return wordView;

	}
}
