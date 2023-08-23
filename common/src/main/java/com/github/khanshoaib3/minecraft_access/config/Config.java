package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static final Config instance;
    private static final String CONFIG_FILE_PATH = Paths.get("config", "minecraft-access", "config.json").toString();
    private ConfigMap configMap = null;
    private Gson gson = null;

    static {
        instance = new Config();
    }

    /**
     * To prevent constructing other instances.
     */
    private Config() {
        
    }

    public static Config getInstance() {
        return instance;
    }

    /**
     * Loads the configurations from the config.json<br>
     * The path for the file is: config/minecraft_access/config.json
     * If the json format is wrong or an error occurs, the config.json is reset to default
     */
    public void loadConfig() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            gson = builder.create();

            createDefaultConfigFileIfNotExist();
            configMap = readJSON();

            if (isConfigMapNotValid(configMap)) resetToDefault();

            // update config map singleton instances reference
            ConfigMap.setInstance(configMap);

        } catch (Exception e) {
            MainClass.errorLog("An error occurred while reading config.json file, resetting to default");
            e.printStackTrace();
            resetToDefault();
        } finally {
            MainClass.infoLog("Loaded configurations from config.json");
        }
    }

    private boolean isConfigMapNotValid(ConfigMap configMap) {
        if (configMap == null) return false;
        return !configMap.validate();
    }

    /**
     * Resets the config.json to default
     */
    protected void resetToDefault() {
        try {
            this.configMap = ConfigMap.buildDefault();
            writeJSON();
        } catch (Exception e) {
            MainClass.errorLog("An error occurred while resetting config.json file to default.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createDefaultConfigFileIfNotExist() throws IOException {
        File configFile = new File(CONFIG_FILE_PATH);
        if (configFile.exists()) return;

        configFile.getParentFile().mkdirs();
        configFile.createNewFile();
        MainClass.infoLog("Created an empty config.json file at: %s".formatted(configFile.getAbsolutePath()));

        resetToDefault();
    }

    public void writeJSON() {
        try (Writer writer = new FileWriter(CONFIG_FILE_PATH)) {
            writer.write(gson.toJson(configMap));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConfigMap readJSON() throws IOException {
        return gson.fromJson(Files.readString(Path.of(CONFIG_FILE_PATH)), ConfigMap.class);
    }
}