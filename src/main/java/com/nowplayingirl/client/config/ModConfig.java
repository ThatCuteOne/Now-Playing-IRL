package com.nowplayingirl.client.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nowplayingirl.NowPlayingIRLMod;
import com.nowplayingirl.client.hud.Theme;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class ModConfig {
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance()
        .getConfigDir().resolve("nowplayingirl.json");
    
    // Config values
    private boolean enabled = true;
    private Position position = Position.BOTTOM_RIGHT;
    private float scale = 1.0f;
    private float opacity = 0.9f;
    private float rotationSpeed = 1.0f;
    private boolean showAlbumArt = true;
    private Theme theme = Theme.CHERRY_BLOSSOM;
    private int pollingIntervalMs = 1000;
    
    public enum Position {
        TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }
    
    // Getters
    public boolean isEnabled() { return enabled; }
    public Position getPosition() { return position; }
    public float getScale() { return scale; }
    public float getOpacity() { return opacity; }
    public float getRotationSpeed() { return rotationSpeed; }
    public boolean isShowAlbumArt() { return showAlbumArt; }
    public Theme getTheme() { return theme; }
    public int getPollingIntervalMs() { return pollingIntervalMs; }
    
    // Setters
    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setPosition(Position position) { this.position = position; }
    public void setScale(float scale) { this.scale = Math.max(0.5f, Math.min(2.0f, scale)); }
    public void setOpacity(float opacity) { this.opacity = Math.max(0.1f, Math.min(1.0f, opacity)); }
    public void setRotationSpeed(float speed) { this.rotationSpeed = Math.max(0f, Math.min(5f, speed)); }
    public void setShowAlbumArt(boolean show) { this.showAlbumArt = show; }
    public void setTheme(Theme theme) { this.theme = theme; }
    
    public void cycleTheme() {
        this.theme = this.theme.next();
    }
    
    public void cyclePosition() {
        Position[] values = Position.values();
        this.position = values[(position.ordinal() + 1) % values.length];
    }
    
    /**
     * Remet tous les paramètres par défaut
     */
    public void reset() {
        this.enabled = true;
        this.position = Position.BOTTOM_RIGHT;
        this.scale = 1.0f;
        this.opacity = 0.9f;
        this.rotationSpeed = 1.0f;
        this.showAlbumArt = true;
        this.theme = Theme.CHERRY_BLOSSOM;
        this.pollingIntervalMs = 1000;
        save();
    }
    
    public static ModConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
                ModConfig config = GSON.fromJson(reader, ModConfig.class);
                if (config != null) {
                    // Validation des valeurs
                    if (config.theme == null) config.theme = Theme.CHERRY_BLOSSOM;
                    if (config.position == null) config.position = Position.BOTTOM_RIGHT;
                    NowPlayingIRLMod.LOGGER.info("Config loaded successfully");
                    return config;
                }
            } catch (Exception e) {
                NowPlayingIRLMod.LOGGER.error("Failed to load config, using defaults", e);
            }
        }
        
        ModConfig config = new ModConfig();
        config.save();
        return config;
    }
    
    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, writer);
                NowPlayingIRLMod.LOGGER.debug("Config saved");
            }
        } catch (Exception e) {
            NowPlayingIRLMod.LOGGER.error("Failed to save config", e);
        }
    }
}