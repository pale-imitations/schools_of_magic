package com.paleimitations.schoolsofmagic.common.data.books;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;

public class BookPageChapter extends BookPage {

    public IBookPageHolder pageHolder;
    public int page, chapterNumber = -1;

    public BookPageChapter(IBookPageHolder pageHolder) {
        super("chapter", Lists.newArrayList());
        this.pageHolder = pageHolder;
    }

    /*public BookPageChapter(IBookPageHolder pageHolder, String name) {
        super("chapter", Lists.newArrayList(new PageElementTitle(name, 72, 58, 99, 16, 0, true)));
        this.pageHolder = pageHolder;
    }

    @Override
    public String getName() {
        for(PageElement element : this.elements){
            if(element instanceof PageElementTitle) {
                return "chapter_"+((PageElementTitle) element).text;
            }
        }
        return super.getName();
    }*/

    public void addPageToRegistry(){
    }

    public void buildChapter() {
        if(pageHolder instanceof BookData){
            BookData book = (BookData) pageHolder;
            int chapterNumber = 0;
            int chapterPageNumber = -1;
            int chapterStart = -1;
            int chapterEnd = -1;
            for(int i = 0; i < book.getBookPages().size(); ++i) {
                BookPage page = book.getBookPage(i);
                if(page instanceof BookPageChapter && chapterPageNumber == -1) {
                    ++chapterNumber;
                    if(page.equals(this)) {
                        chapterPageNumber = i;
                    }
                }
                if(chapterPageNumber > -1 && i > chapterPageNumber) {
                    if(page instanceof BookPageChapter) {
                        break;
                    }
                    else {
                        if(chapterStart < 0)
                            chapterStart = i;
                        chapterEnd = i;
                    }
                }
            }
            if(chapterPageNumber > -1 && chapterStart > -1 && chapterEnd > -1 && chapterNumber > 0) {
                this.page = chapterPageNumber;
                this.chapterNumber = chapterNumber;
                if(this.elements.isEmpty()) {
                    String[] text = new String[2];
                    text[0] = "page.chapter.element";
                    text[1] = String.valueOf(chapterNumber);
                    this.elements.add(new PageElementTitle(text, 72, 58, 99, 16, 0, true));
                }
                for(int i = 0; i <= chapterEnd-chapterStart; ++ i) {
                    BookPage page = book.getBookPage(chapterStart+i);
                    String[] title = new String[2];
                    title[0]="page.page.element";
                    String desc = "";
                    if(page!=null) {
                        for(PageElement element : page.elements){
                            /*if(element instanceof PageElementStandardText) {
                                title[0] = ((PageElementStandardText) element).textLocation;
                                break;
                            }*/
                            if(element instanceof PageElementTitle) {
                                title = ((PageElementTitle) element).text;
                                break;
                            }
                            if(element instanceof PageElementSpellInfo) {
                                title[0] = "spell."+((PageElementSpellInfo)element).spell.getName()+".name";
                                title[1] = "title.spell_page.name";
                                break;
                            }
                        }
                        for(PageElement element : page.elements){
                            if(element instanceof PageElementDescription) {
                                desc = ((PageElementDescription) element).description;
                                break;
                            }
                        }
                        /*if(page instanceof BookPagePotionEffect) {
                            title[1] = "title.potion_page.name";
                        }*/
                    }

                    int segment = 7;
                    int xi = (i / segment) % 2 == 0 ? 23 : 134;
                    int yi = 65 + (i % segment)*18;
                    int targeti = i / (segment*2);
                    this.elements.add(new PageElementChapterEntry(title, desc,chapterStart+i, xi, yi, targeti,99, 8));
                }
            }
        }
    }

}
