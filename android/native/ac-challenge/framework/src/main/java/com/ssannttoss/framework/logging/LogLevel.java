// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.logging;

/**
 * Supported log levels.
 * <p>
 * Created by ssannttoss on 1/20/2018.
 */

public enum LogLevel {
    VERBOSE((short) 0, 15),
    DEBUG((short) 1, 15),
    INFO((short) 2, 15),
    WARN((short) 3, 15),
    ERROR((short) 4, 15),
    DISABLED((short) 99, 0);

    private final short code;
    private final int keepAliveDays;

    LogLevel(short logLevelCode, int keepAliveDays) {
        this.code = logLevelCode;
        this.keepAliveDays = keepAliveDays;
    }

    public static LogLevel valueOf(short code) {
        LogLevel logLevel = ERROR;
        switch (code) {
            case 0:
                logLevel = VERBOSE;
                break;
            case 1:
                logLevel = DEBUG;
                break;
            case 2:
                logLevel = INFO;
                break;
            case 3:
                logLevel = WARN;
                break;
            case 4:
                logLevel = ERROR;
                break;
            case 99:
                logLevel = DISABLED;
                break;
        }

        return logLevel;
    }

    public short getCode() {
        return code;
    }

    public int getKeepAliveDays() {
        return keepAliveDays;
    }
}
