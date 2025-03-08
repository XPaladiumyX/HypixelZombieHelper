package skyxnetwork.hypixelzombiehelper.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    // Déclaration du KeyBinding
    public static final KeyBinding OPEN_OVERLAY = new KeyBinding(
            "key.hypixelzombiehelper.open_overlay", // Identifiant de la touche
            InputUtil.Type.KEYSYM, // Type de touche (clavier)
            GLFW.GLFW_KEY_KP_MULTIPLY, // Touche par défaut (* sur pavé numérique)
            "category.hypixelzombiehelper" // Catégorie dans les contrôles
    );

    public static void register() {
        // Enregistrer la touche dans Minecraft
        KeyBindingHelper.registerKeyBinding(OPEN_OVERLAY);

        // Ajouter un listener pour détecter les appuis
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_OVERLAY.wasPressed()) {
                System.out.println("Touche * pressée ! Ouvrir l'overlay...");
                // Ici, on ouvrira l'interface graphique plus tard
            }
        });
    }
}