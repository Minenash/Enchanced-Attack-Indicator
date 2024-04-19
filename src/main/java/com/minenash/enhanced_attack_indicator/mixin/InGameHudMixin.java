package com.minenash.enhanced_attack_indicator.mixin;

import com.minenash.enhanced_attack_indicator.EnhancedAttackIndicator;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

	@Unique boolean renderFullness = false;

	@Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
	private float setBarProgress(ClientPlayerEntity player, float baseTime) {

		float progress = EnhancedAttackIndicator.getProgress(player.getAttackCooldownProgress(baseTime));

		if (progress == 2.0F)
			renderFullness = true;

		return progress == 2.0F ? 1.0F : progress;

	}

	@Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isAlive()Z"))
	private boolean dontShowPlus(Entity _entity) {
		return false;
	}

	private static final Identifier CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE = new Identifier("hud/crosshair_attack_indicator_full");
	@Inject(method = "renderCrosshair", at = @At(value = "TAIL"))
	private void showPlus(DrawContext context, float tickDelta, CallbackInfo info) {
		if (renderFullness) {
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(
					GlStateManager.SrcFactor.ONE_MINUS_DST_COLOR, GlStateManager.DstFactor.ONE_MINUS_SRC_COLOR, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO
			);
			int j = context.getScaledWindowHeight() / 2 - 7 + 16;
			int k = context.getScaledWindowWidth() / 2 - 8;
			context.drawGuiTexture(CROSSHAIR_ATTACK_INDICATOR_FULL_TEXTURE, k, j, 16, 16);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			renderFullness = false;
		}
	}

	@Redirect(method = "renderHotbar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getAttackCooldownProgress(F)F"))
	private float setHotBarProgress(ClientPlayerEntity player, float baseTime) {
		float progress = EnhancedAttackIndicator.getProgress(player.getAttackCooldownProgress(baseTime));
		return progress == 2.0F ? 0.99F : progress;
	}


}
