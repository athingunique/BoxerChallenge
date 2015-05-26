package com.evanbaker.boxerchallenge;

import junit.framework.Assert;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * This class tests the AlphabeticalArrayList to ensure it works as expected.
 * (All passed)
 */

public class AlphabeticalArrayListTest {
    @Test
    public void AlphabeticalArrayList_Constructor() {
        Assert.assertNotNull("AlphabeticalArrayList null", new AlphabeticalArrayList());
    }

    @Test
    public void AlphabeticalArrayList_Size0() {
        Assert.assertEquals(0, new AlphabeticalArrayList().size());
    }

    @Test
    public void AlphabeticalArrayList_Add() {
        AlphabeticalArrayList testList = new AlphabeticalArrayList();
        Assert.assertEquals(0, testList.size());
        testList.add("b");
        Assert.assertEquals(1, testList.size());
        testList.add("a");
        Assert.assertEquals(2, testList.size());

        Assert.assertEquals(0, testList.indexOf("a"));
        Assert.assertEquals(1, testList.indexOf("b"));
    }

    @Test
    public void AlphabeticalArrayList_SortedAdd() {
        AlphabeticalArrayList testList = new AlphabeticalArrayList();
        Assert.assertEquals(0, testList.size());
        testList.sortedAdd(new String[]{"c", "d"});
        Assert.assertEquals(2, testList.size());
        testList.sortedAdd("a", "b");
        Assert.assertEquals(4, testList.size());

        Assert.assertEquals(0, testList.indexOf("a"));
        Assert.assertEquals(1, testList.indexOf("b"));
        Assert.assertEquals(2, testList.indexOf("c"));
        Assert.assertEquals(3, testList.indexOf("d"));
    }

    @Test
    public void AlphabeticalArrayList_AddAll() {
        AlphabeticalArrayList testList = new AlphabeticalArrayList();
        List testData = new ArrayList();
        testData.add("b");
        testData.add("a");


        Assert.assertEquals(0, testList.size());
        testList.addAll(testData);

        Assert.assertEquals(0, testList.indexOf("a"));
        Assert.assertEquals(1, testList.indexOf("b"));
    }
}
