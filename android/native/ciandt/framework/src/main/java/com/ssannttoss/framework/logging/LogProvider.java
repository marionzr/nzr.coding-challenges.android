// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.logging;

import com.ssannttoss.framework.BuildConfig;

/**
 * Writes a log in a file or database to be sent later to a backend server.
 * <p>
 * Created by ssannttoss on Nov/03/2018.
 */
public final class LogProvider {
    private static final String TAG = LogProvider.class.getSimpleName();
    private static final String LOG_PATH = "/logging/";
    private static LogProvider sInstance;

    private LogProvider() {
        super();
    }

    /**
     * @return Singleton Instance of this class.
     */
    public static LogProvider getInstance() {
        synchronized (LogProvider.class) {
            if (sInstance == null) {
                sInstance = new LogProvider();
            }

            return sInstance;
        }
    }

    /**
     * Appends the log entry into a log file.
     *
     * @param entry
     */
    public synchronized void write(final LogEntry entry) {
        rotateLog();
        // implementation omitted for simplicity
    }


    /**
     * Clears the log files.
     */
    public void clear() {
        // implementation omitted for simplicity
    }

    /**
     * Rotates the log files deleting old ones
     */
    private void rotateLog() {
        // implementation omitted for simplicity
    }

    private String getFileName() {
        String name = "%s.log";
        name = getRawFileName(name);
        return name;
    }

    private String getRawFileName(String format) {
        return String.format(format, BuildConfig.APPLICATION_ID.replace(".", "-"));
    }
}
