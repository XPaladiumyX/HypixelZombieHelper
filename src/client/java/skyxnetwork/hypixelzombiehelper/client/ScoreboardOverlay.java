package skyxnetwork.hypixelzombiehelper.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreboardOverlay {
    private static final File CONFIG_FILE = new File("config/hypixelzombiehelper.json");
    private static int posX = 10; // Position par défaut X
    private static int posY = 20; // Position par défaut Y

    public static void render(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;

        // Dessiner un fond semi-transparent
        drawContext.fill(posX - 5, posY - 5, posX + 100, posY + 50, 0x88000000);

        // Afficher du texte
        drawContext.drawText(textRenderer, Text.of("Hypixel Zombies Stats"), posX, posY, 0xFFFFFF, true);
        drawContext.drawText(textRenderer, Text.of("Kills: 120"), posX, posY + 10, 0xFFFF55, true);
        drawContext.drawText(textRenderer, Text.of("Gold: 500"), posX, posY + 20, 0xFFFF55, true);
    }

    public static void setPosition(int x, int y) {
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // Empêcher de placer l'overlay en dehors de l'écran
        posX = MathHelper.clamp(x, 0, screenWidth - 100);
        posY = MathHelper.clamp(y, 0, screenHeight - 50);
    }

    public static int getPosX() {
        return posX;
    }

    public static int getPosY() {
        return posY;
    }

    public static void savePosition() {
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            JsonObject json = new JsonObject();
            json.addProperty("posX", posX);
            json.addProperty("posY", posY);
            writer.write(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadPosition() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                posX = json.get("posX").getAsInt();
                posY = json.get("posY").getAsInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void render(DrawContext drawContext, RenderTickCounter renderTickCounter) {
    }
}

