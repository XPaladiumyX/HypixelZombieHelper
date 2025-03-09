package skyxnetwork.hypixelzombiehelper.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ScoreboardConfigScreen extends Screen {
    private int posX, posY;
    private boolean dragging = false;
    private int offsetX, offsetY;

    protected ScoreboardConfigScreen() {
        super(Text.of("Déplacer la Scoreboard"));
        this.posX = ScoreboardOverlay.getPosX();
        this.posY = ScoreboardOverlay.getPosY();
    }

    @Override
    protected void init() {
        // Ajouter un bouton "Sauvegarder"
        addDrawableChild(ButtonWidget.builder(Text.of("Sauvegarder"), button -> {
            ScoreboardOverlay.setPosition(posX, posY);
            this.client.setScreen(null); // Fermer l'écran
        }).dimensions(width / 2 - 50, height - 40, 100, 20).build());

        // Ajouter un bouton "Annuler"
        addDrawableChild(ButtonWidget.builder(Text.of("Annuler"), button -> {
            this.client.setScreen(null); // Fermer l'écran
        }).dimensions(width / 2 - 50, height - 15, 100, 20).build());
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float delta) {
        super.render(drawContext, mouseX, mouseY, delta);

        drawContext.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Déplacez la scoreboard avec la souris"), width / 2, 20, 0xFFFFFF);

        // Déplacement seulement si on est en train de drag
        if (dragging) {
            ScoreboardOverlay.setPosition(mouseX - offsetX, mouseY - offsetY);
        }

        // Affichage clair et non flou
        ScoreboardOverlay.render(drawContext, delta);
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
        int overlayWidth = 100;  // Largeur de la scoreboard
        int overlayHeight = 50;  // Hauteur de la scoreboard

        return mouseX >= overlayX && mouseX <= overlayX + overlayWidth &&
                mouseY >= overlayY && mouseY <= overlayY + overlayHeight;
    }
}