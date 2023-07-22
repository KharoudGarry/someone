package com.example.assignmenttwo;

import java.util.*;

public class Scrabble {
        private Map<String, Integer> myLetterScores;
        private Map<String, Integer> myLetterCounts;
        private Set<String> myPreviousWords;
        private int myTotalPoints = 0;

        public Scrabble() {
            myPreviousWords = new HashSet<>();
            myLetterScores = new HashMap<>();
            myLetterCounts = new HashMap<>();

            myLetterScores.put("A", 1);
            myLetterScores.put("B", 3);
            myLetterScores.put("C", 3);
            myLetterScores.put("D", 2);
            myLetterScores.put("E", 1);
            myLetterScores.put("F", 4);
            myLetterScores.put("G", 2);
            myLetterScores.put("H", 4);
            myLetterScores.put("I", 1);
            myLetterScores.put("J", 8);
            myLetterScores.put("K", 5);
            myLetterScores.put("L", 1);
            myLetterScores.put("M", 3);
            myLetterScores.put("N", 1);
            myLetterScores.put("O", 1);
            myLetterScores.put("P", 3);
            myLetterScores.put("Q", 10);
            myLetterScores.put("R", 1);
            myLetterScores.put("S", 1);
            myLetterScores.put("T", 1);
            myLetterScores.put("U", 1);
            myLetterScores.put("V", 4);
            myLetterScores.put("W", 4);
            myLetterScores.put("X", 8);
            myLetterScores.put("Y", 4);
            myLetterScores.put("Z", 10);

            myLetterCounts.put("A", 9);
            myLetterCounts.put("B", 2);
            myLetterCounts.put("C", 2);
            myLetterCounts.put("D", 4);
            myLetterCounts.put("E", 12);
            myLetterCounts.put("F", 2);
            myLetterCounts.put("G", 3);
            myLetterCounts.put("H", 2);
            myLetterCounts.put("I", 9);
            myLetterCounts.put("J", 1);
            myLetterCounts.put("K", 1);
            myLetterCounts.put("L", 4);
            myLetterCounts.put("M", 2);
            myLetterCounts.put("N", 6);
            myLetterCounts.put("O", 8);
            myLetterCounts.put("P", 2);
            myLetterCounts.put("Q", 1);
            myLetterCounts.put("R", 6);
            myLetterCounts.put("S", 4);
            myLetterCounts.put("T", 6);
            myLetterCounts.put("U", 4);
            myLetterCounts.put("V", 2);
            myLetterCounts.put("W", 2);
            myLetterCounts.put("X", 1);
            myLetterCounts.put("Y", 2);
            myLetterCounts.put("Z", 1);
        }

    public Map<String, Integer> getMyLetterScores() {
        return myLetterScores;
    }

    public Map<String, Integer> getMyLetterCounts() {
        return myLetterCounts;
    }

    public void setMyLetterCounts(Map<String, Integer> myLetterCounts) {
        this.myLetterCounts = myLetterCounts;
    }

    public Set<String> getMyPreviousWords() {
        return myPreviousWords;
    }

    public void validateWord(String theWord) {
        if (theWord == null || theWord.trim().isEmpty()) {
            throw new IllegalArgumentException("Word is blank.");
        }

        int wordLength = theWord.trim().length();
        if (wordLength == 1) {
            throw new IllegalArgumentException("Word is too short (only 1 character).");
        } else if (wordLength > 8) {
            throw new IllegalArgumentException("Word is too long (more than 8 characters).");
        }

        boolean hasVowel = false;
        for (char c : theWord.toCharArray()) {
            if (isVowel(c)) {
                hasVowel = true;
                break;
            }
        }

        if (!hasVowel) {
            throw new IllegalArgumentException("Word does not include a vowel.");
        }
    }
    private boolean isVowel(char c) {
        return "AEIOUaeiou".indexOf(c) != -1;
    }

    public Map<String, Integer> checkCount(String theWord) {
        String regex = "[^a-zA-Z]+"; // The regex pattern to match non-alphabetic characters
        String result = theWord.replaceAll(regex, "");
        Map<String, Integer> newMap = new HashMap<>(getMyLetterCounts());
        for (int i = 0; i < theWord.length(); i++) {
            String letter = theWord.substring(i, i + 1).toUpperCase();
            int count = newMap.get(letter);

            if (newMap.containsKey(letter) && count>0) {

                newMap.put(letter, --count);
            }
            else
            {
                throw new IllegalArgumentException("Letter no longer in the bag");
            }
        }
        return newMap;
    }

    public void addPreviousWord(String theWord) {
        if(myPreviousWords.contains(theWord))
        {
            throw new IllegalArgumentException("Word Already Exists");
        }
        myPreviousWords.add(theWord);
    }

    public int getUserPoints(String addPoints) {

        for (char c : addPoints.toCharArray()) {
            c = Character.toUpperCase(c);
            myTotalPoints+=getMyLetterScores().get(String.valueOf(c));
        }
        return myTotalPoints;
    }
    public boolean gameOverCheck() {
        return areOnlyConsonantsRemainingInBag() || isGameOver() || isOneLetterRemainingInBag();
    }

    private boolean isGameOver() {
        return myPreviousWords.isEmpty() && isBagEmpty();
    }

    private boolean isBagEmpty() {
        if(myLetterCounts.isEmpty())
        {
            return false;
        }
        return true;
    }

    public boolean isOneLetterRemainingInBag() {
        int remainingCount = 0;
        for (int count : myLetterCounts.values()) {
            if (count > 0) {
                remainingCount++;
            }
        }
        return remainingCount == 1;
    }

    public boolean areOnlyConsonantsRemainingInBag() {
        return myLetterCounts.getOrDefault("A", 0) == 0
                && myLetterCounts.getOrDefault("E", 0) == 0
                && myLetterCounts.getOrDefault("I", 0) == 0
                && myLetterCounts.getOrDefault("O", 0) == 0
                && myLetterCounts.getOrDefault("U", 0) == 0;
    }
}
