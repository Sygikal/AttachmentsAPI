package dev.sygii.attachmentsapi;

import dev.sygii.attachmentsapi.network.ClientPacketHandler;
import dev.sygii.attachmentsapi.network.ServerPacketHandler;
import dev.sygii.attachmentsapi.registry.AttachmentInitializer;
import dev.sygii.attachmentsapi.registry.AttachmentManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentsAPIClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
			MyAttachments.testingSynced.requestFromServer(entity);
		});
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (entity instanceof LivingEntity living) {
				System.out.println(MyAttachments.testingSynced.get(living));
				return ActionResult.PASS;
			}
			return ActionResult.FAIL;
		});
		ClientPacketHandler.init();
	}
}
