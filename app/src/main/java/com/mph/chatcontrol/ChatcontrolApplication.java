package com.mph.chatcontrol;

import android.app.Application;
import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationManagerCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.data.MessagesRepositoryImpl;
import com.mph.chatcontrol.data.Models;
import com.mph.chatcontrol.device.notification.NotificationFactory;
import com.mph.chatcontrol.device.notification.NotificationFactoryImpl;
import com.mph.chatcontrol.device.notification.Notifications;
import com.mph.chatcontrol.device.notification.NotificationsImpl;
import com.mph.chatcontrol.login.FirebaseAuthData;
import com.mph.chatcontrol.login.FirebaseAuthDataImpl;
import com.mph.chatcontrol.login.SharedPreferencesRepositoryImpl;
import com.mph.chatcontrol.login.contract.SharedPreferencesRepository;
import com.mph.chatcontrol.network.ChatcontrolService;
import com.mph.chatcontrol.network.FirebaseDatabaseData;
import com.mph.chatcontrol.network.FirebaseDatabaseDataImpl;
import com.mph.chatcontrol.network.TokenService;
import com.mph.chatcontrol.network.TokenServiceImpl;
import com.mph.chatcontrol.network.message.MessageFirebaseService;
import com.mph.chatcontrol.network.message.MessageFirebaseServiceImpl;
import com.mph.chatcontrol.network.message.MessageRestService;
import com.mph.chatcontrol.network.message.MessageRestServiceImpl;
import com.mph.chatcontrol.network.message.MessageService;
import com.mph.chatcontrol.network.message.MessageServiceImpl;
import com.mph.chatcontrol.network.message.RestMessageToMessageMapper;

import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* Created by Marcos on 03/08/2017. */

public class ChatcontrolApplication extends Application {

    @SuppressWarnings("unused")
    private static final String API_URL = "https://us-central1-triptips-50da0.cloudfunctions.net/";

//    @SuppressWarnings("unused")
//    private static final String API_URL = "http://localhost:5000/triptips-50da0/us-central1/";

    private SharedPreferencesRepository mSharedPreferencesRepository;
    private FirebaseAuthData mFirebaseAuthData;
    private FirebaseDatabaseData mFirebaseDatabaseData;

    private MessagesRepository messagesRepository;
    private MessageService messageService;
    private MessageFirebaseService messageFirebaseService;
    private MessageRestService messageRestService;

    private ChatcontrolService service;
    private TokenService mTokenService;
    private Notifications mNotifications;
    private NotificationFactory mNotificationFactory;

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
            DatabaseSource source = new DatabaseSource(this, Models.DEFAULT, 8);
            if (BuildConfig.DEBUG) {
                // use this in development mode to drop and recreate the tables on every upgrade
                source.setTableCreationMode(TableCreationMode.DROP_CREATE);
            }
            Configuration configuration = source.getConfiguration();
            dataStore = new EntityDataStore<>(configuration);
        }
        return dataStore;
    }

    public DatabaseReference getFirebaseRootDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public SharedPreferencesRepository getSharedPreferencesRepository(Context context) {
        if (mSharedPreferencesRepository == null) {
            mSharedPreferencesRepository =
                    new SharedPreferencesRepositoryImpl(
                            PreferenceManager.getDefaultSharedPreferences(context),
                            getFirebaseAuthData());
        }
        return mSharedPreferencesRepository;
    }

    public FirebaseAuthData getFirebaseAuthData() {
        if (mFirebaseAuthData == null) {
            mFirebaseAuthData = new FirebaseAuthDataImpl(FirebaseAuth.getInstance());
        }
        return mFirebaseAuthData;
    }

    public FirebaseDatabaseData getFirebaseDatabaseData() {
        if (mFirebaseDatabaseData == null) {
            mFirebaseDatabaseData = new FirebaseDatabaseDataImpl(getFirebaseRootDatabaseReference());
        }
        return mFirebaseDatabaseData;
    }

    public MessagesRepository getMessagesRepository(Context context) {
        if (messagesRepository == null) {
            messagesRepository = new MessagesRepositoryImpl(
                    getSharedPreferencesRepository(context),
                    getData(),
                    new RestMessageToMessageMapper(),
                    getMessageService());
        }
        return messagesRepository;
    }

    public MessageService getMessageService() {
        if (messageService == null) {
            messageService = new MessageServiceImpl(
                    getMessageFirebaseService(),
                    getMessageRestService(),
                    new RestMessageToMessageMapper());
        }
        return messageService;
    }

    private MessageFirebaseService getMessageFirebaseService() {
        if (messageFirebaseService == null) {
            messageFirebaseService = new MessageFirebaseServiceImpl(getFirebaseDatabaseData());
        }
        return messageFirebaseService;
    }

    private MessageRestService getMessageRestService() {
        if (messageRestService == null) {
            messageRestService = new MessageRestServiceImpl(getService());
        }
        return messageRestService;
    }

    public ChatcontrolService getService() {
        if (service == null) {
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            service = retrofit.create(ChatcontrolService.class);
        }
        return service;
    }

    public TokenService getTokenService(Context context) {
        if (mTokenService == null) {
            mTokenService = new TokenServiceImpl(getService(), getSharedPreferencesRepository(context));
        }
        return mTokenService;
    }

    public Notifications getNotifications() {
        if (mNotifications == null) {
            mNotifications = new NotificationsImpl(
                    NotificationManagerCompat.from(getApplicationContext()));
        }
        return mNotifications;
    }

    public NotificationFactory getNotificationFactory() {
        if (mNotificationFactory == null) {
            mNotificationFactory = new NotificationFactoryImpl(getApplicationContext(), getResources());
        }
        return mNotificationFactory;
    }
}
