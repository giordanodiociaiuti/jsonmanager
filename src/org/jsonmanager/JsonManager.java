
package org.jsonmanager;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @class    JsonManager
 *
 * @version  1.0.0
 *
 * @author   Giordano Diociaiuti
 *
 * @date     2022-05-11
 *
 * @email    giordano.diociaiuti@libero.it
 */
public class JsonManager extends UtilityJsonManager {

	private static HashMap<String, HashMap<String, String>> mapJson = new HashMap();
	private static HashMap<String, ArrayList<String>> mapRows = new HashMap();

	public static synchronized ArrayList<String> getRows (String sessionId) {

		return mapRows.get(sessionId);
	}

	public static synchronized void createObject (HttpServletRequest request, String objectName) throws Exception {

		String sessionId = getSessionId(request);
		if (objectName != null && !objectName.trim().equals("") && (mapJson.get(sessionId) == null || mapJson.get(sessionId).get(objectName) == null)) {
			objectName = objectName.trim();
			if (Validation.validateObjectName(objectName) == true) {
				if (hasFatherExisting(sessionId, objectName) == true) {
					if (mapJson.get(sessionId) == null) {
						HashMap<String, String> map = new HashMap();
						mapJson.put(sessionId, map);
					}
					mapJson.get(sessionId).put(objectName, "{}");
				} else {
					throw new Exception("Object '" + getFather(objectName) + "' doesn't exist.");
				}
			} else {
				throw new Exception("Object '" + objectName + "' has not valid chars.");
			}
		}
	}

	public static synchronized void addProperty (HttpServletRequest request, String propertyName, String value) throws Exception {

		String sessionId = getSessionId(request);
		if (propertyName != null && !propertyName.trim().equals("") && (mapJson.get(sessionId) == null || mapJson.get(sessionId).get(propertyName) == null)) {
			propertyName = propertyName.trim();
			if (Validation.validatePropertyName(propertyName) == true) {
				if (hasFatherExisting(sessionId, propertyName) == true) {
					String v = null;
					if (value == null || value.toLowerCase().equals("null") || value.equals("")) {
						v = "null";
					} else {
						v = value;
					}
					if (!v.equals("null")) {
						if (Validation.valideValue(v) == true) {
							if (mapJson.get(sessionId) == null) {
								HashMap<String, String> map = new HashMap();
								mapJson.put(sessionId, map);
							}
							mapJson.get(sessionId).put(propertyName, "\"" + getSinglePropertyName(propertyName) + "\":\"" + v + "\"");
						} else {
							throw new Exception("Value '" + v + "' has not valid chars.");
						}
					} else {
						if (mapJson.get(sessionId) == null) {
							HashMap<String, String> map = new HashMap();
							mapJson.put(sessionId, map);
						}
						mapJson.get(sessionId).put(propertyName, "\"" + getSinglePropertyName(propertyName) + "\":null");
					}
				} else {
					throw new Exception("Object '" + getFather(propertyName) + "' doesn't exist.");
				}
			} else {
				throw new Exception("Property '" + propertyName + "' has not valid chars.");
			}
		}
	}

	public static synchronized void updateProperty (HttpServletRequest request, String propertyName, String value) throws Exception {

		String sessionId = getSessionId(request);
		if (propertyName != null && !propertyName.trim().equals("") && (mapJson.get(sessionId) != null && mapJson.get(sessionId).get(propertyName) != null)) {
			propertyName = propertyName.trim();
			if (Validation.validatePropertyName(propertyName) == true) {
				if (hasFatherExisting(sessionId, propertyName) == true) {
					String v = null;
					if (value == null || value.toLowerCase().equals("null") || value.equals("")) {
						v = "null";
					} else {
						v = value;
					}
					if (!v.equals("null")) {
						if (Validation.valideValue(v) == true) {
							if (mapJson.get(sessionId) == null) {
								HashMap<String, String> map = new HashMap();
								mapJson.put(sessionId, map);
							}
							mapJson.get(sessionId).put(propertyName, "\"" + getSinglePropertyName(propertyName) + "\":\"" + v + "\"");
						} else {
							throw new Exception("Value '" + v + "' has not valid chars.");
						}
					} else {
						if (mapJson.get(sessionId) == null) {
							HashMap<String, String> map = new HashMap();
							mapJson.put(sessionId, map);
						}
						mapJson.get(sessionId).put(propertyName, "\"" + getSinglePropertyName(propertyName) + "\":null");
					}
				} else {
					throw new Exception("Object '" + getFather(propertyName) + "' doesn't exist.");
				}
			} else {
				throw new Exception("Property '" + propertyName + "' has not valid chars.");
			}
		}
	}

