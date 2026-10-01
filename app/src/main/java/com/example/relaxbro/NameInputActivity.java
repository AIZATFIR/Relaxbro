package com.example.relaxbro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class NameInputActivity extends AppCompatActivity {

    public static final String EXTRA_USERNAME = "extra_username";

    private EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_name_input);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.name_input_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etName = findViewById(R.id.etName);
        Button btnMulai = findViewById(R.id.btnMulai);

        // Pre-fill nama dari database lokal jika sudah pernah diisi sebelumnya
        String savedUser = UserPreferences.getCurrentUsername(this);
        if (etName != null && !savedUser.equalsIgnoreCase("Guest")) {
            etName.setText(savedUser);
            etName.setSelection(savedUser.length());
        }

        if (btnMulai != null) {
            btnMulai.setOnClickListener(v -> submitName());
        }
    }

    private void submitName() {
        if (etName == null) return;

        String name = etName.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Nama tidak boleh kosong!");
            return;
        }

        if (name.length() > 20) {
            etName.setError("Maksimal 20 karakter!");
            return;
        }

        // Simpan ke SharedPreferences & Tampilkan Toast
        UserPreferences.saveCurrentUsername(this, name);
        Toast.makeText(this, "Selamat datang, " + name + "!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(NameInputActivity.this, MainActivity.class);
        intent.putExtra(EXTRA_USERNAME, name);
        startActivity(intent);
        finish();
    }
}
