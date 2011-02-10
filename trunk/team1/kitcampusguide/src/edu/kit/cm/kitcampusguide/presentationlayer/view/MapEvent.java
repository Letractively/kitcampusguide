package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.EventObject;

public class MapEvent extends EventObject {

	private String type;
	private Object data;

	public MapEvent(Object source, String type, Object data) {
		super(source);
		this.type = type;
		this.data = data;
	}

	public String getType() {
		return type;
	}
	
	public Object getData() {
		return data;
	}
}
