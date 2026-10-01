package com.example.relaxbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private String username;
    private TextView tvTitle;
    private EditText etUsernameInput;

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

        // 1. Baca Username
        username = getIntent().getStringExtra(NameInputActivity.EXTRA_USERNAME);
        if (username == null || username.trim().isEmpty()) {
            username = UserPreferences.getCurrentUsername(this);
        }

        tvTitle = findViewById(R.id.tvBerandaTitle);
        etUsernameInput = findViewById(R.id.editTextText);

        updateGreetingHeader();

        // Terapkan Kustomisasi Tema
        applyTheme();

        // 2. Listener Tombol Mainkan Pop It -> Buka PopItActivity
        ImageView btnPopIt = findViewById(R.id.btnPopIt);
        if (btnPopIt != null) {
            btnPopIt.setOnClickListener(v -> openPopItActivity());
        }

        // 3. Listener Tombol Play Piano & Gambar Piano -> Buka PianoActivity
        Button btnPlayPiano = findViewById(R.id.btnPlayPiano);
        if (btnPlayPiano != null) {
            btnPlayPiano.setOnClickListener(v -> openPianoActivity());
        }

        ImageView imgPiano = findViewById(R.id.beruang);
        if (imgPiano != null) {
            imgPiano.setOnClickListener(v -> openPianoActivity());
        }

        // 4. Listener Submit Input Username (button5)
        Button btnSubmitName = findViewById(R.id.button5);
        if (btnSubmitName != null) {
            btnSubmitName.setOnClickListener(v -> submitNewUsername());
        }

        // 5. Listener Pilihan Warna Tema
        Button btnClassicWood = findViewById(R.id.button2); // Coklat Classic
        if (btnClassicWood != null) {
            btnClassicWood.setOnClickListener(v -> changeTheme("#A67C52", "Coklat Classic"));
        }

        Button btnSageGreen = findViewById(R.id.button); // Hijau Sage
        if (btnSageGreen != null) {
            btnSageGreen.setOnClickListener(v -> changeTheme("#9CAF88", "Hijau Sage"));
        }

        Button btnTranquilBlue = findViewById(R.id.button4); // Biru Tenang
        if (btnTranquilBlue != null) {
            btnTranquilBlue.setOnClickListener(v -> changeTheme("#191970", "Biru Tenang"));
        }
    }

    private void updateGreetingHeader() {
        if (tvTitle != null) {
            tvTitle.setText(getString(R.string.main_greeting, username));
        }
    }

    private void submitNewUsername() {
        if (etUsernameInput == null) return;

        String newName = etUsernameInput.getText().toString().trim();
        if (newName.isEmpty()) {
            etUsernameInput.setError("Nama tidak boleh kosong!");
            return;
        }

        this.username = newName;
        String currentBg = UserPreferences.getBgColor(this, username);
        String currentFont = UserPreferences.getFontColor(this, username);

        UserPreferences.saveUserCustomization(this, username, currentBg, currentFont, "Custom");
        updateGreetingHeader();
        etUsernameInput.setText("");
    }

    private void changeTheme(String bgColorHex, String themeName) {
        UserPreferences.saveUserCustomization(this, username, bgColorHex, "#FFFFFF", themeName);
        applyTheme();
        Toast.makeText(this, "Tema diubah ke " + themeName, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        applyTheme();
    }

    private void applyTheme() {
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
