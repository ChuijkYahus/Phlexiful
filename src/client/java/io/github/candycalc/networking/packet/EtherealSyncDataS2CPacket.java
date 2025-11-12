package io.github.candycalc.networking.packet;

import io.github.candycalc.util.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class EtherealSyncDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        if (client.player != null) {
            ((EntityDataSaver) client.player).getPersistentData().putBoolean("ethereal", buf.readBoolean());
        }
    }
}
