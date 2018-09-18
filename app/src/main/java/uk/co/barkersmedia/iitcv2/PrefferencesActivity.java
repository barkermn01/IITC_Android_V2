package uk.co.barkersmedia.iitcv2;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class PrefferencesActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new HomePreffernceFragment()).commit();
    }

    public static class HomePreffernceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs_home);
        }
    }
}
