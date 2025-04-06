package com.viraps.utils;


import net.minecraft.client.MinecraftClient;

public class SetSession {

    public static String username = "username";
    public static String accessToken = "access-token";
    public static String UUID = "uuid";
    public static String sessionid = "token:" + accessToken + ":" + UUID;

    public static boolean originalSession = true;
    public static MinecraftClient mc = MinecraftClient.getInstance();



    public static java.util.UUID getUuidOrNull() {
        try {
            return java.util.UUID.fromString(UUID);
        } catch (IllegalArgumentException var2) {
            return null;
        }
    }
}
