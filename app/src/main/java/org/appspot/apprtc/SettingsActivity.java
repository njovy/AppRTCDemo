/*
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package org.appspot.apprtc;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;

import org.webrtc.Camera2Enumerator;
import org.webrtc.voiceengine.WebRtcAudioUtils;

/**
 * Settings activity for AppRTC.
 */
public class SettingsActivity extends Activity
    implements OnSharedPreferenceChangeListener{
  private SettingsFragment settingsFragment;
  private String keyprefVideoCall;
  private String keyprefCamera2;
  private String keyprefResolution;
  private String keyprefFps;
  private String keyprefCaptureQualitySlider;
  private String keyprefStartVideoBitrateType;
  private String keyprefStartVideoBitrateValue;
  private String keyPrefVideoCodec;
  private String keyprefHwCodec;
  private String keyprefCaptureToTexture;

  private String keyprefStartAudioBitrateType;
  private String keyprefStartAudioBitrateValue;
  private String keyPrefAudioCodec;
  private String keyprefNoAudioProcessing;
  private String keyprefAecDump;
  private String keyprefOpenSLES;
  private String keyprefDisableBuiltInAEC;
  private String keyprefDisableBuiltInAGC;
  private String keyprefDisableBuiltInNS;
  private String keyprefEnableLevelControl;
  private String keyprefSpeakerphone;

  private String keyPrefRoomServerUrl;
  private String keyPrefDisplayHud;
  private String keyPrefTracing;

  private String keyprefEnableDataChannel;
  private String keyprefOrdered;
  private String keyprefMaxRetransmitTimeMs;
  private String keyprefMaxRetransmits;
  private String keyprefDataProtocol;
  private String keyprefNegotiated;
  private String keyprefDataId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    keyprefVideoCall = getString(R.string.pref_videocall_key);
    keyprefCamera2 = getString(R.string.pref_camera2_key);
    keyprefResolution = getString(R.string.pref_resolution_key);
    keyprefFps = getString(R.string.pref_fps_key);
    keyprefCaptureQualitySlider = getString(R.string.pref_capturequalityslider_key);
    keyprefStartVideoBitrateType = getString(R.string.pref_startvideobitrate_key);
    keyprefStartVideoBitrateValue = getString(R.string.pref_startvideobitratevalue_key);
    keyPrefVideoCodec = getString(R.string.pref_videocodec_key);
    keyprefHwCodec = getString(R.string.pref_hwcodec_key);
    keyprefCaptureToTexture = getString(R.string.pref_capturetotexture_key);

    keyprefStartAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
    keyprefStartAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
    keyPrefAudioCodec = getString(R.string.pref_audiocodec_key);
    keyprefNoAudioProcessing = getString(R.string.pref_noaudioprocessing_key);
    keyprefAecDump = getString(R.string.pref_aecdump_key);
    keyprefOpenSLES = getString(R.string.pref_opensles_key);
    keyprefDisableBuiltInAEC = getString(R.string.pref_disable_built_in_aec_key);
    keyprefDisableBuiltInAGC = getString(R.string.pref_disable_built_in_agc_key);
    keyprefDisableBuiltInNS = getString(R.string.pref_disable_built_in_ns_key);
    keyprefEnableLevelControl = getString(R.string.pref_enable_level_control_key);
    keyprefSpeakerphone = getString(R.string.pref_speakerphone_key);

    keyPrefRoomServerUrl = getString(R.string.pref_room_server_url_key);
    keyPrefDisplayHud = getString(R.string.pref_displayhud_key);
    keyPrefTracing = getString(R.string.pref_tracing_key);

    keyprefEnableDataChannel = getString(R.string.pref_enable_datachannel_key);
    keyprefOrdered = getString(R.string.pref_ordered_key);
    keyprefMaxRetransmitTimeMs = getString(R.string.pref_max_retransmit_time_ms_key);
    keyprefMaxRetransmits = getString(R.string.pref_max_retransmits_key);
    keyprefDataProtocol = getString(R.string.pref_data_protocol_key);
    keyprefNegotiated = getString(R.string.pref_negotiated_key);
    keyprefDataId = getString(R.string.pref_data_id_key);

    // Display the fragment as the main content.
    settingsFragment = new SettingsFragment();
    getFragmentManager().beginTransaction()
        .replace(android.R.id.content, settingsFragment)
        .commit();
  }

  @Override
  protected void onResume() {
    super.onResume();
    // Set summary to be the user-description for the selected value
    SharedPreferences sharedPreferences =
        settingsFragment.getPreferenceScreen().getSharedPreferences();
    sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    updateSummaryB(sharedPreferences, keyprefVideoCall);
    updateSummaryB(sharedPreferences, keyprefCamera2);
    updateSummary(sharedPreferences, keyprefResolution);
    updateSummary(sharedPreferences, keyprefFps);
    updateSummaryB(sharedPreferences, keyprefCaptureQualitySlider);
    updateSummary(sharedPreferences, keyprefStartVideoBitrateType);
    updateSummaryBitrate(sharedPreferences, keyprefStartVideoBitrateValue);
    setVideoBitrateEnable(sharedPreferences);
    updateSummary(sharedPreferences, keyPrefVideoCodec);
    updateSummaryB(sharedPreferences, keyprefHwCodec);
    updateSummaryB(sharedPreferences, keyprefCaptureToTexture);

    updateSummary(sharedPreferences, keyprefStartAudioBitrateType);
    updateSummaryBitrate(sharedPreferences, keyprefStartAudioBitrateValue);
    setAudioBitrateEnable(sharedPreferences);
    updateSummary(sharedPreferences, keyPrefAudioCodec);
    updateSummaryB(sharedPreferences, keyprefNoAudioProcessing);
    updateSummaryB(sharedPreferences, keyprefAecDump);
    updateSummaryB(sharedPreferences, keyprefOpenSLES);
    updateSummaryB(sharedPreferences, keyprefDisableBuiltInAEC);
    updateSummaryB(sharedPreferences, keyprefDisableBuiltInAGC);
    updateSummaryB(sharedPreferences, keyprefDisableBuiltInNS);
    updateSummaryB(sharedPreferences, keyprefEnableLevelControl);
    updateSummaryList(sharedPreferences, keyprefSpeakerphone);

    updateSummary(sharedPreferences, keyPrefRoomServerUrl);
    updateSummaryB(sharedPreferences, keyPrefDisplayHud);
    updateSummaryB(sharedPreferences, keyPrefTracing);
    updateSummaryB(sharedPreferences, keyprefEnableDataChannel);

    updateSummary(sharedPreferences, keyprefMaxRetransmits);
    updateSummary(sharedPreferences, keyprefMaxRetransmitTimeMs);
    updateSummary(sharedPreferences, keyprefDataId);
    updateSummary(sharedPreferences, keyprefDataProtocol);

    if (!Camera2Enumerator.isSupported()) {
      Preference camera2Preference =
          settingsFragment.findPreference(keyprefCamera2);

      camera2Preference.setSummary(getString(R.string.pref_camera2_not_supported));
      camera2Preference.setEnabled(false);
    }

    // Disable forcing WebRTC based AEC so it won't affect our value.
    // Otherwise, if it was enabled, isAcousticEchoCancelerSupported would always return false.
    WebRtcAudioUtils.setWebRtcBasedAcousticEchoCanceler(false);
    if (!WebRtcAudioUtils.isAcousticEchoCancelerSupported()) {
      Preference disableBuiltInAECPreference =
          settingsFragment.findPreference(keyprefDisableBuiltInAEC);

      disableBuiltInAECPreference.setSummary(getString(R.string.pref_built_in_aec_not_available));
      disableBuiltInAECPreference.setEnabled(false);
    }

    WebRtcAudioUtils.setWebRtcBasedAutomaticGainControl(false);
    if (!WebRtcAudioUtils.isAutomaticGainControlSupported()) {
      Preference disableBuiltInAGCPreference =
          settingsFragment.findPreference(keyprefDisableBuiltInAGC);

      disableBuiltInAGCPreference.setSummary(getString(R.string.pref_built_in_agc_not_available));
      disableBuiltInAGCPreference.setEnabled(false);
    }

    WebRtcAudioUtils.setWebRtcBasedNoiseSuppressor(false);
    if (!WebRtcAudioUtils.isNoiseSuppressorSupported()) {
      Preference disableBuiltInNSPreference =
          settingsFragment.findPreference(keyprefDisableBuiltInNS);

      disableBuiltInNSPreference.setSummary(getString(R.string.pref_built_in_ns_not_available));
      disableBuiltInNSPreference.setEnabled(false);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    SharedPreferences sharedPreferences =
        settingsFragment.getPreferenceScreen().getSharedPreferences();
    sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
      String key) {
    if (key.equals(keyprefResolution)
        || key.equals(keyprefFps)
        || key.equals(keyprefStartVideoBitrateType)
        || key.equals(keyPrefVideoCodec)
        || key.equals(keyprefStartAudioBitrateType)
        || key.equals(keyPrefAudioCodec)
        || key.equals(keyPrefRoomServerUrl)) {
      updateSummary(sharedPreferences, key);
    } else if (key.equals(keyprefStartVideoBitrateValue)
        || key.equals(keyprefStartAudioBitrateValue)) {
      updateSummaryBitrate(sharedPreferences, key);
    } else if (key.equals(keyprefVideoCall)
        || key.equals(keyprefCamera2)
        || key.equals(keyPrefTracing)
        || key.equals(keyprefCaptureQualitySlider)
        || key.equals(keyprefHwCodec)
        || key.equals(keyprefCaptureToTexture)
        || key.equals(keyprefNoAudioProcessing)
        || key.equals(keyprefAecDump)
        || key.equals(keyprefOpenSLES)
        || key.equals(keyprefDisableBuiltInAEC)
        || key.equals(keyprefDisableBuiltInAGC)
        || key.equals(keyprefDisableBuiltInNS)
        || key.equals(keyprefEnableLevelControl)
        || key.equals(keyPrefDisplayHud)) {
      updateSummaryB(sharedPreferences, key);
    } else if (key.equals(keyprefSpeakerphone)) {
      updateSummaryList(sharedPreferences, key);
    }
    if (key.equals(keyprefStartVideoBitrateType)) {
      setVideoBitrateEnable(sharedPreferences);
    }
    if (key.equals(keyprefStartAudioBitrateType)) {
      setAudioBitrateEnable(sharedPreferences);
    }
    if (key.equals(keyprefEnableDataChannel)) {
      setDataChannelEnable(sharedPreferences);
    }
  }

  private void updateSummary(SharedPreferences sharedPreferences, String key) {
    Preference updatedPref = settingsFragment.findPreference(key);
    // Set summary to be the user-description for the selected value
    updatedPref.setSummary(sharedPreferences.getString(key, ""));
  }

  private void updateSummaryBitrate(
      SharedPreferences sharedPreferences, String key) {
    Preference updatedPref = settingsFragment.findPreference(key);
    updatedPref.setSummary(sharedPreferences.getString(key, "") + " kbps");
  }

  private void updateSummaryB(SharedPreferences sharedPreferences, String key) {
    Preference updatedPref = settingsFragment.findPreference(key);
    updatedPref.setSummary(sharedPreferences.getBoolean(key, true)
        ? getString(R.string.pref_value_enabled)
        : getString(R.string.pref_value_disabled));
  }

  private void updateSummaryList(SharedPreferences sharedPreferences, String key) {
    ListPreference updatedPref = (ListPreference) settingsFragment.findPreference(key);
    updatedPref.setSummary(updatedPref.getEntry());
  }

  private void setVideoBitrateEnable(SharedPreferences sharedPreferences) {
    Preference bitratePreferenceValue =
        settingsFragment.findPreference(keyprefStartVideoBitrateValue);
    String bitrateTypeDefault = getString(R.string.pref_startvideobitrate_default);
    String bitrateType = sharedPreferences.getString(
        keyprefStartVideoBitrateType, bitrateTypeDefault);
    if (bitrateType.equals(bitrateTypeDefault)) {
      bitratePreferenceValue.setEnabled(false);
    } else {
      bitratePreferenceValue.setEnabled(true);
    }
  }

  private void setAudioBitrateEnable(SharedPreferences sharedPreferences) {
    Preference bitratePreferenceValue =
        settingsFragment.findPreference(keyprefStartAudioBitrateValue);
    String bitrateTypeDefault = getString(R.string.pref_startaudiobitrate_default);
    String bitrateType = sharedPreferences.getString(
        keyprefStartAudioBitrateType, bitrateTypeDefault);
    if (bitrateType.equals(bitrateTypeDefault)) {
      bitratePreferenceValue.setEnabled(false);
    } else {
      bitratePreferenceValue.setEnabled(true);
    }
  }

  private void setDataChannelEnable(SharedPreferences sharedPreferences) {
    boolean enabled = sharedPreferences.getBoolean(keyprefEnableDataChannel, true);
    settingsFragment.findPreference(keyprefOrdered).setEnabled(enabled);
    settingsFragment.findPreference(keyprefMaxRetransmitTimeMs).setEnabled(enabled);
    settingsFragment.findPreference(keyprefMaxRetransmits).setEnabled(enabled);
    settingsFragment.findPreference(keyprefDataProtocol).setEnabled(enabled);
    settingsFragment.findPreference(keyprefNegotiated).setEnabled(enabled);
    settingsFragment.findPreference(keyprefDataId).setEnabled(enabled);
  }
}
