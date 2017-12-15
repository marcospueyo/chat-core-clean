package com.mph.chatcontrol.device.database;
/* Created by macmini on 15/12/2017. */

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;

public interface Database {

    EntityDataStore<Persistable> getDataStore();

    void clearDataStore();
}
