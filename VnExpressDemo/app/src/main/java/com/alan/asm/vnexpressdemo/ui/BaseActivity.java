package com.alan.asm.vnexpressdemo.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alan.asm.vnexpressdemo.R;
import com.alan.asm.vnexpressdemo.VnExpressDemoApp;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
    }

    protected boolean isDarkMode() {
        return VnExpressDemoApp.getInstance().getSharedPrefs().isDarkMode();
    }

    protected void saveDarkMode(boolean isDarkMode) {
        VnExpressDemoApp.getInstance().getSharedPrefs().saveTheme(isDarkMode);
    }

    protected void setTheme() {
        setTheme(isDarkMode() ? R.style.AppThemeDark : R.style.AppThemeLight);
    }
}
