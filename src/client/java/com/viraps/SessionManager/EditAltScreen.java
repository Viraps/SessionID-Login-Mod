package com.viraps.SessionManager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;

public class EditAltScreen extends Screen {
    private final AltManager parent;
    private final int altIndex;
    private TextFieldWidget nameField;

    public EditAltScreen(AltManager parent, String altName, int altIndex) {
        super(Text.of("Edit Alt"));
        this.parent = parent;
        this.altIndex = altIndex;
    }


    @Override
    protected void init() {
        nameField = new TextFieldWidget(this.textRenderer, this.width / 2 - 100, this.height / 2 - 10, 200, 20, Text.of("Alt Name"));
        nameField.setText(parent.alts.get(altIndex)); // Pre-fill with current name
        this.addSelectableChild(nameField);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Save"), button -> {
            parent.alts.set(altIndex, nameField.getText());
            parent.saveAlts();
            MinecraftClient.getInstance().setScreen(parent);
        }).dimensions(this.width / 2 - 50, this.height / 2 + 30, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        nameField.render(context, mouseX, mouseY, delta); // Render TextField
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.nameField.keyPressed(keyCode, scanCode, modifiers) || this.nameField.isActive()) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
