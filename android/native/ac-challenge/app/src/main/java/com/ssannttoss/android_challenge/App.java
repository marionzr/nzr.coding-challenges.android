package com.ssannttoss.android_challenge;

import android.util.Log;

import com.ssannttoss.android_challenge.mvc.model.repository.MyAddressDao;
import com.ssannttoss.android_challenge.mvc.model.repository.sqlite.MyAddressSqliteDao;
import com.ssannttoss.framework.ioc.DependencyManager;
import com.ssannttoss.framework.ioc.DependencyType;
import com.ssannttoss.framework.logging.LogManager;
import com.ssannttoss.framework.mvc.MvcApplication;
import com.ssannttoss.framework.persistence.sqlite.SqliteDbHelper;

/**
 * Created by ssannttoss on 1/21/2018.
 */

public class App extends MvcApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        final DependencyManager manager = DependencyManager.getInstance();
        manager.register(SqliteDbHelper.class, SqliteDbHelper.class, DependencyType.SINGLETON,
                new SqliteDbHelper(getApplicationContext(), BuildConfig.APPLICATION_ID, BuildConfig.VERSION_CODE));
        //manager.register(MyAddressDao.class, MyAddressDaoMock.class, DependencyType.SINGLETON);
        manager.register(MyAddressDao.class, MyAddressSqliteDao.class, DependencyType.SINGLETON);
        LogManager.i(this, "Application created. Version: " + BuildConfig.VERSION_NAME);
    }

    @Override
    public void onTerminate() {
        try {
            final DependencyManager manager = DependencyManager.getInstance();
            manager.get(SqliteDbHelper.class).closeDB();
        } catch(Exception e) {
            Log.e("App", e.getMessage(), e);
        }
        super.onTerminate();
    }
}
