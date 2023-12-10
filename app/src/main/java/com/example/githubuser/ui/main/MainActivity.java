package com.example.githubuser.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.githubuser.R;
import com.example.githubuser.databinding.ActivityMainBinding;
import com.example.githubuser.ui.detail.DetailUser;
import com.example.githubuser.ui.setting.MainViewModel;
import com.example.githubuser.ui.setting.SettingActivity;
import com.example.githubuser.ui.setting.SettingPreferences;
import com.example.githubuser.ui.setting.ViewModelFactory;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private String title = "Aplikasi Github User";
    private void setActionBarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.topAppBar);
        setActionBarTitle(title);

        /*if (binding.searchView.getText().toString().equals("")){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment(""))
                    .addToBackStack(null)
                    .commit();
        }*/
        RxDataStore<Preferences> dataStore = new RxPreferenceDataStoreBuilder(this, "theme_setting").build();
        SettingPreferences pref = SettingPreferences.getInstance(dataStore);
        MainViewModel mainViewModel = new ViewModelProvider(this, new ViewModelFactory(pref)).get(MainViewModel.class);
        mainViewModel.getThemeSettings().observe(this, isDarkModeActive -> {
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        binding.searchView.setupWithSearchBar(binding.searchBar);
        binding.searchView.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                binding.searchBar.setText(binding.searchView.getText());
                binding.searchView.hide();
                if (binding.searchView.getText().toString().equals("")){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MainFragment("a"))
                            .addToBackStack(null)
                            .commit();
                }else{
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new MainFragment(binding.searchView.getText().toString()))
                            .addToBackStack(null)
                            .commit();
                }

                return false;
            }
        });


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new MainFragment(""))
                        .addToBackStack(null)
                        .commit();
            }
        }, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuPengaturan) {
            Intent MenuSetting = new Intent(this, SettingActivity.class);
            startActivity(MenuSetting);
            return super.onOptionsItemSelected(item);
        } else if (item.getItemId() == R.id.menuFavorite) {
            Intent i = new Intent(this, DetailUser.class);
            startActivity(i);
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}