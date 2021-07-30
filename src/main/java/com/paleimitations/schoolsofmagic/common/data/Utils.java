package com.paleimitations.schoolsofmagic.common.data;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.paleimitations.schoolsofmagic.References;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class Utils {
	
	private static Logger logger;
	
	public static Logger getLogger() {
		if(logger == null) {
			logger = LogManager.getFormatterLogger(References.MODID);
		}
		return logger;
	}

	public static LivingEntity getEntityOnVec(World worldIn, PlayerEntity playerIn, double distance) {
		LivingEntity living = null;
		for(Entity entity : getEntitiesAlongVec(worldIn, playerIn, distance, true))
			if(entity instanceof LivingEntity && (living == null || Utils.getDistance(entity, playerIn) < Utils.getDistance(living, playerIn)) && entity != playerIn)
				living = (LivingEntity)entity;
		return living;
	}

	public static List<Entity> getEntitiesAlongVec(World worldIn, PlayerEntity playerIn, double distance, boolean mustBeVisible) {
		Random rand = playerIn.getRandom();
		List<Entity> list1 = worldIn.getEntitiesOfClass(Entity.class, playerIn.getBoundingBox().inflate(distance));
		List<Entity> list2 = Lists.newArrayList();
		Vector3d vec = playerIn.getLookAngle();

		for(Entity entity : list1) {
			AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(0.30000001192092896D);
			Vector3d observerLocation = new Vector3d(playerIn.getX(), playerIn.getY() + playerIn.getEyeHeight(), playerIn.getZ());
			Optional<Vector3d> traceToEntity = axisalignedbb.clip(observerLocation, observerLocation.add(vec.x*distance, vec.y*distance, vec.z*distance));
			if(traceToEntity.isPresent() && (playerIn.canSee(entity) || !mustBeVisible) && entity != playerIn)
				list2.add(entity);
		}

		return list2;
	}

	/*public static boolean matches(List<ItemStack> inputs, List<ItemStack> recipeItems) {
		if(inputs.isEmpty() || recipeItems.isEmpty())
			return false;

		for(ItemStack input : inputs)
			for(ItemStack recipeItem : recipeItems)
				if (compareStacks(recipeItem, input)) {
					recipeItems.remove(recipeItem);
					break;
				}

		return recipeItems.isEmpty();
	}

	private static boolean compareStacks(ItemStack recipe, ItemStack supplied) {
		return (recipe.isItemEqual(supplied) && recipe.getItemDamage() == supplied.getItemDamage() && NBTUtils.matchTag(recipe.getTagCompound(), supplied.getTagCompound()) && supplied.getCount() >= recipe.getCount()) ||
				(recipe.isEmpty() && supplied.isEmpty());
	}*/
	
	public static <E> List<E> getDifference(List<E> list1, List<E> list2) {
		List<E> listOut = Lists.<E>newArrayList();
		for(E element : list1) {
			if(!list2.contains(element))
				listOut.add(element);
		}
		for(E element : list2) {
			if(!list1.contains(element))
				listOut.add(element);
		}
	    return listOut;
	  }
	
	public static double getDistance(BlockPos pos1, BlockPos pos2) {
		double x1=pos1.getX()+0.5D;
		double y1=pos1.getY()+0.5D;
		double z1=pos1.getZ()+0.5D;
		
		double x2=pos2.getX()+0.5D;
		double y2=pos2.getY()+0.5D;
		double z2=pos2.getZ()+0.5D;
		
		double dx=x1-x2;
		double dy=y1-y2;
		double dz=z1-z2;
		
		double d1=Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2));
		double d2=Math.sqrt(Math.pow(dy, 2)+Math.pow(d1, 2));
		
		return d2;
	}
	
	public static double getDistance(Entity pos1, Entity pos2) {
		double x1=pos1.getX();
		double y1=pos1.getY();
		double z1=pos1.getZ();
		
		double x2=pos2.getX();
		double y2=pos2.getY();
		double z2=pos2.getZ();
		
		double dx=x1-x2;
		double dy=y1-y2;
		double dz=z1-z2;
		
		double d1=Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2));
		double d2=Math.sqrt(Math.pow(dy, 2)+Math.pow(d1, 2));
		
		return d2;
	}
	
	public static double getDistanceDouble(double x1, double y1, double z1, double x2, double y2, double z2) {
		
		double dx=x1-x2;
		double dy=y1-y2;
		double dz=z1-z2;
		
		double d1=Math.sqrt(Math.pow(dx, 2)+Math.pow(dz, 2));
		double d2=Math.sqrt(Math.pow(dy, 2)+Math.pow(d1, 2));
		
		return d2;
	}

	/*public static Entity getEntity(World world, UUID uuid) {
		for(Entity entity : world.getEntities(Entity.class, Predicates.and(EntitySelectors.IS_ALIVE)))
			if(entity.getPersistentID().equals(uuid))
				return entity;

		return null;
	}*/

	/*public static boolean containsStacks(List<ItemStack> inputs, List<ItemStack> recipe) {
		for(ItemStack recipeItem : recipe) {
			if(inputs.contains(recipeItem)) {
				inputs = createListWithoutItem(inputs, recipeItem);
			}
		}
		return inputs.isEmpty();
	}*/

	public static <E> List<E> createListWithoutObject(List<E> list, E input){
		List<E> output = Lists.newArrayList();
		for(E element : list){
			if(!input.equals(element))
				output.add(element);
		}
		return output;
	}

	public static String getRandomName(Random random){
		String name = getRandomStartName(random);
		int k = random.nextInt(2);
		int gender = random.nextInt(3)-1;
		if(gender == 0) gender = random.nextInt(3)-1;
		if(gender == 0) gender = random.nextInt(3)-1;

		boolean gen = gender == -1;
		for(int j = 0; j < k; ++j) {
			name = name + getRandomMiddleName(random);
		}
		String firstName = name + getRandomEndName(random, gen);

		int m = random.nextInt(2);
		String last = getRandomStartName(random);
		for(int j = 0; j < m; ++j) {
			last = last + getRandomMiddleName(random);
		}
		String lastName = last + getRandomEndName(random, false);
		return firstName + " " + lastName;
	}
	
	public static String getRandomStartPhrase(Random random) {
		String start;
		switch(random.nextInt(51)) {
		case 0: start = "A"; break;
		case 1: start = "Ae"; break;
		case 2: start = "Au"; break;
		case 3: start = "B"; break;
		case 4: start = "Br"; break;
		case 5: start = "C"; break;
		case 6: start = "Cr"; break;
		case 7: start = "D"; break;
		case 8: start = "Dr"; break;
		case 9: start = "E"; break;
		case 10: start = "Ea"; break;
		case 11: start = "Eau"; break;
		case 12: start = "F"; break;
		case 13: start = "Fr"; break;
		case 14: start = "G"; break;
		case 15: start = "Gr"; break;
		case 16: start = "Gh"; break;
		case 17: start = "H"; break;
		case 18: start = "I"; break;
		case 19: start = "J"; break;
		case 20: start = "K"; break;
		case 21: start = "Kr"; break;
		case 22: start = "L"; break;
		case 23: start = "M"; break;
		case 24: start = "N"; break;
		case 25: start = "O"; break;
		case 26: start = "Oi"; break;
		case 27: start = "Ou"; break;
		case 28: start = "P"; break;
		case 29: start = "Ph"; break;
		case 30: start = "Pr"; break;
		case 31: start = "Qu"; break;
		case 32: start = "R"; break;
		case 33: start = "S"; break;
		case 34: start = "Sc"; break;
		case 35: start = "Sch"; break;
		case 36: start = "Sh"; break;
		case 37: start = "Sp"; break;
		case 38: start = "Sph"; break;
		case 39: start = "St"; break;
		case 40: start = "Str"; break;
		case 41: start = "Squ"; break;
		case 42: start = "T"; break;
		case 43: start = "Tr"; break;
		case 44: start = "U"; break;
		case 45: start = "V"; break;
		case 46: start = "W"; break;
		case 47: start = "Wr"; break;
		case 48: start = "X"; break;
		case 49: start = "Y"; break;
		case 50: start = "Z"; break;
		default: start = "B"; break;
		}
		
		if(start.contains("A") || start.contains("E") || start.contains("I") || start.contains("O") || start.contains("U") || start.contains("Y")) {
			switch(random.nextInt(82)) {
			case 0: start = start + "b"; break;
			case 1: start = start + "br"; break;
			case 2: start = start + "c"; break;
			case 3: start = start + "ch"; break;
			case 4: start = start + "cr"; break;
			case 5: start = start + "ck"; break;
			case 6: start = start + "ct"; break;
			case 7: start = start + "d"; break;
			case 8: start = start + "dg"; break;
			case 9: start = start + "dr"; break;
			case 10: start = start + "f"; break;
			case 11: start = start + "ft"; break;
			case 12: start = start + "fr"; break;
			case 13: start = start + "g"; break;
			case 14: start = start + "gg"; break;
			case 15: start = start + "gh"; break;
			case 16: start = start + "gr"; break;
			case 17: start = start + "gst"; break;
			case 18: start = start + "h"; break;
			case 19: start = start + "hst"; break;
			case 20: start = start + "ht"; break;
			case 21: start = start + "j"; break;
			case 22: start = start + "k"; break;
			case 23: start = start + "kr"; break;
			case 24: start = start + "kt"; break;
			case 25: start = start + "l"; break;
			case 26: start = start + "ll"; break;
			case 27: start = start + "lst"; break;
			case 28: start = start + "ls"; break;
			case 29: start = start + "lt"; break;
			case 30: start = start + "lf"; break;
			case 31: start = start + "lph"; break;
			case 32: start = start + "lg"; break;
			case 33: start = start + "lb"; break;
			case 34: start = start + "lm"; break;
			case 35: start = start + "lv"; break;
			case 36: start = start + "lw"; break;
			case 37: start = start + "lp"; break;
			case 38: start = start + "m"; break;
			case 39: start = start + "mm"; break;
			case 40: start = start + "mn"; break;
			case 41: start = start + "mst"; break;
			case 42: start = start + "mp"; break;
			case 43: start = start + "n"; break;
			case 44: start = start + "nn"; break;
			case 45: start = start + "nt"; break;
			case 46: start = start + "nst"; break;
			case 47: start = start + "p"; break;
			case 48: start = start + "pr"; break;
			case 49: start = start + "pt"; break;
			case 50: start = start + "pth"; break;
			case 51: start = start + "ph"; break;
			case 52: start = start + "qu"; break;
			case 53: start = start + "r"; break;
			case 54: start = start + "rc"; break;
			case 55: start = start + "rk"; break;
			case 56: start = start + "rt"; break;
			case 57: start = start + "rth"; break;
			case 58: start = start + "rsh"; break;
			case 59: start = start + "rch"; break;
			case 60: start = start + "s"; break;
			case 61: start = start + "ss"; break;
			case 62: start = start + "st"; break;
			case 63: start = start + "sp"; break;
			case 64: start = start + "sh"; break;
			case 65: start = start + "squ"; break;
			case 66: start = start + "sk"; break;
			case 67: start = start + "sc"; break;
			case 68: start = start + "sl"; break;
			case 69: start = start + "scr"; break;
			case 70: start = start + "sch"; break;
			case 71: start = start + "t"; break;
			case 72: start = start + "tt"; break;
			case 73: start = start + "th"; break;
			case 74: start = start + "tr"; break;
			case 75: start = start + "tw"; break;
			case 76: start = start + "v"; break;
			case 77: start = start + "w"; break;
			case 78: start = start + "wst"; break;
			case 79: start = start + "x"; break;
			case 80: start = start + "z"; break;
			case 81: start = start + "zr"; break;
			}
		}
		
		return start;
	}
	
	public static String getRandomMiddlePhrase(Random random) {
		String middle;
		switch(random.nextInt(43)) {
		case 0: middle = "a"; break;
		case 1: middle = "a"; break;
		case 2: middle = "a"; break;
		case 3: middle = "a"; break;
		case 4: middle = "a"; break;
		case 5: middle = "a"; break;
		case 6: middle = "a"; break;
		case 7: middle = "ay"; break;
		case 8: middle = "ae"; break;
		case 9: middle = "au"; break;
		case 10: middle = "e"; break;
		case 11: middle = "e"; break;
		case 12: middle = "e"; break;
		case 13: middle = "e"; break;
		case 14: middle = "e"; break;
		case 15: middle = "ei"; break;
		case 16: middle = "ee"; break;
		case 17: middle = "ea"; break;
		case 18: middle = "au"; break;
		case 19: middle = "i"; break;
		case 20: middle = "i"; break;
		case 21: middle = "i"; break;
		case 22: middle = "i"; break;
		case 23: middle = "io"; break;
		case 24: middle = "ie"; break;
		case 25: middle = "o"; break;
		case 26: middle = "o"; break;
		case 27: middle = "o"; break;
		case 28: middle = "o"; break;
		case 29: middle = "o"; break;
		case 30: middle = "o"; break;
		case 31: middle = "o"; break;
		case 32: middle = "oy"; break;
		case 33: middle = "oe"; break;
		case 34: middle = "oi"; break;
		case 35: middle = "ou"; break;
		case 36: middle = "u"; break;
		case 37: middle = "u"; break;
		case 38: middle = "u"; break;
		case 39: middle = "uo"; break;
		case 40: middle = "ua"; break;
		case 41: middle = "y"; break;
		case 42: middle = "ya"; break;
		default: middle = "a"; break;
		}
		
		switch(random.nextInt(82)) {
		case 0: middle = middle + "b"; break;
		case 1: middle = middle + "br"; break;
		case 2: middle = middle + "c"; break;
		case 3: middle = middle + "ch"; break;
		case 4: middle = middle + "cr"; break;
		case 5: middle = middle + "ck"; break;
		case 6: middle = middle + "ct"; break;
		case 7: middle = middle + "d"; break;
		case 8: middle = middle + "dg"; break;
		case 9: middle = middle + "dr"; break;
		case 10: middle = middle + "f"; break;
		case 11: middle = middle + "ft"; break;
		case 12: middle = middle + "fr"; break;
		case 13: middle = middle + "g"; break;
		case 14: middle = middle + "gg"; break;
		case 15: middle = middle + "gh"; break;
		case 16: middle = middle + "gr"; break;
		case 17: middle = middle + "gst"; break;
		case 18: middle = middle + "h"; break;
		case 19: middle = middle + "hst"; break;
		case 20: middle = middle + "ht"; break;
		case 21: middle = middle + "j"; break;
		case 22: middle = middle + "k"; break;
		case 23: middle = middle + "kr"; break;
		case 24: middle = middle + "kt"; break;
		case 25: middle = middle + "l"; break;
		case 26: middle = middle + "ll"; break;
		case 27: middle = middle + "lst"; break;
		case 28: middle = middle + "ls"; break;
		case 29: middle = middle + "lt"; break;
		case 30: middle = middle + "lf"; break;
		case 31: middle = middle + "lph"; break;
		case 32: middle = middle + "lg"; break;
		case 33: middle = middle + "lb"; break;
		case 34: middle = middle + "lm"; break;
		case 35: middle = middle + "lv"; break;
		case 36: middle = middle + "lw"; break;
		case 37: middle = middle + "lp"; break;
		case 38: middle = middle + "m"; break;
		case 39: middle = middle + "mm"; break;
		case 40: middle = middle + "mn"; break;
		case 41: middle = middle + "mst"; break;
		case 42: middle = middle + "mp"; break;
		case 43: middle = middle + "n"; break;
		case 44: middle = middle + "nn"; break;
		case 45: middle = middle + "nt"; break;
		case 46: middle = middle + "nst"; break;
		case 47: middle = middle + "p"; break;
		case 48: middle = middle + "pr"; break;
		case 49: middle = middle + "pt"; break;
		case 50: middle = middle + "pth"; break;
		case 51: middle = middle + "ph"; break;
		case 52: middle = middle + "qu"; break;
		case 53: middle = middle + "r"; break;
		case 54: middle = middle + "rc"; break;
		case 55: middle = middle + "rk"; break;
		case 56: middle = middle + "rt"; break;
		case 57: middle = middle + "rth"; break;
		case 58: middle = middle + "rsh"; break;
		case 59: middle = middle + "rch"; break;
		case 60: middle = middle + "s"; break;
		case 61: middle = middle + "ss"; break;
		case 62: middle = middle + "st"; break;
		case 63: middle = middle + "sp"; break;
		case 64: middle = middle + "sh"; break;
		case 65: middle = middle + "squ"; break;
		case 66: middle = middle + "sk"; break;
		case 67: middle = middle + "sc"; break;
		case 68: middle = middle + "sl"; break;
		case 69: middle = middle + "scr"; break;
		case 70: middle = middle + "sch"; break;
		case 71: middle = middle + "t"; break;
		case 72: middle = middle + "tt"; break;
		case 73: middle = middle + "th"; break;
		case 74: middle = middle + "tr"; break;
		case 75: middle = middle + "tw"; break;
		case 76: middle = middle + "v"; break;
		case 77: middle = middle + "w"; break;
		case 78: middle = middle + "wst"; break;
		case 79: middle = middle + "x"; break;
		case 80: middle = middle + "z"; break;
		case 81: middle = middle + "zr"; break;
		}
		
		return middle;
	}
	
	public static String getRandomStartName(Random random) {
		String start;
		switch(random.nextInt(51)) {
		case 0: start = "A"; break;
		case 1: start = "L"; break;
		case 2: start = "Au"; break;
		case 3: start = "B"; break;
		case 4: start = "Br"; break;
		case 5: start = "C"; break;
		case 6: start = "Cr"; break;
		case 7: start = "D"; break;
		case 8: start = "Dr"; break;
		case 9: start = "E"; break;
		case 10: start = "Ea"; break;
		case 11: start = "J"; break;
		case 12: start = "F"; break;
		case 13: start = "Fr"; break;
		case 14: start = "G"; break;
		case 15: start = "Gr"; break;
		case 16: start = "P"; break;
		case 17: start = "H"; break;
		case 18: start = "I"; break;
		case 19: start = "J"; break;
		case 20: start = "K"; break;
		case 21: start = "Kr"; break;
		case 22: start = "L"; break;
		case 23: start = "M"; break;
		case 24: start = "N"; break;
		case 25: start = "O"; break;
		case 26: start = "S"; break;
		case 27: start = "S"; break;
		case 28: start = "P"; break;
		case 29: start = "Ph"; break;
		case 30: start = "Pr"; break;
		case 31: start = "Qu"; break;
		case 32: start = "R"; break;
		case 33: start = "S"; break;
		case 34: start = "Sc"; break;
		case 35: start = "B"; break;
		case 36: start = "Sh"; break;
		case 37: start = "Sp"; break;
		case 38: start = "S"; break;
		case 39: start = "St"; break;
		case 40: start = "Str"; break;
		case 41: start = "T"; break;
		case 42: start = "T"; break;
		case 43: start = "Tr"; break;
		case 44: start = "U"; break;
		case 45: start = "V"; break;
		case 46: start = "W"; break;
		case 47: start = "B"; break;
		case 48: start = "X"; break;
		case 49: start = "Y"; break;
		case 50: start = "Z"; break;
		default: start = "B"; break;
		}
		
		if(start.contains("A") || start.contains("E") || start.contains("I") || start.contains("O") || start.contains("U") || start.contains("Y")) {
			switch(random.nextInt(82)) {
			case 0: start = start + "b"; break;
			case 1: start = start + "br"; break;
			case 2: start = start + "c"; break;
			case 3: start = start + "ch"; break;
			case 4: start = start + "cr"; break;
			case 5: start = start + "sc"; break;
			case 6: start = start + "ct"; break;
			case 7: start = start + "d"; break;
			case 8: start = start + "dg"; break;
			case 9: start = start + "dr"; break;
			case 10: start = start + "f"; break;
			case 11: start = start + "ft"; break;
			case 12: start = start + "fr"; break;
			case 13: start = start + "g"; break;
			case 14: start = start + "gg"; break;
			case 15: start = start + "gh"; break;
			case 16: start = start + "gr"; break;
			case 17: start = start + "pp"; break;
			case 18: start = start + "h"; break;
			case 19: start = start + "tt"; break;
			case 20: start = start + "ht"; break;
			case 21: start = start + "j"; break;
			case 22: start = start + "k"; break;
			case 23: start = start + "ll"; break;
			case 24: start = start + "rth"; break;
			case 25: start = start + "l"; break;
			case 26: start = start + "ll"; break;
			case 27: start = start + "lst"; break;
			case 28: start = start + "ls"; break;
			case 29: start = start + "lt"; break;
			case 30: start = start + "lf"; break;
			case 31: start = start + "lph"; break;
			case 32: start = start + "lg"; break;
			case 33: start = start + "lb"; break;
			case 34: start = start + "lm"; break;
			case 35: start = start + "lv"; break;
			case 36: start = start + "lw"; break;
			case 37: start = start + "lp"; break;
			case 38: start = start + "m"; break;
			case 39: start = start + "mm"; break;
			case 40: start = start + "mn"; break;
			case 41: start = start + "mst"; break;
			case 42: start = start + "mp"; break;
			case 43: start = start + "n"; break;
			case 44: start = start + "nn"; break;
			case 45: start = start + "nt"; break;
			case 46: start = start + "nst"; break;
			case 47: start = start + "p"; break;
			case 48: start = start + "pr"; break;
			case 49: start = start + "pt"; break;
			case 50: start = start + "tt"; break;
			case 51: start = start + "ph"; break;
			case 52: start = start + "qu"; break;
			case 53: start = start + "r"; break;
			case 54: start = start + "rc"; break;
			case 55: start = start + "rt"; break;
			case 56: start = start + "rt"; break;
			case 57: start = start + "rth"; break;
			case 58: start = start + "dd"; break;
			case 59: start = start + "rch"; break;
			case 60: start = start + "s"; break;
			case 61: start = start + "ss"; break;
			case 62: start = start + "st"; break;
			case 63: start = start + "sp"; break;
			case 64: start = start + "sh"; break;
			case 65: start = start + "squ"; break;
			case 66: start = start + "sk"; break;
			case 67: start = start + "sc"; break;
			case 68: start = start + "sl"; break;
			case 69: start = start + "scr"; break;
			case 70: start = start + "sch"; break;
			case 71: start = start + "t"; break;
			case 72: start = start + "tt"; break;
			case 73: start = start + "th"; break;
			case 74: start = start + "tr"; break;
			case 75: start = start + "tw"; break;
			case 76: start = start + "v"; break;
			case 77: start = start + "w"; break;
			case 78: start = start + "wst"; break;
			case 79: start = start + "x"; break;
			case 80: start = start + "z"; break;
			case 81: start = start + "st"; break;
			}
		}
		
		return start;
	}
	
	public static String getRandomMiddleName(Random random) {
		String middle;
		switch(random.nextInt(43)) {
		case 0: middle = "a"; break;
		case 1: middle = "a"; break;
		case 2: middle = "a"; break;
		case 3: middle = "a"; break;
		case 4: middle = "a"; break;
		case 5: middle = "a"; break;
		case 6: middle = "a"; break;
		case 7: middle = "ay"; break;
		case 8: middle = "ae"; break;
		case 9: middle = "au"; break;
		case 10: middle = "e"; break;
		case 11: middle = "e"; break;
		case 12: middle = "e"; break;
		case 13: middle = "e"; break;
		case 14: middle = "e"; break;
		case 15: middle = "ei"; break;
		case 16: middle = "ee"; break;
		case 17: middle = "ea"; break;
		case 18: middle = "au"; break;
		case 19: middle = "i"; break;
		case 20: middle = "i"; break;
		case 21: middle = "i"; break;
		case 22: middle = "i"; break;
		case 23: middle = "io"; break;
		case 24: middle = "ie"; break;
		case 25: middle = "o"; break;
		case 26: middle = "o"; break;
		case 27: middle = "o"; break;
		case 28: middle = "o"; break;
		case 29: middle = "o"; break;
		case 30: middle = "o"; break;
		case 31: middle = "o"; break;
		case 32: middle = "o"; break;
		case 33: middle = "oe"; break;
		case 34: middle = "oi"; break;
		case 35: middle = "ou"; break;
		case 36: middle = "u"; break;
		case 37: middle = "u"; break;
		case 38: middle = "u"; break;
		case 39: middle = "uo"; break;
		case 40: middle = "ua"; break;
		case 41: middle = "y"; break;
		case 42: middle = "ya"; break;
		default: middle = "a"; break;
		}
		
		switch(random.nextInt(82)) {
		case 0: middle = middle + "b"; break;
		case 1: middle = middle + "br"; break;
		case 2: middle = middle + "c"; break;
		case 3: middle = middle + "ch"; break;
		case 4: middle = middle + "cr"; break;
		case 5: middle = middle + "ck"; break;
		case 6: middle = middle + "ct"; break;
		case 7: middle = middle + "d"; break;
		case 8: middle = middle + "dg"; break;
		case 9: middle = middle + "dr"; break;
		case 10: middle = middle + "f"; break;
		case 11: middle = middle + "ft"; break;
		case 12: middle = middle + "fr"; break;
		case 13: middle = middle + "g"; break;
		case 14: middle = middle + "gg"; break;
		case 15: middle = middle + "gh"; break;
		case 16: middle = middle + "gr"; break;
		case 17: middle = middle + "gst"; break;
		case 18: middle = middle + "h"; break;
		case 19: middle = middle + "hst"; break;
		case 20: middle = middle + "th"; break;
		case 21: middle = middle + "j"; break;
		case 22: middle = middle + "k"; break;
		case 23: middle = middle + "kr"; break;
		case 24: middle = middle + "kt"; break;
		case 25: middle = middle + "l"; break;
		case 26: middle = middle + "ll"; break;
		case 27: middle = middle + "lst"; break;
		case 28: middle = middle + "ls"; break;
		case 29: middle = middle + "lt"; break;
		case 30: middle = middle + "lf"; break;
		case 31: middle = middle + "lph"; break;
		case 32: middle = middle + "lg"; break;
		case 33: middle = middle + "lb"; break;
		case 34: middle = middle + "lm"; break;
		case 35: middle = middle + "lv"; break;
		case 36: middle = middle + "lw"; break;
		case 37: middle = middle + "lp"; break;
		case 38: middle = middle + "m"; break;
		case 39: middle = middle + "mm"; break;
		case 40: middle = middle + "mn"; break;
		case 41: middle = middle + "mst"; break;
		case 42: middle = middle + "mp"; break;
		case 43: middle = middle + "n"; break;
		case 44: middle = middle + "nn"; break;
		case 45: middle = middle + "nt"; break;
		case 46: middle = middle + "nst"; break;
		case 47: middle = middle + "p"; break;
		case 48: middle = middle + "pr"; break;
		case 49: middle = middle + "pt"; break;
		case 50: middle = middle + "pth"; break;
		case 51: middle = middle + "ph"; break;
		case 52: middle = middle + "qu"; break;
		case 53: middle = middle + "r"; break;
		case 54: middle = middle + "rc"; break;
		case 55: middle = middle + "rk"; break;
		case 56: middle = middle + "rt"; break;
		case 57: middle = middle + "rth"; break;
		case 58: middle = middle + "rsh"; break;
		case 59: middle = middle + "rch"; break;
		case 60: middle = middle + "s"; break;
		case 61: middle = middle + "ss"; break;
		case 62: middle = middle + "st"; break;
		case 63: middle = middle + "sp"; break;
		case 64: middle = middle + "sh"; break;
		case 65: middle = middle + "squ"; break;
		case 66: middle = middle + "sk"; break;
		case 67: middle = middle + "sc"; break;
		case 68: middle = middle + "sl"; break;
		case 69: middle = middle + "scr"; break;
		case 70: middle = middle + "sch"; break;
		case 71: middle = middle + "t"; break;
		case 72: middle = middle + "tt"; break;
		case 73: middle = middle + "th"; break;
		case 74: middle = middle + "tr"; break;
		case 75: middle = middle + "tw"; break;
		case 76: middle = middle + "v"; break;
		case 77: middle = middle + "w"; break;
		case 78: middle = middle + "wst"; break;
		case 79: middle = middle + "x"; break;
		case 80: middle = middle + "z"; break;
		case 81: middle = middle + "s"; break;
		}
		
		return middle;
	}

	public static String getRandomEndName(Random random, boolean isFemale) {
		String middle = "";
		
		switch(random.nextInt(119)) {
		case 0: middle = "a"; break;
		case 1: middle = "a"; break;
		case 2: middle = "ab"; break;
		case 3: middle = "ac"; break;
		case 4: middle = "ad"; break;
		case 5: middle = "ah"; break;
		case 6: middle = "ak"; break;
		case 7: middle = "al"; break;
		case 8: middle = "am"; break;
		case 9: middle = "an"; break;
		case 10: middle = "ar"; break;
		case 11: middle = "as"; break;
		case 12: middle = "ast"; break;
		case 13: middle = "ash"; break;
		case 14: middle = "at"; break;
		case 15: middle = "au"; break;
		case 16: middle = "aw"; break;
		case 17: middle = "ay"; break;
		case 18: middle = "e"; break;
		case 19: middle = "ec"; break;
		case 20: middle = "ed"; break;
		case 21: middle = "edd"; break;
		case 22: middle = "eg"; break;
		case 23: middle = "el"; break;
		case 24: middle = "elle"; break;
		case 25: middle = "elm"; break;
		case 26: middle = "elt"; break;
		case 27: middle = "else"; break;
		case 28: middle = "els"; break;
		case 29: middle = "ell"; break;
		case 30: middle = "em"; break;
		case 31: middle = "en"; break;
		case 32: middle = "enne"; break;
		case 33: middle = "ene"; break;
		case 34: middle = "eo"; break;
		case 35: middle = "epe"; break;
		case 36: middle = "er"; break;
		case 37: middle = "ere"; break;
		case 38: middle = "erre"; break;
		case 39: middle = "ert"; break;
		case 40: middle = "erse"; break;
		case 41: middle = "ew"; break;
		case 42: middle = "ex"; break;
		case 43: middle = "ez"; break;
		case 44: middle = "ey"; break;
		case 45: middle = "i"; break;
		case 46: middle = "ic"; break;
		case 47: middle = "ick"; break;
		case 48: middle = "ie"; break;
		case 49: middle = "il"; break;
		case 50: middle = "ill"; break;
		case 51: middle = "im"; break;
		case 52: middle = "imp"; break;
		case 53: middle = "in"; break;
		case 54: middle = "io"; break;
		case 55: middle = "ip"; break;
		case 56: middle = "ir"; break;
		case 57: middle = "ire"; break;
		case 58: middle = "irt"; break;
		case 59: middle = "irse"; break;
		case 60: middle = "it"; break;
		case 61: middle = "ite"; break;
		case 62: middle = "itte"; break;
		case 63: middle = "is"; break;
		case 64: middle = "ish"; break;
		case 65: middle = "ist"; break;
		case 66: middle = "ive"; break;
		case 67: middle = "ix"; break;
		case 68: middle = "o"; break;
		case 69: middle = "ob"; break;
		case 70: middle = "oc"; break;
		case 71: middle = "od"; break;
		case 72: middle = "ode"; break;
		case 73: middle = "oe"; break;
		case 74: middle = "og"; break;
		case 75: middle = "oh"; break;
		case 76: middle = "ol"; break;
		case 77: middle = "ole"; break;
		case 78: middle = "olle"; break;
		case 79: middle = "om"; break;
		case 80: middle = "on"; break;
		case 81: middle = "op"; break;
		case 82: middle = "or"; break;
		case 83: middle = "ort"; break;
		case 84: middle = "ord"; break;
		case 85: middle = "orn"; break;
		case 86: middle = "orm"; break;
		case 87: middle = "os"; break;
		case 88: middle = "ose"; break;
		case 89: middle = "ost"; break;
		case 90: middle = "osh"; break;
		case 91: middle = "ot"; break;
		case 92: middle = "ott"; break;
		case 93: middle = "otte"; break;
		case 94: middle = "ove"; break;
		case 95: middle = "ow"; break;
		case 96: middle = "ox"; break;
		case 97: middle = "oz"; break;
		case 98: middle = "oy"; break;
		case 99: middle = "u"; break;
		case 100: middle = "ul"; break;
		case 101: middle = "ule"; break;
		case 102: middle = "ut"; break;
		case 103: middle = "ute"; break;
		case 104: middle = "um"; break;
		case 105: middle = "uh"; break;
		case 106: middle = "us"; break;
		case 107: middle = "uss"; break;
		case 108: middle = "ust"; break;
		case 109: middle = "un"; break;
		case 110: middle = "unne"; break;
		case 111: middle = "y"; break;
		case 112: middle = "yne"; break;
		case 113: middle = "yr"; break;
		case 114: middle = "yse"; break;
		case 115: middle = "ey"; break;
		case 116: middle = "y"; break;
		case 117: middle = "yl"; break;
		case 118: middle = "ey"; break;
		}
		
		if(isFemale) {
			switch(random.nextInt(61)) {
			case 0: middle = "a"; break;
			case 1: middle = "a"; break;
			case 2: middle = "ad"; break;
			case 3: middle = "ah"; break;
			case 4: middle = "ah"; break;
			case 5: middle = "alle"; break;
			case 6: middle = "are"; break;
			case 7: middle = "ase"; break;
			case 8: middle = "asse"; break;
			case 9: middle = "ash"; break;
			case 10: middle = "ay"; break;
			case 11: middle = "e"; break;
			case 12: middle = "elle"; break;
			case 13: middle = "ele"; break;
			case 14: middle = "em"; break;
			case 15: middle = "en"; break;
			case 16: middle = "ene"; break;
			case 17: middle = "enne"; break;
			case 18: middle = "eo"; break;
			case 19: middle = "erre"; break;
			case 20: middle = "erse"; break;
			case 21: middle = "ey"; break;
			case 22: middle = "ey"; break;
			case 23: middle = "ey"; break;
			case 24: middle = "elle"; break;
			case 25: middle = "elle"; break;
			case 26: middle = "ie"; break;
			case 27: middle = "ie"; break;
			case 28: middle = "il"; break;
			case 29: middle = "is"; break;
			case 30: middle = "ic"; break;
			case 31: middle = "itte"; break;
			case 32: middle = "ette"; break;
			case 33: middle = "ette"; break;
			case 34: middle = "ille"; break;
			case 35: middle = "eau"; break;
			case 36: middle = "o"; break;
			case 37: middle = "otte"; break;
			case 38: middle = "ol"; break;
			case 39: middle = "ole"; break;
			case 40: middle = "oh"; break;
			case 41: middle = "on"; break;
			case 42: middle = "onne"; break;
			case 43: middle = "om"; break;
			case 44: middle = "olle"; break;
			case 45: middle = "ique"; break;
			case 46: middle = "or"; break;
			case 47: middle = "ore"; break;
			case 48: middle = "ow"; break;
			case 49: middle = "u"; break;
			case 50: middle = "us"; break;
			case 51: middle = "ul"; break;
			case 52: middle = "ude"; break;
			case 53: middle = "udy"; break;
			case 54: middle = "uby"; break;
			case 55: middle = "y"; break;
			case 56: middle = "yr"; break;
			case 57: middle = "yse"; break;
			case 58: middle = "yne"; break;
			case 59: middle = "yme"; break;
			case 60: middle = "y"; break;
			}
		}
		
		return middle;
	}
	
}

