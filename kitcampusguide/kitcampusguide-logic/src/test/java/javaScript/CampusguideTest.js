/**
 * This test case tests the methods of campusguide.js of KITCampusGuide.
 * 
 * It assumes, the OpenLayers framework is working.
 * 
 * @author Kateryna Yurchenko
 */
CampusguideTest = TestCase("CampusguideTest");

/**
 * Initializes the tests.
 * 
 * The current poi is "Mensa am Adenauerring" and current route goes from "Gerthsen H�rsaal" to "Mensa am Adenauerring".
 */
CampusguideTest.prototype.setUp = function() {
	setClientID("test");
	/* 
	 * Adding div campusguide to DOM.
	 */
	/*:DOC += 
	 * <div id="campusguide" style="width: 760px; height: 530px; ">
               <div id="headline" style="width: 758px; height: 28px; ">
                    <div id="headline-content">
                    	<span id="test:search:current-poi" class="hidden">{"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Fre�tempel f�r alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198}</span>
                    </div>
                    <div id="headline-corner"></div>
                </div>
                <form id="test:map-form" name="test:map-form" method="post" action="/kitcampusguide/campuskarte.jsf" enctype="application/x-www-form-urlencoded" class="hidden">
                	<span id="test:map-form:all-poi" class="hidden">[{"id":1,"icon":"..\/images\/icons\/mensa.jpg","visible":false,"description":"Mensen die es auf dem Campus gibt.","name":"Mensen","pois":[{"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Fre�tempel f�r alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198}]},{"id":2,"icon":"..\/images\/icons\/hoersaal.jpg","visible":false,"description":"Alle H�rs�le des KIT.","name":"H�rs�le","pois":[{"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen H�rsaal, 400 Sitzpl�tze, 3 Beamer, Physiker-H�rsaal, ...","name":"Gerthsen H�rsaal","nameSize":16,"lat":49.01258},{"id":3,"icon":"..\/hoersaal\/audimax.jpg","lon":8.41583,"description":"Der gr��te H�rsaal am KIT. Fasst etwa 750 Studenten. Die Sitzpl�tze sind in zwei Halbkreisen angeordnet. Der H�rsaal hat zwei Beamerfl�chen.","name":"Audimax","nameSize":7,"lat":49.01272},{"id":4,"icon":"..\/icons\/hsaf.jpg","lon":8.42036,"description":"Der H�rsaal am Fasanengarten war fr�her eine Turnhalle.","name":"H�rsaal am Fasanengarten","nameSize":24,"lat":49.01481}]}]</span>
                </form>
                <div id="map-corner"></div>
                <div id="map" style="width: 558px; height: 499px; " class="olMap">
                <div id="OpenLayers.Map_3_OpenLayers_ViewPort" style="position: relative; overflow-x: hidden; overflow-y: hidden; width: 100%; height: 100%; " class="olMapViewport olControlDragPanActive olControlZoomBoxActive olControlNavigationActive olControlMousePositionActive"><div id="OpenLayers.Map_3_OpenLayers_Container" style="position: absolute; z-index: 749; left: 0px; top: 0px; "><div id="OpenLayers.Layer.OSM_39" style="position: absolute; width: 100%; height: 100%; z-index: 100; " dir="ltr" class="olLayerDiv"><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv83" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17148/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv85" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17149/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv87" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17150/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv89" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17150/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv91" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17149/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv93" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17148/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv95" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv97" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv99" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv101" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17148/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv103" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17149/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv105" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17150/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv107" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17151/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv109" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17151/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv111" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17151/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv113" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17151/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv115" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17150/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv117" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17149/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv119" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17148/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv121" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv123" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv125" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv127" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv129" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv131" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv133" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv135" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17148/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv137" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17149/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv139" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17150/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv141" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17151/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv143" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv145" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv147" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv149" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv151" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv153" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv155" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17151/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv157" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17150/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv159" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17149/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv161" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17148/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv163" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv165" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11254.png"></div></div><div id="OpenLayers.Layer.Vector_167" style="position: absolute; width: 100%; height: 100%; z-index: 330; left: 0px; top: 0px; " dir="ltr" class="olLayerDiv"><svg id="OpenLayers.Layer.Vector_167_svgRoot" width="558" height="499" viewBox="0 0 558 499"><g id="OpenLayers.Layer.Vector_167_root" style="visibility: visible; " transform=""><g id="OpenLayers.Layer.Vector_167_vroot"></g><g id="OpenLayers.Layer.Vector_167_troot"></g></g></svg></div><div id="OpenLayers.Feature_175_popup" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 330px; height: 116px; left: 21.5px; top: 119.5px; z-index: 751; display: none; " class="olPopup"><div id="OpenLayers.Feature_175_popup_GroupDiv" style="overflow-x: hidden; overflow-y: hidden; position: absolute; top: 0px; left: 0px; height: 100%; width: 100%; "><div id="OpenLayers.Feature_175_popup_contentDiv" style="position: absolute; z-index: 1; height: 67px; left: 8px; top: 9px; width: 100%; " class="olFramedCloudPopupContent">
		            <div class="map-popup-pane" style="height: 81px; ">
		                <div class="poi-name">Mensa am Adenauerring</div>
		                <div class="poi-description">Das ist die Mensa. Fre�tempel f�r alle Studenten.</div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName" value="Mensa am Adenauerring">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div><div id="OpenLayers.Feature_175_popup_close" style="position: absolute; z-index: 1; right: 6px; height: 38px; width: 20px; top: 10px; " class="olPopupCloseBox"></div><div id="OpenLayers.Feature_175_popup_FrameDecorationDiv_0" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 65px; left: 0px; bottom: 51px; right: 22px; top: 0px; "><img id="OpenLayers.Feature_175_popup_FrameDecorationImg_0" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_175_popup_FrameDecorationDiv_1" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 66px; bottom: 50px; right: 0px; top: 0px; "><img id="OpenLayers.Feature_175_popup_FrameDecorationImg_1" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_175_popup_FrameDecorationDiv_2" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 19px; left: 0px; bottom: 32px; right: 22px; "><img id="OpenLayers.Feature_175_popup_FrameDecorationImg_2" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -631px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_175_popup_FrameDecorationDiv_3" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 18px; bottom: 32px; right: 0px; "><img id="OpenLayers.Feature_175_popup_FrameDecorationImg_3" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: -632px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_175_popup_FrameDecorationDiv_4" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 81px; height: 35px; bottom: 0px; right: 0px; "><img id="OpenLayers.Feature_175_popup_FrameDecorationImg_4" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -688px; " src="resources/openlayers/img/cloud-popup-relative.png"></div></div></div><div id="Mensa am Adenauerring" style="position: absolute; overflow-x: hidden; overflow-y: hidden; left: 339px; top: 273px; width: 151px; background-color: white; opacity: 0.7; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(0, 150, 130); border-right-color: rgb(0, 150, 130); border-bottom-color: rgb(0, 150, 130); border-left-color: rgb(0, 150, 130); z-index: 752; display: none; height: 22px; " class="olPopup"><div id="Mensa am Adenauerring_GroupDiv" style="position: relative; overflow-x: hidden; overflow-y: hidden; "><div id="Mensa am Adenauerring_contentDiv" style="position: relative; width: 151px; height: 23px; " class="olPopupContent">Mensa am Adenauerring</div></div></div><div id="OpenLayers.Layer.Markers_173" style="position: absolute; width: 100%; height: 100%; z-index: 335; " dir="ltr" class="olLayerDiv"><div id="OL_Icon_176" style="width: 21px; height: 25px; position: absolute; left: 328.5px; top: 248px; "><img id="OL_Icon_176_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div></div><div id="OpenLayers.Feature_185_popup" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 330px; height: 116px; left: 180.5px; top: 98.5px; z-index: 753; display: none; " class="olPopup"><div id="OpenLayers.Feature_185_popup_GroupDiv" style="overflow-x: hidden; overflow-y: hidden; position: absolute; top: 0px; left: 0px; height: 100%; width: 100%; "><div id="OpenLayers.Feature_185_popup_contentDiv" style="position: absolute; z-index: 1; height: 67px; left: 8px; top: 9px; width: 100%; " class="olFramedCloudPopupContent">
		            <div class="map-popup-pane" style="height: 81px; ">
		                <div class="poi-name">Gerthsen H�rsaal</div>
		                <div class="poi-description">Gerthsen H�rsaal, 400 Sitzpl�tze, 3 Beamer, Physiker-H�rsaal, ...</div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName" value="Gerthsen H�rsaal">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div><div id="OpenLayers.Feature_185_popup_close" style="position: absolute; z-index: 1; right: 6px; height: 38px; width: 20px; top: 10px; " class="olPopupCloseBox"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_0" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 65px; left: 0px; bottom: 51px; right: 22px; top: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_0" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_1" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 66px; bottom: 50px; right: 0px; top: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_1" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_2" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 19px; left: 0px; bottom: 32px; right: 22px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_2" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -631px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_3" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 19px; bottom: 32px; right: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_3" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: -631px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_4" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 81px; height: 35px; left: 0px; bottom: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_4" style="width: 1276px; height: 736px; position: absolute; left: -215px; top: -687px; " src="resources/openlayers/img/cloud-popup-relative.png"></div></div></div><div id="Gerthsen H�rsaal" style="position: absolute; overflow-x: hidden; overflow-y: hidden; left: 215px; top: 252px; width: 121px; background-color: white; opacity: 0.7; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(0, 150, 130); border-right-color: rgb(0, 150, 130); border-bottom-color: rgb(0, 150, 130); border-left-color: rgb(0, 150, 130); z-index: 754; display: none; height: 22px; " class="olPopup"><div id="Gerthsen H�rsaal_GroupDiv" style="position: relative; overflow-x: hidden; overflow-y: hidden; "><div id="Gerthsen H�rsaal_contentDiv" style="position: relative; width: 121px; height: 23px; " class="olPopupContent">Gerthsen H�rsaal</div></div></div><div id="OpenLayers.Feature_193_popup" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 330px; height: 138px; left: -0.5px; top: 259.5px; z-index: 755; display: none; " class="olPopup"><div id="OpenLayers.Feature_193_popup_GroupDiv" style="overflow-x: hidden; overflow-y: hidden; position: absolute; top: 0px; left: 0px; height: 100%; width: 100%; "><div id="OpenLayers.Feature_193_popup_contentDiv" style="position: absolute; z-index: 1; height: 89px; left: 8px; top: 40px; width: 100%; " class="olFramedCloudPopupContent">
		            <div class="map-popup-pane" style="height: 103px; ">
		                <div class="poi-name">Audimax</div>
		                <div class="poi-description">Der gr��te H�rsaal am KIT. Fasst etwa 750 Studenten. Die Sitzpl�tze sind in zwei Halbkreisen angeordnet. Der H�rsaal hat zwei Beamerfl�chen.</div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName" value="Audimax">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div><div id="OpenLayers.Feature_193_popup_close" style="position: absolute; z-index: 1; right: 6px; height: 38px; width: 20px; top: 42px; " class="olPopupCloseBox"></div><div id="OpenLayers.Feature_193_popup_FrameDecorationDiv_0" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 85px; left: 0px; bottom: 21px; right: 22px; top: 32px; "><img id="OpenLayers.Feature_193_popup_FrameDecorationImg_0" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_193_popup_FrameDecorationDiv_1" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 85px; bottom: 21px; right: 0px; top: 32px; "><img id="OpenLayers.Feature_193_popup_FrameDecorationImg_1" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_193_popup_FrameDecorationDiv_2" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 21px; left: 0px; bottom: 0px; right: 22px; "><img id="OpenLayers.Feature_193_popup_FrameDecorationImg_2" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -629px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_193_popup_FrameDecorationDiv_3" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 21px; bottom: 0px; right: 0px; "><img id="OpenLayers.Feature_193_popup_FrameDecorationImg_3" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: -629px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_193_popup_FrameDecorationDiv_4" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 81px; height: 33px; right: 0px; top: 0px; "><img id="OpenLayers.Feature_193_popup_FrameDecorationImg_4" style="width: 1276px; height: 736px; position: absolute; left: -101px; top: -674px; " src="resources/openlayers/img/cloud-popup-relative.png"></div></div></div><div id="Audimax" style="position: absolute; overflow-x: hidden; overflow-y: hidden; left: 316px; top: 247px; width: 67px; background-color: white; opacity: 0.7; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(0, 150, 130); border-right-color: rgb(0, 150, 130); border-bottom-color: rgb(0, 150, 130); border-left-color: rgb(0, 150, 130); z-index: 756; display: none; height: 22px; " class="olPopup"><div id="Audimax_GroupDiv" style="position: relative; overflow-x: hidden; overflow-y: hidden; "><div id="Audimax_contentDiv" style="position: relative; width: 67px; height: 23px; " class="olPopupContent">Audimax</div></div></div><div id="OpenLayers.Feature_201_popup" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 330px; height: 116px; left: 104.5px; top: 185.5px; z-index: 757; display: none; " class="olPopup"><div id="OpenLayers.Feature_201_popup_GroupDiv" style="overflow-x: hidden; overflow-y: hidden; position: absolute; top: 0px; left: 0px; height: 100%; width: 100%; "><div id="OpenLayers.Feature_201_popup_contentDiv" style="position: absolute; z-index: 1; height: 67px; left: 8px; top: 40px; width: 100%; " class="olFramedCloudPopupContent">
		            <div class="map-popup-pane" style="height: 81px; ">
		                <div class="poi-name">H�rsaal am Fasanengarten</div>
		                <div class="poi-description">Der H�rsaal am Fasanengarten war fr�her eine Turnhalle.</div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName" value="H�rsaal am Fasanengarten">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div><div id="OpenLayers.Feature_201_popup_close" style="position: absolute; z-index: 1; right: 6px; height: 38px; width: 20px; top: 42px; " class="olPopupCloseBox"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_0" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 63px; left: 0px; bottom: 21px; right: 22px; top: 32px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_0" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_1" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 63px; bottom: 21px; right: 0px; top: 32px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_1" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_2" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 21px; left: 0px; bottom: 0px; right: 22px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_2" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -629px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_3" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 21px; bottom: 0px; right: 0px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_3" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: -629px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_4" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 81px; height: 33px; right: 0px; top: 0px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_4" style="width: 1276px; height: 736px; position: absolute; left: -101px; top: -674px; " src="resources/openlayers/img/cloud-popup-relative.png"></div></div></div><div id="H�rsaal am Fasanengarten" style="position: absolute; overflow-x: hidden; overflow-y: hidden; left: 421px; top: 173px; width: 169px; background-color: white; opacity: 0.7; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(0, 150, 130); border-right-color: rgb(0, 150, 130); border-bottom-color: rgb(0, 150, 130); border-left-color: rgb(0, 150, 130); z-index: 758; display: none; height: 22px; " class="olPopup"><div id="H�rsaal am Fasanengarten_GroupDiv" style="position: relative; overflow-x: hidden; overflow-y: hidden; "><div id="H�rsaal am Fasanengarten_contentDiv" style="position: relative; width: 169px; height: 23px; " class="olPopupContent">H�rsaal am Fasanengarten</div></div></div><div id="OpenLayers.Layer.Markers_183" style="position: absolute; width: 100%; height: 100%; z-index: 340; " dir="ltr" class="olLayerDiv"><div id="OL_Icon_186" style="width: 21px; height: 25px; position: absolute; left: 204.5px; top: 227px; "><img id="OL_Icon_186_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div><div id="OL_Icon_194" style="width: 21px; height: 25px; position: absolute; left: 305.5px; top: 222px; "><img id="OL_Icon_194_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div><div id="OL_Icon_202" style="width: 21px; height: 25px; position: absolute; left: 410.5px; top: 148px; "><img id="OL_Icon_202_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div></div><div id="OpenLayers.Layer.Markers_209" style="position: absolute; width: 100%; height: 100%; z-index: 345; " dir="ltr" class="olLayerDiv"></div></div><div id="OpenLayers.Control.PanZoom_6" style="position: absolute; left: 4px; top: 4px; z-index: 1004; " class="olControlPanZoom olControlNoSelect" unselectable="on"><div id="OpenLayers.Control.PanZoom_6_panup" style="left: 13px; top: 4px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_panup_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/north-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_panleft" style="left: 4px; top: 22px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_panleft_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/west-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_panright" style="left: 22px; top: 22px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_panright_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/east-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_pandown" style="left: 13px; top: 40px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_pandown_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/south-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_zoomin" style="left: 13px; top: 63px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_zoomin_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/zoom-plus-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_zoomworld" style="left: 13px; top: 81px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_zoomworld_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/zoom-world-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_zoomout" style="left: 13px; top: 99px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_zoomout_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/zoom-minus-mini.png"></div></div><div id="OpenLayers.Control.ArgParser_7" style="position: absolute; z-index: 1004; " class="olControlArgParser olControlNoSelect" unselectable="on"></div><div id="OpenLayers.Control.Attribution_8" style="position: absolute; z-index: 1004; " class="olControlAttribution olControlNoSelect" unselectable="on">Data CC-By-SA by <a href="http://openstreetmap.org/">OpenStreetMap</a></div><div id="OpenLayers.Control.MousePosition_2" style="position: absolute; z-index: 1005; " class="olControlMousePosition olControlNoSelect" unselectable="on"></div><div id="OpenLayers.Control.ScaleLine_38" style="position: absolute; z-index: 1006; " class="olControlScaleLine olControlNoSelect" unselectable="on"><div class="olControlScaleLineTop" style="visibility: visible; width: 42px; ">200 m</div><div class="olControlScaleLineBottom" style="visibility: visible; width: 64px; ">1000 ft</div></div></div></div> 
                <div id="map-popup" class="map-popup">
		            <div class="map-popup-pane">
		                <div class="poi-name"></div>
		                <div class="poi-description"></div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div> 
                <div id="map-contextmenu">
                    <div class="map-contextmenu">
                        <form>
                            <input type="hidden" name="name">
                            <input type="hidden" name="lon">
                            <input type="hidden" name="lat">
                            <input type="button" value="Route von hier" class="green-menubutton" onclick="changeValue('route:from-field', this.form.name.value); hideContextMenu();">
                            <input type="button" value="Route hierher" class="green-menubutton" onclick="changeValue('route:to-field', this.form.name.value); hideContextMenu();">
                            <input type="button" value="Position markieren" class="green-menubutton" onclick="createPositionMarker(this.form.name.value, this.form.lon.value, this.form.lat.value); hideContextMenu();">
                            <input type="button" value="Karte zentrieren" class="green-menubutton" onclick="setMyCenter(this.form.lon.value, this.form.lat.value, 17); hideContextMenu();">
                        </form>
                    </div>
                </div>

                <div id="sidebar" style="width: 199px; height: 499px; ">
	                <div id="sidebar-pane" style="left: -1px; width: 187px; height: 489px; ">
	                    <form id="test:route" name="test:route" method="post" action="/kitcampusguide/campuskarte.jsf" enctype="application/x-www-form-urlencoded">
	                    	<span id="test:route:current-route" class="hidden">[{"lon":8.41152,"lat":49.01258},{"lon":8.41151,"lat":49.0124},{"lon":8.41142,"lat":49.01174},{"lon":8.41186,"lat":49.01157},{"lon":8.41251,"lat":49.01133},{"lon":8.41276,"lat":49.01127},{"lon":8.41318,"lat":49.01112},{"lon":8.41368,"lat":49.01098},{"lon":8.41404,"lat":49.01096},{"lon":8.41406,"lat":49.01116},{"lon":8.41408,"lat":49.01129},{"lon":8.41483,"lat":49.01126},{"lon":8.4152,"lat":49.01127},{"lon":8.41552,"lat":49.01128},{"lon":8.41569,"lat":49.01136},{"lon":8.41578,"lat":49.01149},{"lon":8.41578,"lat":49.01161},{"lon":8.41609,"lat":49.01156},{"lon":8.41663,"lat":49.0115},{"lon":8.41681,"lat":49.0115},{"lon":8.41688,"lat":49.01184},{"lon":8.41684,"lat":49.01198}]</span>
	                   		<input id="test:route:hide-route" name="test:route:hide-route" type="submit" value="X" onclick="jsf.util.chain(document.getElementById('j_id1659819444_62eed51d:route:hide-route'), event,'hideRoute();;', 'jsf.ajax.request(\'j_id1659819444_62eed51d:route:hide-route\',event,{execute:\'j_id1659819444_62eed51d:route:from-field j_id1659819444_62eed51d:route:to-field\',render:\'@none\',\'javax.faces.behavior.event\':\'action\'})'); return false;" style="visibility: visible; " class="green-button">
	                    </form>
						<div id="test:sidebar-suggestions">
                        <br><span id="test:j_id1693328539_1c65ef66"></span></div>
                    </div>
                </div>
                <div id="show-sidebar" onclick="showSidebar()" style="visibility: hidden; top: 35px; "></div>
	        </div> */
};

