package ar.com.despertador;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragmentRatio extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}