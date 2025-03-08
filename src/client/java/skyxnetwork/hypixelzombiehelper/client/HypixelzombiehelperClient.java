package skyxnetwork.hypixelzombiehelper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HypixelzombiehelperClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Enregistrer les KeyBindings
        KeyBindings.register();

        // Ajouter l'affichage de la scoreboard avec la bonne signature
        HudRenderCallback.EVENT.register(ScoreboardOverlay::render);
    }
}
