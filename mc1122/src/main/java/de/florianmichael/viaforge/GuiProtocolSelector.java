package de.florianmichael.viaforge;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import de.florianmichael.vialoadingbase.ViaLoadingBase;
import de.florianmichael.vialoadingbase.platform.InternalProtocolList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiProtocolSelector extends GuiScreen {

    private final GuiScreen parent;
    private SlotList list;

    public GuiProtocolSelector(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(1, width / 2 - 100, height - 27, 200, 20, "Back"));
        list = new SlotList(mc, width, height, 32, height - 32, 10);
    }

    @Override
    protected void actionPerformed(GuiButton p_actionPerformed_1_) throws IOException {
        list.actionPerformed(p_actionPerformed_1_);

        if (p_actionPerformed_1_.id == 1) mc.displayGuiScreen(parent);
    }

    @Override
    public void handleMouseInput() throws IOException {
        list.handleMouseInput();
        super.handleMouseInput();
    }

    @Override
    public void drawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_) {
        list.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);

        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        this.drawCenteredString(this.fontRenderer, ChatFormatting.GOLD + "ViaForge", this.width / 4, 6, 16777215);
        GL11.glPopMatrix();

        drawString(this.fontRenderer, "by EnZaXD/Flori2007", 1, 1, -1);
        drawString(this.fontRenderer, "Discord: EnZaXD#6257", 1, 11, -1);

        super.drawScreen(p_drawScreen_1_, p_drawScreen_2_, p_drawScreen_3_);
    }

    class SlotList extends GuiSlot {

        public SlotList(Minecraft p_i1052_1_, int p_i1052_2_, int p_i1052_3_, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
            super(p_i1052_1_, p_i1052_2_, p_i1052_3_, p_i1052_4_, p_i1052_5_, p_i1052_6_);
        }

        @Override
        protected int getSize() {
            return InternalProtocolList.getProtocols().size();
        }

        @Override
        protected void elementClicked(int i, boolean b, int i1, int i2) {
            ViaLoadingBase.getClassWrapper().reload(InternalProtocolList.getProtocols().get(i));
        }

        @Override
        protected boolean isSelected(int i) {
            return false;
        }

        @Override
        protected void drawBackground() {
            drawDefaultBackground();
        }

        @Override
        protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5, float v) {
            final ProtocolVersion version = InternalProtocolList.getProtocols().get(i);

            drawCenteredString(mc.fontRenderer,(ViaLoadingBase.getClassWrapper().getTargetVersion().getVersion() == version.getVersion() ? ChatFormatting.GREEN.toString() : ChatFormatting.DARK_RED.toString()) + version.getName(), width / 2, i2, -1);
        }
    }
}
