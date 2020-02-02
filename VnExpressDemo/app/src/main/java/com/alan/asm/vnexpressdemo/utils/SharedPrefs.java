package com.alan.asm.vnexpressdemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private static final String KEY_IS_DARK_THEME = "KEY_IS_DARK_THEME";

    private SharedPreferences preferences;

    public SharedPrefs(Context context) {
        if (context == null) throw new NullPointerException("SharedPrefs: Context is null");
        this.preferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public void saveTheme(boolean isDarkMode) {
        preferences.edit().putBoolean(KEY_IS_DARK_THEME, isDarkMode).apply();
    }

    public boolean isDarkMode() {
        return preferences.getBoolean(KEY_IS_DARK_THEME, true);
    }
}
