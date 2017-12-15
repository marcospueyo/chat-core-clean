package com.mph.chatcontrol.device.database;
/* Created by macmini on 15/12/2017. */

import android.content.Context;
import android.support.annotation.NonNull;

import com.mph.chatcontrol.BuildConfig;
import com.mph.chatcontrol.data.Models;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

public class DatabaseImpl implements Database {

    @SuppressWarnings("unused")
    private static final String TAG = DatabaseImpl.class.getSimpleName();

    @NonNull
    private Context mContext;

    private EntityDataStore<Persistable> dataStore;

    public DatabaseImpl(@NonNull Context context) {
        mContext = context;
    }

    /**
     * @return {@link EntityDataStore} single instance for the application.
     * <p/>
     * Note if you're using Dagger you can make this part of your application level module returning
     * {@code @Provides @Singleton}.
     */

    private EntityDataStore<Persistable> getData(Context context) {
        if (dataStore == null) {
            initDataSore(context);
        }
        return dataStore;
    }

    private void initDataSore(Context context) {
        // override onUpgrade to handle migrating to a new version
        DatabaseSource source = new DatabaseSource(context, Models.DEFAULT, 8);
        if (BuildConfig.DEBUG) {
            // use this in development mode to drop and recreate the tables on every upgrade
            source.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = source.getConfiguration();
        dataStore = new EntityDataStore<>(configuration);
    }

    @Override
    public EntityDataStore<Persistable> getDataStore() {
        return getData(mContext);
    }

    @Override
    public void clearDataStore() {
        initDataSore(mContext);
    }
}
