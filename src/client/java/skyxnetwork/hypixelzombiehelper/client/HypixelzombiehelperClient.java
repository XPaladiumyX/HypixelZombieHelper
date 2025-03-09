package skyxnetwork.hypixelzombiehelper.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class HypixelzombiehelperClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Save KeyBindings
        KeyBindings.register();

        // Load the scoreboard position
        ScoreboardOverlay.loadPosition();

        // Add scoreboard display
        HudRenderCallback.EVENT.register(ScoreboardOverlay::renderInClient);
    }
}