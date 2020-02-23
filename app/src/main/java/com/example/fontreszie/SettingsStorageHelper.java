package com.example.fontreszie;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;


import java.util.HashMap;
import java.util.Map;

/**
 * 设置中零散字段的存储、查询
 * Created by lintong on 2017/8/23.
 */

public class SettingsStorageHelper {
    private final static String  SP_FILE_NAME="settings_storage_pref";
    private static SharedPreferences sp;
    //只初始化一次,再使用到的时候调用
    static {
        Log.i("SettingsStorageHelper","static ");
        Context context= MyApplication.getInstance();
        sp = context.getSharedPreferences(SP_FILE_NAME,
                Context.MODE_PRIVATE);
    }

    public static final String FONT_SIZE ="fontSize";//字体

//    public static void  put(String key,String val,String pass){
//        put(key,val,false,pass);
//    }

    public static void  put(String key,String val,String password){
        val= StringUtil.encrypt(val, password);//加密value
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,val);
        editor.commit();
    }

    public static void  put(String key,String val){

        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,val);
        editor.commit();
    }

    public static  String get(String key,String defaultVal){
        String ret=sp.getString(key,defaultVal);
        return ret;
    }

    public static  String get(String key,String defaultVal,String password){
        String ret=sp.getString(key,defaultVal);

               ret= StringUtil.decrypt(ret, password);//解密value

        return ret;
    }







}
