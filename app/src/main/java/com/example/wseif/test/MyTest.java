package com.example.wseif.test;

import android.test.AndroidTestCase;

import com.example.wseif.malattendanceapp.Math;

/**
 * Created by wseif on 12/19/2015.
 */
public class MyTest extends AndroidTestCase {

    public void testAdd4And5ShouldReturn9(){
        Math math = new Math();
        assertEquals(math.Add(4,9),13);
    }

    public void testAddNegative1AndNegative10ShouldReturnNegative11(){
        Math math = new Math();
        assertEquals(math.Add(-1,-10),-11);
    }
}
