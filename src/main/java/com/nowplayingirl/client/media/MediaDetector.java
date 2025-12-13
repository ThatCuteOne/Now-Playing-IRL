package com.nowplayingirl.client.media;

import com.nowplayingirl.NowPlayingIRLMod;

public abstract class MediaDetector {
    
    public abstract MediaInfo detect();
    
    public static MediaDetector create() {
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            NowPlayingIRLMod.LOGGER.info("Using Windows media detector");
            return new WindowsMediaDetector();
        } else if (os.contains("mac")) {
            NowPlayingIRLMod.LOGGER.info("Using macOS media detector");
            return new MacOSMediaDetector();
        } else {
            NowPlayingIRLMod.LOGGER.info("Using Linux media detector");
            return new LinuxMediaDetector();
        }
    }
}

// Fallback pour macOS
class MacOSMediaDetector extends MediaDetector {
    @Override
    public MediaInfo detect() {
        // Utiliser osascript pour détecter iTunes/Music/Spotify
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "osascript", "-e",
                "tell application \"Spotify\" to if player state is playing then " +
                "return (get artist of current track) & \"|\" & (get name of current track) " +
                "end if"
            );
            Process p = pb.start();
            String result = new String(p.getInputStream().readAllBytes()).trim();
            if (!result.isEmpty() && result.contains("|")) {
                String[] parts = result.split("\\|", 2);
                return new MediaInfo(parts[1], parts[0], "Spotify");
            }
        } catch (Exception ignored) {}
        return null;
    }
}

// Fallback pour Linux
class LinuxMediaDetector extends MediaDetector {
    @Override
    public MediaInfo detect() {
        // Utiliser playerctl
        try {
            ProcessBuilder pb = new ProcessBuilder(
                "playerctl", "metadata", "--format", "{{artist}}|{{title}}|{{playerName}}"
            );
            Process p = pb.start();
            String result = new String(p.getInputStream().readAllBytes()).trim();
            if (!result.isEmpty() && result.contains("|")) {
                String[] parts = result.split("\\|", 3);
                if (parts.length >= 2) {
                    return new MediaInfo(parts[1], parts[0], parts.length > 2 ? parts[2] : "Unknown");
                }
            }
        } catch (Exception ignored) {}
        return null;
    }
}