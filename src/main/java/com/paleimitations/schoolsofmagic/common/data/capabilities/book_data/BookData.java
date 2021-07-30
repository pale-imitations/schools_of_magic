package com.paleimitations.schoolsofmagic.common.data.capabilities.book_data;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.data.BindingType;
import com.paleimitations.schoolsofmagic.common.data.books.*;
import com.paleimitations.schoolsofmagic.common.registries.BookPageRegistry;
import com.paleimitations.schoolsofmagic.common.registries.MagicElementRegistry;
import com.paleimitations.schoolsofmagic.common.spells.Spell;
import com.paleimitations.schoolsofmagic.common.spells.SpellHelper;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.IExtensibleEnum;
import org.omg.DynamicAny.DynEnumHelper;

import java.util.List;

public class BookData extends WorldSavedData implements IBookPageHolder {

    private List<BookPage> pages = Lists.newArrayList();
    private List<BookPageChapter> chapters = Lists.newArrayList();

    public BookData(String id) {
        super(id);
    }

    public List<BookPageChapter> getChapters() {
        return chapters;
    }

    public Tuple<Integer, Integer> getSurroundingChapters(int page) {
        int back = page;
        for(int i = page - 1; i >= 0; --i) {
            BookPage bookPage = this.getBookPage(i);
            if(bookPage !=null && bookPage instanceof BookPageChapter) {
                back = i;
                break;
            }
        }
        int forward = page;
        for(int i = page + 1; i < this.pages.size(); ++i) {
            BookPage bookPage = this.getBookPage(i);
            if(bookPage !=null && bookPage instanceof BookPageChapter) {
                forward = i;
                break;
            }
        }
        return new Tuple<>(back, forward);
    }

    public BookPage getBookPage(int page) {
        if(page >= pages.size())
            return null;
        if(page < 0)
            return null;
        return pages.get(page);
    }

    public int getNumPages() {
        return pages.size();
    }

    public List<BookPage> getBookPages() {
        return pages;
    }

    public void setBookPages(List<BookPage> pages) {
        this.pages = pages;
        this.update();
        this.setDirty();
    }

    public void addBookPages(List<BookPage> pages) {
        this.pages.addAll(pages);
        this.update();
        this.setDirty();
    }

    public void addBookPage(BookPage page) {
        this.pages.add(page);
        this.update();
        this.setDirty();
    }

    public void removeBookPage(BookPage page) {
        this.pages.removeIf(bookPage -> bookPage == page);
        this.update();
        this.setDirty();
    }

    public void removeBookPage(int i) {
        if(this.pages.size()>i) {
            this.pages.removeIf(bookPage -> bookPage == this.pages.get(i));
            this.update();
            this.setDirty();
        }
    }

    public void update() {
        //this.getConnectedPages();
        chapters.clear();
        for(BookPage page : pages) {
            if(page instanceof BookPageChapter) {
                ((BookPageChapter) page).pageHolder = this;
                ((BookPageChapter) page).buildChapter();
                chapters.add((BookPageChapter) page);
            }
        }
        if(this.pages!=null && !this.pages.isEmpty() && this.pages.get(0)!=null && this.pages.get(0) instanceof BookPageTableContent) {
            ((BookPageTableContent) this.pages.get(0)).pageHolder = this;
            ((BookPageTableContent) this.pages.get(0)).buildTableContent();
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        nbt.putInt("pages_size", pages.size());
        for(int i = 0; i < pages.size(); ++i) {
            if(pages.get(i)!=null) {
                if (pages.get(i) instanceof BookPageSpell) {
                    nbt.putBoolean("is_spell_page_" + i, true);
                    nbt.putString("spell_location_" + i, ((BookPageSpell) pages.get(i)).getSpell().getResourceLocation().toString());
                    nbt.put("spell_data_" + i, ((BookPageSpell) pages.get(i)).getSpell().serializeNBT());
                }
                else
                    nbt.putString("page_" + i, pages.get(i).getName());
            }
        }
        return nbt;
    }

    @Override
    public void load(CompoundNBT nbt) {
        this.pages.clear();
        for(int i = 0; i < nbt.getInt("pages_size"); ++i) {
            if(nbt.contains("is_spell_page_"+i)) {
                Spell spell = SpellHelper.getSpellInstance(new ResourceLocation(nbt.getString("spell_location_"+i)), nbt.getCompound("spell_data_"+i));
                if(spell!=null)
                    this.pages.add(new BookPageSpell(spell));
            }
            else {
                BookPage p = BookPageRegistry.getBookPage(nbt.getString("page_" + i));
                if (p != null)
                    this.pages.add(p);
            }
        }
        this.update();
    }

    public static DecorationType[] FACETS = new DecorationType[BindingType.values().length];
    public static DecorationType[] JEWELS = new DecorationType[BindingType.values().length];
    public static DecorationType[] FRAMES = new DecorationType[BindingType.values().length];
    public static DecorationType[] SWIRLS = new DecorationType[DyeColor.values().length];
    public static DecorationType[] MAGICS = new DecorationType[BindingType.values().length*MagicElementRegistry.ELEMENTS.size()];

    static {
        //Enum.
        for(BindingType binding : BindingType.values()) {
            FACETS[binding.ordinal()] = DecorationType.create(binding.name() + "_FACET");
            JEWELS[binding.ordinal()] = DecorationType.create(binding.name() + "_JEWEL");
            FRAMES[binding.ordinal()] = DecorationType.create(binding.name() + "_FRAME");
            for(MagicElement element : MagicElementRegistry.ELEMENTS) {
                MAGICS[binding.ordinal()*MagicElementRegistry.ELEMENTS.size()+element.getId()] = DecorationType.create(binding.name() + "_" + element.getName().toUpperCase());
            }
        }
        for(DyeColor color : DyeColor.values()) {
            SWIRLS[color.ordinal()] = DecorationType.create(color.name() + "_SWIRL");
        }
    }

    public static DecorationType getMagicDecoration(BindingType binding, MagicElement element) {
        return MAGICS[binding.ordinal()*MagicElementRegistry.ELEMENTS.size()+element.getId()];
    }

    public enum DecorationType implements IStringSerializable, IExtensibleEnum {

        NONE
        ;

        @Override
        public String getSerializedName() {
            return name().toLowerCase();
        }

        public static DecorationType create(String enumName) {
            throw new IllegalStateException("Enum not extended");
        }

        public static DecorationType fromName(String name) {
            for(DecorationType type : values()) {
                if (type.getSerializedName().equalsIgnoreCase(name))
                    return type;
            }
            return NONE;
        }

        public static DecorationType fromIndex(int i) {
            for(DecorationType type : values()) {
                if (type.ordinal() == i)
                    return type;
            }
            return NONE;
        }
    }
}
