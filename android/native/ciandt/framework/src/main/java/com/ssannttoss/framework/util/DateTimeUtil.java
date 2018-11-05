// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utility class to work with Date and time.
 * Created by ssannttoss on Nov/03/2018.
 */

public class DateTimeUtil {
    public static final String PATTERN_DATE_BR = "dd/MM/yyyy";
    public static final String PATTERN_DATE_TIME_BR = "dd/MM/yyyy HH:mm:ss";
    public static final String PATTERN_DATE_TIME_BR_2 = "dd/MM/yyyy 'às' HH:mm";
    public static final String PATTERN_DATE_DD_MM = "dd/MM";
    public static final String PATTERN_DATE_MM_YY = "MM/yy";
    public static final String PATTERN_DATE_MM_YYYY = "MM/yyyy";
    public static final String PATTERN_DATE_DB = "yyyy-MM-dd";
    public static final String PATTERN_DATE_TIME_DB = "yyyy-MM-dd HH:mm:ss.S";
    public static final String PATTERN_TIME_FULL_BR = "HH:mm:ss";
    public static final String PATTERN_TIME_HH_MM_BR = "HH:mm";
    public static final String PATTERN_TIME_HH = "HH";
    public static final String PATTERN_TIME_MM = "mm";
    public static final String PATTERN_DATE_TIME_NO_SECONDS = "dd/MM/yy HH:mm";

    private static final Locale DEFAULT_LOCALE = Locale.getDefault();


    private DateTimeUtil() {
        super();
    }

    /**
     * Check if two Date objects is in the same instant of time.
     * <p/>
     * This method compare the milliseconds in long of both objects.
     *
     * @param first  the first date, not null.
     * @param second the second date, not null.
     * @return true if they are on the same instant in milliseconds.
     * @throws IllegalArgumentException if both are {@code null}
     */
    public static boolean isSameInstant(Date first, Date second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return first.getTime() == second.getTime();
    }

    /**
     * Check if two Calendar objects is in the same instant of time.
     * <p/>
     * This method compare the milliseconds in long of both objects.
     *
     * @param first  the first calendar, not null.
     * @param second the second calendar, not null.
     * @return true if they are on the same instant in milliseconds.
     * @throws IllegalArgumentException if both are {@code null}
     * @since 2.1
     */
    public static boolean isSameInstant(Calendar first, Calendar second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return first.getTime().getTime() == second.getTime().getTime();
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the actual date.
     * <p/>
     * This method don't validate the date.
     *
     * @return The Actual date, without time, in {@code String}.
     */
    public static String getDateNowString() {
        return getDateTimeNow(PATTERN_DATE_BR, false);
    }

    /**
     * Gets the actual date
     *
     * @param lenient true if need to validate the date.
     * @return The Actual date, without time, in {@code String}.
     */
    public static String getDateNowString(boolean lenient) {
        return getDateTimeNow(PATTERN_DATE_BR, lenient);
    }

    /**
     * Gets the actual datetime
     * <p/>
     * This method don't validate the date.
     *
     * @return The actual Datetime in {@code String}.
     */
    public static String getDateTimeNowString() {
        return getDateTimeNow(PATTERN_DATE_TIME_BR, false);
    }

    /**
     * Gets the actual datetime
     *
     * @param lenient true if need to validate the date.
     * @return The actual Datetime in {@code String}.
     */
    public static String getDateTimeNowString(boolean lenient) {
        return getDateTimeNow(PATTERN_DATE_TIME_BR, lenient);
    }

    /**
     * Gets the actual datetime
     *
     * @param format  The date format.
     * @param lenient true if need to validate the date.
     * @return The actual Datetime in {@code String}.
     */
    private static String getDateTimeNow(String format, boolean lenient) {
        Calendar c = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat(format, DEFAULT_LOCALE);
        formatter.setLenient(lenient);

        return formatter.format(c.getTime());
    }

    /**
     * Gets the actual datetime
     *
     * @return Date Object contain the actual datetime.
     */
    public static Date now() {
        final Calendar c = Calendar.getInstance();
        return c.getTime();
    }

    /**
     * Gets the actual datetime
     *
     * @return Calendar Object contain the actual datetime.
     */
    public static Calendar nowAsCalendar() {
        return Calendar.getInstance();
    }

    /**
     * Gets the actual date in the desired hour/minute/seconds/milliseconds.
     *
     * @param hour         the desired hour.
     * @param minute       the desired minute.
     * @param seconds      the desired seconds.
     * @param milliseconds the desired milliseconds.
     * @return An actual Date object in the desired time.
     */
    public static Date now(int hour, int minute, int seconds, int milliseconds) {
        final Calendar c = setTimeCalendar(Calendar.getInstance(), hour, minute, seconds, milliseconds);
        return c.getTime();
    }

    /**
     * Gets the actual datetime
     *
     * @return Date Object contain the actual datetime.
     * @deprecated use {@link #now()}
     */
    @Deprecated
    public static Date getDateNow() {
        return now();
    }

    /**
     * Gets a Date object from milliseconds.
     *
     * @param milliseconds Date in milliseconds
     * @return Date Object
     */
    public static Date getDateFromMilliseconds(long milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);

        return calendar.getTime();
    }

