package bzh.edgar.polycraftool.mixin;

import bzh.edgar.polycraftool.AutoLoginManager;
import bzh.edgar.polycraftool.PolycraftoolClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {
    @Shadow @Nullable protected MinecraftClient client;

    @Inject(
        method = "handleTextClick",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/text/ClickEvent;getAction()Lnet/minecraft/text/ClickEvent$Action;", ordinal = 0, shift = At.Shift.BEFORE),
        cancellable = true
    )
    void onHandleTextClick(Style style, CallbackInfoReturnable<Boolean> cir) {
        if (style.getClickEvent() instanceof AutoLoginManager.RememberClickEvent rce) {
            PolycraftoolClient.getInstance(client).autoLoginManager.remember(rce.getPassword());
            if (client != null) {
                client.inGameHud.getChatHud().addMessage(Text.translatable("login.polycraftool.saved"));
            }
            cir.setReturnValue(true);
        }
    }
}
