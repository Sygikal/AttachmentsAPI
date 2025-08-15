package dev.sygii.attachmentsapi.registry;

import dev.sygii.attachmentsapi.attachment.Attachment;
import dev.sygii.attachmentsapi.attachment.AttachmentIdentifier;

@FunctionalInterface
public interface AttachmentDeclarer {
    //<T> void register(Identifier id, String target, Class<?> fieldType, T defaultValue);
    //<T> Attachment<T> register(AttachmentIdentifier id, String target, Class<?> fieldType, T defaultValue);
    <T> void declareAttachment(AttachmentIdentifier id, String target, Class<?> fieldType, T defaultValue);
}
