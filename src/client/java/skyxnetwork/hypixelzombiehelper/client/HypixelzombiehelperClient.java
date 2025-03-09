package skyxnetwork.hypixelzombiehelper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HypixelzombiehelperClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Enregistrer les KeyBindings
        KeyBindings.register();

        // Charger la position de la scoreboard
        ScoreboardOverlay.loadPosition();

        // Ajouter l'affichage de la scoreboard
        HudRenderCallback.EVENT.register((drawContext, tickDelta) ->
                ScoreboardOverlay.render(drawContext, tickDelta)
        );
    }
}
