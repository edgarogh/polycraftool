package bzh.edgar.polycraftool.mixin;

import bzh.edgar.polycraftool.AutoLoginManager;
import bzh.edgar.polycraftool.PolycraftoolClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Shadow @Final private MinecraftClient client;

    @Inject(
        method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V",
        at = @At("HEAD"),
        cancellable = true
    )
    void onAddMessage(Text message, MessageSignatureData signature, int ticks, MessageIndicator indicator, boolean refresh, CallbackInfo ci) {
        PolycraftoolClient mod = PolycraftoolClient.getInstance(client);
        if (!mod.isOnPolycraft(false)) return;

        if (client.player == null) return;
        String rawMessage = message.getString();

        AutoLoginManager alm = mod.autoLoginManager;
        if (rawMessage.startsWith("Please log in using /login")) {
            System.out.println("Attempting auto-login");
            String savedPassword = alm.getSavedPassword();
            if (savedPassword != null) {
                ci.cancel();
                alm.setAutoLoggingIn();
                client.player.sendCommand("login " + savedPassword);
            }
        } else if (rawMessage.startsWith("Successfully logged in")) {
            if (alm.isAutoLoggingIn()) {
                ci.cancel();
                client.inGameHud.setOverlayMessage(Text.translatable("login.polycraftool.logged_in"), true);
            } else {
                alm.askToRememberLast(client);
            }
        }
    }

    @ModifyVariable(
        method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;ILnet/minecraft/client/gui/hud/MessageIndicator;Z)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/ChatHud;logChatMessage(Lnet/minecraft/text/Text;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", shift = At.Shift.AFTER),
        argsOnly = true,
        ordinal = 0
    )
    Text onAddMessageModify(Text message) {
        return PolycraftoolClient.getInstance(client).collapser.collapseCached(message);
    }
}
