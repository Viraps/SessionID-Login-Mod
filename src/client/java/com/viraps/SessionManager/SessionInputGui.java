package com.viraps.SessionManager;

import com.viraps.utils.SetSession;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;




public class SessionInputGui extends Screen {
    private ButtonWidget loginbutton;
    private TextFieldWidget usernameField;
    private TextFieldWidget accessTokenField;
    private TextFieldWidget uuidField;

    public SessionInputGui() {
        super(Text.of("Session Input"));
    }

    @Override
    protected void init() {
        super.init();

        int centerX = this.width / 2;

        // Username Field
        usernameField = new TextFieldWidget(this.textRenderer, centerX - 100, 40, 200, 20, Text.of("Username"));
        usernameField.setText(SetSession.username);
        this.addSelectableChild(usernameField);

        // Access Token Field
        accessTokenField = new TextFieldWidget(this.textRenderer, centerX - 100, 80, 200, 20, Text.of("Access Token"));
        accessTokenField.setText(SetSession.accessToken);
        this.addSelectableChild(accessTokenField);

        // UUID Field
        uuidField = new TextFieldWidget(this.textRenderer, centerX - 100, 120, 200, 20, Text.of("UUID"));
        uuidField.setText(SetSession.UUID);
        this.addSelectableChild(uuidField);

        accessTokenField.setMaxLength(100000000);
        uuidField.setMaxLength(100000000);


        // Login Button
        ButtonWidget.Builder login = ButtonWidget.builder(Text.literal("Login"), button -> {

            SetSession.username = usernameField.getText();
            SetSession.accessToken = accessTokenField.getText();
            SetSession.UUID = uuidField.getText();
            SetSession.originalSession = false;




            MinecraftClient.getInstance().setScreen(null);
        });
        login.dimensions(centerX - 100, 160, 200, 20);
        login.tooltip(Tooltip.of(Text.literal("You gotta lock in!")));
        loginbutton = login
                .build();
        this.addDrawableChild(loginbutton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        usernameField.render(context, mouseX, mouseY, delta);
        accessTokenField.render(context, mouseX, mouseY, delta);
        uuidField.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.usernameField.keyPressed(keyCode, scanCode, modifiers) || this.usernameField.isActive()) {
            return true;
        }
        if (this.accessTokenField.keyPressed(keyCode, scanCode, modifiers) || this.accessTokenField.isActive()) {
            return true;
        }
        if (this.uuidField.keyPressed(keyCode, scanCode, modifiers) || this.uuidField.isActive()) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
