package bzh.edgar.polycraftool;

import bzh.edgar.polycraftool.mixin.MinecraftClientAccessor;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KeyBindings {
    private static final String CATEGORY = "category.polycraftool.all";

    public final KeyBinding kbdOpenTm = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.polycraftool.team_msg",
        GLFW.GLFW_KEY_Y,
        CATEGORY
    ));
    public final CommandKeyBinding kbdTpHome = new CommandKeyBinding("key.polycraftool.tp_home", "home").register();
    public final CommandKeyBinding kbdSetHome = new CommandKeyBinding("key.polycraftool.set_home", "sethome").register();
    public final EnumMap<PolytechLocation, CommandKeyBinding> kbdRezoTp = new EnumMap<>(
        Arrays.stream(PolytechLocation.values()).collect(Collectors.toMap(
            Function.identity(),
            loc -> new CommandKeyBinding("key.polycraftool.rezo_tp." + loc.id, "rezo tp " + loc.displayName).register()
        ))
    );

    public void tick(MinecraftClient client) {
        kbdTpHome.tick(client);
        kbdSetHome.tick(client);
        kbdRezoTp.values().forEach(v -> v.tick(client));
        if (kbdOpenTm.wasPressed()) {
            MinecraftClientAccessor clientAccessor = (MinecraftClientAccessor) client;
            clientAccessor.invokeOpenChatScreen("/tm ");
        }
    }

    static class CommandKeyBinding extends KeyBinding {
        private final String command;

        public CommandKeyBinding(String translationKey, String command) {
            super(translationKey, InputUtil.UNKNOWN_KEY.getCode(), CATEGORY);
            this.command = command;
        }

        public CommandKeyBinding register() {
            return (CommandKeyBinding) KeyBindingHelper.registerKeyBinding(this);
        }

        public void tick(MinecraftClient client) {
            if (client.player != null && this.wasPressed()) {
                client.player.sendCommand(this.command);
            }
        }
    }
}
