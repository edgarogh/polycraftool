package bzh.edgar.polycraftool.mixin;

import bzh.edgar.polycraftool.PolycraftoolClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.toast.Toast;
import net.minecraft.client.toast.ToastManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(
        method = "onServerMetadata",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/toast/ToastManager;add(Lnet/minecraft/client/toast/Toast;)V")
    )
    void addInsecureChatToast(ToastManager that, Toast toast) {
        boolean isOnPolycraft = PolycraftoolClient.getInstance(client).isOnPolycraft(false);
        if (!isOnPolycraft) that.add(toast);
    }
}
