package com.example.relaxbro;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class UserPreferences {

    private static final String PREF_NAME = "RelaxbroUserPrefs";
    private static final String KEY_CURRENT_USER = "current_username";

    // Default Warna (Cokelat Wood & Putih)
    public static final String DEFAULT_BG_COLOR = "#A67C52";
    public static final String DEFAULT_FONT_COLOR = "#FFFFFF";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // 1. Simpan Nama User yang Sedang Aktif
    public static void saveCurrentUsername(Context context, String username) {
        getPrefs(context).edit().putString(KEY_CURRENT_USER, username).apply();
    }

    // 2. Ambil Nama User yang Sedang Aktif
    public static String getCurrentUsername(Context context) {
        return getPrefs(context).getString(KEY_CURRENT_USER, "Guest");
    }

    // 3. Simpan Kustomisasi Warna Per Username ke Database Lokal (SharedPreferences)
    public static void saveUserCustomization(Context context, String username, String bgColorHex, String fontColorHex, String radioChoice) {
        if (username == null || username.trim().isEmpty()) {
            username = "Guest";
        }

        username = username.trim();
        saveCurrentUsername(context, username);

        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString(username + "_bg_color", bgColorHex);
        editor.putString(username + "_font_color", fontColorHex);
        editor.putString(username + "_radio_choice", radioChoice);
        editor.apply();

        // Toast Notification Notif Masuk Nama & Customization
        Toast.makeText(context, "Selamat datang, " + username + "! Customization disimpan.", Toast.LENGTH_SHORT).show();
    }

    // 4. Ambil Warna Background Tersimpan per User
    public static String getBgColor(Context context, String username) {
        return getPrefs(context).getString(username + "_bg_color", DEFAULT_BG_COLOR);
    }

    // 5. Ambil Warna Font Tersimpan per User
    public static String getFontColor(Context context, String username) {
        return getPrefs(context).getString(username + "_font_color", DEFAULT_FONT_COLOR);
    }

    // 6. Ambil Opsi RadioButton Tersimpan per User
    public static String getRadioChoice(Context context, String username) {
        return getPrefs(context).getString(username + "_radio_choice", "Wood");
    }

    // 7. Dynamic UI Engine: Terapkan Warna Background & Font Secara Otomatis ke Layout
    public static void applyCustomization(Activity activity, ViewGroup rootLayout, TextView titleTextView) {
        if (activity == null) return;

        Context context = activity.getApplicationContext();
        String username = getCurrentUsername(context);
        String bgColorHex = getBgColor(context, username);
        String fontColorHex = getFontColor(context, username);

        try {
            int bgColor = Color.parseColor(bgColorHex);
            int fontColor = Color.parseColor(fontColorHex);

            if (rootLayout != null) {
                rootLayout.setBackgroundColor(bgColor);
            }

            if (titleTextView != null) {
                titleTextView.setTextColor(fontColor);
            }

            // Opsional: Jika ada ImageView background universal, atur transparansi atau tint jika perlu
            View bgUniversal = activity.findViewById(R.id.bgUniversal);
            if (bgUniversal == null) {
                bgUniversal = activity.findViewById(R.id.bgUniversalPopIt);
            }

            if (bgUniversal != null && !bgColorHex.equalsIgnoreCase(DEFAULT_BG_COLOR)) {
                bgUniversal.setAlpha(0.3f); // Membuat background gambar soft saat memakai warna custom
            } else if (bgUniversal != null) {
                bgUniversal.setAlpha(1.0f);
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
