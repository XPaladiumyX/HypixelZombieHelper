package skyxnetwork.hypixelzombiehelper.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class ScoreboardConfigScreen extends Screen {
    private int posX, posY;
    private boolean dragging = false;
    private int offsetX, offsetY;

    // Sauvegarde temporaire de la position pour "Annuler"
    private final int initialPosX;
    private final int initialPosY;

    protected ScoreboardConfigScreen() {
        super(Text.of("Déplacer la Scoreboard"));
        this.posX = ScoreboardOverlay.getPosX();
        this.posY = ScoreboardOverlay.getPosY();

        // Sauvegarde la position initiale pour "Annuler"
        this.initialPosX = posX;
        this.initialPosY = posY;
    }

    @Override
    protected void init() {
        // Ajouter un bouton "Sauvegarder"
        addDrawableChild(ButtonWidget.builder(Text.of("Sauvegarder"), button -> {
            ScoreboardOverlay.setPosition(posX, posY);
            ScoreboardOverlay.savePosition(); // Sauvegarde persistante
            MinecraftClient.getInstance().execute(() -> {
                ScoreboardOverlay.setPosition(posX, posY);
                ScoreboardOverlay.savePosition();
            });
            this.client.setScreen(null); // Ferme l'écran
        }).dimensions(width / 2 - 50, height - 40, 100, 20).build());

        // Ajouter un bouton "Annuler"
        addDrawableChild(ButtonWidget.builder(Text.of("Annuler"), button -> {
            // Rétablit la position initiale
            ScoreboardOverlay.setPosition(initialPosX, initialPosY);
            this.client.setScreen(null); // Ferme l'écran
        }).dimensions(width / 2 - 50, height - 15, 100, 20).build());
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);

        // Rendre l'overlay dans l'interface d'édition
        ScoreboardOverlay.renderInConfigScreen(drawContext, delta);

        drawContext.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Déplacez la scoreboard avec la souris"), width / 2, 20, 0xFFFFFF);

        if (dragging) {
            int screenWidth = this.client.getWindow().getScaledWidth();
            int screenHeight = this.client.getWindow().getScaledHeight();

            // Prevent the overlay from going off-screen
            posX = MathHelper.clamp(mouseX - offsetX, 0, screenWidth - 100);
            posY = MathHelper.clamp(mouseY - offsetY, 0, screenHeight - 50);

            ScoreboardOverlay.setPosition(posX, posY);
            ScoreboardOverlay.savePosition();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Clic gauche
            if (isHoveringOverOverlay(mouseX, mouseY)) {
                dragging = true;
                offsetX = (int) mouseX - ScoreboardOverlay.getPosX();
                offsetY = (int) mouseY - ScoreboardOverlay.getPosY();
                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) { // Relâche le clic gauche
            dragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    private boolean isHoveringOverOverlay(double mouseX, double mouseY) {
        int overlayX = ScoreboardOverlay.getPosX();
        int overlayY = ScoreboardOverlay.getPosY();
        int overlayWidth = 100;
        int overlayHeight = 50;

        return mouseX >= overlayX && mouseX <= overlayX + overlayWidth &&
                mouseY >= overlayY && mouseY <= overlayY + overlayHeight;
    }
}