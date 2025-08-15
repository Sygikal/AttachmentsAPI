package dev.sygii.attachmentsapi.registry;

import dev.sygii.attachmentsapi.attachment.AttachmentIdentifier;
import dev.sygii.attachmentsapi.attachment.synced.SyncedAttachment;

@FunctionalInterface
public interface SyncedAttachmentRegistrar {
    <T> SyncedAttachment<T> registerSyncedAttachment(AttachmentIdentifier id, SyncedAttachment.Writer<T> writer, SyncedAttachment.Reader reader);

}
