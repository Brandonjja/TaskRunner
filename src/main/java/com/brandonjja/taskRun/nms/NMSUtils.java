package com.brandonjja.taskRun.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class NMSUtils {

    private static final int FADE_IN = 20;
    private static final int DURATION = 100;
    private static final int FADE_OUT = 20;

    private static String version = null;

    /**
     * Sends a title message to the player
     *
     * @param player     the player to send the title to
     * @param message    the message for the title
     * @param subMessage the sub message of the title (empty String for no sub message)
     */
    public static void sendTitleMessage(Player player, String message, String subMessage) {
        String titleMessage = "{\"text\":\"" + ChatColor.GREEN + message + "\"}";
        String titleSubMessage = "{\"text\":\"" + subMessage + "\"}";

        if (isAtLeastOneTwelve()) {
            player.sendTitle(ChatColor.GREEN + message, subMessage);
            return;
        }

        Class<?> enumTitleActionClass = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
        Class<?> iChatBaseCompClass = getNMSClass("IChatBaseComponent");

        Object chatSerializer;
        Object chatSerializerSub;

        Object enumTimes;
        Object enumTitle;
        Object enumSubTitle;

        Object packetTimes;
        Object packetTitleMessage;
        Object packetSubTitleMessage;

        try {
            Method a = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);

            chatSerializer = a.invoke(iChatBaseCompClass, titleMessage);
            chatSerializerSub = a.invoke(iChatBaseCompClass, titleSubMessage);

            enumTimes = enumTitleActionClass.getField("TIMES").get(null);
            enumTitle = enumTitleActionClass.getField("TITLE").get(null);
            enumSubTitle = enumTitleActionClass.getField("SUBTITLE").get(null);

            Constructor<?> packetPlayOutTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(enumTitleActionClass, iChatBaseCompClass, int.class, int.class, int.class);

            packetTimes = packetPlayOutTitleConstructor.newInstance(enumTimes, chatSerializer, FADE_IN, DURATION, FADE_OUT);
            packetTitleMessage = packetPlayOutTitleConstructor.newInstance(enumTitle, chatSerializer, FADE_IN, DURATION, FADE_OUT);
            packetSubTitleMessage = packetPlayOutTitleConstructor.newInstance(enumSubTitle, chatSerializerSub, FADE_IN, DURATION, FADE_OUT);

            sendPacket(player, packetTimes);
            sendPacket(player, packetTitleMessage);
            sendPacket(player, packetSubTitleMessage);

        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                 | NoSuchMethodException | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static String getVersion() {
        if (version == null) {
            version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        }

        return version;
    }

    public static boolean isAtLeastOneNine() {
        String version = getVersion();
        int ver = Integer.parseInt(version.split("_")[1]);
        return ver >= 9;
    }

    public static boolean isAtLeastOneTwelve() {
        String version = getVersion();
        int ver = Integer.parseInt(version.split("_")[1]);
        return ver >= 12;
    }

    public static boolean isAtLeastOneThirteen() {
        String version = getVersion();
        int ver = Integer.parseInt(version.split("_")[1]);
        return ver >= 13;
    }

    /**
     * Sends a packet to the provided player
     *
     * @param player the player to send a packet to
     * @param packet the packet to send
     */
    private static void sendPacket(Player player, Object packet) {
        try {
            Object playerHandle = player.getClass().getMethod("getHandle").invoke(player);
            Object playerConnection = playerHandle.getClass().getDeclaredField("playerConnection").get(playerHandle);
            playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(playerConnection, packet);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                 | SecurityException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param name the name of an NMS class to search for
     * @return the NMS Class with the specified name, if it exists
     */
    private static Class<?> getNMSClass(String name) {
        try {
            String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            return Class.forName("net.minecraft.server." + version + "." + name);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
