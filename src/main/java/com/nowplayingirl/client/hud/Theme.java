package com.nowplayingirl.client.hud;

public enum Theme {
    CHERRY_BLOSSOM(
        "Cherry Blossom",
        0xFFFFB7C5, // Rose sakura
        0xFFFFC0CB, // Rose clair
        0xFFFFFFFF, // Blanc
        0xFF8B4557, // Rose foncé
        0xCC1A0A14  // Fond semi-transparent
    ),
    PURPLE(
        "Purple",
        0xFF9B59B6, // Violet
        0xFFBB8FCE, // Violet clair
        0xFFFFFFFF, // Blanc
        0xFF6C3483, // Violet foncé
        0xCC1A0A20  // Fond
    ),
    OCEAN(
        "Ocean",
        0xFF3498DB, // Bleu ocean
        0xFF85C1E9, // Bleu clair
        0xFFFFFFFF, // Blanc
        0xFF21618C, // Bleu foncé
        0xCC0A141A  // Fond
    ),
    FOREST(
        "Forest",
        0xFF27AE60, // Vert forêt
        0xFF82E0AA, // Vert clair
        0xFFFFFFFF, // Blanc
        0xFF1E8449, // Vert foncé
        0xCC0A1A14  // Fond
    ),
    SUNSET(
        "Sunset",
        0xFFE74C3C, // Rouge sunset
        0xFFF1948A, // Rose saumon
        0xFFFFFFFF, // Blanc
        0xFFC0392B, // Rouge foncé
        0xCC1A140A  // Fond
    );
    
    private final String displayName;
    private final int primaryColor;
    private final int secondaryColor;
    private final int textColor;
    private final int accentColor;
    private final int backgroundColor;
    
    Theme(String displayName, int primary, int secondary, int text, int accent, int background) {
        this.displayName = displayName;
        this.primaryColor = primary;
        this.secondaryColor = secondary;
        this.textColor = text;
        this.accentColor = accent;
        this.backgroundColor = background;
    }
    
    public String getDisplayName() { return displayName; }
    public int getPrimaryColor() { return primaryColor; }
    public int getSecondaryColor() { return secondaryColor; }
    public int getTextColor() { return textColor; }
    public int getAccentColor() { return accentColor; }
    public int getBackgroundColor() { return backgroundColor; }
    
    public Theme next() {
        Theme[] values = values();
        return values[(ordinal() + 1) % values.length];
    }
}