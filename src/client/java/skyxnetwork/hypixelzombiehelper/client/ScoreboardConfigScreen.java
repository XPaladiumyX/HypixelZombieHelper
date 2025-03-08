package skyxnetwork.hypixelzombiehelper.client;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ScoreboardConfigScreen extends Screen {
    private int posX, posY;

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
        super.render(drawContext, mouseX, mouseY, delta); // Appel correct à la superclasse

        // Affichage du texte d'instruction
        drawContext.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Déplacez la scoreboard avec la souris"), width / 2, 20, 0xFFFFFF);

        // Met à jour temporairement la position de la scoreboard pendant le déplacement
        ScoreboardOverlay.setPosition(mouseX - 50, mouseY - 10);
    }

}