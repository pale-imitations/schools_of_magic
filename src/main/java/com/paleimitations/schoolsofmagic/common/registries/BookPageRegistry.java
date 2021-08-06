package com.paleimitations.schoolsofmagic.common.registries;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.data.books.*;

import java.util.List;

public class BookPageRegistry {

    public static final List<BookPage> PAGES = Lists.newArrayList();
    public static final List<BookPage> BASIC_ARCANA = Lists.newArrayList();

    public static void init() {
        BasicArcanaRegistry.init();
        new BookPage("ccw_letter_1", Lists.newArrayList(new ParagraphsPageElement("ccw_letter_1", 0.55f, 0, 0, new ParagraphBox(16,28, 0, 124, 112))));

    }

    public static BookPage getBookPage(String name) {
        if(name.equalsIgnoreCase("chapter")){
            return new BookPageChapter(null);
        }
		/*if(name.length()>7 && name.substring(0,7).equalsIgnoreCase("chapter")){
			return new BookPageChapter(null, name.substring(8));
		}*/
        if(name.equalsIgnoreCase("table_content")){
            return new BookPageTableContent(null);
        }
        /*if(name.length()>9 && name.substring(0,9).equalsIgnoreCase("writeable")){
            String title = "";
            String paragraph = "";
            if(name.substring(9).split("<title>").length>1){
                String[] strs = name.substring(9).split("<title>")[0].split("<paragraph>");
                if(strs.length>1) {
                    paragraph = strs[1];
                }
                title = strs[0];
            }
            return new BookPageWriteable(title, paragraph);
        }*/
        for(BookPage page : PAGES)
            if(page.getName().equalsIgnoreCase(name))
                return page;
        return null;
    }

    /*public static BookPage getPotionEffectPage(Potion potion){
        for(BookPagePotionEffect bookPagePotionEffect : POTION_EFFECT_PAGES){
            if(bookPagePotionEffect.potion.getPotion().equals(potion)){
                return bookPagePotionEffect;
            }
        }
        return null;
    }

    public static List<BookPage> getPotionEffectPages(IMagicType magicType) {
        List<BookPage> pages = Lists.newArrayList();
        for(BookPagePotionEffect bookPagePotionEffect : POTION_EFFECT_PAGES){
            if(bookPagePotionEffect.element == magicType || bookPagePotionEffect.school == magicType){
                pages.add(bookPagePotionEffect);
            }
        }
        return pages;
    }*/

}
