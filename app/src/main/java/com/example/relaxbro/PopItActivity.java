package com.example.relaxbro;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PopItActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private final List<Integer> soundList = new ArrayList<>();
    private final Random random = new Random();

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

        // 1. Inisialisasi SoundPool untuk memutar efek suara secara instant
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(10) // Mendukung hingga 10 suara bersamaan untuk tapping cepat
                .setAudioAttributes(audioAttributes)
                .build();

        // 2. Load variasi efek suara mp3 dari res/raw/
        soundList.add(soundPool.load(this, R.raw.minecraft_pickup, 1));
        soundList.add(soundPool.load(this, R.raw.pop_cat_meme, 1));

        // 3. Logic tombol kembali ke beranda
        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        // 4. Tracking otomatis SELURUH buletan Pop It di dalam layout
        ViewGroup rootLayout = findViewById(R.id.pop_it_root);
        if (rootLayout != null) {
            setupBubbleListeners(rootLayout);
        }
    }

    private void setupBubbleListeners(ViewGroup parent) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            
            if (view instanceof ViewGroup) {
                setupBubbleListeners((ViewGroup) view);
            } else if (view.getId() != View.NO_ID) {
                String resName = getResources().getResourceEntryName(view.getId());
                if (resName != null && resName.startsWith("bubble")) {
                    view.setOnClickListener(this::playPopAndAnimate);
                }
            }
        }
    }

    private void playPopAndAnimate(View view) {
        // Memutar salah satu suara secara ACAK (Random Cycle) dari pilihan suara mp3
        if (!soundList.isEmpty() && soundPool != null) {
            int randomSoundId = soundList.get(random.nextInt(soundList.size()));
            soundPool.play(randomSoundId, 1.0f, 1.0f, 0, 0, 1.0f);
        }

        // Efek animasi "ngepop" (mengecil lalu kembali normal)
        view.animate()
                .scaleX(0.80f)
                .scaleY(0.80f)
                .setDuration(70)
                .withEndAction(() -> view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(70).start())
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Bersihkan memori SoundPool saat Activity ditutup
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
