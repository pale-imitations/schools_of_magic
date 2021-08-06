package com.paleimitations.schoolsofmagic.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.paleimitations.schoolsofmagic.References;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.data.BookUtils;
import com.paleimitations.schoolsofmagic.common.data.books.PageElement;
import com.paleimitations.schoolsofmagic.common.data.books.ButtonPageElement;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GrimoireScreen extends Screen {

    private ItemStack stack;
    private final int imageWidth = 256;
    private final int imageHeight = 256;
    private static ResourceLocation[] COVERS = new ResourceLocation[DyeColor.values().length+1];
    private static ResourceLocation[] BINDINGS = new ResourceLocation[BindingType.values().length];
    private static final ResourceLocation PAPER = new ResourceLocation(References.MODID, "textures/gui/books/grimoire_paper.png");
    private static final ResourceLocation MENU_OPTIONS = new ResourceLocation(References.MODID, "textures/gui/books/menu_options.png");
    static{
        COVERS[0] = new ResourceLocation(References.MODID, "textures/gui/books/cover.png");
        for(DyeColor color : DyeColor.values())
            COVERS[color.getId()+1] = new ResourceLocation(References.MODID, "textures/gui/books/cover_"+color.getName()+".png");
        for(BindingType binding : BindingType.values())
            BINDINGS[binding.ordinal()] = new ResourceLocation(References.MODID, "textures/gui/books/grimoire_"+binding.getSerializedName()+"_bindings.png");
    }
    private int leftPos;
    private int topPos;
    private TurnPageButton nextPage, backPage;
    private TurnChapterButton nextChapter, backChapter;
    private IndexReturnButton indexReturn;
    private CloseButton closeBook;
    private PlayerEntity player;
    private Hand hand;

    public GrimoireScreen(ItemStack stack, Hand hand) {
        super(NarratorChatListener.NO_TITLE);
        this.stack = stack;
        this.player = Minecraft.getInstance().player;
        this.hand = hand;
    }

    public boolean keyPressed(int p_231046_1_, int p_231046_2_, int p_231046_3_) {
        if (super.keyPressed(p_231046_1_, p_231046_2_, p_231046_3_)) {
            return true;
        } else {
            switch(p_231046_1_) {
                case 266:
                    this.nextPage.onPress();
                    return true;
                case 267:
                    this.backPage.onPress();
                    return true;
                default:
                    return false;
            }
        }
    }

    protected void chapterBack() {
        if(stack!=null) {
            CompoundNBT nbt = stack.getTag();
            int page = nbt.getInt("page");
            BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
            if(data!=null) {
                Tuple<Integer, Integer> chapters = data.getSurroundingChapters(page);
                ActionResult<ItemStack> result = BookUtils.turnPage(data, stack, chapters.getA(), hand);
                if (result.getResult() == ActionResultType.SUCCESS) {
                    this.stack = result.getObject();
                    player.setItemInHand(hand, this.stack);
                    this.updateButtonVisibility();
                }
            }
        }
    }

    protected void chapterForward() {
        if(stack!=null) {
            CompoundNBT nbt = stack.getTag();
            int page = nbt.getInt("page");
            BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
            if(data!=null) {
                Tuple<Integer, Integer> chapters = data.getSurroundingChapters(page);
                ActionResult<ItemStack> result = BookUtils.turnPage(data, stack, chapters.getB(), hand);
                if (result.getResult() == ActionResultType.SUCCESS) {
                    this.stack = result.getObject();
                    player.setItemInHand(hand, this.stack);
                    this.updateButtonVisibility();
                }
            }
        }
    }

    protected void pageBack() {
        if(stack!=null) {
            BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
            ActionResult<ItemStack> result = BookUtils.turnPage(data, stack, false, hand);
            if(result.getResult()== ActionResultType.SUCCESS) {
                this.stack = result.getObject();
                player.setItemInHand(hand, this.stack);
                this.updateButtonVisibility();
            }
        }
    }

    protected void pageForward() {
        if(stack!=null) {
            BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
            ActionResult<ItemStack> result = BookUtils.turnPage(data, stack, true, hand);
            if(result.getResult() == ActionResultType.SUCCESS) {
                this.stack = result.getObject();
                player.setItemInHand(hand, this.stack);
                this.updateButtonVisibility();
            }
        }
    }

    protected void indexReturn() {
        if(stack!=null) {
            BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
            ActionResult<ItemStack> result = BookUtils.turnPage(data, stack, 0, hand);
            if(result.getResult() == ActionResultType.SUCCESS) {
                this.stack = result.getObject();
                player.setItemInHand(hand, this.stack);
                this.updateButtonVisibility();
            }
        }
    }

    private void updateButtonVisibility() {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(stack!=null) {
            int page = nbt.getInt("page");
            int subpage = nbt.getInt("subpage");
            BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
            if(data!=null && data.getBookPage(page)!=null) {
                boolean flag = false;
                for (int i = subpage + 1; i < data.getBookPage(page).getSubPages(); ++i)
                    if (!data.getBookPage(page).isSubPageBlank(i)) flag = true;
                this.nextPage.visible = page + 1 < data.getNumPages()|| flag;
                this.backPage.visible = page > 0 || subpage > 0;
                Tuple<Integer, Integer> chaps = data.getSurroundingChapters(page);
                this.nextChapter.visible = chaps.getB()!=page;
                this.backChapter.visible = chaps.getA()!=page;
            }
            else {
                this.nextPage.visible = false;
                this.backPage.visible = false;
            }
            this.indexReturn.visible = !(page==0 && subpage==0);
        }
        else {
            this.nextPage.visible = false;
            this.backPage.visible = false;
        }
    }

    @Override
    protected void init() {
        super.init();
        this.leftPos = (this.width - this.imageWidth) / 2;
        this.topPos = (this.height - this.imageHeight) / 2;

        this.nextPage = this.addButton(new TurnPageButton(this.leftPos + 167, topPos + 16, true, (pressable) -> this.pageForward(), true));
        this.backPage = this.addButton(new TurnPageButton(this.leftPos + 70, topPos + 16, false, (pressable) -> this.pageBack(), true));
        this.indexReturn = this.addButton(new IndexReturnButton(this.leftPos + 112, topPos + 16, (pressable) -> this.indexReturn(), true));
        this.closeBook = this.addButton(new CloseButton(this.leftPos + 130, topPos + 16, (pressable) -> this.minecraft.setScreen(null), true));
        this.nextChapter = this.addButton(new TurnChapterButton(this.leftPos + 149, topPos + 16, true, (pressable) -> this.chapterForward(), true));
        this.backChapter = this.addButton(new TurnChapterButton(this.leftPos + 93, topPos + 16, false, (pressable) -> this.chapterBack(), true));

        this.updateButtonVisibility();
    }

    @Override
    public void renderBackground(MatrixStack matrix) {
        super.renderBackground(matrix);
    }

    @Override
    public void render(MatrixStack matrix, int mouseX, int mouseY, float p_230430_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(MENU_OPTIONS);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        super.render(matrix, mouseX, mouseY, p_230430_4_);

        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        CompoundNBT nbt = stack.getOrCreateTag();
        int color = 0;
        if(nbt.contains("dye"))
            color = nbt.getInt("dye") + 1;
        this.minecraft.getTextureManager().bind(COVERS[color]);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        if(nbt.contains("binding")) {
            this.minecraft.getTextureManager().bind(BINDINGS[BindingType.fromName(nbt.getString("binding")).ordinal()]);
            this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);
        }

        this.minecraft.getTextureManager().bind(PAPER);
        this.blit(matrix, leftPos, topPos, 0, 0, this.imageWidth, this.imageHeight);

        BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
        int page = nbt.getInt("page");
        int subpage = nbt.getInt("subpage");
        if(data!=null && !data.getBookPages().isEmpty() && data.getBookPage(page)!=null) {
            data.getBookPage(page).drawPage(matrix, mouseX - leftPos, mouseY - topPos, leftPos, topPos, 0, true, subpage, 15728880);
        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        BookData data = BookDataProvider.getBook(this.minecraft.level, stack);
        CompoundNBT nbt = stack.getOrCreateTag();
        int page = nbt.getInt("page");
        int subpage = nbt.getInt("subpage");
        if(data!=null && data.getBookPage(page)!=null) {
            for(PageElement element : data.getBookPage(page).elements){
                if(element instanceof ButtonPageElement){
                    if(((ButtonPageElement)element).click((float)mouseX - leftPos, (float)mouseY - topPos, subpage, stack, null, player, hand)) {
                        this.stack = player.getItemInHand(hand);
                        this.updateButtonVisibility();
                    }
                }
            }
        }
        return super.mouseClicked(mouseX,mouseY,mouseButton);
    }

    /*public boolean handleComponentClicked(Style p_230455_1_) {
        ClickEvent clickevent = p_230455_1_.getClickEvent();
        if (clickevent == null) {
            return false;
        } else if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            String s = clickevent.getValue();

            try {
                int i = Integer.parseInt(s) - 1;
                return this.forcePage(i);
            } catch (Exception exception) {
                return false;
            }
        } else {
            boolean flag = super.handleComponentClicked(p_230455_1_);
            if (flag && clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                this.minecraft.setScreen((Screen)null);
            }

            return flag;
        }
    }*/

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public class TurnChapterButton extends Button {
        private final boolean isForward;
        private final boolean playTurnSound;

        public TurnChapterButton(int x, int y, boolean isForward, Button.IPressable action, boolean playTurnSound) {
            super(x, y, 14, 14, StringTextComponent.EMPTY, action);
            this.isForward = isForward;
            this.playTurnSound = playTurnSound;
        }

        public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float f) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bind(GrimoireScreen.MENU_OPTIONS);
            int i = 93;
            int j = 41;
            if (!this.isHovered()) {
                j += 14;
            }

            if (this.isForward) {
                i += 56;
            }

            this.blit(matrix, this.x, this.y, i, j, 14, 14);
        }

        public void playDownSound(SoundHandler handler) {
            if (this.playTurnSound) {
                handler.play(SimpleSound.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class TurnPageButton extends Button {
        private final boolean isForward;
        private final boolean playTurnSound;

        public TurnPageButton(int x, int y, boolean isForward, Button.IPressable action, boolean playTurnSound) {
            super(x, y, 19, 14, StringTextComponent.EMPTY, action);
            this.isForward = isForward;
            this.playTurnSound = playTurnSound;
        }

        public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float f) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bind(GrimoireScreen.MENU_OPTIONS);
            int i = 70;
            int j = 41;
            if (!this.isHovered()) {
                j += 14;
            }

            if (this.isForward) {
                i += 97;
            }

            this.blit(matrix, this.x, this.y, i, j, 19, 14);
        }

        public void playDownSound(SoundHandler handler) {
            if (this.playTurnSound) {
                handler.play(SimpleSound.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class IndexReturnButton extends Button {
        private final boolean playTurnSound;

        public IndexReturnButton(int x, int y, Button.IPressable action, boolean playTurnSound) {
            super(x, y, 14, 14, StringTextComponent.EMPTY, action);
            this.playTurnSound = playTurnSound;
        }

        public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float f) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bind(GrimoireScreen.MENU_OPTIONS);
            int i = 112;
            int j = 41;
            if (!this.isHovered()) {
                j += 14;
            }

            this.blit(matrix, this.x, this.y, i, j, 14, 14);
        }

        public void playDownSound(SoundHandler handler) {
            if (this.playTurnSound) {
                handler.play(SimpleSound.forUI(SoundEvents.BOOK_PAGE_TURN, 1.0F));
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public class CloseButton extends Button {
        private final boolean playTurnSound;

        public CloseButton(int x, int y, Button.IPressable action, boolean playTurnSound) {
            super(x, y, 14, 14, StringTextComponent.EMPTY, action);
            this.playTurnSound = playTurnSound;
        }

        public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float f) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getInstance().getTextureManager().bind(GrimoireScreen.MENU_OPTIONS);
            int i = 130;
            int j = 41;
            if (!this.isHovered()) {
                j += 14;
            }

            this.blit(matrix, this.x, this.y, i, j, 14, 14);
        }

        public void playDownSound(SoundHandler handler) {
            if (this.playTurnSound) {
                handler.play(SimpleSound.forUI(SoundEvents.BOOK_PAGE_TURN, 0.1F));
            }
        }
    }
}
