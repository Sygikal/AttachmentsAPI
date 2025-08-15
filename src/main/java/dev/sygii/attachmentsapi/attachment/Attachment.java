package dev.sygii.attachmentsapi.attachment;

public class Attachment<V> {
    private final AttachmentIdentifier id;
    private final FieldAccessor<V> accessor;

    public Attachment(AttachmentIdentifier id, FieldAccessor<V> accessor) {
        this.id = id;
        this.accessor = accessor;
    }

    public void set(Object instance, V value) {
        accessor.set(instance, value);
    }

    public V get(Object instance) {
        return accessor.get(instance);
    }

    public AttachmentIdentifier getId() {
        return this.id;
    }
}
