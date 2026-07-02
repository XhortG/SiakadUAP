package com.example.siakaduap;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.siakaduap.models.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "siakad_pref";
    private static final String KEY_ID       = "keyid";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL    = "keyemail";
    private static final String KEY_NAMA     = "keynama";
    private static final String KEY_NPM      = "keynpm";
    private static final String KEY_PRODI    = "keyprodi";
    private static final String KEY_SEMESTER = "keysemester";
    private static final String KEY_FAKULTAS = "keyfakultas";
    private static final String KEY_TAHUN_MASUK = "keytahunmasuk";
    private static final String KEY_STATUS_MAHASISWA = "keystatusmahasiswa";

    private static SharedPrefManager mInstance;
    private final Context mCtx;

    private SharedPrefManager(Context context) {
        // Gunakan applicationContext agar tidak memory leak
        mCtx = context.getApplicationContext();
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    /** Simpan data user setelah login berhasil */
    public void userLogin(User user) {
        SharedPreferences.Editor editor = getPrefs().edit();
        editor.putString(KEY_ID,       user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL,    user.getEmail());
        editor.putString(KEY_NAMA,     user.getNama_lengkap());
        editor.putString(KEY_NPM,      user.getNpm());
        editor.putString(KEY_PRODI,    user.getProdi());
        editor.putString(KEY_SEMESTER, user.getSemester());
        editor.putString(KEY_FAKULTAS, user.getFakultas());
        editor.putString(KEY_TAHUN_MASUK, user.getTahunMasuk());
        editor.putString(KEY_STATUS_MAHASISWA, user.getStatusMahasiswa());
        editor.apply();
    }

    public boolean isLoggedIn() {
        return getPrefs().getString(KEY_USERNAME, null) != null;
    }

    public String getId()       { return getPrefs().getString(KEY_ID, ""); }
    public String getUsername() { return getPrefs().getString(KEY_USERNAME, ""); }
    public String getEmail()    { return getPrefs().getString(KEY_EMAIL, ""); }
    public String getNama()     { return getPrefs().getString(KEY_NAMA, ""); }
    public String getNpm()      { return getPrefs().getString(KEY_NPM, ""); }
    public String getProdi()    { return getPrefs().getString(KEY_PRODI, ""); }
    public String getSemester() { return getPrefs().getString(KEY_SEMESTER, ""); }
    public String getFakultas() { return getPrefs().getString(KEY_FAKULTAS, ""); }
    public String getTahunMasuk() { return getPrefs().getString(KEY_TAHUN_MASUK, ""); }
    public String getStatusMahasiswa() { return getPrefs().getString(KEY_STATUS_MAHASISWA, ""); }

    public void logout() {
        getPrefs().edit().clear().apply();
    }

    private SharedPreferences getPrefs() {
        return mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }
}
