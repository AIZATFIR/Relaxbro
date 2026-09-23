package com.example.relaxbro;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.HashMap;
import java.util.Map;

public class PianoActivity extends AppCompatActivity {

    private SoundPool soundPool;

    // Map untuk menyimpan Sound ID dan Pitch untuk setiap tombol piano
    private final Map<Integer, KeySoundInfo> keySoundMap = new HashMap<>();

    private static class KeySoundInfo {
        final int soundId;
        final float pitch;

        KeySoundInfo(int soundId, float pitch) {
            this.soundId = soundId;
            this.pitch = pitch;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_piano);

        // Arahkan tombol volume HP langsung mengontrol volume Musik/Media agar suara piano maksimal
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.piano_root), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Inisialisasi SoundPool dengan USAGE_MEDIA & CONTENT_TYPE_MUSIC (suara jernih & kencang)
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(16) // 16 stream bersamaan agar tuts tidak terputus mendadak
                .setAudioAttributes(audioAttributes)
                .build();

        // 2. Load file nada piano .wav (note_do, re, mi, fa, sol, la, si, do_tinggi)
        loadPianoSounds();

        // 3. Tombol Kembali
        ImageButton btnBack = findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // 4. Hubungkan event click ke setiap tuts piano
        setupPianoKeyListeners();
    }

    private int loadRawSound(String soundName) {
        int resId = getResources().getIdentifier(soundName, "raw", getPackageName());
        if (resId != 0 && soundPool != null) {
            return soundPool.load(this, resId, 1);
        }
        return 0;
    }

    private void loadPianoSounds() {
        // Load file .wav murni nada piano dari folder res/raw
        int soundDo = loadRawSound("note_do");
        if (soundDo == 0) {
            soundDo = loadRawSound("do");
        }
        int soundRe = loadRawSound("re");
        int soundMi = loadRawSound("mi");
        int soundFa = loadRawSound("fa");
        int soundSol = loadRawSound("sol");
        int soundLa = loadRawSound("la");
        int soundSi = loadRawSound("si");
        int soundDoTinggi = loadRawSound("do_tinggi");

        // Semitone multiplier (1/12 octave) untuk tuts hitam (sharps)
        final float sharpPitch = 1.0595f;

        // Pemetaan Tombol Tuts Putih (Do, Re, Mi, Fa, Sol, La, Si, Do Tinggi)
        keySoundMap.put(R.id.btnKeyC, new KeySoundInfo(soundDo, 1.0f));
        keySoundMap.put(R.id.btnKeyD, new KeySoundInfo(soundRe, 1.0f));
        keySoundMap.put(R.id.btnKeyE, new KeySoundInfo(soundMi, 1.0f));
        keySoundMap.put(R.id.btnKeyF, new KeySoundInfo(soundFa, 1.0f));
        keySoundMap.put(R.id.btnKeyG, new KeySoundInfo(soundSol, 1.0f));
        keySoundMap.put(R.id.btnKeyA, new KeySoundInfo(soundLa, 1.0f));
        keySoundMap.put(R.id.btnKeyB, new KeySoundInfo(soundSi, 1.0f));
        keySoundMap.put(R.id.btnKeyC2, new KeySoundInfo(soundDoTinggi, 1.0f));

        // Pemetaan Tombol Tuts Hitam (Sharps: C#, D#, F#, G#, A#)
        keySoundMap.put(R.id.btnKeyCSharp, new KeySoundInfo(soundDo, sharpPitch));
        keySoundMap.put(R.id.btnKeyDSharp, new KeySoundInfo(soundRe, sharpPitch));
        keySoundMap.put(R.id.btnKeyFSharp, new KeySoundInfo(soundFa, sharpPitch));
        keySoundMap.put(R.id.btnKeyGSharp, new KeySoundInfo(soundSol, sharpPitch));
        keySoundMap.put(R.id.btnKeyASharp, new KeySoundInfo(soundLa, sharpPitch));
    }

    private void setupPianoKeyListeners() {
        int[] keyIds = {
                R.id.btnKeyC, R.id.btnKeyD, R.id.btnKeyE, R.id.btnKeyF,
                R.id.btnKeyG, R.id.btnKeyA, R.id.btnKeyB, R.id.btnKeyC2,
                R.id.btnKeyCSharp, R.id.btnKeyDSharp, R.id.btnKeyFSharp,
                R.id.btnKeyGSharp, R.id.btnKeyASharp
        };

        for (int keyId : keyIds) {
            View keyView = findViewById(keyId);
            if (keyView != null) {
                keyView.setOnClickListener(v -> playKeySound(keyId, v));
            }
        }
    }

    private void playKeySound(int keyId, View view) {
        KeySoundInfo soundInfo = keySoundMap.get(keyId);
        if (soundInfo != null && soundInfo.soundId != 0 && soundPool != null) {
            // Memutar dengan volume penuh (1.0f, 1.0f) dan priority 1
            soundPool.play(soundInfo.soundId, 1.0f, 1.0f, 1, 0, soundInfo.pitch);
        }

        // Animasi halus saat tuts ditekan
        view.animate()
                .scaleY(0.90f)
                .setDuration(60)
                .withEndAction(() -> view.animate().scaleY(1.0f).setDuration(60).start())
                .start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
}
