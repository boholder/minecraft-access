package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class FallDetectorConfigMap {
    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Range")
    private int range;
    @SerializedName("Depth Threshold")
    private int depth;
//    @SerializedName("Play Sound")
//    private boolean playSound;
    @SerializedName("Sound Volume")
    private float volume;
    @SerializedName("Delay (in milliseconds)")
    private int delay;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

//    public boolean isPlaySound() {
//        return playSound;
//    }
//
//    public void setPlaySound(boolean playSound) {
//        this.playSound = playSound;
//    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public static FallDetectorConfigMap defaultFallDetectorConfigMap(){
        FallDetectorConfigMap fallDetectorConfigMap = new FallDetectorConfigMap();
        fallDetectorConfigMap.setEnabled(true);
        fallDetectorConfigMap.setRange(6);
        fallDetectorConfigMap.setDepth(4);
//        fallDetectorConfigMap.setPlaySound(true);
        fallDetectorConfigMap.setVolume(0.25f);
        fallDetectorConfigMap.setDelay(2500);

        return fallDetectorConfigMap;
    }
}
