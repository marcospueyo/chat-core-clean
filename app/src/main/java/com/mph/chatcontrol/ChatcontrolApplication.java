package com.mph.chatcontrol;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.support.v4.app.NotificationManagerCompat;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mph.chatcontrol.data.MessagesRepository;
import com.mph.chatcontrol.data.MessagesRepositoryImpl;
import com.mph.chatcontrol.device.database.Database;
import com.mph.chatcontrol.device.database.DatabaseImpl;
import com.mph.chatcontrol.device.notification.NotificationFactory;
import com.mph.chatcontrol.device.notification.NotificationFactoryImpl;
import com.mph.chatcontrol.device.notification.Notifications;
import com.mph.chatcontrol.device.notification.NotificationsImpl;
import com.mph.chatcontrol.device.screen.ScreenSupervisor;
import com.mph.chatcontrol.device.screen.ScreenSupervisorImpl;
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
import com.mph.chatcontrol.settings.FirebaseLogoutService;
import com.mph.chatcontrol.settings.FirebaseLogoutServiceImpl;
import com.mph.chatcontrol.utils.EventFactory;
import com.mph.chatcontrol.utils.EventFactoryImpl;
import com.mph.chatcontrol.utils.IntentFactory;
import com.mph.chatcontrol.utils.IntentFactoryImpl;
import com.mph.chatcontrol.utils.Router;
import com.mph.chatcontrol.utils.RouterImpl;

import org.greenrobot.eventbus.EventBus;

import io.requery.Persistable;
import io.requery.sql.EntityDataStore;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/* Created by Marcos on 03/08/2017. */

public class ChatcontrolApplication extends MultiDexApplication {

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
    private ScreenSupervisor mScreenSupervisor;
    private IntentFactory mIntentFactory;
    private EventFactory mEventFactory;
    private Database mDatabase;
    private Router mRouter;
    private FirebaseLogoutService mFirebaseLogoutService;
    private Context mContext;

    public EntityDataStore<Persistable> getData() {
        return getDatabase().getDataStore();
    }

    public Database getDatabase() {
        if (mDatabase == null) {
            mDatabase = new DatabaseImpl(getContext());
        }
        return mDatabase;
    }

    public DatabaseReference getFirebaseRootDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference();
    }

    public Router getRouter() {
        if (mRouter == null) {
            mRouter = new RouterImpl(EventBus.getDefault(), getEventFactory());
        }
        return mRouter;
    }

    public EventFactory getEventFactory() {
        if (mEventFactory == null) {
            mEventFactory = new EventFactoryImpl();
        }
        return mEventFactory;
    }

    public FirebaseLogoutService getFirebaseLogoutService() {
        if (mFirebaseLogoutService == null) {
            mFirebaseLogoutService = new FirebaseLogoutServiceImpl(getFirebaseAuthInstance());
        }
        return mFirebaseLogoutService;
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
            mFirebaseAuthData = new FirebaseAuthDataImpl(getFirebaseAuthInstance());
        }
        return mFirebaseAuthData;
    }

    private FirebaseAuth getFirebaseAuthInstance() {
        return FirebaseAuth.getInstance();
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
                    NotificationManagerCompat.from(getContext()),
                    getSharedPreferencesRepository(getContext()));
        }
        return mNotifications;
    }

    public NotificationFactory getNotificationFactory() {
        if (mNotificationFactory == null) {
            mNotificationFactory = new NotificationFactoryImpl(getContext(), getResources());
        }
        return mNotificationFactory;
    }

    public ScreenSupervisor getScreenSupervisor() {
        if (mScreenSupervisor == null) {
            mScreenSupervisor = new ScreenSupervisorImpl(
                    getSharedPreferencesRepository(getContext()));
        }
        return mScreenSupervisor;
    }

    public IntentFactory getIntentFactory() {
        if (mIntentFactory == null) {
            mIntentFactory = new IntentFactoryImpl(getContext());
        }
        return mIntentFactory;
    }

    public Context getContext() {
        if (mContext == null) {
            mContext = getApplicationContext();
        }
        return mContext;
    }
}