    //-----------------------------------------------------------------------

    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * <p/>
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser will be lenient toward the parsed date.
     *
     * @param str           the date to parse, not null
     * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException           if none of the date patterns were suitable (or there were none)
     */
    public static Date parseDate(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, true, parsePatterns);
    }

    /**
     * Parse and format a Date object to String
     *
     * @param date The date that will be parsed.
     * @return Formatted date in {@code String}.
     */
    public static String parseDateToString(@NonNull Date date, final String pattern) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        DateFormat formatter = new SimpleDateFormat(pattern, DEFAULT_LOCALE);

        return formatter.format(c.getTime());
    }

    //-----------------------------------------------------------------------

    public static Date parseDateStrictly(String str) throws ParseException {
        return parseDateStrictly(str, PATTERN_DATE_BR);
    }

    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * <p/>
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     * The parser parses strictly - it does not allow for dates such as "February 942, 1996".
     *
     * @param str           the date to parse, not null
     * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
     * @return the parsed date
     * @throws IllegalArgumentException if the date string or pattern array is null
     * @throws ParseException           if none of the date patterns were suitable
     * @since 2.5
     */
    public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
        return parseDateWithLeniency(str, false, parsePatterns);
    }

    /**
     * <p>Parses a string representing a date by trying a variety of different parsers.</p>
     * <p/>
     * <p>The parse will try each parse pattern in turn.
     * A parse is only deemed successful if it parses the whole of the input string.
     * If no parse patterns match, a ParseException is thrown.</p>
     *
     * @param str           the date to parse, not null
     * @param parsePatterns the date format patterns to use, see SimpleDateFormat, not null
     * @param lenient       Specify whether or not date/time parsing is to be lenient.
     * @return the parsed date
     * @throws IllegalArgumentException if the pattern array is null
     * @throws ParseException           if none of the date patterns were suitable
     * @see java.util.Calender#isLenient()
     */
    private static Date parseDateWithLeniency(String str, boolean lenient, String... parsePatterns)
            throws ParseException {
        if (str == null) {
            return null;
        }

        if (parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }

        SimpleDateFormat parser = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
        parser.setLenient(lenient);
        ParsePosition pos = new ParsePosition(0);
        for (String parsePattern : parsePatterns) {

            String pattern = parsePattern;

            // LANG-530 - need to make sure 'ZZ' output doesn't get passed to SimpleDateFormat
            if (parsePattern.endsWith("ZZ")) {
                pattern = pattern.substring(0, pattern.length() - 1);
            }

            parser.applyPattern(pattern);
            pos.setIndex(0);

            String str2 = str;
            // LANG-530 - need to make sure 'ZZ' output doesn't hit SimpleDateFormat as it will ParseException
            if (parsePattern.endsWith("ZZ")) {
                int signIdx = indexOfSignChars(str2, 0);
                while (signIdx >= 0) {
                    str2 = reformatTimezone(str2, signIdx);
                    signIdx = indexOfSignChars(str2, ++signIdx);
                }
            }

            Date date = parser.parse(str2, pos);
            if (date != null && pos.getIndex() == str2.length()) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }

    /**
     * Index of sign characters (i.e. '+' or '-').
     *
     * @param str      The string to search
     * @param startPos The start position
     * @return the index of the first sign character or -1 if not found
     */
    private static int indexOfSignChars(String str, int startPos) {
        int idx = TextUtils.indexOf(str, '+', startPos);
        if (idx < 0) {
            idx = TextUtils.indexOf(str, '-', startPos);
        }
        return idx;
    }

    /**
     * Reformat the timezone in a date string.
     *
     * @param str     The input string
     * @param signIdx The index position of the sign characters
     * @return The reformatted string
     */
    private static String reformatTimezone(String str, int signIdx) {
        String str2 = str;
        if (signIdx >= 0 &&
                signIdx + 5 < str.length() &&
                Character.isDigit(str.charAt(signIdx + 1)) &&
                Character.isDigit(str.charAt(signIdx + 2)) &&
                str.charAt(signIdx + 3) == ':' &&
                Character.isDigit(str.charAt(signIdx + 4)) &&
                Character.isDigit(str.charAt(signIdx + 5))) {
            str2 = str.substring(0, signIdx + 3) + str.substring(signIdx + 4);
        }
        return str2;
    }

    //-----------------------------------------------------------------------

    /**
     * Converts a Date object from a format to another
     *
     * @param date       The date that will be formatted.
     * @param fromFormat the original format.
     * @param toFormat   the target format
     * @param lenient    true if need to validate the date.
     * @return A {@code String} value of the formatted date.
     * @throws ParseException if the original and/or target format is in invalid format.
     */
    public static String convertDateFormat(String date, String fromFormat, String toFormat, boolean lenient)
            throws ParseException {
        Calendar c = Calendar.getInstance();
        DateFormat originalFormat = new SimpleDateFormat(fromFormat, DEFAULT_LOCALE);
        DateFormat endFormat = new SimpleDateFormat(toFormat, DEFAULT_LOCALE);
        endFormat.setLenient(lenient);
        try {
            c.setTime(originalFormat.parse(date));
        } catch (ParseException e) {
            throw new ParseException("Não foi possível converter a data: ".concat(date), -1);
        }

        return endFormat.format(c.getTime());
    }

    /**
     * Converts a Date object from a format to another with automatic validation.
     *
     * @param date       The date that will be formatted.
     * @param fromFormat the original format.
     * @param toFormat   the target format
     * @return A {@code String} value of the formatted date.
     * @throws ParseException if the original and/or target format is in invalid format.
     */
    public static String convertDateFormat(String date, String fromFormat, String toFormat) throws ParseException {
        return convertDateFormat(date, fromFormat, toFormat, true);
    }

    /**
     * Convert milliseconds to date in {@code String}
     *
     * @param milliseconds The date in milliseconds
     * @param partern      The target format
     * @return A {@code String} value of the formatted date.
     */
    public static String convertMillisecondsToStringDate(long milliseconds, String partern) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        DateFormat formatter = new SimpleDateFormat(partern, DEFAULT_LOCALE);

        return formatter.format(c.getTime());
    }

    //-----------------------------------------------------------------------

    /**
     * Gets the actual date in the desired hour/minute/seconds/milliseconds.
     *
     * @param hour         the desired hour.
     * @param minute       the desired minute.
     * @param seconds      the desired seconds.
     * @param milliseconds the desired milliseconds.
     * @return An actual Date object in the desired time.
     * @deprecated use {@link #now(int, int, int, int)}
     */
    @Deprecated
    public static Date getDateNow(int hour, int minute, int seconds, int milliseconds) {
        Calendar cal = setTimeCalendar(Calendar.getInstance(), hour, minute, seconds, milliseconds);
        return cal.getTime();
    }

    /**
     * Adds an amount of hour in the Calendar passed as parameter.
     *
     * @param calendar The calendar that will be add hours.
     * @param amount   The amount of hours that will be added.
     * @return A Calendar Object with the hour updated.
     */
    public static Calendar addHours(Calendar calendar, int amount) {
        calendar.add(Calendar.HOUR_OF_DAY, amount);
        return calendar;
    }

    /**
     * Adds an amount of minutes in the Calendar passed as parameter.
     *
     * @param calendar The calendar that will be add minutes.
     * @param amount   The amount of minutes that will be added.
     * @return A Calendar Object with the minutes updated.
     */
    public static Calendar addMinute(Calendar calendar, int amount) {
        calendar.add(Calendar.MINUTE, amount);
        return calendar;
    }

    /**
     * Adds an amount of seconds in the Calendar passed as parameter.
     *
     * @param calendar The Calendar that will be add seconds.
     * @param amount   The amount of seconds that will be added.
     * @return A {@link Calendar} Object with the seconds updated.
     */
    public static Calendar addSeconds(Calendar calendar, int amount) {
        calendar.add(Calendar.SECOND, amount);
        return calendar;
    }

    /**
     * Adds an amount of hour in the Date passed as parameter.
     *
     * @param date   The date that will be add hours.
     * @param amount The amount of hours that will be added.
     * @return A Date Object with the hour updated.
     */
    public static Date addHours(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, amount);

        return calendar.getTime();
    }

    /**
     * Adds an amount of minutes in the Date passed as parameter.
     *
     * @param date   The date that will be add minutes.
     * @param amount The amount of minutes that will be added.
     * @return A Date Object with the minutes updated.
     */
    public static Date addMinutes(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, amount);

        return calendar.getTime();
    }

    /**
     * Adds an amount of seconds in the {@link Date} passed as parameter.
     *
     * @param date   The date that will be add seconds.
     * @param amount The amount of seconds that will be added.
     * @return A {@link Date} Object with the seconds updated.
     */
    public static Date addSeconds(Date date, int amount) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, amount);
        return calendar.getTime();
    }

    /**
     * Adds an amount of days in the Date passed as parameter.
     *
     * @param date   The date that will be add days.
     * @param amount The amount of day that will be added.
     * @return A Date Object with the day updated.
     */
    public static Date addDays(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);

        return calendar.getTime();
    }

    /**
     * Adds an amount of years in the Date passed as parameter.
     *
     * @param date   The date that will be add years.
     * @param amount The amount of year that will be added.
     * @return A Date Object with the year updated.
     */
    public static Date addYears(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, amount);
        return calendar.getTime();
    }

    /**
     * Changes the date from a Calendar object passed as parameter
     *
     * @param calendar The calendar that will be changed.
     * @param day      The day to update.
     * @param month    The month to update.
     * @param year     The year to update.
     * @return A Calendar Object with the hour updated.
     */
    public static Calendar setDateCalendar(Calendar calendar, int day, int month, int year) {
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);

        return calendar;
    }

    /**
     * Changes the hour from a Calendar object passed as parameter
     *
     * @param calendar     The calendar that will be changed.
     * @param hour         The hour to update.
     * @param minutes      The minutes to update.
     * @param seconds      The seconds to update.
     * @param milliseconds The milliseconds to update.
     * @return A Calendar Object with the hour updated.
     */
    public static Calendar setTimeCalendar(Calendar calendar, int hour, int minutes, int seconds, int milliseconds) {
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);

        return calendar;
    }

    /**
     * Convert an Date object to Calendar.
     */
    public static Calendar toCalendar(Date date) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static int dataDiff(java.util.Date dataLow, java.util.Date dataHigh) {
        GregorianCalendar startTime = new GregorianCalendar();
        GregorianCalendar endTime = new GregorianCalendar();
        GregorianCalendar curTime = new GregorianCalendar();
        GregorianCalendar baseTime = new GregorianCalendar();

        startTime.setTime(dataLow);
        endTime.setTime(dataHigh);

        int dif_multiplier = 1;
        // Verifica a ordem de inicio das datas
        if (dataLow.compareTo(dataHigh) < 0) {
            baseTime.setTime(dataHigh);
            curTime.setTime(dataLow);
            dif_multiplier = 1;
        } else {
            baseTime.setTime(dataLow);
            curTime.setTime(dataHigh);
            dif_multiplier = -1;
        }
        int result_years = 0;
        int result_months = 0;
        int result_days = 0;
        // Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
        // no total de dias. Ja leva em consideracao ano bissesto
        while (curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR) ||
                curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)) {
            int max_day = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            result_months += max_day;
            curTime.add(GregorianCalendar.MONTH, 1);
        }
        // Marca que é um saldo negativo ou positivo
        result_months = result_months * dif_multiplier;
        // Retirna a diferenca de dias do total dos meses
        result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));
        return result_years + result_months + result_days;
    }

    public static Date newDate(int year, int month, int day) {
        return newDate(year, month, day, 0, 0, 0);
    }

    public static Date newDate(int year, int month, int day, int hour, int minute, int second) {
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }
}
