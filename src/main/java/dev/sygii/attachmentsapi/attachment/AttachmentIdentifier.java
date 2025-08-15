package dev.sygii.attachmentsapi.attachment;

import org.jetbrains.annotations.Nullable;

public class AttachmentIdentifier {
    private final String namespace;
    private final String path;

    protected AttachmentIdentifier(String namespace, String path) {
        this.namespace = validateNamespace(namespace, path);
        this.path = validatePath(namespace, path);
    }

    private AttachmentIdentifier(String[] id) {
        this(id[0], id[1]);
    }

    public AttachmentIdentifier(String id) {
        this(split(id, ':'));
    }

    public static AttachmentIdentifier splitOn(String id, char delimiter) {
        return new AttachmentIdentifier(split(id, delimiter));
    }

    @Nullable
    public static AttachmentIdentifier tryParse(String id) {
        try {
            return new AttachmentIdentifier(id);
        } catch (Exception var2) {
            return null;
        }
    }

    @Nullable
    public static AttachmentIdentifier of(String namespace, String path) {
        try {
            return new AttachmentIdentifier(namespace, path);
        } catch (Exception var3) {
            return null;
        }
    }

    protected static String[] split(String id, char delimiter) {
        String[] strings = new String[]{"minecraft", id};
        int i = id.indexOf(delimiter);
        if (i >= 0) {
            strings[1] = id.substring(i + 1);
            if (i >= 1) {
                strings[0] = id.substring(0, i);
            }
        }

        return strings;
    }

    public String getPath() {
        return this.path;
    }

    public String getNamespace() {
        return this.namespace;
    }

    private static String validatePath(String namespace, String path) {
        if (!isPathValid(path)) {
            throw new RuntimeException("Invalid AttachmentIdentifier path: " + path);
        } else {
            return path;
        }
    }

    private static boolean isPathValid(String path) {
        for(int i = 0; i < path.length(); ++i) {
            if (!isPathCharacterValid(path.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPathCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '/' || character == '.';
    }

    private static String validateNamespace(String namespace, String path) {
        if (!isNamespaceValid(namespace)) {
            throw new RuntimeException("Invalid AttachmentIdentifier namespace: " + namespace);
        } else {
            return namespace;
        }
    }

    private static boolean isNamespaceValid(String namespace) {
        for(int i = 0; i < namespace.length(); ++i) {
            if (!isNamespaceCharacterValid(namespace.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isNamespaceCharacterValid(char character) {
        return character == '_' || character == '-' || character >= 'a' && character <= 'z' || character >= '0' && character <= '9' || character == '.';
    }

    public String toString() {
        return this.namespace + ":" + this.path;
    }
}
