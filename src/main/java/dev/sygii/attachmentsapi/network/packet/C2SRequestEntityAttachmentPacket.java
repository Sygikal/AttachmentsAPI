package dev.sygii.attachmentsapi.network.packet;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.UUID;

public class C2SRequestEntityAttachmentPacket<V> implements FabricPacket {
    public static final Identifier PACKET_ID = Identifier.of(AttachmentsAPI.MOD_ID, "request_attachment");
    protected final UUID uuid;
    protected final String id;
    private V value;

    public static final PacketType<C2SRequestEntityAttachmentPacket>  TYPE = PacketType.create(
            PACKET_ID, C2SRequestEntityAttachmentPacket::new
    );

    public C2SRequestEntityAttachmentPacket(PacketByteBuf buf) {
        this(buf.readUuid(), buf.readString());
    }

    public C2SRequestEntityAttachmentPacket(UUID uuid, String id) {
        this.uuid = uuid;
        this.id = id;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeUuid(getUuid());
        buf.writeString(getId());
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
