package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import net.minecraft.client.resource.language.I18n;

/**
 * This feature speaks when the player xp level is increased or decreased.
 */
public class XPIndicator {
    private static int previousXPLevel;

    public XPIndicator() {
        previousXPLevel = -999;
    }

    public static void runOnTick() {
        int currentXPLevel = PlayerUtils.getExperienceLevel();
        if (previousXPLevel == currentXPLevel) return;

        boolean increased = previousXPLevel < currentXPLevel;
        previousXPLevel = currentXPLevel;

        String toSpeak = (increased) ? I18n.translate("minecraft_access.xp_indicator.increased", currentXPLevel)
                : I18n.translate("minecraft_access.xp_indicator.decreased", currentXPLevel);
        MainClass.speakWithNarrator(toSpeak, true);
    }

    public static void speakCurrentXP() {
        MainClass.speakWithNarrator(currentXpNarration(), true);
    }

    private static String currentXpNarration() {
        return I18n.translate("minecraft_access.access_menu.xp",
                PlayerUtils.getExperienceLevel(),
                PlayerUtils.getExperienceProgress());
    }
}
