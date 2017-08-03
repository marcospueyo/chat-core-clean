package com.mph.chatcontrol.data;
/* Created by macmini on 03/08/2017. */

import android.provider.ContactsContract;

import java.util.Date;
import java.util.Set;

import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.OneToMany;
import io.requery.Transient;

@Entity
public abstract class AbstractPerson {

    @Key
    @Generated
    int id;

    String name;
    String email;
    Date birthday;

    @Transient
    String link; // this field is not persisted because of @Transient

}
