package com.viraps.mixin.client;

import com.viraps.utils.SetSession;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Session.class)
public class SessionMixin {

    @Inject(at = @At("TAIL"), method = "getSessionId", cancellable = true)
    private void getSessionId(CallbackInfoReturnable<String> cir) {
        if (SetSession.originalSession) return;
        cir.setReturnValue("token:" + SetSession.accessToken + ":" + SetSession.UUID);
    }

    @Inject(at = @At("TAIL"), method = "getAccessToken", cancellable = true)
    private void getAccessToken(CallbackInfoReturnable<String> cir) {
        if (SetSession.originalSession) return;
        cir.setReturnValue(SetSession.accessToken);
    }


    @Inject(at = @At("TAIL"), method = "getUsername", cancellable = true)
    private void getUsername(CallbackInfoReturnable<String> cir) {
        if (SetSession.originalSession) return;
        cir.setReturnValue(SetSession.username);
    }

    @Inject(at = @At("TAIL"), method = "getUuidOrNull", cancellable = true)
    private void getUuidOrNull(CallbackInfoReturnable<UUID> cir) {
        if (SetSession.originalSession) return;
        cir.setReturnValue(UUID.fromString(SetSession.UUID));
    }
}
