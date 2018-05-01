package com.ssannttoss.android_challenge.mvc.model.repository.sqlite;

import android.content.ContentValues;

import com.ssannttoss.android_challenge.mvc.model.entity.MyAddress;
import com.ssannttoss.android_challenge.mvc.model.repository.MyAddressDao;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.persistence.sqlite.CacheManager;
import com.ssannttoss.framework.persistence.sqlite.ConstraintException;
import com.ssannttoss.framework.persistence.sqlite.CursorExt;
import com.ssannttoss.framework.persistence.sqlite.SqliteDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * SQlite DAO implementation for MyAddressDao.
 * Created by ssannttoss on 1/21/2018.
 */

public class MyAddressSqliteDao implements MyAddressDao {
    public static final String TABLE = "my_address";
    private SqliteDbHelper db;
    private CacheManager<MyAddress> cacheManager;

    /**
     * Pk
     * Type: "int", Nullable: No
    */
    public static String ID_MY_ADDRESS = "id_my_address";
    /**
     * Type: "text", Nullable: No
     */
    public static String FEATURE_NAME = "feature_name";
    /**
     * Type: "decimal", Nullable: No
     */
    public static String LATITUDE = "latitude";
    /**
     * Type: "decimal", Nullable: No
     */
    public static String LONGITUDE = "longitude";

    // SQL
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS my_address("
            + ID_MY_ADDRESS + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + FEATURE_NAME + " TEXT NOT NULL, "
            + LATITUDE + " REAL NOT NULL, "
            + LONGITUDE + " REAL NOT NULL)";

    public static final String CREATE_INDEX_IX_ID_MY_ADDRESS =
            "CREATE INDEX IF NOT EXISTS IX_ID_MY_ADDRESS ON my_address (" + ID_MY_ADDRESS + " DESC)";

    public static final String SELECT_ALL_SQL = "SELECT * FROM " + TABLE;
    public static final String SELECT_SQL = SELECT_ALL_SQL + " WHERE " + ID_MY_ADDRESS + " = ?";

    /**
     * Constructor
     */
    public MyAddressSqliteDao() {
        cacheManager = new CacheManager<>();
        db = DependencyManager.getInstance().get(SqliteDbHelper.class);

        if (db != null) {
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_INDEX_IX_ID_MY_ADDRESS);
        }
    }

    @Override
    public String getTableName() {
        return TABLE;
    }

    @Override
    public List<MyAddress> selectAll() {
        CursorExt cursor = null;
        List<MyAddress> results = new ArrayList<>();

        try {
            cursor = db.rawQuery(SELECT_ALL_SQL);
            if (cursor.moveToFirst()) {
                do {
                    results.add(createInstance(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            db.closeCursor(cursor);
        }

        return results;
    }

    /**
     * Selects MyAddress based on template ID
     * @param template
     * @return
     */
    @Override
    public List<MyAddress> select(MyAddress template) {
        CursorExt cursor = null;
        List<MyAddress> results = new ArrayList<>();

        try {
            cursor = db.rawQuery(SELECT_SQL, template.getId());
            if (cursor.moveToFirst()) {
                do {
                    results.add(createInstance(cursor));
                } while (cursor.moveToNext());
            }
        } finally {
            db.closeCursor(cursor);
        }

        return results;
    }

    /**
     * Inserts a new MyAddress.
     * @param entity
     * @return The id of the object.
     * @throws ConstraintException
     */
    @Override
    public long insert(MyAddress entity) throws ConstraintException {
        checkConstraints(entity);
        ContentValues contentValues = new ContentValues();
        contentValues.put(FEATURE_NAME, entity.getFeatureName());
        contentValues.put(LATITUDE, entity.getLatitude());
        contentValues.put(LONGITUDE, entity.getLongitude());
        long result = db.insert(getTableName(), contentValues);
        entity.setId(result);
        return entity.getId();
    }

    /**
     * Updates the MyAddress.
     * @param entity
     * @return
     * @throws ConstraintException
     */
    @Override
    public int update(MyAddress entity) throws ConstraintException {
        checkConstraints(entity);
        ContentValues contentValues = new ContentValues();
        contentValues.put(FEATURE_NAME, entity.getFeatureName());
        contentValues.put(LATITUDE, entity.getLatitude());
        contentValues.put(LONGITUDE, entity.getLongitude());
        final String where = ID_MY_ADDRESS + " = ?";
        int result = db.update(getTableName(), contentValues, where, entity.getId());
        return result;
    }

    /**
     * Deletes the saved address bases on template ID.
     * @param entity
     * @return
     */
    @Override
    public int delete(MyAddress entity) {
        final String where = ID_MY_ADDRESS + " = ?";
        int result = db.delete(getTableName(), where, entity.getId());
        cacheManager.remove(entity);
        return result;
    }

    /**
     * Checks null constraints for feature_name
     * @param myAddress
     */
    private void checkConstraints(MyAddress myAddress) {
        if (myAddress.getFeatureName() == null) {
            throw new ConstraintException(getTableName(), "feature_name", null, "(1299) Constraint failed: feature_name cannot be null");
        }
    }

    /**
     * Creates an instance of MyAddress from a cursor.
     * @param cursor
     * @return
     */
    private MyAddress createInstance(CursorExt cursor) {
        final MyAddress entity = new MyAddress();
        entity.setId(cursor.getInteger(ID_MY_ADDRESS));
        entity.setFeatureName(cursor.getString(FEATURE_NAME));
        entity.setLatitude(cursor.getDouble(LATITUDE));
        entity.setLongitude(cursor.getDouble(LONGITUDE));
        cacheManager.add(entity);
        return entity;
    }
}
