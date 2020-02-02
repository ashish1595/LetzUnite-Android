package com.letzunite.letzunite.utils;

import android.util.Log;

import com.letzunite.letzunite.BuildConfig;

/**
 * Created by Deven Singh on 18 May, 2018.
 */
public class Logger {

    private static final String TAG="Logger";

    private Logger(){}

    public static void debug(String tag,String logText, Throwable t){
        if (BuildConfig.DEBUG){
         if (t==null){
             Log.d(tag,logText);
         }  else {
             Log.d(tag,logText,t);
         }
        }
    }

    public static void debug(String tag,String logText){
        debug(tag,logText,null);
    }

    public static void debug(String logText){
        debug(TAG,logText);
    }

    public static void info(String tag,String logText, Throwable t){
        if (BuildConfig.DEBUG){
            if (t==null){
                Log.i(tag,logText);
            }  else {
                Log.i(tag,logText,t);
            }
        }
    }

    public static void info(String tag,String logText){
        info(tag,logText,null);
    }

    public static void info(String logText){
        info(TAG,logText);
    }

    public static void warn(String tag,String logText, Throwable t){
        if (BuildConfig.DEBUG){
            if (t==null){
                Log.w(tag,logText);
            }  else {
                Log.w(tag,logText,t);
            }
        }
    }

    public static void warn(String tag,String logText){
        warn(tag,logText,null);
    }

    public static void warn(String logText){
        warn(TAG,logText);
    }

    public static void error(String tag,String logText, Throwable t){
        if (BuildConfig.DEBUG){
            if (t==null){
                Log.e(tag,logText);
            }  else {
                Log.e(tag,logText,t);
            }
        }
    }

    public static void error(String tag,String logText){
        error(tag,logText,null);
    }

    public static void error(String logText){
        error(TAG,logText);
    }
}
