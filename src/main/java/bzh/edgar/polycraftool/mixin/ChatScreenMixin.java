package bzh.edgar.polycraftool.mixin;

import bzh.edgar.polycraftool.AutoLoginManager;
import bzh.edgar.polycraftool.PolycraftoolClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ChatScreen.class)
public class ChatScreenMixin extends Screen {
    private ChatScreenMixin(Text title) {
        super(title);
    }

    @Inject(
        method = "sendMessage",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;sendCommand(Ljava/lang/String;Lnet/minecraft/text/Text;)V", shift = At.Shift.AFTER)
    )
    void onSendMessage(String chatText, boolean addToHistory, CallbackInfoReturnable<Boolean> cir) {
        PolycraftoolClient mod = PolycraftoolClient.getInstance(client);
        if (!mod.isOnPolycraft(false)) return;

        if (chatText.startsWith("/login ")) {
            String password = chatText.substring(7);
            if (password.length() > 0) {
                AutoLoginManager alm = mod.autoLoginManager;
                alm.resetState(Objects.requireNonNull(client));
                alm.setLastPassword(client, password);
            }
        }
    }
}
