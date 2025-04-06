package com.viraps.SessionManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class AltTypeSelectionScreen extends Screen {
    private final AltManager parent;

    public AltTypeSelectionScreen(AltManager parent) {
        super(Text.of("Select Alt Type"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Cracked"), button -> {
            MinecraftClient.getInstance().setScreen(new NameInputScreen(parent));
        }).dimensions(this.width / 2 - 50, this.height / 2 - 30, 100, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Session ID"), button -> {
            MinecraftClient.getInstance().setScreen(new SessionInputGui());
        }).dimensions(this.width / 2 - 50, this.height / 2, 100, 20).build());

    }
}
