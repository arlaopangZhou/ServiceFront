package hisi.stb.sevicfront;

import java.util.HashMap;
import java.util.Map;
import com.hisilicon.android.HiDisplayManager;
import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint({ "NewApi", "ResourceAsColor" })
public class MainActivity extends Activity implements OnClickListener,
		OnFocusChangeListener {
	private static String SEVIC = "sevic";
	private static final boolean fullscreen = true;

	private VideoBean videoBean;
	private TextView disModle;
	private TextView brightNess;
	private TextView colorTemp;
	private TextView dynamicCon;
	private TextView intellColor;
	private TextView sharpen;
	private TextView denoise;
	private TextView reset;

	private String[] disModleStrings;
	private String[] colorTempStrings;
	private String[] dynamicConStrings;
	private String[] intellColorStrings;
	private String[] denoiseStrings;
	private String[] resetStrings;

	private String[] disModleValueStrings;
	private String[] colorTempValueStrings;
	private String[] dynamicConValueStrings;
	private String[] intellColorValueStrings;
	private String[] denoiseValueStrings;

	private RelativeLayout displayRel;
	private RelativeLayout brightnessRel;
	private RelativeLayout colorTempRel;
	private RelativeLayout hdrRel;
	private RelativeLayout smartRel;
	private RelativeLayout sharpenRel;
	private RelativeLayout noiseRel;
	private RelativeLayout resetRel;

	private static HiDisplayManager st = null;
	private static Map<String, SetDisplayParamIntf> displaySettingMap;

	interface SetDisplayParamIntf {
		public int setDisplayParam(HiDisplayManager st, Object value);
	};

	/**
	 * 选项代号 1:图像模式设置 2:亮度 3:色温 4:动态对比度 5:智能颜色管理 6:锐化强度 7:降噪使能 8:复位
	 */
	private int valueInt;

	private int showValue;// 每项数据位置坐标，列表中的每组数据是数组形式保存，该变量是每项数组的坐标。
	private int defaultBrightVolume = 50; // 默认的亮度锐化度显示值
	private int defaultSharpenVolume = 50; // 默认的锐化度显示值

	
/**
 * 拦截左右按键监听事件，首先判断获取焦点的是哪个条目，之后通过左右按键操作操作对应的数组.	
 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		// TODO Auto-generated method stub
		int keyCode = event.getKeyCode();
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getRepeatCount() == 0) {
			if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {

				switch (valueInt) {
				case 1:
					if (showValue > 0) {
						showValue--;
					} else {
						showValue = disModleStrings.length - 1;
					}

					if (showValue < disModleStrings.length && showValue != -1) {
						disModle.setText(disModleStrings[showValue]);
					}
					break;
				case 2:
					if (defaultBrightVolume > 0 && defaultBrightVolume <= 100) {
						defaultBrightVolume--;
					}
					brightNess.setText(String.valueOf(defaultBrightVolume));
					break;
				case 3:
					if (showValue > 0) {
						showValue--;
					} else {
						showValue = colorTempStrings.length - 1;
					}
					if (showValue < colorTempStrings.length && showValue != -1) {
						colorTemp.setText(colorTempStrings[showValue]);
					}
					break;
				case 4:
					if (showValue > 0) {
						showValue--;
					} else {
						showValue = dynamicConStrings.length - 1;
					}
					if (showValue < dynamicConStrings.length && showValue != -1) {
						dynamicCon.setText(dynamicConStrings[showValue]);
					}
					break;
				case 5:
					if (showValue > 0) {
						showValue--;
					} else {
						showValue = intellColorStrings.length - 1;
					}
					if (showValue < intellColorStrings.length
							&& showValue != -1) {
						intellColor.setText(intellColorStrings[showValue]);
					}
					break;
				case 6:
					if (defaultSharpenVolume > 0 && defaultSharpenVolume <= 100) {
						defaultSharpenVolume--;
					}
					sharpen.setText(String.valueOf(defaultSharpenVolume));
					break;
				case 7:
					if (showValue > 0) {
						showValue--;
					} else {
						showValue = denoiseStrings.length - 1;
					}
					if (showValue < denoiseStrings.length && showValue != -1) {
						denoise.setText(denoiseStrings[showValue]);
					}
					break;
				case 8:
					if (showValue > 0) {
						showValue--;
					} else {
						showValue = resetStrings.length - 1;
					}
					if (showValue < resetStrings.length && showValue != -1) {
						reset.setText(resetStrings[showValue]);
					}
					break;
				}
			}
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			switch (valueInt) {
			case 1:
				if (showValue < disModleStrings.length - 1) {
					showValue++;
				} else {
					showValue = 0;
				}
				if (showValue < disModleStrings.length && showValue != -1) {
					disModle.setText(disModleStrings[showValue]);
				}
				break;
			case 2:
				if (defaultBrightVolume >= 0 && defaultBrightVolume < 100) {
					defaultBrightVolume++;
				}
				brightNess.setText(String.valueOf(defaultBrightVolume));
				break;
			case 3:
				if (showValue < colorTempStrings.length - 1) {
					showValue++;
				} else {
					showValue = 0;
				}
				if (showValue < colorTempStrings.length && showValue != -1) {
					colorTemp.setText(colorTempStrings[showValue]);
				}
				break;
			case 4:
				if (showValue < dynamicConStrings.length - 1) {
					showValue++;
				} else {
					showValue = 0;
				}
				if (showValue < dynamicConStrings.length && showValue != -1) {
					dynamicCon.setText(dynamicConStrings[showValue]);
				}
				break;
			case 5:
				if (showValue < intellColorStrings.length - 1) {
					showValue++;
				} else {
					showValue = 0;
				}
				if (showValue < intellColorStrings.length && showValue != -1) {
					intellColor.setText(intellColorStrings[showValue]);
				}
				break;
			case 6:
				if (defaultSharpenVolume >= 0 && defaultSharpenVolume < 100) {
					defaultSharpenVolume++;
				}
				sharpen.setText(String.valueOf(defaultSharpenVolume));
				break;
			case 7:
				if (showValue < denoiseStrings.length - 1) {
					showValue++;
				} else {
					showValue = 0;
				}
				if (showValue < denoiseStrings.length && showValue != -1) {
					denoise.setText(denoiseStrings[showValue]);
				}
				break;
			case 8:
				if (showValue < resetStrings.length - 1) {
					showValue++;
				} else {
					showValue = 0;
				}
				if (showValue < resetStrings.length && showValue != -1) {
					reset.setText(resetStrings[showValue]);
				}
				break;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_setting_service);
		videoBean = new VideoBean();
		initView();
//		initData();

		WindowManager.LayoutParams wlParams = getWindow().getAttributes();
		wlParams.x = 300;
		wlParams.gravity = Gravity.CENTER;
		getWindow().setAttributes(wlParams);

		// getFragmentManager().beginTransaction()
		// .replace(android.R.id.content, new SevicFragement()).commit();

		if (st == null) {
			st = new HiDisplayManager();
		}

		if (displaySettingMap == null) {
			displaySettingMap = new HashMap<String, SetDisplayParamIntf>();

			displaySettingMap.put("displaymode", new SetDisplayParamIntf() {
				@Override
				public int setDisplayParam(HiDisplayManager st, Object value) {
					return st.SetDisplayImageMode_PQ(Integer
							.parseInt((String) value));
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

	/**
	 * 点击确定后调用该方法保存到配置中
	 * 
	 * @param key
	 *            Preference对应的键
	 * @param newValue
	 *            键对应的值
	 */
	public void savePreference(String key, String newValue) {
		// String key = preference.getKey();
		SetDisplayParamIntf set = displaySettingMap.get(key);
		if (set != null) {
			int ret = set.setDisplayParam(st, newValue);
			Log.e(SEVIC, "calling " + key + " return " + ret);
		}
	}

	private void initData() {
		Resources resources = getResources();
		disModleStrings = resources.getStringArray(R.array.display_mode_array);
		colorTempStrings = resources.getStringArray(R.array.color_temp_array);
		dynamicConStrings = resources.getStringArray(R.array.hdr_array);
		intellColorStrings = resources
				.getStringArray(R.array.smart_color_array);
		denoiseStrings = resources.getStringArray(R.array.onoff_array);
		resetStrings = resources.getStringArray(R.array.reset);// 复位

		disModleValueStrings = resources
				.getStringArray(R.array.display_mode_value_array);
		colorTempValueStrings = resources
				.getStringArray(R.array.color_temp_value_array);
		dynamicConValueStrings = resources
				.getStringArray(R.array.hdr_value_array);
		intellColorValueStrings = resources
				.getStringArray(R.array.smart_color_value_array);
		denoiseValueStrings = resources
				.getStringArray(R.array.onoff_value_array);
		// initValueText();
	}

	/**
	 * 初始化界面
	 */
	private void initView() {
//初始化每个选项的数组
		Resources resources = getResources();
		disModleStrings = resources.getStringArray(R.array.display_mode_array);
		colorTempStrings = resources.getStringArray(R.array.color_temp_array);
		dynamicConStrings = resources.getStringArray(R.array.hdr_array);
		intellColorStrings = resources
				.getStringArray(R.array.smart_color_array);
		denoiseStrings = resources.getStringArray(R.array.onoff_array);
		resetStrings = resources.getStringArray(R.array.reset);// 复位
		disModleValueStrings = resources
				.getStringArray(R.array.display_mode_value_array);
		colorTempValueStrings = resources
				.getStringArray(R.array.color_temp_value_array);
		dynamicConValueStrings = resources
				.getStringArray(R.array.hdr_value_array);
		intellColorValueStrings = resources
				.getStringArray(R.array.smart_color_value_array);
		denoiseValueStrings = resources
				.getStringArray(R.array.onoff_value_array);
//初始化包含每个选项的布局
		displayRel = (RelativeLayout) findViewById(R.id.display_mode_rela);
		brightnessRel = (RelativeLayout) findViewById(R.id.brightness_rela);
		colorTempRel = (RelativeLayout) findViewById(R.id.color_temp_rela);
		hdrRel = (RelativeLayout) findViewById(R.id.hdr_rela);
		smartRel = (RelativeLayout) findViewById(R.id.smart_color_rela);
		sharpenRel = (RelativeLayout) findViewById(R.id.sharpen_rela);
		noiseRel = (RelativeLayout) findViewById(R.id.noise_reduction_rela);
		resetRel = (RelativeLayout) findViewById(R.id.reset_reduction_rela);
//初始化每个条目的显示值的文本组建
		disModle = (TextView) findViewById(R.id.display_modeValue);
		brightNess = (TextView) findViewById(R.id.brightnessValue);
		colorTemp = (TextView) findViewById(R.id.color_tempValue);
		dynamicCon = (TextView) findViewById(R.id.hdrValue);
		intellColor = (TextView) findViewById(R.id.smart_colorValue);
		sharpen = (TextView) findViewById(R.id.sharpenValue);
		denoise = (TextView) findViewById(R.id.noise_reductionValue);
		reset = (TextView) findViewById(R.id.resetValue);

		initValueText();

		disModle.setClickable(true);
		disModle.setFocusable(true);
		disModle.setOnClickListener(this);
		disModle.setOnFocusChangeListener(this);

		brightNess.setClickable(true);
		brightNess.setFocusable(true);
		brightNess.setOnClickListener(this);
		brightNess.setOnFocusChangeListener(this);

		colorTemp.setClickable(true);
		colorTemp.setFocusable(true);
		colorTemp.setOnClickListener(this);
		colorTemp.setOnFocusChangeListener(this);

		dynamicCon.setClickable(true);
		dynamicCon.setFocusable(true);
		dynamicCon.setOnClickListener(this);
		dynamicCon.setOnFocusChangeListener(this);

		intellColor.setClickable(true);
		intellColor.setFocusable(true);
		intellColor.setOnClickListener(this);
		intellColor.setOnFocusChangeListener(this);

		sharpen.setClickable(true);
		sharpen.setFocusable(true);
		sharpen.setOnClickListener(this);
		sharpen.setOnFocusChangeListener(this);

		denoise.setClickable(true);
		denoise.setFocusable(true);
		denoise.setOnClickListener(this);
		denoise.setOnFocusChangeListener(this);

		reset.setClickable(true);
		reset.setFocusable(true);
		reset.setOnClickListener(this);
		reset.setOnFocusChangeListener(this);
	}

	/**
	 * 首次初始化时的显示值
	 */
	private void initValueText() {
		// TODO Auto-generated method stub
		if (videoBean.getDisModle() == null) {
			disModle.setText(disModleStrings[0]);
		} else {
			disModle.setText(videoBean.getDisModle());
		}
		if (videoBean.getBrightNess() == null) {
			brightNess.setText("50");
		} else {
			brightNess.setText(videoBean.getBrightNess());
		}

		if (videoBean.getColorTemp() == null) {
			colorTemp.setText(colorTempStrings[0]);
		} else {
			colorTemp.setText(videoBean.getColorTemp());
		}

		if (videoBean.getDynamicCon() == null) {
			dynamicCon.setText(dynamicConStrings[0]);
		} else {
			dynamicCon.setText(videoBean.getDynamicCon());
		}

		if (videoBean.getIntellColor() == null) {
			intellColor.setText(intellColorStrings[0]);
		} else {
			intellColor.setText(videoBean.getIntellColor());
		}

		if (videoBean.getSharpen() == null) {
			sharpen.setText("50");
		} else {
			sharpen.setText(videoBean.getSharpen());
		}

		if (videoBean.getDenoise() == null) {
			denoise.setText(denoiseStrings[0]);
		} else {
			denoise.setText(videoBean.getDenoise());
		}

		if (videoBean.getReset() == null) {
			reset.setText(resetStrings[0]);
		} else {
			reset.setText(videoBean.getReset());
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

		// 复位方法
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
			Log.e(SEVIC, pref.getKey().toString() + " clicked");
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
					+ newValue.toString());// 弹出框选择后调用方法。
			String key = preference.getKey();
			SetDisplayParamIntf set = displaySettingMap.get(key);
			if (set != null) {
				int ret = set.setDisplayParam(st, newValue);
				Log.e(SEVIC, "calling " + key + " return " + ret);
			}
			return true;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.display_modeValue:
			Log.e("service", "display_modeValue======"
					+ disModleValueStrings[showValue]);
			savePreference("displaymode", disModleValueStrings[showValue]);
			videoBean.setDisModle(disModleStrings[showValue]);
			break;
		case R.id.brightnessValue:
			savePreference("brightness", String.valueOf(defaultBrightVolume));
			videoBean.setBrightNess(String.valueOf(defaultBrightVolume));
			break;
		case R.id.color_tempValue:
			Log.e("service", "display_modeValue======"
					+ colorTempValueStrings[showValue]);
			savePreference("colortemp", colorTempValueStrings[showValue]);
			videoBean.setColorTemp(colorTempStrings[showValue]);
			break;
		case R.id.hdrValue:
			Log.e("service", "display_modeValue======"
					+ dynamicConValueStrings[showValue]);
			savePreference("colortemp", dynamicConValueStrings[showValue]);
			videoBean.setDynamicCon(dynamicConStrings[showValue]);
			break;
		case R.id.smart_colorValue:
			Log.e("service", "display_modeValue======"
					+ intellColorValueStrings[showValue]);
			savePreference("colortemp", intellColorValueStrings[showValue]);
			videoBean.setIntellColor(intellColorStrings[showValue]);
			break;
		case R.id.sharpenValue:
			savePreference("sharpness", String.valueOf(defaultSharpenVolume));
			videoBean.setSharpen(String.valueOf(defaultSharpenVolume));
			break;
		case R.id.noise_reductionValue:
			Log.e("service", "display_modeValue======"
					+ denoiseValueStrings[showValue]);
			savePreference("colortemp", denoiseValueStrings[showValue]);
			videoBean.setDenoise(denoiseStrings[showValue]);
			break;
		case R.id.resetValue:
			// Log.e("service",
			// "display_modeValue======"+resetStrings[showValue]);
			// savePreference("colortemp", resetValueStrings[showValue]);
			videoBean.setReset(resetStrings[showValue]);
			if (reset.getText().toString().equals(resetStrings[0])) {
				st.SetDisplayDefault_PQ();// 复位
			} 
			break;

		default:
			break;
		}
	}

	
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.display_modeValue:
			if (hasFocus) {
				valueInt = 1;
				displayRel.setBackgroundResource(R.drawable.video_back);
			} else {
				displayRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.brightnessValue:
			if (hasFocus) {
				valueInt = 2;
				brightnessRel.setBackgroundResource(R.drawable.video_back);
			} else {
				brightnessRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.color_tempValue:
			if (hasFocus) {
				valueInt = 3;
				colorTempRel.setBackgroundResource(R.drawable.video_back);
			} else {
				colorTempRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.hdrValue:
			if (hasFocus) {
				valueInt = 4;
				hdrRel.setBackgroundResource(R.drawable.video_back);
			} else {
				hdrRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.smart_colorValue:
			if (hasFocus) {
				valueInt = 5;
				smartRel.setBackgroundResource(R.drawable.video_back);
			} else {
				smartRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.sharpenValue:
			if (hasFocus) {
				valueInt = 6;
				sharpenRel.setBackgroundResource(R.drawable.video_back);
			} else {
				sharpenRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.noise_reductionValue:
			if (hasFocus) {
				valueInt = 7;
				noiseRel.setBackgroundResource(R.drawable.video_back);
			} else {
				noiseRel.setBackgroundColor(color.transparent);
			}
			break;
		case R.id.resetValue:
			if (hasFocus) {
				valueInt = 8;
				resetRel.setBackgroundResource(R.drawable.video_back);
			} else {
				resetRel.setBackgroundColor(color.transparent);
			}
			break;

		default:
			break;
		}
	}
}
