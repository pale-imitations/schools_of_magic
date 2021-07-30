package com.paleimitations.schoolsofmagic.common.data.books;

import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.SchoolsOfMagicMod;
import com.paleimitations.schoolsofmagic.common.MagicElement;
import com.paleimitations.schoolsofmagic.common.spells.Spell;

import java.util.List;


public class BookPageSpell extends BookPage {

	public final Spell spell;
	
	public BookPageSpell(Spell spell) {
		super(spell.getName(), Lists.newArrayList(
				new PageElementSpellInfo(spell),
				new PageElementParagraphs("spell_"+spell.getName(), 0.75f, 0, 1,
						new ParagraphBox(134,50, 0, 99, 140),
						new ParagraphBox(23,50, 1, 99, 140),
						new ParagraphBox(134,50, 1, 99, 140)),
				new PageElementDescription("page.spell_"+spell.getName()+".desc")
				));
		this.spell = spell;
		/*for(Spell.EnumSpellModifier mod : spell.modifiers.keySet()){
			this.addElement(new PageElementDescription("modifier."+mod.getName()+".name", TextFormatting.GOLD));
		}*/
	}

	public void addPageToRegistry(){
	}

	public static int getColorFromElementList(List<MagicElement> elements)
	{
		int i = 3694022;

		if (elements.isEmpty())
		{
			return 3694022;
		}
		else
		{
			float f = 0.0F;
			float f1 = 0.0F;
			float f2 = 0.0F;
			int j = 0;

			for (MagicElement element : elements)
			{
				int k = element.getColor();
				f += (float)(k >> 16 & 255) / 255.0F;
				f1 += (float)(k >> 8 & 255) / 255.0F;
				f2 += (float)(k >> 0 & 255) / 255.0F;
				j += 1;
			}

			if (j == 0)
			{
				return 0;
			}
			else
			{
				f = f / (float)j * 255.0F;
				f1 = f1 / (float)j * 255.0F;
				f2 = f2 / (float)j * 255.0F;
				return (int)f << 16 | (int)f1 << 8 | (int)f2;
			}
		}
	}
	
	public Spell getSpell() {
		return spell;
	}
}
