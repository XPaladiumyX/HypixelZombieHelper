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
import java.util.HashMap;
import java.util.Map;

public class ScoreboardOverlay {
    private static final File CONFIG_FILE = new File("config/hypixelzombiehelper.json");
    private static int posX = 10; // Position par défaut X
    private static int posY = 20; // Position par défaut Y
    private static Map<String, String> scoreboardData = new HashMap<>();

    public static void renderInClient(DrawContext drawContext, RenderTickCounter tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;

        // Récupérer les données de la scoreboard
        Map<String, String> scoreboardData = ScoreboardUtils.getScoreboardData();

        // Texte à afficher
        String[] lines = {
                "Hypixel Zombies Stats",
                "Round: " + scoreboardData.getOrDefault("Round", "N/A"),
                "Zombies Left: " + scoreboardData.getOrDefault("ZombiesLeft", "N/A"),
                "Zombie Kills: " + scoreboardData.getOrDefault("ZombieKills", "N/A"),
                "Time: " + scoreboardData.getOrDefault("Time", "N/A"),
                "Area: " + scoreboardData.getOrDefault("Area", "N/A")
        };

        // Calculer la largeur maximale du texte
        int maxTextWidth = 0;
        for (String line : lines) {
            int lineWidth = textRenderer.getWidth(line);
            if (lineWidth > maxTextWidth) {
                maxTextWidth = lineWidth;
            }
        }

        // Calculer la hauteur totale du texte
        int textHeight = textRenderer.fontHeight * lines.length;

        // Définir les marges
        int paddingX = 5; // Marge horizontale
        int paddingY = 5; // Marge verticale

        // Calculer la taille du fond
        int backgroundWidth = maxTextWidth + 2 * paddingX;
        int backgroundHeight = textHeight + 2 * paddingY;

        // Draw a semi-transparent background
        drawContext.fill(posX - paddingX, posY - paddingY, posX + backgroundWidth, posY + backgroundHeight, 0x88000000);

        // Display text
        int yOffset = 0;
        for (String line : lines) {
            drawContext.drawText(textRenderer, Text.of(line), posX, posY + yOffset, 0xFFFFFF, true);
            yOffset += textRenderer.fontHeight;
        }
    }

    public static void updateData() {
        scoreboardData = ScoreboardUtils.getScoreboardData();
    }

    public static void setPosition(int x, int y) {
        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // Prevent placing the overlay outside the screen
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

    public static void renderInConfigScreen(DrawContext drawContext, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;

        TextRenderer textRenderer = client.textRenderer;

        // Récupérer les données de la scoreboard
        Map<String, String> scoreboardData = ScoreboardUtils.getScoreboardData();

        // Texte à afficher
        String[] lines = {
                "Hypixel Zombies Stats",
                "Round: " + scoreboardData.getOrDefault("Round", "N/A"),
                "Zombies Left: " + scoreboardData.getOrDefault("ZombiesLeft", "N/A"),
                "Zombie Kills: " + scoreboardData.getOrDefault("ZombieKills", "N/A"),
                "Time: " + scoreboardData.getOrDefault("Time", "N/A"),
                "Area: " + scoreboardData.getOrDefault("Area", "N/A")
        };

        // Calculer la largeur maximale du texte
        int maxTextWidth = 0;
        for (String line : lines) {
            int lineWidth = textRenderer.getWidth(line);
            if (lineWidth > maxTextWidth) {
                maxTextWidth = lineWidth;
            }
        }

        // Calculer la hauteur totale du texte
        int textHeight = textRenderer.fontHeight * lines.length;

        // Définir les marges
        int paddingX = 5; // Marge horizontale
        int paddingY = 5; // Marge verticale

        // Calculer la taille du fond
        int backgroundWidth = maxTextWidth + 2 * paddingX;
        int backgroundHeight = textHeight + 2 * paddingY;

        // Draw a semi-transparent background
        drawContext.fill(posX - paddingX, posY - paddingY, posX + backgroundWidth, posY + backgroundHeight, 0x88000000);

        // Display text
        int yOffset = 0;
        for (String line : lines) {
            drawContext.drawText(textRenderer, Text.of(line), posX, posY + yOffset, 0xFFFFFF, true);
            yOffset += textRenderer.fontHeight;
        }
    }
}


