package dev.sygii.attachmentsapi;

import dev.sygii.attachmentsapi.network.ServerPacketHandler;
import dev.sygii.attachmentsapi.registry.AttachmentInitializer;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentsAPI implements ModInitializer {
	public static final String MOD_ID = "attachmentsapi";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final String attachmentFieldScheme = MOD_ID + "_%s_%s";
	public static final String ATTACHMENT_NBT_KEY = "attachmentsapi:attachments";

	@Override
	public void onInitialize() {
		FabricLoader.getInstance()
				.getEntrypoints(AttachmentsAPI.MOD_ID + ":attachments", AttachmentInitializer.class)
				.forEach(initializer -> {
					initializer.registerAttachments(AttachmentManager::registerAttachment);
					initializer.registerSyncedAttachments(AttachmentManager::registerSyncedAttachment);
				});

		ServerPacketHandler.init();

		LOGGER.info("we're back in kansas?");
	}

	public static String getAttachmentFieldScheme(AttachmentManager.AttachmentDeclaration att) {
		return String.format(attachmentFieldScheme, att.id().getNamespace(), att.id().getPath());
	}
}
