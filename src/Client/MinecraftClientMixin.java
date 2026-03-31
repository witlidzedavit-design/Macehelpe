package com.example.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    // We use CallbackInfoReturnable because doAttack now returns a boolean in 1.21.4+
    @Inject(method = "doAttack", at = @At("HEAD"))
    private void onAttack(CallbackInfoReturnable<Boolean> info) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.getNetworkHandler() != null) {
            // Hotbar slots are 0-8. 
            // Slot 1 is the 2nd slot in your hotbar. 
            // Put your Mace with Breach here!
            int maceSlot = 1; 
            
            // This sends the swap packet to the server 
            // at the exact moment you click to attack.
            client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(maceSlot));
        }
    }
}

