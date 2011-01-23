package edu.kit.cm.kitcampusguide.model;

/**
 * This class contains the current parameters of the map. This includes the
 * scale factor, current language, height and width of the map and the point in
 * which the map has to be centered.
 * 
 * @author Monica Haurilet
 * 
 */
public class Settings {

	private double width;
	private double height;
	private double scaleFactor;
	private int language;
	private Point center;

	/**
	 * 
	 * This constructor creates a new object of the type Settings, which
	 * contains parameters of the current view of the map. The parameters are:
	 * width, height, scale factor, language and the center of the map.
	 * 
	 * @param width
	 *            contains the current width of the view of the map.
	 * @param height
	 *            contains the current height of the view of the map.
	 * @param scaleFactor
	 *            is the dimension with which the map should be scaled.
	 * @param language
	 *            is the id of the current language of the KitCampusGuide.
	 * @param center
	 *            is the point, in which the view of the map is centered.
	 */
	public Settings(double width, double height, double scaleFactor,
			int language, Point center) {
		setWidth(width);
		setHeight(height);
		setScaleFactor(scaleFactor);
		setLanguage(language);
		setCenter(center);
	}

	// TODO SetWidth behalten? dann auch im Entwurf ändern
	public void setWidth(double width) {
		if (width <= 0) {
			throw new IllegalArgumentException(
					"The width can't be negative or 0");
		}
		this.width = width;
	}

	// TODO SetHeight behalten? dann auch im Entwurf ändern
	public void setHeight(double height) {
		if (height <= 0) {
			throw new IllegalArgumentException(
					"The height can't be negative or 0");
		}
		this.height = height;
	}

	/**
	 * Returns a point of the center of the map.
	 * 
	 * @return a point in which the map has to be centered.
	 */
	public Point getCenter() {
		return this.center;
	}

	/**
	 * This method sets a new point as center of the map.
	 * 
	 * @param center
	 *            is the point with the new center of the map.
	 * @throws NullPointerException
	 *             if the center is <code>null</code>
	 */
	public void setCenter(Point center) {
		if (center == null) {
			throw new NullPointerException("the center shouldn't be null");
		}
		this.center = center;
	}

	/**
	 * This method returns the scale factor, that was set to the map.
	 * 
	 * @return the current scale factor of the map.
	 */
	public double getScaleFactor() {
		return this.scaleFactor;
	}

	/**
	 * This method sets a new scale factor to the map.
	 * 
	 * @param scaleFactor
	 *            is the new scale factor, which has to be set to the map.
	 */
	public void setScaleFactor(double scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	/**
	 * This method returns the id of the current language of the KitCampusGuide.
	 * 
	 * @return the id of the current language of the KitCampusGuide.
	 */
	public int getLanguage() {
		return this.language;
	}

	/**
	 * This method sets a new language as the current language.
	 * 
	 * @param language
	 *            is the id of the new language, which has to be set.
	 * @throws IllegalArgumentException
	 *             if the id of the language negative is.
	 */
	public void setLanguage(Integer language) {
		if (language < 0) {
			throw new IllegalArgumentException(
					"The id of a language can't be negative");
		}
		this.language = language;
	}

	/**
	 * Returns the width of the map.
	 * 
	 * @return the width of the map.
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Returns the height of the map.
	 * 
	 * @return the height of the map.
	 */
	public double getHeight() {
		return this.height;
	}
}

	
