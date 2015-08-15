package com.pkrete.xrd4j.tools.test_service.util;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class offers helper methods for the application.
 *
 * @author Petteri Kivimäki
 */
public class ApplicationHelper {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationHelper.class);
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789 ";

    /**
     * Returns a random string of given length.
     *
     * @param length length of the string
     * @return random string
     */
    public static String getRandomString(int length) {
        logger.debug("Generate random string of {} charaters.", length);
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        while (sb.toString().getBytes().length < length) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        logger.debug("String generated.");
        return sb.toString();
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
