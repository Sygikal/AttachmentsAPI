package dev.sygii.attachmentsapi.registry;

import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.attachment.*;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;
import net.minecraft.network.PacketByteBuf;

import java.util.HashMap;
import java.util.Map;

public class AttachmentManager {

    //public static AttachmentManager instance;

    private static final Map<String, AttachmentDeclaration> ATTACHMENT_DECLARATIONS = new HashMap<>();


    public static <T> void declareAttachment(AttachmentIdentifier id, String target, Class<?> type, T defaultValue) {
        AttachmentDeclaration<T> declaration = new AttachmentDeclaration<>(id, target, type, defaultValue);
        ATTACHMENT_DECLARATIONS.put(id.toString(), declaration);
    }

    public static Map<String, AttachmentDeclaration> getAll() {
        return ATTACHMENT_DECLARATIONS;
    }

    public static <T> Attachment<T> registerAttachment(AttachmentIdentifier id) {
        try {
            AttachmentDeclaration<T> pairedDeclaration = ATTACHMENT_DECLARATIONS.get(id.toString());
            Class<?> targetClass = Class.forName(pairedDeclaration.target());
            String fieldName = AttachmentsAPI.getAttachmentFieldScheme(pairedDeclaration);
            T defaultValue = pairedDeclaration.defaultValue();
            AttachmentsAPI.LOGGER.info(String.format("Registering Attachment %s for class %s", id.toString(), pairedDeclaration.target()));
            return new Attachment<T>(id, new FieldAccessor<T>(targetClass, fieldName, defaultValue));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Map<String, FieldAccessor<?>> SYNCED_ACCESSORS = new HashMap<>();
    public static final Map<String, SyncedAttachment.Reader> READERS = new HashMap<>();
    public static final Map<String, SyncedAttachment.Writer> WRITERS = new HashMap<>();
    public static final Map<String, SyncedAttachment.NBTReader> NBTREADERS = new HashMap<>();
    public static final Map<String, SyncedAttachment.NBTWriter> NBTWRITERS = new HashMap<>();

    public static <T> SyncedAttachment<T> registerSyncedAttachment(AttachmentIdentifier id, SyncedAttachment.Writer<T> writer, SyncedAttachment.Reader reader) {
        try {
            AttachmentDeclaration<T> pairedDeclaration = ATTACHMENT_DECLARATIONS.get(id.toString());
            Class<?> targetClass = Class.forName(pairedDeclaration.target());
            String fieldName = AttachmentsAPI.getAttachmentFieldScheme(pairedDeclaration);
            T defaultValue = pairedDeclaration.defaultValue();
            AttachmentsAPI.LOGGER.info(String.format("Registering Synced Attachment %s for class %s", id.toString(), pairedDeclaration.target()));

            SYNCED_ACCESSORS.put(id.toString(), new FieldAccessor<T>(targetClass, fieldName, defaultValue));
            WRITERS.put(id.toString(), writer);
            READERS.put(id.toString(), reader);
            return new SyncedAttachment<T>(id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public record AttachmentDeclaration<T> (AttachmentIdentifier id, String target, Class<?> type, T defaultValue) {}
}