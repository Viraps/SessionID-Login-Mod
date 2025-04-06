package com.viraps.SessionManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mojang.authlib.GameProfile;
import com.viraps.utils.SetSession;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AltManager extends Screen {
    public List<String> alts = new ArrayList<>();
    private int selectedAltIndex = -1;
    private long lastClickTime = 0;

    public AltManager(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        this.clearChildren();
        loadAlts();

        int buttonWidth = 100;
        int buttonHeight = 20;
        int buttonY = this.height - 30;

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Add"), button -> {
            MinecraftClient.getInstance().setScreen(new AltTypeSelectionScreen(this));
        }).dimensions(this.width / 2 - buttonWidth - 10, buttonY, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Edit"), button -> {
            if (selectedAltIndex >= 0 && selectedAltIndex < alts.size()) {
                MinecraftClient.getInstance().setScreen(new EditAltScreen(this, alts.get(selectedAltIndex), selectedAltIndex));
            }
        }).dimensions(this.width / 2 - 10, buttonY, buttonWidth, buttonHeight).build());

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Remove"), button -> {
            if (selectedAltIndex >= 0 && selectedAltIndex < alts.size()) {
                alts.remove(selectedAltIndex);
                saveAlts();
                selectedAltIndex = -1;
                init();
            }
        }).dimensions(this.width / 2 + buttonWidth + 10, buttonY, buttonWidth, buttonHeight).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawText(this.textRenderer, this.title, this.width / 2 - this.textRenderer.getWidth(this.title) / 2, 20, 0xFFFFFF, true);

        int yStart = 50;
        for (int i = 0; i < alts.size(); i++) {
            String alt = alts.get(i);
            int x = this.width / 2 - 100;
            int y = yStart + i * 35;
            int color = i == selectedAltIndex ? 0xFFAAAAAA : 0xFFFFFFFF;


            if (i == selectedAltIndex) {
                context.fill(x - 5, y - 5, x + 210, y + 25, 0xFF303030);
            } else {
                context.fill(x - 5, y - 5, x + 210, y + 25, 0xFF202020);
            }

            drawPlayerHead(context, alt, x - 30, y);

            context.drawText(this.textRenderer, Text.literal(alt), x + 5, y + 5, color, false);
        }

        String currentUsername = MinecraftClient.getInstance().getSession().getUsername();
        context.drawText(this.textRenderer, Text.literal("Logged in as: " + currentUsername), this.width - 200, 10, 0xFFFFFF, false);
    }

    private void drawPlayerHead(DrawContext context, String username, int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        ItemStack playerHead = new ItemStack(Items.PLAYER_HEAD);
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), username);
        NbtCompound nbt = new NbtCompound();
        playerHead.setNbt(nbt);
        context.drawItem(playerHead, x, y);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int yStart = 50;
        for (int i = 0; i < alts.size(); i++) {
            int x = this.width / 2 - 100;
            int y = yStart + i * 35;
            if (mouseX > x - 5 && mouseX < x + 210 && mouseY > y - 5 && mouseY < y + 25) {
                if (selectedAltIndex == i) {
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - lastClickTime < 500) {
                        String altName = alts.get(i);
                        SetSession.username = altName;
                        SetSession.UUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + altName).getBytes()).toString();
                        SetSession.originalSession = false;
                    }
                }
                selectedAltIndex = i;
                lastClickTime = System.currentTimeMillis();

                return true;
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    public void saveAlts() {
        File file = new File(MinecraftClient.getInstance().runDirectory, "config/altmanager.json");
        try (FileWriter writer = new FileWriter(file)) {
            new Gson().toJson(alts, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadAlts() {
        File file = new File(MinecraftClient.getInstance().runDirectory, "config/altmanager.json");
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<String>>() {}.getType();
                alts = new Gson().fromJson(reader, listType);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addAlt(String altName) {
        alts.add(altName);
        saveAlts();
    }
}
