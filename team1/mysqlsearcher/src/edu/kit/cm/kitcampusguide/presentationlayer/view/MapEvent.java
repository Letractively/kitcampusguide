package edu.kit.cm.kitcampusguide.presentationlayer.view;

import java.util.EventObject;

/**
 * Represents an event sent by the map component. This can be a click on a POI,
 * the change of the current map section or the user's intention to change the
 * current from marker. A MapEvent has an arbitrary data parameter which
 * contains more information about the event. MapEvents will be registered by
 * {@link MapEventsListener}.
 * 
 * @author Stefan
 * 
 */
public class MapEvent extends EventObject {

	/**
	 * The event's type identifier.
	 */
	private String type;

	/**
	 * The event's data object.
	 */
	private Object data;

	/**
	 * Creates a new MapEvent with all necessary data.
	 * 
	 * @param source
	 *            the event's source, see {@link #getSource()}
	 * @param type
	 *            the event's type, for example "clickOnPOI" or
	 *            "setRouteFromByContextMenu"
	 * @param data
	 *            an arbitrary data object which can be used to parameterize the
	 *            event
	 * @throws IllegalArgumentException
	 *             if <code>type</code> or <code>source</code> is
	 *             <code>null</code>
	 */
	public MapEvent(Object source, String type, Object data) {
		super(source);
		if (type == null) {
			throw new IllegalArgumentException();
		}
		this.type = type;
		this.data = data;
	}

	/**
	 * Returns the event's type identifier. It gives information about which
	 * kind of event occurred.
	 * 
	 * @return the event type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns an arbitrary data object which gives more information about the
	 * event. The object's type depends on the event type.
	 * 
	 * @return a data object
	 */
	public Object getData() {
		return data;
	}
}
