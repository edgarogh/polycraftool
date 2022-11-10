package bzh.edgar.polycraftool;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public enum PolytechLocation {
    ANGERS(0xf0876f),
    ANNECY_CHAMBERY(0xe6007e, "Annecy-Chambery", "Annecy-Chambéry"),
    CLERMONT_FERRAND(0x86bc24, "Clermont-Ferrand"),
    GRENOBLE(0xbf9c71),
    LILLE(0xe4022e),
    LYON(0x781c00),
    MARSEILLE(0x009cb4),
    MONTPELLIER(0x2581c4),
    NANCY(0xe63023),
    NANTES(0xffdd00),
    NICE_SOPHIA(0xc7d301, "Nice"),
    ORLEANS(0xf39200),
    PARIS_SACLAY(0xa4a2b4, "Saclay"),
    SORBONNE(0xcabeba),
    TOURS(0xa84e98);

    public final String id;
    public final String displayName;
    public final String teamPrefix;
    public final int color;

    PolytechLocation(int color) {
        this(color, null);
    }

    PolytechLocation(int color, String displayName) {
        this(color, displayName, null);
    }

    PolytechLocation(int color, String displayName, String teamPrefix) {
        this.id = this.name().toLowerCase();
        this.displayName = displayName == null ? StringUtils.capitalize(id) : displayName;
        this.teamPrefix = String.format("[%s] ", teamPrefix == null ? this.displayName : teamPrefix);
        this.color = color;
    }

    @Nullable
    public static PolytechLocation fromTeamPrefix(String teamPrefix) {
        return Arrays.stream(values()).filter(l -> l.teamPrefix.equals(teamPrefix)).findAny().orElse(null);
    }
}
