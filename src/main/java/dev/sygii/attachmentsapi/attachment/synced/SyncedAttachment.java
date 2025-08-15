package dev.sygii.attachmentsapi.attachment.synced;

import dev.sygii.attachmentsapi.attachment.AttachmentIdentifier;
import dev.sygii.attachmentsapi.network.packet.C2SRequestEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.C2SUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.network.packet.S2CUpdateEntityAttachmentPacket;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class SyncedAttachment<V> {
    private final AttachmentIdentifier id;

    public SyncedAttachment(AttachmentIdentifier id) {
        this.id = id;
    }

    public V get(Object instance) {
        return (V) AttachmentManager.SYNCED_ACCESSORS.get(id.toString()).get(instance);
    }

    public void setAndSyncFromClient(Object instance, V value) {
        set(instance, value);
        syncFromClient(instance, value);
    }

    public void set(Object instance, V value) {
        AttachmentManager.SYNCED_ACCESSORS.get(id.toString()).set(instance, value);
    }

    public void syncFromClient(Object instance, V value) {
        if (instance instanceof Entity entity) {
            net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(new C2SUpdateEntityAttachmentPacket<V>(entity.getUuid(), getId().toString(), value));
        }
        /*if (instance instanceof PlayerEntity player) {
            net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(new C2SUpdatePlayerAttachmentPacket<V>(getId().toString(), value));
        }*/
    }

    public void requestFromServer(Object instance) {
        if (instance instanceof Entity entity) {
            net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.send(new C2SRequestEntityAttachmentPacket<>(entity.getUuid(), getId().toString()));
        }
    }

    public void syncFromServer(ServerPlayerEntity player, Object instance, V value) {
        if (instance instanceof Entity entity) {
            net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking.send(player, new S2CUpdateEntityAttachmentPacket<>(entity.getId(), getId().toString(), value));
        }
    }

    public SyncedAttachment<V> registerNBTSerializers(SyncedAttachment.NBTWriter<V> writer, SyncedAttachment.NBTReader<V> reader) {
        AttachmentManager.NBTWRITERS.put(getId().toString(), writer);
        AttachmentManager.NBTREADERS.put(getId().toString(), reader);
        return this;
    }

    public interface Writer<T> {
        void run(PacketByteBuf buf, T value);
    }

    public interface Reader<T> {
        T run(PacketByteBuf buf);
    }

    public interface NBTWriter<T> {
        void run(NbtCompound nbt, T value);
    }

    public interface NBTReader<T> {
        T run(NbtCompound nbt);
    }

    public AttachmentIdentifier getId() {
        return this.id;
    }
}
