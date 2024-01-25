package com.example.a2048;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.a2048.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    //Binding
    ActivityMainBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        G.activity = this;

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.gameView.resetGame();
            }
        });
        updateScore();
    }

    public void updateScore() {
        binding.txtScore.setText("" + G.score);
        binding.txtBest.setText("" + G.best);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            G.preferences.edit().putInt("BEST", G.best).apply();
        }
        return super.onKeyDown(keyCode, event);
    }
}