package io.github.candycalc.registry;

import io.github.candycalc.networking.packet.EtherealSyncDataS2CPacket;
import io.github.candycalc.util.PhlexUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class PacketRegistry {
    // if is in client? if is server?
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(PhlexUtil.Companion.getETHEREAL_SYNC_ID(), EtherealSyncDataS2CPacket::receive);
    }
}
