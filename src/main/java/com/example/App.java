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

import java.sql.*;

import static java.sql.DriverManager.getConnection;

/**
 * Description of App Class
 *
 * @author Juan Perez
 * @version 2.3
 */
public class App {
    static String rawUrl;

    public App(String url) {
        /**
         * @param rawUrl is the URL passed into the class when created
         */
        rawUrl = url;

    }

    /**
     * Here it sorts an input of map type into a sorted set from based on the frequency of the words
     *
     * @param map A Map of generic type input but should be a string and integer to avoid issues
     * @return a sorted set the map input
     */
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

    /**
     * @return a JAVAFX ListView object with the top twenty occurrences and frequency to use in the scene
     */

    public void Main() {
        ListView wordView = new ListView<>();
        /**
         * @param map is a Map<String,Integer> object used to insert word count without doubles
         */
        Map<String, Integer> map = new TreeMap<>();
        String[] wordList;
        String rawString = "";
        Object[] sortList;
        String dbUrl = "jdbc:sqlserver://localhost:64253;databaseName=wordoccurences;encrypt=false;IntegratedSecurity=true;";

        try {
            Document doc;
            doc = Jsoup.connect(rawUrl).get();
            rawString = doc.select("p").text();

        } catch (IOException e) {

            e.printStackTrace();
        }

        // removing any non letter character
        rawString = rawString.replaceAll("’", "");
        rawString = rawString.replaceAll("[—  \\[“‘,(”.!?;:-]", " ");

        // after removing all non letter chars split the single string above into
        // individual string in a list of strings
        wordList = rawString.toLowerCase().split(" ");

        /**
         * After that, go through each string and make it into a map entry and count
         * each occurrences
         */
        for (String initialWord : wordList) {
            map.put(initialWord, 0);

        }
        for (String initialWord : wordList) {
            if (map.containsKey(initialWord)) {
                map.replace(initialWord, map.get(initialWord) + 1);
            }
        }
        /**
         * Removes any empty spaces after removing unwanted chars
         */
        map.remove("");

        /**
         * Correctly returns a sorted set ordered by values
         */
        sortList = entriesSortedByValues(map).toArray();


        try (Connection con = getConnection(dbUrl); Statement stm = con.createStatement()) {


            String[] word;
            String insertString = "INSERT INTO word(words,frequency) VALUES( ?,?)";
            PreparedStatement insertWord = con.prepareStatement(insertString);
            stm.executeUpdate("DELETE FROM word");//Delete all previous records
            for (int index = 0; index < 20; index++) {
                word = sortList[index].toString().split("=");
                insertWord.setString(1, word[0]);
                insertWord.setInt(2, Integer.parseInt(word[1]));
                insertWord.executeUpdate();

            }


        } catch (Exception e) {
            e.printStackTrace();
        }


		/*
		for (int index = 0; index <= 20; index++) {
			wordView.getItems().add(sortList[index].toString());

		}
		*/


    }
}
