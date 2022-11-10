package bzh.edgar.polycraftool.mixin;

import bzh.edgar.polycraftool.PolycraftoolClient;
import bzh.edgar.polycraftool.PolytechLocation;
import bzh.edgar.polycraftool.logocollapse.Collapser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Team.class)
public class TeamMixin {
    @Inject(
        method = "decorateName(Lnet/minecraft/text/Text;)Lnet/minecraft/text/MutableText;",
        at = @At("HEAD"),
        cancellable = true
    )
    void onDecorateName(Text name, CallbackInfoReturnable<MutableText> cir) {
        PolycraftoolClient mod = PolycraftoolClient.getInstance(MinecraftClient.getInstance());
        if (!mod.isOnPolycraft(false)) return;

        Team that = (Team) (Object) this;
        Text prefix = that.getPrefix();

        PolytechLocation location = PolytechLocation.fromTeamPrefix(prefix.getString());
        if (location != null) {
            Collapser collapser = mod.collapser;

            cir.setReturnValue(Text.empty()
                .setStyle(collapser.getColoredStyle(location, false))
                .append(collapser.transformPrefix(prefix, false))
                .append(name)
                .append(that.getSuffix())
            );
        }
    }
}
