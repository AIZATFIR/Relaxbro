package com.example.relaxbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

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

        // 1. Inisialisasi komponen dari layout (activity_main.xml)
        ImageView btnPopIt = findViewById(R.id.btnPopIt);
        ImageView btnFidget2 = findViewById(R.id.btnFidget2);
        ImageView btnFidget3 = findViewById(R.id.btnFidget3);

        // 2. Set listener klik untuk pindah ke PopItActivity
        btnPopIt.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PopItActivity.class);
            startActivity(intent);
        });

        // Listener untuk fidget lainnya (bisa kamu ganti dengan Activity/Fragment lain nantinya)
        btnFidget2.setOnClickListener(v ->
                Toast.makeText(this, "Fidget 2 diklik", Toast.LENGTH_SHORT).show()
        );

        btnFidget3.setOnClickListener(v ->
                Toast.makeText(this, "Fidget 3 diklik", Toast.LENGTH_SHORT).show()
        );
    }
}
