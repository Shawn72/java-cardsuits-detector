
package com.bigbadwolf.cardsuitsdetector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 *
 * @author vampireklaus
 */
public class CardSuitsDetector {

    public static void main(String[] args) {
       //get the card list to evaluate : "2J", "3J", "10J", "KS", "KS", "3S", "8S", "10S", "10S", "JS", "QS", "KS", "AS";
       List<String> cards = Arrays.asList("5S", "5S", "5S", "5S", "5S", "QS", "JS");
      
       //check the cards using cards detector method
       List<String> detectedSets = CardSetsDetector(cards);
       
        System.out.println("Detected card sets: " + detectedSets);
    }
    
     public static List<String> CardSetsDetector(List<String> cards) {
        List<String> detectedSets = new ArrayList<>();

        // Sort the cards in ascending order of rank
        Collections.sort(cards, new Comparator<String>() {
            @Override
            public int compare(String card1, String card2) {
                char rank1 = card1.charAt(0);
                char rank2 = card2.charAt(0);
                return rank1 - rank2;
            }
        });

        // Detect each card set starting from the strongest one
        if (isRoyalFlush(cards)) {
            detectedSets.add("Royal Flush");
        } else if (isStraightFlush(cards)) {
            detectedSets.add("Straight Flush");
        } else if (isFourOfAKind(cards)) {
            detectedSets.add("Four of a Kind");
        } else if (isFullHouse(cards)) {
            detectedSets.add("Full House");
        } else if (isFlush(cards)) {
            detectedSets.add("Flush");
        } else if (isStraight(cards)) {
            detectedSets.add("Straight");
        }
        
        return detectedSets;
    }

    
    // Check if the cards form a Royal Flush
    public static boolean isRoyalFlush(List<String> cards) {
        return isStraightFlush(cards) && cards.get(0).charAt(0) == 10;
    }

    // Check if the cards form a Straight Flush
    public static boolean isStraightFlush(List<String> cards) {
        return isFlush(cards) && isStraight(cards);
    }

    // Check if the cards form a Four of a Kind
    public static boolean isFourOfAKind(List<String> cards) {
        String cardSet = "";
        Map<Character, Integer> rankCounts = new HashMap<>();
        for (String card : cards) {
            char rank = card.charAt(0);
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
        }
        if(rankCounts.containsValue(4)){
            //print the cards forming the set
           for(Map.Entry map: rankCounts.entrySet()){  

             cardSet+= map.getKey()+ "- count: "+map.getValue()+"\n";         
           }        
           System.out.println("Cards provided: \n"+cardSet);
        }
        
        return rankCounts.containsValue(4);
    }

    // Check if the cards form a Full House
    public static boolean isFullHouse(List<String> cards) {
        String cardSet = "";
        Map<Character, Integer> rankCounts = new HashMap<>();
        for (String card : cards) {
            char rank = card.charAt(0);
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
        }
        
        if(rankCounts.containsValue(3) && rankCounts.containsValue(2)){

            //print the cards forming the set
            for(Map.Entry map: rankCounts.entrySet()){  

              cardSet+= map.getKey()+ "- count: "+map.getValue()+"\n";         
            }        
            System.out.println("Cards provided: \n"+cardSet);        
        }
       
        return rankCounts.containsValue(3) && rankCounts.containsValue(2);
    }

    // Check if the cards form a Flush
    public static boolean isFlush(List<String> cards) {
        Set<Character> suits = new HashSet<>();
        
        for (String card : cards) {
            char suit = card.charAt(1);
            suits.add(suit);            
        }
        //get the first element of the set
        System.out.println("Card Suit: "+suits.stream().findFirst());  
        
        return suits.size() == 1;
    }

    // Check if the cards form a Straight
    public static boolean isStraight(List<String> cards) {
        int rankDifference = cards.get(cards.size() - 1).charAt(0) - cards.get(0).charAt(0);
        return rankDifference == cards.size() - 1;
    }
}
