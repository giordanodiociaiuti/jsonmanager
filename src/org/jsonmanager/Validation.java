
package org.jsonmanager;

/**
 * @class    Validation
 *
 * @version  1.0.0
 *
 * @author   Giordano Diociaiuti
 *
 * @date     2022-05-11
 *
 * @email    giordano.diociaiuti@libero.it
 */
public class Validation {

	public static synchronized boolean valideText (String text, String validChars) {

		boolean b = true;
		if (text == null || text.trim().equals("")) return false;
		for (int i = 0; (b == true && i < text.length()); i++) {
			String c = text.substring(i, i+1);
			if (validChars.indexOf(c) == -1) {
				b = false;
			}
		}

		return b;
	}

	public static synchronized boolean validateObjectName (String objectName) {

		boolean b = valideText(objectName, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.");
		if (b == true) {
			String firstChar = objectName.substring(0, 1);
			String lastChar = objectName.substring(objectName.length()-1, objectName.length());
			if (firstChar.equals(".") || lastChar.equals(".") || objectName.indexOf("..") != -1) {
				b = false;
			}
			if (b == true) {
				String[] objects = objectName.split("\\.");
				for (int i = 0; (b == true && i < objects.length); i++) {
					firstChar = objects[i].substring(0, 1);
					if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(firstChar) == -1) {
						b = false;
					}
				}
			}
		}

		return b;
	}

	public static synchronized boolean validatePropertyName (String propertyName) {

		boolean b = valideText(propertyName, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.-");
		if (b == true) {
			String firstChar = propertyName.substring(0, 1);
			String lastChar = propertyName.substring(propertyName.length()-1, propertyName.length());
			if (firstChar.equals(".") || lastChar.equals(".") || propertyName.indexOf("..") != -1 || propertyName.indexOf("--") != -1) {
				b = false;
			}
			if (b == true) {
				if (propertyName.indexOf(".") == -1) {
					b = false;
				}
				if (b == true) {
					String[] properties = propertyName.split("\\.");
					for (int i = 0; (b == true && i < (properties.length-1)); i++) {
						firstChar = properties[i].substring(0, 1);
						if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(firstChar) == -1) {
							b = false;
						}
					}
					if (b == true) {
						firstChar = properties[properties.length-1].substring(0, 1);
						lastChar = properties[properties.length-1].substring(properties[properties.length-1].length()-1, properties[properties.length-1].length());
						if ("abcdefghijklmnopqrstuvwxyz".indexOf(firstChar) == -1 || lastChar.equals("-")) {
							b = false;
						}
					}
				}
			}
		}

		return b;
	}

	public static synchronized boolean validateSinglePropertyName (String propertyName) {

		boolean b = valideText(propertyName, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-");
		if (b == true) {
			String firstChar = propertyName.substring(0, 1);
			String lastChar = propertyName.substring(propertyName.length()-1, propertyName.length());
			if ("abcdefghijklmnopqrstuvwxyz".indexOf(firstChar) == -1 || lastChar.equals("-") || propertyName.indexOf("--") != -1) {
				b = false;
			}
		}

		return b;
	}

	public static synchronized boolean valideValue (String value) {

		boolean b = true;
		if (value == null || value.equals("")) return false;
		if ((value.indexOf("{") != -1) || (value.indexOf("}") != -1) || (value.indexOf("[") != -1) || (value.indexOf("]") != -1)) {
			b = false;
		}

		return b;
	}

}
