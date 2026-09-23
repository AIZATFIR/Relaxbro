package com.example.relaxbro;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PopItActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pop_it);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pop_it_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Logic tombol kembali ke beranda
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish()); // finish() menutup Activity dan kembali ke halaman sebelumnya

        // Contoh interaksi Pop It
        findViewById(R.id.imgPopIt).setOnClickListener(v -> 
            Toast.makeText(this, "Pop!", Toast.LENGTH_SHORT).show()
        );
    }
}
