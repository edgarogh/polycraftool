package bzh.edgar.polycraftool;

import bzh.edgar.polycraftool.config.ModConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.WeakHashMap;

/**
 * Manages the auto-login / password storing feature
 */
public class AutoLoginManager {
    private final PolycraftoolClient mod;

    // This map should only have one element at a given time, it is used to tie the last password to the lifetime of a
    // ClientWorld
    private final WeakHashMap<ClientWorld, String> lastPassword = new WeakHashMap<>();
    private boolean autoLoggingIn = false;

    public AutoLoginManager(PolycraftoolClient mod) {
        this.mod = mod;
    }

    public void setLastPassword(MinecraftClient client, String password) {
        this.lastPassword.put(client.world, password);
    }

    public void resetState(MinecraftClient client) {
        lastPassword.remove(client.world);
        autoLoggingIn = false;
    }

    public void askToRememberLast(MinecraftClient client) {
        String lastPassword = this.lastPassword.get(client.world);
        if (lastPassword == null) return;

        Text message = Text.empty().append(
            Text.translatable("login.polycraftool.remember.button")
                .setStyle(
                    Style.EMPTY
                        .withBold(true)
                        .withColor(Formatting.YELLOW)
                        .withClickEvent(new RememberClickEvent(lastPassword))
            ))
            .append(Text.translatable("login.polycraftool.remember").setStyle(Style.EMPTY));

        client.inGameHud.getChatHud().addMessage(message);
    }

    public void remember(String password) {
        ConfigHolder<ModConfig> config = mod.config;
        config.getConfig().password = password;
        config.save();
    }

    public String getSavedPassword() {
        String password = mod.config.getConfig().password;
        return password.isEmpty() ? null : password;
    }

    public boolean isAutoLoggingIn() {
        return autoLoggingIn;
    }

    public void setAutoLoggingIn() {
        this.autoLoggingIn = true;
    }

    public static class RememberClickEvent extends ClickEvent {
        private final String password;

        public RememberClickEvent(String password) {
            super(Action.CHANGE_PAGE, null);
            this.password = password;
        }

        public String getPassword() {
            return password;
        }
    }
}
