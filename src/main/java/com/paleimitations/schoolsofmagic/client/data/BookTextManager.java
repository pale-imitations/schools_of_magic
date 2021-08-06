package com.paleimitations.schoolsofmagic.client.data;

import com.paleimitations.schoolsofmagic.common.data.books.BookPage;
import com.paleimitations.schoolsofmagic.common.data.books.PageElement;
import com.paleimitations.schoolsofmagic.common.data.books.ParagraphsPageElement;
import com.paleimitations.schoolsofmagic.common.registries.BookPageRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import net.minecraftforge.resource.VanillaResourceType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public enum BookTextManager implements ISelectiveResourceReloadListener {

    INSTANCE;

    private IResourceManager manager;
    private final Map<ResourceLocation, IResource> cache = new HashMap<>();

    @Nullable
    @Override
    public IResourceType getResourceType() {
        return VanillaResourceType.LANGUAGES;
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager, Predicate<IResourceType> resourcePredicate) {
        if (resourcePredicate.test(getResourceType())) {
            onResourceManagerReload(resourceManager);
        }
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        System.out.println("Book Text reloaded");
        this.manager = resourceManager;
        cache.clear();
        loadText();
    }

    public IResource loadTextFile(ResourceLocation fileLoc, ResourceLocation backupLoc) {
        if(this.manager==null) {
            this.manager = Minecraft.getInstance().getResourceManager();
        }
        if(cache.containsKey(fileLoc))
            return cache.get(fileLoc);
        else {
            IResource resource = null;
            try {
                resource = this.manager.getResource(fileLoc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(resource!=null)
                return resource;
        }
        if(cache.containsKey(backupLoc))
            return cache.get(backupLoc);
        else {
            IResource resource = null;
            try {
                resource = this.manager.getResource(backupLoc);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return resource;
        }
    }

    public static void loadText() {
        for(BookPage page : BookPageRegistry.PAGES){
            for(PageElement element : page.elements)
                if(element instanceof ParagraphsPageElement)
                    ((ParagraphsPageElement)element).loadText();
        }
    }

    public static void loadText(BookPage page) {
        for(PageElement element : page.elements)
            if(element instanceof ParagraphsPageElement)
                ((ParagraphsPageElement)element).loadText();
    }
}
