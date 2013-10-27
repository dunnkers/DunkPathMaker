package com.dunnkers.util;

/**
 * 
 * @author Dunnkers
 */
public class Enums {

	/**
	 * Searches the given enum for an object matching the given name, if not
	 * found returns null.
	 * 
	 * @return An instance of the found enum, if not found returns <i>null</i>.
	 */
	public static <E extends Enum<E>, T> E findEnumObject(Class<E> enumClass,
			String enumName) {
		return findEnumObject(enumClass, enumName, null);
	}

	/**
	 * Searches the given enum for an object matching the given name, if not
	 * found returns the given default Enum.
	 * 
	 * @return An instance of the found enum, if not found returns the given
	 *         <i>defaultEnum</i>.
	 */
	public static <E extends Enum<E>, T> E findEnumObject(Class<E> enumClass,
			String enumName, E defaultEnum) {
		for (final E enumObject : enumClass.getEnumConstants()) {
			if (enumObject.name().equals(enumName)) {
				return enumObject;
			}
		}
		return defaultEnum;
	}
}
