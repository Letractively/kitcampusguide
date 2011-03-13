/**
 * @author Kateryna Yurchenko
 */
CampusguideTest = TestCase("CampusguideTest");

CampusguideTest.prototype.setUp = function() {
	setClientID("test");
	/* Adding map-form:all-poi to DOM.
	 * (Has to be in one line) */
	/*:DOC += <span id="test:map-form:all-poi">[{"id":1,"icon":"..\/images\/icons\/mensa.jpg","visible":false,"description":"Mensen die es auf dem Campus gibt.","name":"Mensen","pois":[{"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Fre�tempel f�r alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198}]},{"id":2,"icon":"..\/images\/icons\/hoersaal.jpg","visible":false,"description":"Alle H�rs�le des KIT.","name":"H�rs�le","pois":[{"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen H�rsaal, 400 Sitzpl�tze, 3 Beamer, Physiker-H�rsaal, ...","name":"Gerthsen H�rsaal","nameSize":16,"lat":49.01258},{"id":3,"icon":"..\/hoersaal\/audimax.jpg","lon":8.41583,"description":"Der gr��te H�rsaal am KIT. Fasst etwa 750 Studenten. Die Sitzpl�tze sind in zwei Halbkreisen angeordnet. Der H�rsaal hat zwei Beamerfl�chen.","name":"Audimax","nameSize":7,"lat":49.01272},{"id":4,"icon":"..\/icons\/hsaf.jpg","lon":8.42036,"description":"Der H�rsaal am Fasanengarten war fr�her eine Turnhalle.","name":"H�rsaal am Fasanengarten","nameSize":24,"lat":49.01481}]}]</span> */
	
	/* Adding search:current-poi to DOM */
	/*:DOC += <span id="test:search:current-poi"></span> */
	
	/* Adding route:current-route to DOM */
	/*:DOC += <span id="test:route:current-route"></span> */
	
	/* Adding map-popup to DOM */
	/*:DOC += <div id="map-popup" class="map-popup"><div class="map-popup-pane">
		<div class="poi-name"></div>
		<div class="poi-description"></div>
		<div class="poi-menu"><form>
			<input type="hidden" name="poiName" />
			<input type="button" value="#{msgs.route_from_here}" class="green-button" onclick="setRouteFrom(this.form.poiName.value)" />
			<input type="button" value="#{msgs.route_to_here}" class="green-button" onclick="setRouteTo(this.form.poiName.value)" />
		</form></div></div>
	</div> */
}

CampusguideTest.prototype.testSetUp = function() {
	assertNotNull(document.getElementById("test:map-form:all-poi"));
	assertNotNull(document.getElementById("test:map-form:all-poi").innerHTML);
	assertNotNull(document.getElementById("test:search:current-poi"));
	assertNotNull(document.getElementById("test:search:current-poi").innerHTML);
	assertNotNull(document.getElementById("test:route:current-route"));
	assertNotNull(document.getElementById("test:route:current-route").innerHTML);
};

CampusguideTest.prototype.testDrawMap = function() {
	drawmap();
	assertNotNull(map);
}