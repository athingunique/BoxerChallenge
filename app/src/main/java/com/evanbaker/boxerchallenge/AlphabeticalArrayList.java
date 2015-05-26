package com.evanbaker.boxerchallenge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * This class is a wrapper around an ArrayList which provides one basic function: Alphabetizing, and
 * keeping the ArrayList alphabetized.
 * Since we're sorting alphabetically, it defines the Type (as String) and thus prevents trying to
 * alphabetize non-alpha data
 */

public class AlphabeticalArrayList extends ArrayList<String> {

    // Uses the default constructor

    /**
     * A helper method which finds the index at which to insert a given String in the emails
     * Collection by using a Binary Search
     * @param s the String to determine the insertion index of
     * @return the alphabetized index
     */
    private int getAlphabeticalIndex(String s) {
        // Use a Binary Search to search through the existing items and determine at what index
        // the passed String should be inserted
        int index = (Collections.binarySearch(this, s) + 1);

        // The binary search returns negative values if it doesn't find the value
        if (index > 0) {
            // We should always check if the value is already in the array before attempting this,
            // so throwing this exception is just a safety net
            throw new IllegalStateException();
        }

        // Finally, return (the positive value of) the insertion index
        return -index;
    }

    /**
     * This varags helper method inserts any number of Strings passed simultaneously (or String[])
     * at their correct alphabetized position in the ArrayList, keeping the ArrayList sorted.
     * @param strings the String(s) to insert
     */
    public void sortedAdd(String... strings) {
        for (String s : strings) {
            int alphaIndex = getAlphabeticalIndex(s);
            add(alphaIndex, s);
        }
    }

    /**
     * The default #add() methods are overridden here to <b>always</b> go through the sortedAdd()
     * method instead of appending or allowing the position to be defined
     * @param string the String to add
     * @return true
     */
    @Override
    public boolean add(String string) {
        sortedAdd(string);
        return true;
    }

    /**
     * The default #addAll() methods are overridden here to <b>always</b> go through the sortedAdd()
     * method instead of appending or allowing the position to be defined
     * @param collection the Collection to add
     * @return true
     */
    public boolean addAll(Collection collection) {

        // Add all of the String data in the passed Collection to this list
        for (Object object : collection) {
            try {
                String string = (String) object;
                sortedAdd(string);
            } catch (ClassCastException classCastException) {
                // Squash
                // We don't want to attempt to add non-String data, so it is ignored
            }
        }

        return true;
    }

    /**
     * The default #addAll() methods are overridden here to <b>always</b> go through the sortedAdd()
     * method instead of appending or allowing the position to be defined
     * @param position ignored
     * @param collection the Collection to add
     * @return true
     */
    @Override
    public boolean addAll(int position, Collection collection) {
        // Ignore position

        // Add all of the String data in the passed Collection to this List
        for (Object object : collection) {
            try {
                String string = (String) object;
                sortedAdd(string);
            } catch (ClassCastException classCastException) {
                // Squash
                // We don't want to attempt to add non-String data, so it is ignored
            }
        }

        return true;
    }
}
