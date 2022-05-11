
package org.jsonmanager;

import java.util.*;

/**
 * @class    UtilityJsonManager
 *
 * @version  1.0.0
 *
 * @author   Giordano Diociaiuti
 *
 * @date     2022-05-11
 *
 * @email    giordano.diociaiuti@libero.it
 */
public class UtilityJsonManager {

	public static synchronized int getNumberSpace (String key) {

		int ns = 0;
		boolean stop = false;
		for (int i = 0; (stop == false && i < key.length()); i++) {
			if (key.substring(i, i+1).equals(" ")) {
				ns = ns + 1;
			} else {
				stop = true; 
			}
		}

		return ns;
	}

	public static synchronized String getFiller (int ns) {

		String s = "";
		for (int i = 0; i < ns; i++) s = s + " ";

		return s;
	}

	public static synchronized int getNumItems (String listName, ArrayList<String> array) {

		int numItems = -1;
		for (int i = 0; i < array.size(); i++) {
			String key = array.get(i).trim();
			String k = listName + ".[";
			if (key.startsWith(k)) {
				int pos = key.indexOf("]");
				int index = Integer.valueOf(key.substring(k.length(), pos));
				if (index > numItems) numItems = index;
			}
		}

		return (numItems+1);
	}

	public static synchronized String getSinglePropertyName (String propertyName) {

		String property = "";
		if (propertyName != null && !propertyName.equals("") && propertyName.indexOf(".") != -1) {
			int pos = propertyName.lastIndexOf(".");
			property = propertyName.substring(pos+1, propertyName.length());
		}

		return property;
	}

	public static synchronized int getLevel (String name) {

		return (name.split("\\.")).length;
	}

	public static String getFather (String name) {

		String father = null;
		if (name.indexOf(".") != -1) {
			int pos = name.lastIndexOf(".");
			father = name.substring(0, pos);
		}

		return father;
	}

	public static synchronized int[] findFather (String sessionId, String father) {

		int[] limit = new int[2];
		int level = getLevel(father);
		int ns = 2 * (level - 1);
		String k1 = father + ":{";
		String k2 = "}";
		for (int i = 0; i < ns; i++) {
			k1 = " " + k1;
			k2 = " " + k2;
		}

		int index1 = -1;
		int index2 = -1;
		boolean find = false;
		for (int i = 0; (find == false && i < JsonManager.getRows(sessionId).size()); i++) {
			if (JsonManager.getRows(sessionId).get(i).equals(k1)) {
				find = true;
				index1 = i;
			}
		}
		if (index1 > -1) {
			find = false;
			for (int i = index1+1; (find == false && i < JsonManager.getRows(sessionId).size()); i++) {
				if (JsonManager.getRows(sessionId).get(i).startsWith(k2)) {
					find = true;
					index2 = i;
				}
			}
		}

		limit[0] = index1;
		limit[1] = index2;

		return limit;
	}

}
