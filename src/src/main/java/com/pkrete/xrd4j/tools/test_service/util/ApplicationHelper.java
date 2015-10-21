package com.pkrete.xrd4j.tools.test_service.util;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * This class offers helper methods for the application.
 *
 * @author Petteri Kivimäki
 */
public class ApplicationHelper {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);

    /**
     * Returns a random string of given length.
     *
     * @param length length of the string
     * @return random string
     */
    public static String getRandomString(int length) {
        logger.debug("Generate random string of {} charaters.", length);
        String s = RandomStringUtils.randomAlphanumeric(length);
        logger.debug("String generated");
        return s;
    }

    /**
     * Returns a random string of given length.
     *
     * @param length length of the string
     * @return random string
     */
    public static String getRandomString(String length) {
        return ApplicationHelper.getRandomString(ApplicationHelper.fromStr2Int(length));
    }

    /**
     * Converts the given string to integer. If the conversion fails, 0 is
     * returned.
     *
     * @param number String to be converted
     * @return integer the integer value represented by the argument in decimal
     */
    public static int fromStr2Int(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