/**
 * Resets the variables for next testcase.
 */
CampusguideTest.prototype.tearDown = function() {
	fullscreen = false;
	clientID = null;
}

/**
 * Testing if divs were added correctly to DOM.
 */
CampusguideTest.prototype.testSetUp = function() {
	/* all relevant divs and JSONObjects */
	assertNotNull(document.getElementById("test:map-form:all-poi"));
	assertNotNull(document.getElementById("test:map-form:all-poi").innerHTML);
	assertNotNull(document.getElementById("test:search:current-poi"));
	assertNotNull(document.getElementById("test:search:current-poi").innerHTML);
	assertNotNull(document.getElementById("test:route:current-route"));
	assertNotNull(document.getElementById("test:route:current-route").innerHTML);
	assertNotNull(document.getElementById("test:route:hide-route"));
	assertNotNull(document.getElementById("campusguide"));
	assertNotNull(document.getElementById("headline"));
	assertNotNull(document.getElementById("map"));
	assertNotNull(document.getElementById("sidebar"));
	assertNotNull(document.getElementById("sidebar-pane"));
	assertNotNull(document.getElementById("show-sidebar"));
	
	/* the infobox popups */
	var popup = document.getElementById("map-popup");
	assertNotNull(popup);
	assertNotNull(popup.getElementsByTagName("input")[0]);
	assertNotNull(popup.getElementsByTagName("div")[1]);
	assertNotNull(popup.getElementsByTagName("div")[2]);
	
	/* the map contextmenu */
	var content = document.getElementById("map-contextmenu");
	assertNotNull(content);
	assertNotNull(content.getElementsByTagName("input")[0]);
	assertNotNull(content.getElementsByTagName("input")[1]);
	assertNotNull(content.getElementsByTagName("input")[2]);
};

