// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.logging;

import android.util.Log;

/**
 * Manages the log entries showingm them in log cat and also save in a log file.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */
public final class LogManager {
    private static final LogProvider sLogProvider = LogProvider.getInstance();
    private static LogLevel logLevel = LogLevel.ERROR;

    private LogManager() {
    }

    /**
     * Send a {@link LogLevel#DEBUG} log message.
     *
     * @param owner   Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void d(Object owner, String message) {
        d(owner, message, false);
    }

    /**
     * Send a {@link LogLevel#DEBUG} log message.
     *
     * @param owner      Used to identify the source of a log message.  It usually identifies
     *                   the class or activity where the log call occurs.
     * @param message    The message you would like logged.
     * @param stacktrace force shows stacktrace.
     */
    public static void d(Object owner, String message, boolean stacktrace) {
        Log.d(getTag(owner), "[" + owner + "] " + message);
        if (stacktrace) {
            getStackTrace(owner);
        }
        addLogEntry(owner, LogLevel.DEBUG, message);
    }

    /**
     * Send a {@link LogLevel#DEBUG} log message and log the exception.
     *
     * @param owner     Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    public static void d(Object owner, String message, Throwable throwable) {
        d(owner, message, throwable, false);
    }

    /**
     * Send a {@link LogLevel#DEBUG} log message and log the exception.
     *
     * @param owner      Used to identify the source of a log message.  It usually identifies
     *                   the class or activity where the log call occurs.
     * @param message    The message you would like logged.
     * @param throwable  An exception to log
     * @param stacktrace force shows stacktrace.
     */
    public static void d(Object owner, String message, Throwable throwable, boolean stacktrace) {
        Log.d(getTag(owner), "[" + owner + "] " + message);
        if (stacktrace) {
            getStackTrace(owner);
        }
        addLogEntry(owner, LogLevel.DEBUG, message);
    }

    /**
     * Send a {@link LogLevel#ERROR} log message and log the exception.
     *
     * @param owner   Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void e(Object owner, String message) {
        Log.e(getTag(owner), "[" + owner + "] " + message);
        addLogEntry(owner, LogLevel.ERROR, message);
    }

    /**
     * Send a {@link LogLevel#ERROR} log message and log the exception.
     *
     * @param owner     Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    public static void e(Object owner, String message, Throwable throwable) {
        if (throwable == null) {
            e(owner, message);
        } else {
            Log.e(getTag(owner), "[" + owner + "] " + message, throwable);

            addLogEntry(owner, LogLevel.ERROR, message + String.format("\nMessage:%s\nStack:%s",
                    throwable.getMessage(), getStackTrace(throwable.getStackTrace(), 0)));
        }
    }

    /**
     * Send a {@link LogLevel#WARN} log message.
     *
     * @param owner   Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void w(Object owner, String message) {
        Log.w(getTag(owner), "[" + owner + "] " + message);
        addLogEntry(owner, LogLevel.WARN, message);
    }

    /**
     * Send a {@link LogLevel#WARN} log message and log the exception.
     *
     * @param owner     Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    public static void w(Object owner, String message, Throwable throwable) {
        Log.w(getTag(owner), "[" + owner + "] " + message, throwable);
        addLogEntry(owner, LogLevel.WARN, message);
    }

    /**
     * Send an {@link LogLevel#INFO} log message.
     *
     * @param owner   Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void i(Object owner, String message) {
        Log.i(getTag(owner), "[" + owner + "] " + message);
        addLogEntry(owner, LogLevel.INFO, message);
    }

    /**
     * Send a {@link LogLevel#INFO} log message and log the exception.
     *
     * @param owner     Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    public static void i(Object owner, String message, Throwable throwable) {
        Log.i(getTag(owner), "[" + owner + "] " + message, throwable);
        addLogEntry(owner, LogLevel.INFO, message);
    }

    /**
     * Send a {@link LogLevel#VERBOSE} log message.
     *
     * @param owner   Used to identify the source of a log message.  It usually identifies
     *                the class or activity where the log call occurs.
     * @param message The message you would like logged.
     */
    public static void v(Object owner, String message) {
        Log.v(getTag(owner), "[" + owner + "] " + message);
        addLogEntry(owner, LogLevel.VERBOSE, message);
    }

    /**
     * Send a {@link LogLevel#VERBOSE} log message and log the exception.
     *
     * @param owner     Used to identify the source of a log message.  It usually identifies
     *                  the class or activity where the log call occurs.
     * @param message   The message you would like logged.
     * @param throwable An exception to log
     */
    public static void v(Object owner, String message, Throwable throwable) {
        Log.v(getTag(owner), "[" + owner + "] " + message, throwable);
        addLogEntry(owner, LogLevel.VERBOSE, message);
    }

    /**
     * Gets the Tag to LogCat basing on Owner.
     *
     * @param owner The owner of the log
     * @return The tag.
     */
    private static String getTag(Object owner) {
        return owner instanceof Class ? ((Class) owner).getSimpleName() : owner.getClass().getSimpleName();
    }

    private static void addLogEntry(Object owner, LogLevel level, String text) {
        if (level.getCode() >= logLevel.getCode()) {
            final LogEntry logEntry = new LogEntry(level, getTag(owner), text);
            write(logEntry);
        }
    }

    private static void write(LogEntry entry) {
        sLogProvider.write(entry);
    }

    public static LogLevel getLevel() {
        return logLevel;
    }

    public static void setLevel(LogLevel level) {
        logLevel = level;
    }

    public static void clear() {
        sLogProvider.clear();
    }

    private static void getStackTrace(Object owner) {
        Log.d(getTag(owner), getStackTrace(Thread.currentThread().getStackTrace(), 4));
    }

    private static String getStackTrace(StackTraceElement[] stackTraceElements, int jumpNumber) {
        {
            final StringBuilder sb = new StringBuilder("StackTrace: \n");
            for (StackTraceElement stackTraceElement : stackTraceElements) {
                if (jumpNumber-- < 0) {
                    sb.append("\t").append(stackTraceElement).append("\n");
                }
            }
            return cleanStackTrace(sb.toString());
        }
    }

    /**
     * Removes default packages from the stack allowing to reduce the log size on the disk or database.
     *
     * @param stackTrace
     * @return
     */
    public static String cleanStackTrace(String stackTrace) {
        String cleanStrackTrace = stackTrace
                .replaceAll("\n.*at dalvik.system..*(.*)", "")
                .replaceAll("\n.*at com.android..*(.*)", "")
                .replaceAll("\n.*at java..*", "")
                .replaceAll("\n.*at android..*", "")
                .replaceAll("\\(.*.java:", "(")
                .replace("\tat", "\t").trim();

        return cleanStrackTrace;
    }
}
