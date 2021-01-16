package com.brandonjja.taskRun.nms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class NMSUtils {
	
	/**
	 * Sends a title message to the player
	 * 
	 * @param player the player to send the title to
	 * @param message the message for the title
	 * @param subMessage the sub message of the title (empty String for no sub message)
	 */
	public static void sendTitleMessage(Player player, String message, String subMessage) {
		String titleMessage = "{\"text\":\"§a" + message + "\"}";
		String titleSubMessage = "{\"text\":\"" + subMessage + "\"}";
		
		Class<?> enumTitleActionClass = getNMSClass("PacketPlayOutTitle$EnumTitleAction");
		Class<?> iChatBaseCompClass = getNMSClass("IChatBaseComponent");
		
		Object chatSerializer;
		Object chatSerializerSub;
		Object enumTitle;
		Object enumSubTitle;
		Object packetTitleMessage;
		Object packetSubTitleMessage;
		
		try {
			Method a = getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class);
			
			chatSerializer = a.invoke(iChatBaseCompClass, titleMessage);
			chatSerializerSub = a.invoke(iChatBaseCompClass, titleSubMessage);
			
			enumTitle = enumTitleActionClass.getField("TITLE").get(null);
			enumSubTitle = enumTitleActionClass.getField("SUBTITLE").get(null);
			
			Constructor<?> packetPlayOutTitleConstructor = getNMSClass("PacketPlayOutTitle").getConstructor(enumTitleActionClass, iChatBaseCompClass, int.class, int.class, int.class);
			
			packetTitleMessage = packetPlayOutTitleConstructor.newInstance(enumTitle, chatSerializer, 20, 100, 20);
			packetSubTitleMessage = packetPlayOutTitleConstructor.newInstance(enumSubTitle, chatSerializerSub, 20, 100, 20);
			
			sendPacket(player, packetTitleMessage);
			sendPacket(player, packetSubTitleMessage);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	/** Sends a packet to the player */
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
	
	/** Returns the NMS Class with the specified name, if it exists */
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
