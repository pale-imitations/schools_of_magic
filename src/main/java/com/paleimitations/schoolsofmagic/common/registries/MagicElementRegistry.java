package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.common.MagicElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MagicElementRegistry {
	public static final List<MagicElement> ELEMENTS = new ArrayList<>();

	public static MagicElement PYROMANCY;
	public static MagicElement HELIOMANCY;
	public static MagicElement AEROMANCY;
	public static MagicElement GEOMANCY;
	public static MagicElement HERBALMANCY;
	public static MagicElement ELECTROMANCY;
	public static MagicElement HYDROMANCY;
	public static MagicElement CRYOMANCY;
	public static MagicElement HIEROMANCY;
	public static MagicElement CHAOTIMANCY;
	public static MagicElement ANIMANCY;
	public static MagicElement ASTROMANCY;
	public static MagicElement CHRONOMANCY;
	public static MagicElement SPECTROMANCY;
	public static MagicElement UMBRAMANCY;
	public static MagicElement NECROMANCY;
	
	public static void init() {
		PYROMANCY = new MagicElement("pyromancy", 0, new Color(162,38,38).getRGB());
		HELIOMANCY = new MagicElement("heliomancy", 1, new Color(233,100,0).getRGB());
		AEROMANCY = new MagicElement("aeromancy", 2, new Color(250,188,40).getRGB());
		GEOMANCY = new MagicElement("geomancy", 3, new Color(101,180,28).getRGB());
		HERBALMANCY = new MagicElement("herbalmancy", 4, new Color(83,103,41).getRGB());
		ELECTROMANCY = new MagicElement("electromancy", 5, new Color(30,132,150).getRGB());
		HYDROMANCY = new MagicElement("hydromancy", 6, new Color(51,155,218).getRGB());
		CRYOMANCY = new MagicElement("cryomancy", 7, new Color(53,55,159).getRGB());
		HIEROMANCY = new MagicElement("hieromancy", 8, new Color(115,39,177).getRGB());
		CHAOTIMANCY = new MagicElement("chaotimancy", 9, new Color(188,54,177).getRGB());
		ANIMANCY = new MagicElement("animancy", 10, new Color(224,118,156).getRGB());
		ASTROMANCY = new MagicElement("astromancy", 11, new Color(216,222,222).getRGB());
		CHRONOMANCY = new MagicElement("chronomancy", 12, new Color(165,168,170).getRGB());
		SPECTROMANCY = new MagicElement("spectromancy", 13,new Color(112,114,116).getRGB());
		UMBRAMANCY = new MagicElement("umbramancy", 14,new Color(49,51,53).getRGB());
		NECROMANCY = new MagicElement("necromancy", 15, new Color(112,70,38).getRGB());
	}

	public static MagicElement getElementFromName(String name){
		for(MagicElement element : ELEMENTS){
			if(element.getName().equalsIgnoreCase(name)){
				return element;
			}
		}
		return null;
	}

	public static MagicElement getElementFromId(int id){
		for(MagicElement element : ELEMENTS){
			if(element.getId() == id){
				return element;
			}
		}
		return null;
	}

	public static int[] generateArray(int... elements){
		int[] array = new int[ELEMENTS.size()];
		int a = 0;
		for(int i : elements){
			array[a] = i;
			a++;
		}
		return array;
	}

	public static int[] generateArraySingle(int index, int value){
		int[] array = new int[ELEMENTS.size()];
		array[index] = value;
		return array;
	}

	public static int[] generateEmptyArray() {
		return new int[ELEMENTS.size()];
	}
}
