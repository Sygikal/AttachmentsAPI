package dev.sygii.attachmentsapi.network.packet;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class S2CUpdateEntityAttachmentPacket<V> implements FabricPacket {
    public static final Identifier PACKET_ID = Identifier.of(AttachmentsAPI.MOD_ID, "update_attachment_from_server");
    protected final int entityid;
    protected final String id;
    private V value;

    public static final PacketType<S2CUpdateEntityAttachmentPacket>  TYPE = PacketType.create(
            PACKET_ID, S2CUpdateEntityAttachmentPacket::new
    );

    public S2CUpdateEntityAttachmentPacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readString());
    }

    public S2CUpdateEntityAttachmentPacket(int entityid, String id) {
        this.entityid = entityid;
        this.id = id;
    }

    public S2CUpdateEntityAttachmentPacket(int entityid, String id, V value) {
        this.entityid = entityid;
        this.id = id;
        this.value = value;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(getEntityId());
        buf.writeString(getId());
        AttachmentManager.WRITERS.get(getId()).run(buf, value);
    }

    @Override
    public PacketType<?>  getType() {
        return TYPE;
    }

    public int getEntityId() {
        return this.entityid;
    }

    public String getId() {
        return this.id;
    }
}
