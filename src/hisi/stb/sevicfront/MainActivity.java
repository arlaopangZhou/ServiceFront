package hisi.stb.sevicfront;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.hisilicon.android.*;

public class MainActivity extends Activity {
	private static String SEVIC = "sevic";
	private static final boolean fullscreen = true;

	private static HiDisplayManager st = null;
	private static Map<String, SetDisplayParamIntf> displaySettingMap;

	interface SetDisplayParamIntf {
		public int setDisplayParam(HiDisplayManager st, Object value);
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SevicFragement()).commit();

		if (st == null) {
			st = new HiDisplayManager();
		}

		if (displaySettingMap == null) {
			displaySettingMap = new HashMap<String, SetDisplayParamIntf>();

			displaySettingMap.put("displaymode", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayImageMode_PQ(Integer.parseInt((String) value));
				}
			});

			displaySettingMap.put("brightness", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayBright_PQ((Integer) value);
				}
			});

			displaySettingMap.put("contrast", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayContrast_PQ((Integer) value);
				}
			});

			displaySettingMap.put("chroma", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayHue_PQ((Integer) value);
				}
			});

			displaySettingMap.put("saturation", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplaySaturation_PQ((Integer) value);
				}
			});

			displaySettingMap.put("colortemp", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayColorTemperature_PQ(Integer
							.parseInt((String) value));
				}
			});

			displaySettingMap.put("hdr", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayDynamicContrast_PQ(Integer
							.parseInt((String) value));
				}
			});

			displaySettingMap.put("smartcolor", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayIntelligentColor_PQ(Integer
							.parseInt((String) value));
				}
			});

			displaySettingMap.put("sharpness", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayIntelligentColor_PQ((Integer) value);
				}
			});

			displaySettingMap.put("noisereduction", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayIntelligentColor_PQ(Integer
							.parseInt((String) value));
				}
			});

			displaySettingMap.put("filmmode", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayIntelligentColor_PQ(Integer
							.parseInt((String) value));
				}
			});

			displaySettingMap.put("reset", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayDefault_PQ();
				}
			});
		}

	}

	public void onPause() {
		super.onPause();

	}

	public static class SevicFragement extends PreferenceFragment implements
			OnPreferenceClickListener, OnPreferenceChangeListener {

		private static final String RESET_KEY = "reset";

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
				addPreferencesFromResource(R.xml.sevic_preferences);

		}

		public void setPreferenceListener(Preference p) {
			if (PreferenceGroup.class.isAssignableFrom(p.getClass())) {
				PreferenceGroup pg = (PreferenceGroup) p;
				for (int i = 0; i < pg.getPreferenceCount(); i++) {
					setPreferenceListener(pg.getPreference(i));
				}
			} else {
				p.setOnPreferenceClickListener(this);
				p.setOnPreferenceChangeListener(this);

				String key = p.getKey();
				if (key != null && key.compareTo(RESET_KEY) == 0) {
					AskDialogPreference ap = (AskDialogPreference) p;
					ap.setDescription(getResources().getString(
							R.string.reset_hint));
				}
			}
		}

		@Override
		public void onStart() {
			super.onStart();

			PreferenceScreen ps = getPreferenceScreen();
			setPreferenceListener(ps);
		}

		@Override
		public boolean onPreferenceClick(Preference pref) {
			// TODO Auto-generated method stub
			Log.e(SEVIC, pref.getTitle().toString() + " clicked");
			String key = pref.getKey();
			if (key.compareTo(RESET_KEY) == 0) {
				int ret = st.SetDisplayDefault_PQ();
				Log.e(SEVIC, "SetDisplayDefault() return " + ret);
			}
			return true;
		}

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			// TODO Auto-generated method stub
			Log.e(SEVIC, preference.getTitle().toString() + " changed to "
					+ newValue.toString());
			String key = preference.getKey();
			SetDisplayParamIntf set = displaySettingMap.get(key);
			if (set != null) {
				int ret = set.setDisplayParam(st, newValue);
				Log.e(SEVIC, "calling " + key + " return " + ret);
			}

			return true;
		}
	}
}