	public static synchronized void createList (HttpServletRequest request, String listName) throws Exception {

		String sessionId = getSessionId(request);
		if (listName != null && !listName.trim().equals("") && (mapJson.get(sessionId) == null || mapJson.get(sessionId).get(listName) == null)) {
			listName = listName.trim();
			if (Validation.validatePropertyName(listName) == true) {
				if (hasFatherExisting(sessionId, listName) == true) {
					if (mapJson.get(sessionId) == null) {
						HashMap<String, String> map = new HashMap();
						mapJson.put(sessionId, map);
					}
					mapJson.get(sessionId).put(listName, "[]");
				} else {
					throw new Exception("Object '" + getFather(listName) + "' doesn't exist.");
				}
			} else {
				throw new Exception("List '" + listName + "' has not valid chars.");
			}
		}
	}

	public static synchronized void addItem (HttpServletRequest request, String itemName, int index, String propertyName, String value) throws Exception {

		String sessionId = getSessionId(request);
		if (itemName != null && !itemName.trim().equals("") && index > -1 && (mapJson.get(sessionId) == null || mapJson.get(sessionId).get(itemName + ".[" + index + "]." + propertyName) == null)) {
			itemName = itemName.trim();
			if (Validation.validatePropertyName(itemName) == true) {
				if (Validation.validateSinglePropertyName(propertyName) == true) {
					if (mapJson.get(sessionId) != null && mapJson.get(sessionId).get(itemName) != null) {
						if (index == 0 || (mapJson.get(sessionId) != null && mapJson.get(sessionId).get(itemName + ".[" + (index-1) + "]." + propertyName) != null)) {
							String v = null;
							if (value == null || value.toLowerCase().equals("null") || value.equals("")) {
								v = "null";
							} else {
								v = value;
							}
							if (!v.equals("null")) {
								if (Validation.valideValue(v) == true) {
									if (mapJson.get(sessionId) == null) {
										HashMap<String, String> map = new HashMap();
										mapJson.put(sessionId, map);
									}
									mapJson.get(sessionId).put(itemName + ".[" + index + "]." + propertyName, "\"" + propertyName + "\":\"" + v + "\"");
								} else {
									throw new Exception("Value '" + v + "' has not valid chars.");
								}
							} else {
								if (mapJson.get(sessionId) == null) {
									HashMap<String, String> map = new HashMap();
									mapJson.put(sessionId, map);
								}
								mapJson.get(sessionId).put(itemName + ".[" + index + "]." + propertyName, "\"" + propertyName + "\":null");
							}
						} else {
							throw new Exception("List '" + itemName + "' has a missing item: '" + propertyName + "[" + index + "]' is present but not '" + propertyName + "[" + (index-1) + "]'.");
						}
					} else {
						throw new Exception("List '" + itemName + "' doesn't exist.");
					}
				} else {
					throw new Exception("Property '" + propertyName + "' has not valid chars.");
				}
			} else {
				throw new Exception("Item '" + itemName + "' has not valid chars.");
			}
		}
	}

