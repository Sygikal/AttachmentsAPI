package dev.sygii.attachmentsapi.network.packet;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class C2SUpdateEntityAttachmentPacket<V> implements FabricPacket {
    public static final Identifier PACKET_ID = Identifier.of(AttachmentsAPI.MOD_ID, "update_attachment");
    protected final UUID uuid;
    protected final String id;
    private V value;

    public static final PacketType<C2SUpdateEntityAttachmentPacket>  TYPE = PacketType.create(
            PACKET_ID, C2SUpdateEntityAttachmentPacket::new
    );

    public C2SUpdateEntityAttachmentPacket(PacketByteBuf buf) {
        this(buf.readUuid(), buf.readString());
    }

    public C2SUpdateEntityAttachmentPacket(UUID uuid, String id) {
        this.uuid = uuid;
        this.id = id;
    }

    public C2SUpdateEntityAttachmentPacket(UUID uuid, String id, V value) {
        this.uuid = uuid;
        this.id = id;
        this.value = value;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(getUuid());
        buf.writeString(getId());
        AttachmentManager.WRITERS.get(getId()).run(buf, value);
    }

    @Override
    public PacketType<?>  getType() {
        return TYPE;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getId() {
        return this.id;
    }
}
