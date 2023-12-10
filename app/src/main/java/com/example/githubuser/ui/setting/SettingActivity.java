package com.example.githubuser.ui.setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.githubuser.R;
import com.google.android.material.switchmaterial.SwitchMaterial;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SwitchMaterial switchTheme = findViewById(R.id.switch_theme);

        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "settings").build();
        SettingPreferences pref = SettingPreferences.getInstance(dataStore);

        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelFactory(pref)).get(MainViewModel.class);
        mainViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                switchTheme.setChecked(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                switchTheme.setChecked(false);
            }
        });

        switchTheme.setOnCheckedChangeListener((buttonView, isChecked) ->
                mainViewModel.saveThemeSetting(isChecked)
        );
    }
}