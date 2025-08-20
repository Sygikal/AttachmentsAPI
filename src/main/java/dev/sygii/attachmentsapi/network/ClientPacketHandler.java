package dev.sygii.attachmentsapi.network;

import dev.sygii.attachmentsapi.network.packet.S2CUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

import java.util.Objects;
import java.util.UUID;

public class ClientPacketHandler {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(S2CUpdateEntityAttachmentPacket.PACKET_ID, ((client, handler, buf, sender) -> {
            int entityId = buf.readInt();
            String id = buf.readString();
            Object asd = AttachmentManager.READERS.get(id).run(buf, entityId);
            client.execute(() -> {
                if (client.world != null) {
                    Entity entity = client.world.getEntityById(entityId);
                    if (entity != null) {
                        AttachmentManager.SYNCED_ACCESSORS.get(id).set(entity, asd);
                    }
                }
            });
        }));
    }

}
