package dev.sygii.attachmentsapi.attachment;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;

public class FieldAccessor<T> {
    private final MethodHandle getter;
    private final MethodHandle setter;
    private final Class<?> target;
    private final T defaultValue;

    public FieldAccessor(Class<?> ownerClass, String fieldName, T defaultValue) {
        try {
            MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(ownerClass, MethodHandles.lookup());
            Field field = ownerClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            this.target = ownerClass;
            this.getter = lookup.unreflectGetter(field);
            this.setter = lookup.unreflectSetter(field);
            this.defaultValue = defaultValue;
        } catch (Throwable t) {
            throw new RuntimeException("Failed to create FieldAccessor", t);
        }
    }

    public Class<?> getTarget() {
        return this.target;
    }

    @SuppressWarnings("unchecked")
    public T get(Object instance) {
        try {
            T value = (T) getter.invoke(instance);
            return value != null ? value : defaultValue;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public <T> void set(Object instance, T value) {
        try {
            setter.invoke(instance, value);
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }
}
