// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.util;

import android.os.Build;
import android.support.annotation.Nullable;

import java.util.Iterator;

/**
 * Created by ssannttoss on Nov/03/2018.
 */

public class StringUtil {
    private StringUtil() {
        super();
    }

    /**
     * Trims a String. If its null, returns null, avoiding {@link NullPointerException}
     *
     * @param string String to trim
     * @return Trimmed String or null
     */
    public static String trim(String string) {
        return string == null ? null : string.trim();
    }

    /**
     * Calculate the parameters quantity to put in a Sql Prepared Statement.
     *
     * @param i Number of columns
     * @return Parameter {@link String} to creation of Sql Prepared Statement.
     */
    public static String params(int i) {
        String params = "?";
        for (int p = 1; p < i; p++) {
            params += ",?";
        }
        return params;
    }

    /**
     * Returns true if the string is null or 0-length.
     *
     * @param string the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable String string) {
        return string == null || string.isEmpty();
    }

    /**
     * <a href="https://android.googlesource.com/platform/libcore.git/+/51b1b6997fd3f980076b8081f7f1165ccc2a4008/ojluni/src/main/java/java/beans/Introspector.java">
     * Documentation
     * </a>
     *
     * @param name
     * @return
     */
    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        char chars[] = name.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        return new String(chars);
    }

    /**
     * Utility method to take a string and convert it to normal Java variable
     * name capitalization. This normally means converting the first
     * character from upper case to lower case, but in the (unusual) special
     * case when there is more than one character and both the first and
     * second characters are upper case, we leave it alone.
     * <p>
     * Thus "AbstractActivity" becomes "abstractActivity" and "X" becomes "x", but "URL" stays
     * as "URL".
     *
     * @param value The string to be decapitalized.
     * @return The decapitalized version of the string.
     */
    public static String decapitalize(String value) {
        if (value == null || value.isEmpty()) {
            return value;
        }
        if (value.length() > 1 && Character.isUpperCase(value.charAt(1)) &&
                Character.isUpperCase(value.charAt(0))) {
            return value;
        }
        char chars[] = value.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(String delimiter, Object[] tokens) {
        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token : tokens) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /**
     * Returns a string containing the tokens joined by delimiters.
     *
     * @param tokens an array objects to be joined. Strings will be formed from
     *               the objects by calling object.toString().
     */
    public static String join(String delimiter, Iterable tokens) {
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = tokens.iterator();
        if (it.hasNext()) {
            sb.append(it.next());
            while (it.hasNext()) {
                sb.append(delimiter);
                sb.append(it.next());
            }
        }
        return sb.toString();
    }

    /**
     * Gets the line separator (break line).
     * If the SDK version is 19 or higher then it will use System.lineSeparator()
     * otherwise will use System.getProperty("line.separator") is available or \n as default.
     * @return
     */
    public static String getLineSeparator() {
        if (Build.VERSION.SDK_INT >= 19) {
            return System.lineSeparator();
        } else {
            String lineSeparator = System.getProperty("line.separator");
            if (StringUtil.isEmpty(lineSeparator)) {
                return "\n";
            } else {
                return lineSeparator;
            }
        }
    }

}
