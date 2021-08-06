package com.paleimitations.schoolsofmagic.common.data.books;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.common.data.capabilities.book_data.BookData;

public class BookPageTableContent extends BookPage {

    public IBookPageHolder pageHolder;

    public BookPageTableContent(IBookPageHolder pageHolder) {
        super("table_content", Lists.newArrayList());
        this.pageHolder = pageHolder;
    }

    public void addPageToRegistry(){
    }

    public void buildTableContent() {
        if(pageHolder instanceof BookData){
            BookData book = (BookData) pageHolder;
            this.elements.add( new StringPageElement("page.table_content.element", 72, 58, 99, 16, 0, true));
            for(int i = 0; i < book.getChapters().size(); ++ i) {
                BookPageChapter chapter = book.getChapters().get(i);
                String[] title = new String[2];
                title[0] = "page.chapter.element";
                title[1] = String.valueOf(chapter.chapterNumber);
                for(PageElement element : chapter.elements){
                    if(element instanceof TitlePageElement)
                        title = ((TitlePageElement) element).text;
                }

                int segment = 7;
                int xi = (i / segment) % 2 == 0 ? 23 : 134;
                int yi = 65 + (i % segment)*18;
                int targeti = i / (segment*2);
                this.elements.add( new ChapterEntryPageElement(title, "",chapter.page, xi, yi, targeti,99, 8));
            }
        }
    }

}
