package com.viraps.mixin.client;

import com.viraps.SessionManager.AltManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.ServerList;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MultiplayerScreen.class)
public class MultiplayerScreenMixin extends Screen {


    protected MultiplayerScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "render", at = @At(value = "TAIL"), cancellable = true)
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {

    }

    @Inject(method = "init", at = @At("RETURN"))
    public void onInit(CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("Altmanager"), (button) -> {
            this.client.setScreen(new AltManager(Text.empty()));
        }).dimensions(15, 10, 120, 20).build());
    }
}

