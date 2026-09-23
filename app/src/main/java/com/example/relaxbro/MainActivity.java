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

        // 1. Tombol Pop It
        ImageView btnPopIt = findViewById(R.id.btnPopIt);
        if (btnPopIt != null) {
            btnPopIt.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PopItActivity.class);
                startActivity(intent);
            });
        }

        // 2. Tombol Play Piano di bawah gambar Piano
        Button btnPlayPiano = findViewById(R.id.btnPlayPiano);
        if (btnPlayPiano != null) {
            btnPlayPiano.setOnClickListener(v -> openPianoActivity());
        }

        // 3. Klik gambar Piano langsung
        ImageView imgPiano = findViewById(R.id.beruang);
        if (imgPiano != null) {
            imgPiano.setOnClickListener(v -> openPianoActivity());
        }

        // 4. Item Fidget lainnya
        ImageView btnFidget2 = findViewById(R.id.btnFidget2);
        if (btnFidget2 != null) {
            btnFidget2.setOnClickListener(v -> openPianoActivity());
        }

        ImageView btnFidget3 = findViewById(R.id.btnFidget3);
        if (btnFidget3 != null) {
            btnFidget3.setOnClickListener(v -> openPianoActivity());
        }
    }

    private void openPianoActivity() {
        Intent intent = new Intent(MainActivity.this, PianoActivity.class);
        startActivity(intent);
    }
}
