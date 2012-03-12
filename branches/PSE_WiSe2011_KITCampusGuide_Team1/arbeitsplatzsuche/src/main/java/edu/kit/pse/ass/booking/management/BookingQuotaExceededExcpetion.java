package edu.kit.pse.ass.booking.management;

/**
 * The Class BookingQuotaExceededExcpetion.
 */
public class BookingQuotaExceededExcpetion extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4732218047474119159L;

	/** The quota. */
	private final Quota quota;

	/** The limit. */
	private final double quotaLimit;

	/** The userid. */
	private final String userId;

	/**
	 * The Enum Quota.
	 */
	public static enum Quota {

		/** The UNKNOWN. */
		UNKNOWN,
		/** HOURS_PER_BOOKING: limit for hours per booking. */
		HOURS_PER_BOOKING,

		/** SIMULTANEOUS_BOOKINGS: limit for bookings of one user at the same time. */
		SIMULTANEOUS_BOOKINGS,
		/** BOOKINGS_PER_DAY: limit for amount of bookings per day. */
		BOOKINGS_PER_DAY
	}

	/**
	 * Instantiates a new booking quota exceeded excpetion.
	 * 
	 * @param quota
	 *            the quota
	 * @param limit
	 *            the limit
	 * @param userid
	 *            the userid
	 */
	public BookingQuotaExceededExcpetion(Quota quota, double limit, String userid) {
		this.quota = quota;
		this.quotaLimit = limit;
		this.userId = userid;
	}

	/**
	 * Instantiates a new booking quota exceeded excpetion.
	 * 
	 * @param message
	 *            the message
	 * @param quota
	 *            the quota
	 * @param limit
	 *            the limit
	 * @param userid
	 *            the userid
	 */
	public BookingQuotaExceededExcpetion(String message, Quota quota, double limit, String userid) {
		super(message);
		this.quota = quota;
		this.quotaLimit = limit;
		this.userId = userid;
	}

	/**
	 * Instantiates a new booking quota exceeded excpetion.
	 * 
	 * @param cause
	 *            the cause
	 * @param quota
	 *            the quota
	 * @param limit
	 *            the limit
	 * @param userid
	 *            the userid
	 */
	public BookingQuotaExceededExcpetion(Throwable cause, Quota quota, double limit, String userid) {
		super(cause);
		this.quota = quota;
		this.quotaLimit = limit;
		this.userId = userid;
	}

	/**
	 * Instantiates a new booking quota exceeded excpetion.
	 * 
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 * @param quota
	 *            the quota
	 * @param limit
	 *            the limit
	 * @param userid
	 *            the userid
	 */
	public BookingQuotaExceededExcpetion(String message, Throwable cause, Quota quota, double limit, String userid) {
		super(message, cause);
		this.quota = quota;
		this.quotaLimit = limit;
		this.userId = userid;
	}

	/**
	 * Gets the quota which caused the exception.
	 * 
	 * @return the quota
	 */
	public Quota getQuota() {
		return this.quota;
	}

	/**
	 * Gets the quota limit which caused the exception.
	 * 
	 * @return the quota limit
	 */
	public double getQuotaLimit() {
		return this.quotaLimit;
	}

	/**
	 * Gets the user id affected by the quota.
	 * 
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

}
