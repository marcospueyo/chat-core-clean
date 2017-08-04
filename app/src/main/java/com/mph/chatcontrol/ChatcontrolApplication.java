package com.mph.chatcontrol;

import android.app.Application;


import com.mph.chatcontrol.data.Models;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

/**
 * Created by Marcos on 03/08/2017.
 */

public class ChatcontrolApplication extends Application {

    private EntityDataStore<Persistable> dataStore;

    /**
     * @return {@link EntityDataStore} single instance for the application.
     * <p/>
     * Note if you're using Dagger you can make this part of your application level module returning
     * {@code @Provides @Singleton}.
     */

    public EntityDataStore<Persistable> getData() {
        if (dataStore == null) {
            // override onUpgrade to handle migrating to a new version
            DatabaseSource source = new DatabaseSource(this, Models.DEFAULT, 3);
            if (BuildConfig.DEBUG) {
                // use this in development mode to drop and recreate the tables on every upgrade
                source.setTableCreationMode(TableCreationMode.DROP_CREATE);
            }
            Configuration configuration = source.getConfiguration();
            dataStore = new EntityDataStore<>(configuration);
        }
        return dataStore;
    }
}
