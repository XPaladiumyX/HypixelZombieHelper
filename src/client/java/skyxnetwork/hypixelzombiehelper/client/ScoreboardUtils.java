package skyxnetwork.hypixelzombiehelper.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardUtils {

    public static Map<String, String> getScoreboardData() {
        Map<String, String> scoreboardData = new HashMap<>();
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return scoreboardData;

        Scoreboard scoreboard = client.player.getScoreboard();
        ScoreboardObjective objective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);

        if (objective == null) return scoreboardData;

        // Récupérer tous les détenteurs de score (ScoreHolder)
        for (ScoreHolder holder : scoreboard.getKnownScoreHolders()) {
            ReadableScoreboardScore score = scoreboard.getScore(holder, objective);

            // Vérifier si le score est valide
            if (score == null) continue;

            String entry = holder.getNameForScoreboard(); // Récupérer l'entrée affichée (le texte de la scoreboard)
            int scoreValue = score.getScore(); // Récupérer la valeur du score

            // Extraire les informations utiles de la scoreboard
            if (entry.startsWith("Round ")) {
                scoreboardData.put("Round", entry.replace("Round ", "").trim());
            } else if (entry.startsWith("Zombies Left : ")) {
                scoreboardData.put("ZombiesLeft", entry.replace("Zombies Left : ", "").trim());
            } else if (entry.startsWith("Zombie Kills: ")) {
                scoreboardData.put("ZombieKills", entry.replace("Zombie Kills: ", "").trim());
            } else if (entry.startsWith("Time: ")) {
                scoreboardData.put("Time", entry.replace("Time: ", "").trim());
            } else if (entry.startsWith("Area: ")) {
                scoreboardData.put("Area", entry.replace("Area: ", "").trim());
            } else if (entry.contains(":")) {
                // Gestion des joueurs et de leurs points
                String[] parts = entry.split(":");
                if (parts.length == 2) {
                    String playerName = parts[0].trim();
                    String playerScore = parts[1].trim();
                    scoreboardData.put("Player_" + playerName, playerScore);
                }
            }
        }

        return scoreboardData;
    }
}