package com.example.learningapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

public class LanguageManager {
    private Context context;
    private SharedPreferences sharedPreferences;

    public LanguageManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("LANG_PREFS", Context.MODE_PRIVATE);
    }

    public void updateResource(String code) {
        Locale locale = new Locale(code);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        setLang(code); // Lưu lại vào bộ nhớ
    }

    public String getLang() {
        return sharedPreferences.getString("lang", "vi"); // Mặc định là 'vi'
    }

    public void setLang(String code) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("lang", code);
        editor.apply();
    }
}