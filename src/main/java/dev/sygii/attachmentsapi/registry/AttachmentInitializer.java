package dev.sygii.attachmentsapi.registry;

public interface AttachmentInitializer {
    void declareAttachments(AttachmentDeclarer declarer);
    void registerAttachments(AttachmentRegistrar registrar);
    void registerSyncedAttachments(SyncedAttachmentRegistrar syncedRegistrar);
    /*void declareAttachments(AttachmentManager manager);
    void registerAttachments(AttachmentManager manager);*/
}
