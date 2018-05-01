// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.persistence.sqlite;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.Date;

/**
 * Wrapper class for Cursor that delegates all calls to the actual cursor object.  The primary
 * use for this class is to extend a cursor while overriding only a subset of its methods.
 * <p>
 * Created by ssannttoss on 1/20/2018.
 */
public class CursorExt extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CursorExt(Cursor cursor) {
        super(cursor);
    }

    /**
     * Gets an {@link Integer} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Integer} value or {@code null}
     */
    public Integer getInteger(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex) ? null : getInt(columnIndex);
    }

    /**
     * Gets an {@link Long} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Long} value or {@code null}
     */
    public Long getLong(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex) ? null : getLong(columnIndex);
    }

    /**
     * Gets an trimmed {@link String} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link String} value or {@code null}
     */
    public String getString(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        final String value = getString(columnIndex);
        return value == null ? null : value.trim();
    }

    /**
     * Gets an {@link Boolean} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Boolean} value or {@code null}
     */
    public Boolean getBoolean(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex) ? null : getInt(columnIndex) == 1;
    }

    /**
     * Gets an {@link Date} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Date} value or {@code null}
     */
    public Date getDate(String columnName) {
        final Long value = getLong(columnName);
        return value == null ? null : new Date(value);
    }

    /**
     * Gets an {@link Short} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Short} value or {@code null}
     */
    public Short getShort(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex) ? null : getShort(columnIndex);
    }

    /**
     * Gets an {@link Byte} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Byte} value or {@code null}
     */
    public Byte getByte(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex) ? null : Byte.parseByte(getString(columnIndex));
    }

    /**
     * Gets an {@link Double} data from database using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return An {@link Double} value or {@code null}
     */
    public Double getDouble(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex) ? null : getDouble(columnIndex);
    }

    /**
     * Checks if the value on database is {@code null} using the {@code columnName}
     *
     * @param columnName the desired column name
     * @return {@code true} if is {@code null}
     */
    public boolean isNull(String columnName) {
        final int columnIndex = getColumnIndex(columnName);
        return isNull(columnIndex);
    }
}
