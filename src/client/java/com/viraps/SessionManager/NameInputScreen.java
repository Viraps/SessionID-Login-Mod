package com.viraps.SessionManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class NameInputScreen extends Screen {
    private TextFieldWidget nameField;
    private final AltManager parentScreen;

    public NameInputScreen(AltManager parentScreen) {
        super(Text.literal("Enter Alt Name"));
        this.parentScreen = parentScreen;
    }



    @Override
    protected void init() {
        this.clearChildren();
        nameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 20, 200, 20, Text.literal("Alt Name"));
        this.addSelectableChild(nameField);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Confirm"), button -> {
            String altName = nameField.getText().isEmpty() ? "Cracked Alt" : nameField.getText();
            parentScreen.addAlt(altName);
            MinecraftClient.getInstance().setScreen(parentScreen);
        }).dimensions(this.width / 2 - 50, this.height / 2 + 20, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        nameField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) { // Escape key
            MinecraftClient.getInstance().setScreen(parentScreen);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
