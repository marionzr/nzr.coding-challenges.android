// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.logging;

import android.text.TextUtils;
import android.util.Log;

import com.ssannttoss.framework.util.DateTimeUtil;

import java.text.ParseException;
import java.util.Date;

/**
 * Represents a log entry to be persisted bases on its log level
 * to sent later to the server.
 * <p>
 * Created by ssannttoss on 1/20/2018.
 */
class LogEntry {
    private static final String SPLIT = "¨";

    private LogLevel mLevel;
    private String mNomeClasse;
    private String mText;
    private Date mDate;

    public LogEntry(LogLevel level, String nomeClasse, String text) {
        this(level, nomeClasse, text, DateTimeUtil.now());
    }

    public LogEntry(LogLevel level, String nomeClasse, String text, Date date) {
        super();
        mLevel = level;
        mNomeClasse = nomeClasse;
        mText = text;
        mDate = date;
    }

    public static LogEntry parse(String logString) {
        try {
            if (TextUtils.isEmpty(logString)) {
                return null;
            }

            final String[] parts = logString.split(SPLIT);

            if (parts.length != 4) {
                return new LogEntry(LogLevel.ERROR, LogEntry.class.getSimpleName(), logString.replace(SPLIT, ";"), DateTimeUtil.now());
            }

            final short iLevel = Short.parseShort(parts[0]);
            final LogLevel logLevel = LogLevel.valueOf(iLevel);
            final String className = parts[1].replace("<br>", "\n");
            final Date date = DateTimeUtil.parseDate(parts[2], DateTimeUtil.PATTERN_DATE_TIME_DB);
            final String text = parts[3].replace("<br>", "\n");
            return new LogEntry(logLevel, className, text, date);
        } catch (ParseException e) {
            Log.e(LogEntry.class.getSimpleName(), "Error on parsing logString to LogEntry", e);
            return null;
        }
    }

    public LogLevel getLevel() {
        return mLevel;
    }

    public String getText() {
        return mText;
    }

    public Date getDate() {
        return mDate;
    }

    public String getNomeClasse() {
        return mNomeClasse;
    }

    public void clean() {
        String newMessage = mText;

        final String patternFramework = "(com\\.ssannttoss\\.framework\\.[a-z]+)|(com\\.ssannttoss\\.framework)";
        newMessage = newMessage.replaceAll(patternFramework, "FRM");

        mText = newMessage;
    }

    @Override
    public String toString() {
        if (mText == null) {
            mText = "";
        }

        // {logLevel}¨{className}¨{Date}¨{Content}
        return String.format("%2$s%1$s%5$s%1$s%3$s%1$s%4$s", SPLIT, mLevel.getCode(), DateTimeUtil.toString(mDate,
                DateTimeUtil.PATTERN_DATE_US_YEAR_TO_SECOND), mText.replace("\n", "<br>"), mNomeClasse);
    }
}
