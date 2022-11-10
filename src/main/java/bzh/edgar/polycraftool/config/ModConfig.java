package bzh.edgar.polycraftool.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "polycraftool")
public class ModConfig implements ConfigData {
    @ConfigEntry.Category("autologin")
    @ConfigEntry.Gui.Tooltip
    public String password = "";

    @ConfigEntry.Category("location_prefix_transform")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public LocationPrefixTransform chatMessageTransform = new LocationPrefixTransform(true, true);

    @ConfigEntry.Category("location_prefix_transform")
    @ConfigEntry.Gui.CollapsibleObject(startExpanded = true)
    public LocationPrefixTransform displayNameTransform = new LocationPrefixTransform(true, true);

    @ConfigEntry.Category("debug")
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean enableEverywhere = false;

    public static final class LocationPrefixTransform {
        public boolean enableLogo;
        public boolean enableColor;

        public LocationPrefixTransform(boolean enableLogo, boolean enableColor) {
            this.enableLogo = enableLogo;
            this.enableColor = enableColor;
        }
    }
}
