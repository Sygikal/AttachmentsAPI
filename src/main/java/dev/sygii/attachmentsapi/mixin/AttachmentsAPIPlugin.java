package dev.sygii.attachmentsapi.mixin;

import com.chocohead.mm.api.ClassTinkerers;
import dev.sygii.attachmentsapi.AttachmentsAPI;
import dev.sygii.attachmentsapi.registry.AttachmentInitializer;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public class AttachmentsAPIPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {
        /*AttachmentsAPI.instance = new AttachmentManager();
        FabricLoader.getInstance()
                .getEntrypoints(AttachmentsAPI.MOD_ID + ":attachments", AttachmentInitializer.class)
                .forEach(initializer -> initializer.declareAttachments(AttachmentsAPI.instance));*/
        FabricLoader.getInstance()
                .getEntrypoints(AttachmentsAPI.MOD_ID + ":attachments", AttachmentInitializer.class)
                .forEach(initializer -> initializer.declareAttachments(AttachmentManager::declareAttachment));
        AttachmentsAPI.LOGGER.info("we're not in kansas anymore");

        for (AttachmentManager.AttachmentDeclaration att : AttachmentManager.getAll().values()) {
            String internalName = att.target();
            ClassTinkerers.addTransformation(internalName, (targetClass) -> {
                targetClass.fields.add(new FieldNode(
                        Opcodes.ACC_PUBLIC,
                        AttachmentsAPI.getAttachmentFieldScheme(att),
                        Type.getDescriptor(att.type()),
                        null,
                        null
                ));
            });
        }

        /*try {
            ClassNode node = getClassNode("net.minecraft.server.network.ServerPlayerEntity");
            for (AttachmentManager.AttachmentDeclaration att : AttachmentManager.getAll().values()) {
                String internalName = att.target();

                if (!internalName.equals(node)) continue;

                node.fields.add(new FieldNode(
                        Opcodes.ACC_PUBLIC,
                        AttachmentsAPI.getAttachmentFieldScheme(att),
                        Type.getDescriptor(att.type()),
                        null,
                        null
                ));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        //net.fabricmc.loader.impl.launch.FabricLauncherBase.getLauncher().;
    }

    public static ClassNode getClassNode(String className) throws IOException {
        // Convert from dot name to slash name
        String internalName = className.replace('.', '/');

        // Get class bytes from classpath
        try (InputStream in = ClassLoader.getSystemResourceAsStream(internalName + ".class")) {
            if (in == null) {
                throw new IOException("Class not found: " + className);
            }

            ClassReader cr = new ClassReader(in);
            ClassNode cn = new ClassNode();
            cr.accept(cn, 0);
            return cn;
        }
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // Optional: dynamically decide which mixins to apply
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        /*System.out.println("trnasformaed");
        for (AttachmentManager.AttachmentDeclaration att : AttachmentManager.getAll().values()) {
            String internalName = att.target();
            System.out.println(internalName + " | " + AttachmentsAPI.getAttachmentFieldScheme(att));
            if (!internalName.equals(targetClassName)) continue;
            System.out.println("trnasformaed");
            targetClass.fields.add(new FieldNode(
                    Opcodes.ACC_PUBLIC,
                    AttachmentsAPI.getAttachmentFieldScheme(att),
                    Type.getDescriptor(att.type()),
                    null,
                    null
            ));
        }*/

    }

}
