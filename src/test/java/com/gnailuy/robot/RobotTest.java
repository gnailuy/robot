package com.gnailuy.robot;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for Robot Class.
 */
public class RobotTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public RobotTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(RobotTest.class);
    }

    /**
     * Example Test
     */
    public void testXXX() {
        assertTrue(true);
    }
}

