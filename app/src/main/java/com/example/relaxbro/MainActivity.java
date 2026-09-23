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

        // 1. Inisialisasi button pindah activity
        ImageView btnPopIt = findViewById(R.id.btnPopIt);
        Button btnPindah = findViewById(R.id.btnPindah);
        // 2. Klik Pop It Toy Button untuk berpindah ke PopItActivity
        btnPopIt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PopItActivity.class);
            startActivity(intent);
        });
        btnPindah.setOnClickListener(v -> {
            // 3. Intent(Activity_Saat_Ini.this, Activity_Tujuan.class)
            Intent intent = new Intent(MainActivity.this, PianoActivity.class);
            startActivity(intent);
        });

        // 3. Inisialisasi Piano / Fidget 2 Button
        ImageView btnFidget2 = findViewById(R.id.btnFidget2);
        if (btnFidget2 != null) {
            btnFidget2.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PianoActivity.class);
                startActivity(intent);
            });
        }

        ImageView btnFidget3 = findViewById(R.id.btnFidget3);
        if (btnFidget3 != null) {
            btnFidget3.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PianoActivity.class);
                startActivity(intent);
            });
        }
    }
}
