package com.letzunite.letzunite.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.letzunite.letzunite.BuildConfig;
import com.letzunite.letzunite.LetzUnitApplication;
import com.letzunite.letzunite.data.database.LetzUniteDatabase;
import com.letzunite.letzunite.data.manager.EncryptedRequestGenerator;
import com.letzunite.letzunite.di.ApplicationContext;
import com.letzunite.letzunite.di.DatabaseInfo;
import com.letzunite.letzunite.di.PreferenceInfo;
import com.letzunite.letzunite.network.ApiService;
import com.letzunite.letzunite.security.SecureAesEncryption;
import com.letzunite.letzunite.security.SecureRsaEncryption;
import com.letzunite.letzunite.utils.AppConstants;
import com.letzunite.letzunite.utils.AppUtils;
import com.letzunite.letzunite.utils.LiveDataCallAdapterFactory;
import com.letzunite.letzunite.utils.RequestBuilder;
import com.letzunite.letzunite.utils.RevealAnimationUtil;
import com.letzunite.letzunite.utils.SharedPrefs;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Deven Singh on 4/9/2018.
 */
@Module
public class AppModule {

    @Provides
    Application provideApplication(LetzUnitApplication application) {
        return application;
    }

    @Provides
    @ApplicationContext
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    LetzUniteDatabase provideDatabase(@ApplicationContext Context context, @DatabaseInfo String databaseName) {
        return Room.databaseBuilder(context, LetzUniteDatabase.class, databaseName).build();
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DATABASE_NAME;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context, @PreferenceInfo String prefName) {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit
                .Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder, HttpLoggingInterceptor interceptor) {
//        if (sharedPrefs.isUserLoggedIn()) {
//            builder.addInterceptor(chain -> {
//                Request original = chain.request();
//                Request.Builder requestBuilder = original.newBuilder()
//                        .header("AUTH_TOKEN", sharedPrefs.getToken())
//                        .header("userId", sharedPrefs.getUserId());
//                Request request = requestBuilder.build();
//                return chain.proceed(request);
//            });
//        }
        return ((BuildConfig.DEBUG && !builder.interceptors().contains(interceptor))
                ? builder.addInterceptor(interceptor)
                : builder).addNetworkInterceptor(new StethoInterceptor()).build();
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder() {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        return new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    AssetManager provideAssetManager(@ApplicationContext Context context) {
        return context.getAssets();
    }

    @Provides
    @Singleton
    SecureRsaEncryption provideSecureRsaEncryption(AssetManager assetManager) {
        return new SecureRsaEncryption(assetManager);
    }

    @Provides
    @Singleton
    SecureAesEncryption provideSecureAesEncryption() {
        return new SecureAesEncryption();
    }

    @Provides
    @Singleton
    EncryptedRequestGenerator provideEncryptedRequestGenerator(SecureAesEncryption aesEncryption, SecureRsaEncryption rsaEncryption) {
        return new EncryptedRequestGenerator(aesEncryption, rsaEncryption);
    }

    @Provides
    @Singleton
    SharedPrefs provideSharedPrefs(SharedPreferences preferences) {
        return new SharedPrefs(preferences);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    @Singleton
    AppUtils provideAppUtils(@ApplicationContext Context context, Gson gson) {
        return new AppUtils(context, gson);
    }

    @Provides
    @Singleton
    RequestBuilder provideRequestBuilder(AppUtils appUtils) {
        return new RequestBuilder(appUtils);
    }
}
