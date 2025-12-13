package com.nowplayingirl.client.hud;

public class HudAnimator {
    
    // Animation d'apparition/disparition
    private float visibility = 0f;
    private float targetVisibility = 0f;
    private static final float FADE_SPEED = 0.08f;
    
    // Rotation de la pochette
    private float albumRotation = 0f;
    private float rotationSpeed = 1f;
    
    // Animation de la note qui dort
    private float sleepingNotePhase = 0f;
    private float sleepingNoteY = 0f;
    
    // Animation de slide
    private float slideOffset = 50f;
    private float targetSlideOffset = 0f;
    private static final float SLIDE_SPEED = 0.15f;
    
    // Animation de pulsation (quand musique joue)
    private float pulsePhase = 0f;
    
    // Nouveau média détecté - animation de bounce
    private float bouncePhase = 0f;
    private boolean isBouncing = false;
    
    public void update(float deltaTime, boolean hasMedia, float configRotationSpeed) {
        this.rotationSpeed = configRotationSpeed;
        
        // Update target visibility
        targetVisibility = hasMedia ? 1f : 0.7f; // Légèrement visible même sans média
        
        // Smooth visibility transition
        if (visibility < targetVisibility) {
            visibility = Math.min(visibility + FADE_SPEED, targetVisibility);
        } else if (visibility > targetVisibility) {
            visibility = Math.max(visibility - FADE_SPEED, targetVisibility);
        }
        
        // Slide animation
        if (slideOffset > targetSlideOffset) {
            slideOffset = Math.max(slideOffset - (slideOffset * SLIDE_SPEED), targetSlideOffset);
            if (slideOffset < 0.5f) slideOffset = 0f;
        }
        
        // Album rotation (continuous)
        if (hasMedia) {
            albumRotation += rotationSpeed * 0.5f;
            if (albumRotation >= 360f) albumRotation -= 360f;
        }
        
        // Sleeping note animation
        if (!hasMedia) {
            sleepingNotePhase += 0.05f;
            sleepingNoteY = (float) Math.sin(sleepingNotePhase) * 3f;
        }
        
        // Pulse animation
        pulsePhase += 0.1f;
        
        // Bounce animation
        if (isBouncing) {
            bouncePhase += 0.15f;
            if (bouncePhase >= Math.PI) {
                bouncePhase = 0f;
                isBouncing = false;
            }
        }
    }
    
    public void triggerAppear() {
        slideOffset = 100f;
        targetSlideOffset = 0f;
        visibility = 0f;
        isBouncing = true;
        bouncePhase = 0f;
    }
    
    public void triggerDisappear() {
        targetVisibility = 0f;
    }
    
    public float getVisibility() { return visibility; }
    public float getAlbumRotation() { return albumRotation; }
    public float getSlideOffset() { return slideOffset; }
    public float getSleepingNoteY() { return sleepingNoteY; }
    public float getSleepingNotePhase() { return sleepingNotePhase; }
    
    public float getPulseScale() {
        return 1f + (float) Math.sin(pulsePhase) * 0.02f;
    }
    
    public float getBounceOffset() {
        if (!isBouncing) return 0f;
        return (float) Math.sin(bouncePhase) * 5f;
    }
    
    public boolean isFullyHidden() {
        return visibility < 0.01f;
    }
}