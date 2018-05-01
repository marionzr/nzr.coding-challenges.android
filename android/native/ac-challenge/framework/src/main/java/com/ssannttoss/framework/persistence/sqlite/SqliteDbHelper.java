// Copyright © 2018 by Mário Heleno Nazareth Santos - ssannttoss@gmail.com
// All rights reserved. No part of this publication may be reproduced, distributed, or transmitted
// in any form or by any means, including photocopying, recording, or other electronic or mechanical
// methods, without the prior written permission of the publisher
// For permission requests, send an e-mail write to the publisher address ssannttoss@gmail.com

package com.ssannttoss.framework.persistence.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.ssannttoss.framework.logging.LogManager;

/**
 * An {@link SQLiteOpenHelper} extends class that helps to implement the DAO pattern.
 * <p>
 * Created by ssannttoss on 1/20/2018.
 */

public class SqliteDbHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;

    public SqliteDbHelper(Context context, String dbName, int dbVersion) {
        super(context, dbName, null, dbVersion);
        open();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogManager.d(this, String.format("Database created. Version: %s", db.getVersion()));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogManager.d(this, String.format("Database upgraded. Old Version: %s | New Version: %s", oldVersion, newVersion));
        // on upgrade drop older tables
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
        onCreate(db);
    }

    public void execSQL(@NonNull final String sql) {
        open();
        db.execSQL(sql);
        LogManager.v(this, String.format("execSQL: %s", sql));
    }

    public CursorExt rawQuery(@NonNull final String query, Object... whereArgs) {
        open();
        final String[] args = convertToStringArgs(whereArgs);
        Cursor cursor = db.rawQuery(query, args);
        LogManager.v(this, String.format("rawQuery: %s", query));
        return new CursorExt(cursor);
    }

    public long insert(@NonNull final String table, @NonNull final ContentValues contentValues) {
        open();
        long result = db.insertOrThrow(table, null, contentValues);
        LogManager.v(this, String.format("insert into %s. Result: %d", table, result));
        return result;
    }

    public int update(@NonNull final String table, ContentValues contentValues, @NonNull final String where, final Object... whereArgs) {
        open();
        final String[] args = convertToStringArgs(whereArgs);
        int result = db.update(table, contentValues, where, args);
        LogManager.v(this, String.format("insert into %s. Result: %d", table, result));
        return result;
    }

    public int delete(@NonNull final String table, @NonNull final String where, final Object... whereArgs) {
        open();
        final String[] args = convertToStringArgs(whereArgs);
        int result = db.delete(table, where, args);
        LogManager.v(this, String.format("insert into %s. Result: %d", table, result));
        return result;
    }

    public void closeCursor(CursorExt cursorExt) {
        if (cursorExt != null && !cursorExt.isClosed()) {
            cursorExt.close();
            LogManager.d(this, "Cursor closed");
        }
    }

    // closing database
    public void closeDB() {
        if (db != null && db.isOpen()) {
            db.close();
            super.close();
            LogManager.d(this, "DB closed");
        }
    }

    public SQLiteOpenHelper open() {
        db = this.getReadableDatabase();

        if (!db.isOpen()) {
            super.onOpen(db);
        }

        return this;
    }

    private String[] convertToStringArgs(Object... whereArgs) {
        final String[] args = new String[whereArgs.length];
        for (int i = 0; i < whereArgs.length; i++) {
            args[0] = String.valueOf(whereArgs[i]);
        }

        return args;
    }
}