/**
 * Tests the setup() method.
 */
CampusguideTest.prototype.testSetup = function() { 
	setup();
	
	var campusguide = document.getElementById("campusguide");
	var headline = document.getElementById("headline");
	var map = document.getElementById("map");
	var sidebar = document.getElementById("sidebar");
	var sidebarPane = document.getElementById("sidebar-pane");
	var showSidebar = document.getElementById("show-sidebar");
	
	assertEquals("760px", campusguide.style.width);
	assertEquals("530px", campusguide.style.height);
	
	assertEquals("758px", headline.style.width);
	assertEquals("28px", headline.style.height);
	
	assertEquals("558px", map.style.width);
	assertEquals("499px", map.style.height);
	
	assertEquals("199px", sidebar.style.width);
	assertEquals("499px", sidebar.style.height);
	
	assertEquals("187px", sidebarPane.style.width);
	assertEquals("489px", sidebarPane.style.height);
	
	assertEquals("35px", showSidebar.style.top);
};

/**
 * Tests the createJSONObject() method.
 */
CampusguideTest.prototype.testCreateJSONObject = function() {
	assertNull(createJSONObject(""));
	assertEquals(JSON.parse(document.getElementById("test:search:current-poi").innerHTML), createJSONObject(document.getElementById("test:search:current-poi").innerHTML));
};

/**
 * Tests the getElement() method.
 */
CampusguideTest.prototype.testGetElement = function() {
	assertNull(getElement("abc"));
	assertNull(getElement(""));
	assertNotNull(getElement("map-form:all-poi"));	
	
	setClientID("id");
	assertNull(getElement("map-form:all-poi"));
	setClientID("test");
	assertNotNull(getElement("map-form:all-poi"));
};

/**
 * Tests the toggleFullscreen method.
 */
CampusguideTest.prototype.testToggleFullscreen = function() {
	var campusguide = document.getElementById("campusguide");
	
	assertFalse(fullscreen);
	
	toggleFullscreen();
	assertTrue(fullscreen);
	assertEquals("fixed", campusguide.style.position);
	assertEquals("0px", campusguide.style.left);
	assertEquals("0px", campusguide.style.top);
	
	toggleFullscreen();
	assertFalse(fullscreen);
	assertEquals("relative", campusguide.style.position);
	assertEquals("", campusguide.style.left);
	assertEquals("", campusguide.style.top);
};

