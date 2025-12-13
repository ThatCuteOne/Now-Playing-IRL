package com.nowplayingirl.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class RenderHelper {
    
    /**
     * Dessine une texture avec rotation autour de son centre
     */
    public static void drawRotatedTexture(DrawContext context, Identifier texture, 
            int x, int y, int width, int height, float rotation, float opacity) {
        
        context.getMatrices().push();
        
        // Translate to center
        float centerX = x + width / 2f;
        float centerY = y + height / 2f;
        context.getMatrices().translate(centerX, centerY, 0);
        
        // Rotate
        context.getMatrices().multiply(
            new org.joml.Quaternionf().rotateZ((float) Math.toRadians(rotation))
        );
        
        // Translate back
        context.getMatrices().translate(-width / 2f, -height / 2f, 0);
        
        // Draw avec nouvelle API 1.21.4
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, opacity);
        
        context.drawTexture(
            RenderLayer::getGuiTextured,
            texture,
            0, 0,
            0f, 0f,
            width, height,
            width, height
        );
        
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        
        context.getMatrices().pop();
    }
    
    /**
     * Dessine un rectangle arrondi (approximation)
     */
    public static void drawRoundedRect(DrawContext context, int x, int y, 
            int width, int height, int radius, int color) {
        context.fill(x + radius, y, x + width - radius, y + height, color);
        context.fill(x, y + radius, x + width, y + height - radius, color);
        context.fill(x + 1, y + 1, x + radius, y + radius, color);
        context.fill(x + width - radius, y + 1, x + width - 1, y + radius, color);
        context.fill(x + 1, y + height - radius, x + radius, y + height - 1, color);
        context.fill(x + width - radius, y + height - radius, x + width - 1, y + height - 1, color);
    }
    
    /**
     * Interpole une couleur pour les animations
     */
    public static int lerpColor(int color1, int color2, float t) {
        int a1 = (color1 >> 24) & 0xFF;
        int r1 = (color1 >> 16) & 0xFF;
        int g1 = (color1 >> 8) & 0xFF;
        int b1 = color1 & 0xFF;
        
        int a2 = (color2 >> 24) & 0xFF;
        int r2 = (color2 >> 16) & 0xFF;
        int g2 = (color2 >> 8) & 0xFF;
        int b2 = color2 & 0xFF;
        
        int a = (int) (a1 + (a2 - a1) * t);
        int r = (int) (r1 + (r2 - r1) * t);
        int g = (int) (g1 + (g2 - g1) * t);
        int b = (int) (b1 + (b2 - b1) * t);
        
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}