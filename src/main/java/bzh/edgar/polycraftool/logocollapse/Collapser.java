package bzh.edgar.polycraftool.logocollapse;

import bzh.edgar.polycraftool.PolycraftoolClient;
import bzh.edgar.polycraftool.PolytechLocation;
import bzh.edgar.polycraftool.config.ModConfig;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public class Collapser {
    private static final Identifier FONT_ID = new Identifier("polycraftool", "polytech_logo");
    private static final Style FONT_STYLE = Style.EMPTY.withFont(FONT_ID);
    private static final Text POLYTECH_LOGO = Text.literal("P ").setStyle(FONT_STYLE);

    private static final WeakHashMap<Text, Text> CACHE = new WeakHashMap<>();

    private final PolycraftoolClient mod;

    public Collapser(PolycraftoolClient mod) {
        this.mod = mod;

        // Weak ref prevents leaking memory through the lambda function, a common error
        WeakReference<WeakHashMap<Text, Text>> cacheRef = new WeakReference<>(CACHE);
        mod.config.registerSaveListener((h, t) -> {
            WeakHashMap<Text, Text> cache = cacheRef.get();
            if (cache != null) cache.clear();
            return ActionResult.SUCCESS;
        });
    }

    private boolean hasProp(boolean isChat, boolean propIsColored) {
        if (!mod.isOnPolycraft(false)) return false;

        ModConfig config = mod.config.getConfig();
        ModConfig.LocationPrefixTransform transform = isChat ? config.chatMessageTransform : config.displayNameTransform;
        return propIsColored ? transform.enableColor : transform.enableLogo;
    }

    public Style getColoredStyle(PolytechLocation location, boolean isChat) {
        return hasProp(isChat, true) ? Style.EMPTY.withColor(location.color) : Style.EMPTY;
    }

    public Style getColoredStyle(Text base, PolytechLocation location, boolean isChat) {
        return hasProp(isChat, true) ? base.getStyle().withColor(location.color) : base.getStyle();
    }

    public Text transformPrefix(Text basePrefix, boolean isChat) {
        return hasProp(isChat, false) ? POLYTECH_LOGO : basePrefix;
    }

    public Text collapseCached(Text text) {
        try {
            return CACHE.computeIfAbsent(text, this::collapse);
        } catch (Exception e) {
            e.printStackTrace();
            return text;
        }
    }

    private static final Map<String, Integer> TRANSLATABLE = Map.of(
        "chat.type.text", 0,
        "chat.type.advancement.task", 0,
        "chat.type.team.text", 1
    );

    public Text collapse(Text text) {
        Integer offset;
        if (text.getContent() instanceof TranslatableTextContent ttc && (offset = TRANSLATABLE.get(ttc.getKey())) != null) {
            if (ttc.getArg(offset) instanceof Text t) {
                Text prefixText = t.getSiblings().get(0);
                PolytechLocation location = PolytechLocation.fromTeamPrefix(prefixText.getString());

                if (location != null) {
                    Text with0 = Text.empty()
                        .setStyle(getColoredStyle(t, location, true))
                        .append(transformPrefix(prefixText, true))
                        .append(t.getSiblings().get(1))
                        .append(t.getSiblings().get(2));

                    Object[] args = ttc.getArgs().clone();
                    args[offset] = with0;

                    if (offset == 1) {
                        Text arg = (Text) ttc.getArg(0);
                        MutableText mt = MutableText.of(arg.getContent()).setStyle(getColoredStyle(arg, location, true));

                        for (Text sibling : arg.getSiblings()) {
                            mt.append(sibling);
                        }

                        args[0] = mt;
                    }

                    return Text.translatable(ttc.getKey(), args).setStyle(text.getStyle());
                }
            }
        }

        return text;
    }
}
