package bzh.edgar.polycraftool;

import bzh.edgar.polycraftool.config.ModConfig;
import bzh.edgar.polycraftool.logocollapse.Collapser;
import com.mojang.brigadier.tree.RootCommandNode;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.command.CommandSource;

import java.util.Objects;
import java.util.WeakHashMap;

public class PolycraftoolClient implements ClientModInitializer {

    private static final WeakHashMap<MinecraftClient, PolycraftoolClient> INSTANCE = new WeakHashMap<>(1, 1);

    public static PolycraftoolClient getInstance(MinecraftClient client) {
        return Objects.requireNonNull(INSTANCE.get(client));
    }

    private final MinecraftClient client;

    public KeyBindings keyBindings;
    public ConfigHolder<ModConfig> config;
    public AutoLoginManager autoLoginManager = new AutoLoginManager(this);
    public Collapser collapser;


    @SuppressWarnings("unused") // This constructor is actually called by Fabric
    public PolycraftoolClient() {
        this(MinecraftClient.getInstance());
    }

    public PolycraftoolClient(MinecraftClient client) {
        this.client = client;
        INSTANCE.put(client, this);
    }

    @Override
    public void onInitializeClient() {
        AutoConfig.register(ModConfig.class, Toml4jConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class);
        collapser = new Collapser(this);

        keyBindings = new KeyBindings();

        ClientTickEvents.END_CLIENT_TICK.register(keyBindings::tick);
        ClientLoginConnectionEvents.DISCONNECT.register((h, client) -> autoLoginManager.resetState(client));
    }

    /**
     * Returns true if the current player is logged into Polycraft, based on the existence of the "/rezo" command and
     * other heuristics
     * @param strict When false, the `enableEverywhere` will be able to override the returned value. When `true`, it
     *               will ignore it.
     * @see ModConfig#enableEverywhere
     */
    public boolean isOnPolycraft(boolean strict) {
        if (config.getConfig().enableEverywhere && !strict) return true;

        ClientPlayerEntity player = client.player;
        RootCommandNode<CommandSource> commandNode = player != null ? player.networkHandler.getCommandDispatcher().getRoot() : null;
        if (commandNode != null) {
            if (commandNode.getChildren().size() > 0 && client.world != null) {
                return commandNode.getChild("rezo") != null;
            } else {
                ServerInfo entry = client.getCurrentServerEntry();
                return entry != null && entry.address.equals("play.polycraft.fr");
            }
        } else {
            return false;
        }
    }

}