	public static synchronized String getJson (HttpServletRequest request, String objectName) throws Exception {

		String json = null;
		String sessionId = getSessionId(request);

		if (mapJson.get(sessionId) == null) {
			return json;
		}

		try {

			int levelMax = 0;
			ArrayList<String> keys = new ArrayList();

			for (String key : mapJson.get(sessionId).keySet()) {
				if (key.startsWith(objectName)) {
					keys.add(key);
				}
			}
			for (int i = 0; i < keys.size(); i++) {
				int level = ((keys.get(i)).split("\\.")).length;
				if (level > levelMax) levelMax = level;
			}

			ArrayList<String> array = new ArrayList();
			for (int level = 1; level <= levelMax; level++) {
				for (int i = 0; i < keys.size(); i++) {
					int lev = getLevel(keys.get(i));
					if (lev == level) {
						array.add(keys.get(i));
					}
				}
			}

			for (int i = 0; i < array.size(); i++) {
				String key = array.get(i);
				String father = getFather(key);
				String property = getSinglePropertyName(key);
				if (father == null) {
					if (mapRows.get(sessionId) == null) {
						ArrayList<String> list = new ArrayList();
						mapRows.put(sessionId, list);
					}
					mapRows.get(sessionId).add(key + ":{");
					mapRows.get(sessionId).add("}");
				} else {
					int[] limit = findFather(sessionId, father);
					if (limit[0] > -1 && limit[1] > limit[0]) {
						String firstChar = property.substring(0, 1);
						if ("abcdefghijklmnopqrstuvwxyz".indexOf(firstChar) != -1) {
							String k = mapJson.get(sessionId).get(key);
							int level = getLevel(key);
							int ns = 2 * (level - 1);
							String filler = getFiller(ns);
							if (!k.equals("[]")) {
								k = filler + k;
								if (mapRows.get(sessionId) == null) {
									ArrayList<String> list = new ArrayList();
									mapRows.put(sessionId, list);
								}
								mapRows.get(sessionId).add(limit[1], k + ",");
							} else {
								String k1 = key;
								String k2 = "],";
								k1 = filler + k1;
								k2 = filler + k2;
								if (mapRows.get(sessionId) == null) {
									ArrayList<String> list = new ArrayList();
									mapRows.put(sessionId, list);
								}
								mapRows.get(sessionId).add(limit[1], k1 + ":[");
								int numItems = getNumItems(k1.trim(), array);
								for (int r = 0; r < numItems; r++) {
									mapRows.get(sessionId).add(limit[1]+1, "  " + k1 + ".[" + (numItems-r-1) + "]:{");
									mapRows.get(sessionId).add(limit[1]+2, filler + "  },");
								}
								mapRows.get(sessionId).add(limit[1]+2*numItems+1, k2);
							}
						}
						if ("ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(firstChar) != -1) {
							int level = getLevel(key);
							int ns = 2 * (level - 1);
							String filler = getFiller(ns);
							String k1 = key + ":{";;
							String k2 = "},";
							k1 = filler + k1;
							k2 = filler + k2;
							if (mapRows.get(sessionId) == null) {
								ArrayList<String> list = new ArrayList();
								mapRows.put(sessionId, list);
							}
							mapRows.get(sessionId).add(limit[1], k1);
							mapRows.get(sessionId).add(limit[1]+1, k2);
						}
					}
				}
			}

			if (mapRows.get(sessionId) == null) {
				ArrayList<String> list = new ArrayList();
				mapRows.put(sessionId, list);
			}
			mapRows.get(sessionId).add(0, "{");
			mapRows.get(sessionId).remove(1);
			for (int i = 1; i < mapRows.get(sessionId).size(); i++) {
				if (mapRows.get(sessionId).get(i).endsWith(":{")) {
					if (!mapRows.get(sessionId).get(i).endsWith("]:{")) {
						int ns = getNumberSpace(mapRows.get(sessionId).get(i));
						String filler = getFiller(ns);
						String key = mapRows.get(sessionId).get(i).substring(0, mapRows.get(sessionId).get(i).length()-2).trim();
						String childName = getSinglePropertyName(key);
						mapRows.get(sessionId).add(i, filler + "\"" + childName + "\":{");
						mapRows.get(sessionId).remove(i+1);
					} else {
						int ns = getNumberSpace(mapRows.get(sessionId).get(i));
						String filler = getFiller(ns);
						mapRows.get(sessionId).add(i, filler + "{");
						mapRows.get(sessionId).remove(i+1);
					}
				}
				if (mapRows.get(sessionId).get(i).endsWith(":[")) {
					int ns = getNumberSpace(mapRows.get(sessionId).get(i));
					String filler = getFiller(ns);
					String key = mapRows.get(sessionId).get(i).substring(0, mapRows.get(sessionId).get(i).length()-2).trim();
					String childName = getSinglePropertyName(key);
					mapRows.get(sessionId).add(i, filler + "\"" + childName + "\":[");
					mapRows.get(sessionId).remove(i+1);
				}
			}

			for (int i = 0; i < mapRows.get(sessionId).size(); i++) {
				String row = mapRows.get(sessionId).get(i);
				if (row.endsWith(",")) {
					String nextRow = mapRows.get(sessionId).get(i+1).trim();
					if (nextRow.startsWith("}") || nextRow.startsWith("]")){
						mapRows.get(sessionId).add(i, row.substring(0, row.length()-1));
						mapRows.get(sessionId).remove(i+1);
					}
				}
			}
			json = "";
			for (int i = 0; i < mapRows.get(sessionId).size(); i++) {
				json = json + mapRows.get(sessionId).get(i).trim();
			}

			mapJson.put(sessionId, null);
			mapRows.put(sessionId, null);
		}
		catch (Exception ex) {
			json = null;
			throw new Exception("The json string could not be created.", ex);
		}

		return json;
	}

	public static synchronized void showJson (HttpServletRequest request) throws Exception {

		String sessionId = getSessionId(request);
		for (int i = 0; i < mapRows.get(sessionId).size(); i++) {
			System.out.println(mapRows.get(sessionId).get(i));
		}
	}

	private static synchronized boolean hasFatherExisting (String sessionId, String name) throws Exception {

		boolean b = false;
		String father = getFather(name);
		if (father != null) {
			if (mapJson.get(sessionId).get(father) != null) {
				b = true;
			}
		} else {
			b = true;
		}

		return b;
	}

	private static synchronized String getSessionId (HttpServletRequest request) throws Exception {

		String sessionId = null;
		if (request != null && request.getSession() != null && request.getSession().getId() != null) {
			sessionId = request.getSession().getId();
		} else {
			throw new Exception("Session is not valid or expired.");
		}

		return sessionId;
	}

}
