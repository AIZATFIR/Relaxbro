package com.example.relaxbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

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

        // 1. Tombol Pop It (btnPopIt dan btnFidget2) -> Buka PopItActivity
        ImageView btnPopIt = findViewById(R.id.btnPopIt);
        if (btnPopIt != null) {
            btnPopIt.setOnClickListener(v -> openPopItActivity());
        }

        ImageView btnFidget2 = findViewById(R.id.btnFidget2);
        if (btnFidget2 != null) {
            btnFidget2.setOnClickListener(v -> openPopItActivity());
        }

        // 2. Tombol Play Piano dan Gambar Piano -> Buka PianoActivity
        Button btnPlayPiano = findViewById(R.id.btnPlayPiano);
        if (btnPlayPiano != null) {
            btnPlayPiano.setOnClickListener(v -> openPianoActivity());
        }

        ImageView imgPiano = findViewById(R.id.beruang);
        if (imgPiano != null) {
            imgPiano.setOnClickListener(v -> openPianoActivity());
        }
    }

    private void openPopItActivity() {
        Intent intent = new Intent(MainActivity.this, PopItActivity.class);
        startActivity(intent);
    }

    private void openPianoActivity() {
        Intent intent = new Intent(MainActivity.this, PianoActivity.class);
        startActivity(intent);
    }
}
