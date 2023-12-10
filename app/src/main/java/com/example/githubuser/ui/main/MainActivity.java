package com.example.githubuser.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.githubuser.ui.setting.SettingActivity;

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

        if (binding.searchView.getText().toString().equals("")){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MainFragment(""))
                    .addToBackStack(null)
                    .commit();
        }

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
            Intent i = new Intent(this, SettingActivity.class);
            startActivity(i);
            return super.onOptionsItemSelected(item);
        } else if (item.getItemId() == R.id.menuFavorite) {
            Intent i = new Intent(this, DetailUser.class);
            startActivity(i);
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}