package com.example.relaxbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Read username extra with fallback
        username = getIntent().getStringExtra(NameInputActivity.EXTRA_USERNAME);
        if (username == null || username.trim().isEmpty()) {
            username = "Teman";
        }

        // Display formatted greeting
        TextView tvTitle = findViewById(R.id.tvBerandaTitle);
        if (tvTitle != null) {
            tvTitle.setText(getString(R.string.main_greeting, username));
        }

        // Terapkan Kustomisasi Warna User
        applyTheme();

        // 1. Tombol Mainkan Pop It -> Buka PopItActivity
        ImageView btnPopIt = findViewById(R.id.btnPopIt);
        if (btnPopIt != null) {
            btnPopIt.setOnClickListener(v -> openPopItActivity());
        }

        // 2. Tombol Play Piano & Gambar Piano -> Buka PianoActivity
        Button btnPlayPiano = findViewById(R.id.btnPlayPiano);
        if (btnPlayPiano != null) {
            btnPlayPiano.setOnClickListener(v -> openPianoActivity());
        }

        ImageView imgPiano = findViewById(R.id.beruang);
        if (imgPiano != null) {
            imgPiano.setOnClickListener(v -> openPianoActivity());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update warna jika berubah setelah dari halaman lain
        applyTheme();
    }

    private void applyTheme() {
        TextView tvTitle = findViewById(R.id.tvBerandaTitle);
        UserPreferences.applyCustomization(this, findViewById(R.id.main), tvTitle);
    }

    private void openPopItActivity() {
        Intent intent = new Intent(MainActivity.this, PopItActivity.class);
        intent.putExtra(NameInputActivity.EXTRA_USERNAME, username);
        startActivity(intent);
    }

    private void openPianoActivity() {
        Intent intent = new Intent(MainActivity.this, PianoActivity.class);
        intent.putExtra(NameInputActivity.EXTRA_USERNAME, username);
        startActivity(intent);
    }
}
