package com.example.absensikaryawan;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.absensikaryawan.model.login.LoginData;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID = "user_id";
    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String NIP = "nip";
    public static final String KETERANGAN = "keterangan";
    public static final String WAKTU = "waktu";
    public static final String TANGGAL = "tanggal";
    public static final String ALAMAT_IP = "alamat_ip";

    public SessionManager (Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }


    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getUserId());
        editor.putString(USERNAME, user.getUsername());
        editor.putString(NAME, user.getName());
        editor.putString(NIP,user.getNip());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID,null));
        user.put(USERNAME, sharedPreferences.getString(USERNAME,null));
        user.put(NAME, sharedPreferences.getString(NAME,null));
        user.put(NIP, sharedPreferences.getString(NIP, null));
        user.put(KETERANGAN, sharedPreferences.getString(KETERANGAN, null));
        user.put(WAKTU, sharedPreferences.getString(WAKTU, null));
        user.put(TANGGAL, sharedPreferences.getString(TANGGAL, null));
        user.put(ALAMAT_IP, sharedPreferences.getString(ALAMAT_IP, null));
        return user;
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
