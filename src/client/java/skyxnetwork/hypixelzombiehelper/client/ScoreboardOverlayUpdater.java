package skyxnetwork.hypixelzombiehelper.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class ScoreboardOverlayUpdater {

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                // Mettre à jour les données de l'overlay
                ScoreboardOverlay.updateData();
                System.out.println("Mise à jour des données de la scoreboard !");
            }
        });
    }
}
