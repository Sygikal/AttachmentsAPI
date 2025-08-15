package dev.sygii.attachmentsapi.network;

import dev.sygii.attachmentsapi.network.packet.C2SRequestEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.C2SUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.S2CUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

import java.util.Objects;
import java.util.UUID;

public class ServerPacketHandler {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(C2SUpdateEntityAttachmentPacket.PACKET_ID, (server, player, handler, buf, responseSender) -> {
            UUID entityId = buf.readUuid();
            String id = buf.readString();
            Object asd = AttachmentManager.READERS.get(id).run(buf);
            server.execute(() -> {
                for (ServerWorld serverWorld : server.getWorlds()) {
                    Entity entity = serverWorld.getEntity(entityId);
                    if (entity != null && Objects.equals(entity.getClass(), AttachmentManager.SYNCED_ACCESSORS.get(id).getTarget())) {
                        AttachmentManager.SYNCED_ACCESSORS.get(id).set(entity, asd);
                    }
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(C2SRequestEntityAttachmentPacket.PACKET_ID, (server, player, handler, buf, responseSender) -> {
            UUID entityUuid = buf.readUuid();
            String id = buf.readString();
            server.execute(() -> {
                Object asd;
                for (ServerWorld serverWorld : server.getWorlds()) {
                    Entity entity = serverWorld.getEntity(entityUuid);
                    Class<?> clazz = AttachmentManager.SYNCED_ACCESSORS.get(id).getTarget();
                    if (entity != null && clazz.isInstance(entity)) {
                        asd = AttachmentManager.SYNCED_ACCESSORS.get(id).get(entity);
                        net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, new S2CUpdateEntityAttachmentPacket<>(entity.getId(), id, asd));
                    }
                }
            });
        });

        /*ServerPlayNetworking.registerGlobalReceiver(C2SUpdatePlayerAttachmentPacket.PACKET_ID, (server, player, handler, buf, responseSender) -> {
            String id = buf.readString();
            Object asd = AttachmentManager.READERS.get(id).run(buf);
            server.execute(() -> {
                AttachmentManager.SYNCED_ACCESSORS.get(id).set(player, asd);
            });
        });*/
    }

}
