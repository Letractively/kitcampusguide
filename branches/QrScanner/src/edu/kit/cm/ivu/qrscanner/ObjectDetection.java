package edu.kit.cm.ivu.qrscanner;

/**
 * The ObjectDetection specifies a uniform and extensible interface to enable
 * any activity to identify an object in use of different technologies. The
 * interface defines the actions used to fetch object information from a so
 * called detector and define the request codes and data field indices to read
 * the fetched object information.
 * 
 * @author Christoph F�hrdes <christoph.foehrdes@student.kit.edu>
 */
public interface ObjectDetection {

	/**
	 * Defines an action to fetch an object identification String with an intent
	 * over an object detector. This action requires the type field of the
	 * submitted intent object to be set to the detector type you want to use.
	 * The type has to have the following form: <code>detector/*</code> <br>
	 * <br>
	 * Possible object detectors may be:
	 * <ul>
	 * <li><code>detector/nfc</code>: To detect an object by reading an NFC Tag</li>
	 * <li><code>detector/qr</code>: To detect an object by scanning a QR code</li>
	 * <li><code>detector/manual</code>: To detecting an object by asking the
	 * user to type the Id manually</li>
	 * <li>...</li>
	 * </ul>
	 * Or any way you can image to identify an object standing around.<br>
	 * <br>
	 * The result of the detection will be returned in the intend extra field
	 * defined by the constant
	 * <code>edu.kit.cm.ivu.ObjectDetection.EXTRA_OBJECT_ID</code>.
	 */
	public static final String ACTION_GET_IDENTIFIER = "edu.kit.cm.ivu.actions.ACTION_GET_IDENTIFIER";

	/**
	 * The request code suggested to be used when requesting an object detection
	 * with the method <code>startActivityForResult(Intent, RequestCode)</code>
	 */
	public static final int GET_IDENTIFIER_REQUEST_CODE = 100;

	/**
	 * The extra field index used by the detector to store the detected object
	 * ID in the returned Intent.
	 */
	public static final String EXTRA_OBJECT_ID = "edu.kit.cm.ivu.extras.OBJECT_ID";

	/**
	 * An additional field that can be used by the detector to return additional
	 * metadata about the detected object. For example additional data read by
	 * the detector on an NFC tag.
	 */
	public static final String EXTRA_OBJECT_DATA = "edu.kit.cm.ivu.extras.OBJECT_DATA";

}
