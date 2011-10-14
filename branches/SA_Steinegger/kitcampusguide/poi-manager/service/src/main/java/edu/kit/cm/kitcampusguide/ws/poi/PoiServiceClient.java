package edu.kit.cm.kitcampusguide.ws.poi;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by Apache CXF 2.4.1 2011-07-20T19:00:25.687+02:00
 * Generated source version: 2.4.1
 * 
 */
@WebServiceClient(name = "PoiService", wsdlLocation = "http://localhost:8080/poi/poiservice/?wsdl", targetNamespace = "http://cm.tm.kit.edu/kitcampusguide/poiservice/")
public class PoiServiceClient extends Service {

	public final static URL WSDL_LOCATION;

	public final static QName SERVICE = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/", "PoiService");
	public final static QName PoiServiceSOAP = new QName("http://cm.tm.kit.edu/kitcampusguide/poiservice/",
			"PoiServiceSOAP");
	static {
		URL url = null;
		try {
			url = new URL("http://localhost:8080/poi/poiservice/?wsdl");
		} catch (MalformedURLException e) {
			java.util.logging.Logger.getLogger(PoiServiceClient.class.getName()).log(java.util.logging.Level.INFO,
					"Can not initialize the default wsdl from {0}", "http://localhost:8080/poi/poiservice/?wsdl");
		}
		WSDL_LOCATION = url;
	}

	public PoiServiceClient(URL wsdlLocation) {
		super(wsdlLocation, SERVICE);
	}

	public PoiServiceClient(URL wsdlLocation, QName serviceName) {
		super(wsdlLocation, serviceName);
	}

	public PoiServiceClient() {
		super(WSDL_LOCATION, SERVICE);
	}

	/**
	 * 
	 * @return returns PoiService
	 */
	@WebEndpoint(name = "PoiServiceSOAP")
	public PoiService getPoiServiceSOAP() {
		return super.getPort(PoiServiceSOAP, PoiService.class);
	}

	/**
	 * 
	 * @param features
	 *            A list of {@link javax.xml.ws.WebServiceFeature} to configure
	 *            on the proxy. Supported features not in the
	 *            <code>features</code> parameter will have their default
	 *            values.
	 * @return returns PoiService
	 */
	@WebEndpoint(name = "PoiServiceSOAP")
	public PoiService getPoiServiceSOAP(WebServiceFeature... features) {
		return super.getPort(PoiServiceSOAP, PoiService.class, features);
	}

}
