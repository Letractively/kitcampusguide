package edu.kit.pse.ass.gui.layout;

import java.util.HashMap;

import edu.kit.pse.ass.entity.Property;

/**
 * 
 * @author Oliver Schneider
 * 
 */
public class PropertyIconMap {
	HashMap<String, String> map = new HashMap<String, String>();

	private static String iconFolder = "images/icons/properties/";

	public PropertyIconMap() {
		map.put("WLAN", "network-wireless");
		map.put("W-LAN", "network-wireless");
		map.put("Licht", "light");
		map.put("PC", "computer");
		map.put("LAN", "network-wired");
		map.put("Barrierefrei", "accessibility");
		map.put("Drucker", "printer");
		map.put("Steckdose", "plug");
		map.put("Beamer", "projector");
		map.put("Tafel", "whiteboard");
		map.put("FlipChart", "whiteboard");
	}

	public String getIconFileName(Property prop, int size) {
		if (size != 32 && size != 22 && size != 16) {
			throw new IllegalArgumentException("icon size has to be 16, 22 or 32.");
		}
		String iconName = getIconName(prop);
		String iconFileName = iconFolder + iconName + "_" + size + ".png";
		return iconFileName;
	}

	private String getIconName(Property prop) {
		String value = "noicon";
		String name = prop.getName();

		if (map.containsKey(name)) {
			value = map.get(name);
		} else {
			for (String key : map.keySet()) {
				if (name.contains(key)) {
					value = map.get(key);
					break;
				}
			}
		}
		return value;
	}
}
