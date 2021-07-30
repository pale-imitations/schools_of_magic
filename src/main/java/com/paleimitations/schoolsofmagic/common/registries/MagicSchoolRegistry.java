package com.paleimitations.schoolsofmagic.common.registries;

import com.paleimitations.schoolsofmagic.common.MagicSchool;

import java.util.ArrayList;
import java.util.List;

public class MagicSchoolRegistry {
	public static final List<MagicSchool> SCHOOLS = new ArrayList<>();

	public static MagicSchool EVOCATION, TRANSFIGURATION, DIVINATION, ABJURATION, CONJURATION, ILLUSION;
	
	public static void init() {
		EVOCATION = new MagicSchool("evocation", 0);
		TRANSFIGURATION = new MagicSchool("transfiguration", 1);
		DIVINATION = new MagicSchool("divination", 2);
		ABJURATION = new MagicSchool("abjuration", 3);
		CONJURATION = new MagicSchool("conjuration", 4);
		ILLUSION = new MagicSchool("illusion", 5);
	}

	public static MagicSchool getSchoolFromName(String name){
		for(MagicSchool school : SCHOOLS){
			if(school.getName().equalsIgnoreCase(name)){
				return school;
			}
		}
		return null;
	}

    public static MagicSchool getSchoolFromId(int i) {
		for(MagicSchool school : SCHOOLS){
			if(school.getId()==i){
				return school;
			}
		}
		return null;
    }

    public static int[] generateArray(int... schools){
		int[] array = new int[SCHOOLS.size()];
		int a = 0;
		for(int i : schools){
			array[a] = i;
			a++;
		}
		return array;
	}

	public static int[] generateEmptyArray() {
		return new int[SCHOOLS.size()];
	}
}
