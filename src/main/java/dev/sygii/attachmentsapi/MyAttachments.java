package dev.sygii.attachmentsapi;

import dev.sygii.attachmentsapi.attachment.Attachment;
import dev.sygii.attachmentsapi.attachment.AttachmentIdentifier;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;
import dev.sygii.attachmentsapi.registry.AttachmentDeclarer;
import dev.sygii.attachmentsapi.registry.AttachmentInitializer;
import dev.sygii.attachmentsapi.registry.AttachmentRegistrar;
import dev.sygii.attachmentsapi.registry.SyncedAttachmentRegistrar;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;

public class MyAttachments implements AttachmentInitializer {
    //public static Attachment<Boolean> testing;
    public static SyncedAttachment<String> testingSynced;

    @Override
    public void declareAttachments(AttachmentDeclarer declarer) {
        /*declarer.declareAttachment(
                AttachmentIdentifier.of("custom", "spawn_protection"),
                FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_3222"),
                Boolean.class, false);*/
        /*declarer.declareAttachment(
                AttachmentIdentifier.of("custom", "synced"),
                FabricLoader.getInstance().getMappingResolver().mapClassName("intermediary", "net.minecraft.class_1309"),
                String.class, "default"
        );*/
    }

    @Override
    public void registerAttachments(AttachmentRegistrar registrar) {
        //testing = registrar.registerAttachment(AttachmentIdentifier.of("custom", "spawn_protection"));
        //testing2 = manager.registerSyncableAttachment(AttachmentIdentifier.of("custom", "spawn_protection"), (buf, value) -> {buf.writeBoolean(value);}, (buf) -> {buf.readBoolean();});
        //testing3 = manager.registerSyncableAttachment(AttachmentIdentifier.of("custom", "spawn_protection2"), (buf, value) -> {buf.writeInt(value);});
    }

    @Override
    public void registerSyncedAttachments(SyncedAttachmentRegistrar syncedRegistrar) {
        /*testingSynced = syncedRegistrar.registerSyncedAttachment(AttachmentIdentifier.of("custom", "synced"), PacketByteBuf::writeString, PacketByteBuf::readString);
        testingSynced.registerNBTSerializers(
                (nbt, value) -> {
                    nbt.put("test", NbtString.of(value));
                },
                (nbt) -> {
                    return nbt.getString("test");
                });*/
    }
}
