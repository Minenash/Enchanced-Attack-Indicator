package com.minenash.enhanced_attack_indicator.config;

public class Config extends MidnightConfig{

    public enum WeaponCoolDownImportance { FIRST, MIDDLE, LAST }

    @Entry public static WeaponCoolDownImportance weaponCoolDownImportance = WeaponCoolDownImportance.MIDDLE;
    @Entry public static boolean disablePickaxesAndShovels = true;
    @Entry public static boolean disableAxes = true;

    @Entry public static boolean showBlockBreaking = true;
    @Entry public static boolean showRangeWeaponDraw = true;
    @Entry public static boolean showItemCooldowns = true;
    @Entry public static boolean showFoodAndPotions = true;
    @Entry public static boolean showSleep = true;
    @Entry public static boolean showItemContainerFullness = true;

}
