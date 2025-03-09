package skyxnetwork.hypixelzombiehelper.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;

public class ScoreboardOverlay {
    private static int posX = 10; // Position par défaut X
    private static int posY = 20; // Position par défaut Y

    public static void render(DrawContext drawContext, float _tickDelta) { // ✅ Bonne signature avec float
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;

        // Dessiner un fond semi-transparent
        drawContext.fill(posX - 5, posY - 5, posX + 100, posY + 50, 0x88000000);

        // Afficher du texte (exemple)
        drawContext.drawText(textRenderer, Text.of("Hypixel Zombies Stats"), posX, posY, 0xFFFFFF, true);
        drawContext.drawText(textRenderer, Text.of("Kills: 120"), posX, posY + 10, 0xFFFF55, true);
        drawContext.drawText(textRenderer, Text.of("Gold: 500"), posX, posY + 20, 0xFFFF55, true);
    }

    public static void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }

    public static int getPosX() {
        return posX;
    }

    public static int getPosY() {
        return posY;
    }

    public static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
    }
}