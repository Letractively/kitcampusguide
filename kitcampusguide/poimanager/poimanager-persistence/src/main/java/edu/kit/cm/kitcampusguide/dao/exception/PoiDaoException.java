package edu.kit.cm.kitcampusguide.dao.exception;

/**
 * Exception thrown during poi dao execution.
 * 
 * @author Roland Steinegger, Karlsruhe Institute of Technology
 */
public class PoiDaoException extends Exception {

    public PoiDaoException() {
    }

    public PoiDaoException(String message) {
        super(message);
    }

    public PoiDaoException(Throwable cause) {
        super(cause);
    }

    public PoiDaoException(String message, Throwable cause) {
        super(message, cause);
    }

}
