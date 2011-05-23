/**
 * This test case tests the methods of map.js of KITCampusGuide.
 * 
 * It assumes, the OpenLayers framework is working.
 * 
 * @author Kateryna Yurchenko
 */
MapTest = TestCase("MapTest");

/**
 * Initializes the tests.
 * 
 * The current poi is "Gerthsen Hörsaal" and current route goes from "Hörsaal am Fasanengarten" to "Audimax".
 */
MapTest.prototype.setUp = function() {
	setClientID("test");
	/* 
	 * Adding relevant divs to DOM.
	 */
	/*:DOC += 
	 * <div id="campusguide" style="width: 760px; height: 530px; ">
               <div id="headline" style="width: 758px; height: 28px; ">
                    <div id="headline-content">
                    	<span id="test:search:current-poi" class="hidden">{"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258}</span> </div>
                    <div id="headline-corner"></div>
                </div>
                <form id="test:map-form" name="test:map-form" method="post" action="/kitcampusguide/campuskarte.jsf" enctype="application/x-www-form-urlencoded" class="hidden">
                	<span id="test:map-form:all-poi" class="hidden">[{"id":1,"icon":"..\/images\/icons\/mensa.jpg","visible":false,"description":"Mensen die es auf dem Campus gibt.","name":"Mensen","pois":[{"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Freßtempel für alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198}]},{"id":2,"icon":"..\/images\/icons\/hoersaal.jpg","visible":false,"description":"Alle Hörsäle des KIT.","name":"Hörsäle","pois":[{"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258},{"id":3,"icon":"..\/hoersaal\/audimax.jpg","lon":8.41583,"description":"Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. Der Hörsaal hat zwei Beamerflächen.","name":"Audimax","nameSize":7,"lat":49.01272},{"id":4,"icon":"..\/icons\/hsaf.jpg","lon":8.42036,"description":"Der Hörsaal am Fasanengarten war früher eine Turnhalle.","name":"Hörsaal am Fasanengarten","nameSize":24,"lat":49.01481}]}]</span>
                </form>
                <div id="map-corner"></div>
                <div id="map" style="width: 558px; height: 499px; " class="olMap">
                <div id="OpenLayers.Map_3_OpenLayers_ViewPort" style="position: relative; overflow-x: hidden; overflow-y: hidden; width: 100%; height: 100%; " class="olMapViewport olControlDragPanActive olControlZoomBoxActive olControlNavigationActive olControlMousePositionActive"><div id="OpenLayers.Map_3_OpenLayers_Container" style="position: absolute; z-index: 749; left: 0px; top: 0px; "><div id="OpenLayers.Layer.OSM_39" style="position: absolute; width: 100%; height: 100%; z-index: 100; " dir="ltr" class="olLayerDiv"><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv83" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17148/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv85" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17149/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv87" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17150/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv89" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17150/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv91" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17149/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv93" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17148/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv95" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv97" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv99" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv101" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17148/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv103" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17149/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv105" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17150/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv107" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17151/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv109" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17151/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv111" style="position: relative; width: 256px; height: 256px; " class="olTileImage" src="resources/tiles/campus/15/17151/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv113" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17151/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv115" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17150/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv117" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17149/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv119" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17148/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv121" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv123" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv125" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv127" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv129" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv131" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv133" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv135" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17148/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv137" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17149/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv139" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17150/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv141" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17151/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 633px; width: 256px; height: 256px; "><img id="OpenLayersDiv143" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11253.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 377px; width: 256px; height: 256px; "><img id="OpenLayersDiv145" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11252.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 121px; width: 256px; height: 256px; "><img id="OpenLayersDiv147" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11251.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: -135px; width: 256px; height: 256px; "><img id="OpenLayersDiv149" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11250.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: -391px; width: 256px; height: 256px; "><img id="OpenLayersDiv151" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11249.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 821px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv153" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17152/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 565px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv155" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17151/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 309px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv157" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17150/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 53px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv159" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17149/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -203px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv161" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17148/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: -459px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv163" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17147/11254.png"></div><div style="overflow-x: hidden; overflow-y: hidden; position: absolute; z-index: 1; left: 1077px; top: 889px; width: 256px; height: 256px; "><img id="OpenLayersDiv165" style="position: relative; width: 256px; height: 256px; " class="olTileImage olImageLoadError" src="resources/tiles/campus/15/17153/11254.png"></div></div><div id="OpenLayers.Layer.Vector_167" style="position: absolute; width: 100%; height: 100%; z-index: 330; left: 0px; top: 0px; " dir="ltr" class="olLayerDiv"><svg id="OpenLayers.Layer.Vector_167_svgRoot" width="558" height="499" viewBox="0 0 558 499"><g id="OpenLayers.Layer.Vector_167_root" style="visibility: visible; " transform=""><g id="OpenLayers.Layer.Vector_167_vroot"></g><g id="OpenLayers.Layer.Vector_167_troot"></g></g></svg></div><div id="OpenLayers.Feature_175_popup" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 330px; height: 116px; left: 21.5px; top: 119.5px; z-index: 751; display: none; " class="olPopup"><div id="OpenLayers.Feature_175_popup_GroupDiv" style="overflow-x: hidden; overflow-y: hidden; position: absolute; top: 0px; left: 0px; height: 100%; width: 100%; "><div id="OpenLayers.Feature_175_popup_contentDiv" style="position: absolute; z-index: 1; height: 67px; left: 8px; top: 9px; width: 100%; " class="olFramedCloudPopupContent">
		            <div class="map-popup-pane" style="height: 81px; ">
		                <div class="poi-name">Mensa am Adenauerring</div>
		                <div class="poi-description">Das ist die Mensa. Freßtempel für alle Studenten.</div>
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
		                <div class="poi-name">Gerthsen Hörsaal</div>
		                <div class="poi-description">Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...</div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName" value="Gerthsen Hörsaal">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div><div id="OpenLayers.Feature_185_popup_close" style="position: absolute; z-index: 1; right: 6px; height: 38px; width: 20px; top: 10px; " class="olPopupCloseBox"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_0" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 65px; left: 0px; bottom: 51px; right: 22px; top: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_0" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_1" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 66px; bottom: 50px; right: 0px; top: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_1" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_2" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 19px; left: 0px; bottom: 32px; right: 22px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_2" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -631px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_3" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 19px; bottom: 32px; right: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_3" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: -631px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_185_popup_FrameDecorationDiv_4" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 81px; height: 35px; left: 0px; bottom: 0px; "><img id="OpenLayers.Feature_185_popup_FrameDecorationImg_4" style="width: 1276px; height: 736px; position: absolute; left: -215px; top: -687px; " src="resources/openlayers/img/cloud-popup-relative.png"></div></div></div><div id="Gerthsen Hörsaal" style="position: absolute; overflow-x: hidden; overflow-y: hidden; left: 215px; top: 252px; width: 121px; background-color: white; opacity: 0.7; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(0, 150, 130); border-right-color: rgb(0, 150, 130); border-bottom-color: rgb(0, 150, 130); border-left-color: rgb(0, 150, 130); z-index: 754; display: none; height: 22px; " class="olPopup"><div id="Gerthsen Hörsaal_GroupDiv" style="position: relative; overflow-x: hidden; overflow-y: hidden; "><div id="Gerthsen Hörsaal_contentDiv" style="position: relative; width: 121px; height: 23px; " class="olPopupContent">Gerthsen Hörsaal</div></div></div><div id="OpenLayers.Feature_193_popup" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 330px; height: 138px; left: -0.5px; top: 259.5px; z-index: 755; display: none; " class="olPopup"><div id="OpenLayers.Feature_193_popup_GroupDiv" style="overflow-x: hidden; overflow-y: hidden; position: absolute; top: 0px; left: 0px; height: 100%; width: 100%; "><div id="OpenLayers.Feature_193_popup_contentDiv" style="position: absolute; z-index: 1; height: 89px; left: 8px; top: 40px; width: 100%; " class="olFramedCloudPopupContent">
		            <div class="map-popup-pane" style="height: 103px; ">
		                <div class="poi-name">Audimax</div>
		                <div class="poi-description">Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. Der Hörsaal hat zwei Beamerflächen.</div>
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
		                <div class="poi-name">Hörsaal am Fasanengarten</div>
		                <div class="poi-description">Der Hörsaal am Fasanengarten war früher eine Turnhalle.</div>
		                <div style="width: 20px; height: 10px;"></div>
		                <div class="poi-menu">
		                    <form>
		                        <input type="hidden" name="poiName" value="Hörsaal am Fasanengarten">
		                        <input type="button" value="Route von hier" class="green-button" onclick="changeValue('route:from-field', this.form.poiName.value)">
		                        <input type="button" value="Route hierher" class="green-button" onclick="changeValue('route:to-field', this.form.poiName.value)">
		                    </form>
		                </div>
		            </div>
		        </div><div id="OpenLayers.Feature_201_popup_close" style="position: absolute; z-index: 1; right: 6px; height: 38px; width: 20px; top: 42px; " class="olPopupCloseBox"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_0" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 63px; left: 0px; bottom: 21px; right: 22px; top: 32px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_0" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_1" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 63px; bottom: 21px; right: 0px; top: 32px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_1" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: 0px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_2" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 308px; height: 21px; left: 0px; bottom: 0px; right: 22px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_2" style="width: 1276px; height: 736px; position: absolute; left: 0px; top: -629px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_3" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 22px; height: 21px; bottom: 0px; right: 0px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_3" style="width: 1276px; height: 736px; position: absolute; left: -1238px; top: -629px; " src="resources/openlayers/img/cloud-popup-relative.png"></div><div id="OpenLayers.Feature_201_popup_FrameDecorationDiv_4" style="position: absolute; overflow-x: hidden; overflow-y: hidden; width: 81px; height: 33px; right: 0px; top: 0px; "><img id="OpenLayers.Feature_201_popup_FrameDecorationImg_4" style="width: 1276px; height: 736px; position: absolute; left: -101px; top: -674px; " src="resources/openlayers/img/cloud-popup-relative.png"></div></div></div><div id="Hörsaal am Fasanengarten" style="position: absolute; overflow-x: hidden; overflow-y: hidden; left: 421px; top: 173px; width: 169px; background-color: white; opacity: 0.7; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; border-top-style: solid; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-top-color: rgb(0, 150, 130); border-right-color: rgb(0, 150, 130); border-bottom-color: rgb(0, 150, 130); border-left-color: rgb(0, 150, 130); z-index: 758; display: none; height: 22px; " class="olPopup"><div id="Hörsaal am Fasanengarten_GroupDiv" style="position: relative; overflow-x: hidden; overflow-y: hidden; "><div id="Hörsaal am Fasanengarten_contentDiv" style="position: relative; width: 169px; height: 23px; " class="olPopupContent">Hörsaal am Fasanengarten</div></div></div><div id="OpenLayers.Layer.Markers_183" style="position: absolute; width: 100%; height: 100%; z-index: 340; " dir="ltr" class="olLayerDiv"><div id="OL_Icon_186" style="width: 21px; height: 25px; position: absolute; left: 204.5px; top: 227px; "><img id="OL_Icon_186_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div><div id="OL_Icon_194" style="width: 21px; height: 25px; position: absolute; left: 305.5px; top: 222px; "><img id="OL_Icon_194_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div><div id="OL_Icon_202" style="width: 21px; height: 25px; position: absolute; left: 410.5px; top: 148px; "><img id="OL_Icon_202_innerImage" style="width: 21px; height: 25px; position: relative; " src="resources/openlayers/img/marker.png"></div></div><div id="OpenLayers.Layer.Markers_209" style="position: absolute; width: 100%; height: 100%; z-index: 345; " dir="ltr" class="olLayerDiv"></div></div><div id="OpenLayers.Control.PanZoom_6" style="position: absolute; left: 4px; top: 4px; z-index: 1004; " class="olControlPanZoom olControlNoSelect" unselectable="on"><div id="OpenLayers.Control.PanZoom_6_panup" style="left: 13px; top: 4px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_panup_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/north-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_panleft" style="left: 4px; top: 22px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_panleft_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/west-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_panright" style="left: 22px; top: 22px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_panright_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/east-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_pandown" style="left: 13px; top: 40px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_pandown_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/south-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_zoomin" style="left: 13px; top: 63px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_zoomin_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/zoom-plus-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_zoomworld" style="left: 13px; top: 81px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_zoomworld_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/zoom-world-mini.png"></div><div id="OpenLayers.Control.PanZoom_6_zoomout" style="left: 13px; top: 99px; width: 18px; height: 18px; position: absolute; "><img id="OpenLayers.Control.PanZoom_6_zoomout_innerImage" style="width: 18px; height: 18px; position: relative; " src="resources/openlayers/img/zoom-minus-mini.png"></div></div><div id="OpenLayers.Control.ArgParser_7" style="position: absolute; z-index: 1004; " class="olControlArgParser olControlNoSelect" unselectable="on"></div><div id="OpenLayers.Control.Attribution_8" style="position: absolute; z-index: 1004; " class="olControlAttribution olControlNoSelect" unselectable="on">Data CC-By-SA by <a href="http://openstreetmap.org/">OpenStreetMap</a></div><div id="OpenLayers.Control.MousePosition_2" style="position: absolute; z-index: 1005; " class="olControlMousePosition olControlNoSelect" unselectable="on"></div><div id="OpenLayers.Control.ScaleLine_38" style="position: absolute; z-index: 1006; " class="olControlScaleLine olControlNoSelect" unselectable="on"><div class="olControlScaleLineTop" style="visibility: visible; width: 42px; ">200 m</div><div class="olControlScaleLineBottom" style="visibility: visible; width: 64px; ">1000 ft</div></div></div></div> 
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
	                    	<span id="test:route:current-route" class="hidden">[{"lon":8.42036,"lat":49.01481},{"lon":8.4204,"lat":49.01447},{"lon":8.41991,"lat":49.01416},{"lon":8.41948,"lat":49.01385},{"lon":8.41936,"lat":49.01369},{"lon":8.41914,"lat":49.01358},{"lon":8.41891,"lat":49.0135},{"lon":8.41842,"lat":49.01351},{"lon":8.41831,"lat":49.01342},{"lon":8.41777,"lat":49.01344},{"lon":8.41662,"lat":49.01349},{"lon":8.41628,"lat":49.0135},{"lon":8.41625,"lat":49.01318},{"lon":8.41583,"lat":49.01319},{"lon":8.41575,"lat":49.01301},{"lon":8.41584,"lat":49.01285},{"lon":8.41583,"lat":49.01272}]</span>       		<input id="test:route:hide-route" name="test:route:hide-route" type="submit" value="X" onclick="jsf.util.chain(document.getElementById('j_id1659819444_62eed51d:route:hide-route'), event,'hideRoute();;', 'jsf.ajax.request(\'j_id1659819444_62eed51d:route:hide-route\',event,{execute:\'j_id1659819444_62eed51d:route:from-field j_id1659819444_62eed51d:route:to-field\',render:\'@none\',\'javax.faces.behavior.event\':\'action\'})'); return false;" style="visibility: visible; " class="green-button">
	                    </form>
						<div id="test:sidebar-suggestions">
                        <br><span id="test:j_id1693328539_1c65ef66"></span></div>
                    </div>
                </div>
                <div id="show-sidebar" onclick="showSidebar()" style="visibility: hidden; top: 35px; "></div>
	        </div> */
};

/**
 * Resets the map for next testcase.
 */
MapTest.prototype.tearDown = function() {
	map = null;
	mapLayer = null;
	routeLayer = null;
	positionMarkerLayer = null;
	POICategorieLayers = new Object();
	POICategories = new Object();
	POIFeatures = new Object();
	currentPOI = null;
	contextmenu = null;
};

/**
 * Testing if divs were added correctly to DOM.
 */
MapTest.prototype.testSetUp = function() {
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
 * Tests the createMap() method.
 */
MapTest.prototype.testCreateMap = function() {
	createMap(); 
	
	assertNotNull("map is null", map);
	assertNotNull("mapLayer is null", mapLayer);
	assertNotNull("routeLayer is null", routeLayer);
	assertNotNull("positionMarkerLayer is null", positionMarkerLayer);
	assertNotNull("WGS1984 is null", WGS1984);
	assertNotNull("MercatorProjection is null", MercatorProjection);
	
	assertNotSame(new Object(), POICategorieLayers);
	assertNotSame(new Object(), POICategories);
	assertNotSame(new Object(), POIFeatures);
	
	assertEquals(WGS1984, map.displayProjection);
	assertEquals(MercatorProjection, map.projection);
	assertEquals(6, map.controls.length);
	assertEquals(5, map.layers.length);
	assertEquals(new OpenLayers.LonLat(936670.02527692, 6277008.1429632), map.getCenter());
};

/**
 * Tests the createPOICategoryLayer() method.
 */
MapTest.prototype.testCreatePOICategoryLayer = function() {
	this.initMap(); 
	
	assertNotNull(createPOICategoryLayer({}));
	assertEquals(0, createPOICategoryLayer({}).markers.length);
	
	
	var categoryMensen = createPOICategoryLayer({"id":1,"icon":"..\/images\/icons\/mensa.jpg","visible":false,"description":"Mensen die es auf dem Campus gibt.","name":"Mensen","pois":[{"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Freßtempel für alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198}]});
	assertNotNull(categoryMensen);
	assertEquals(1, categoryMensen.markers.length);
	assertTrue(categoryMensen.visibility);
	assertEquals("Mensen", categoryMensen.name);
	
	var categoryHoersaele = createPOICategoryLayer({"id":2,"icon":"..\/images\/icons\/hoersaal.jpg","visible":false,"description":"Alle Hörsäle des KIT.","name":"Hörsäle","pois":[{"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258},{"id":3,"icon":"..\/hoersaal\/audimax.jpg","lon":8.41583,"description":"Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. Der Hörsaal hat zwei Beamerflächen.","name":"Audimax","nameSize":7,"lat":49.01272},{"id":4,"icon":"..\/icons\/hsaf.jpg","lon":8.42036,"description":"Der Hörsaal am Fasanengarten war früher eine Turnhalle.","name":"Hörsaal am Fasanengarten","nameSize":24,"lat":49.01481}]});
	assertNotNull(categoryHoersaele); 
	assertEquals(3, categoryHoersaele.markers.length);
	assertTrue(categoryHoersaele.visibility);
	assertEquals("Hörsäle", categoryHoersaele.name);
	
	var categoryTest = createPOICategoryLayer({"name":"Test Category", "pois":[{"name":"TestPOI 1","lon":8.0,"lat":49.0,"nameSize":9},{"name":"TestPOI 2","lon":8.0,"lat":49.0,"nameSize":9},
	                                                                           {"name":"TestPOI 3","lon":8.0,"lat":49.0,"nameSize":9},{"name":"TestPOI 4","lon":8.0,"lat":49.0,"nameSize":9},
	                                                                           {"name":"TestPOI 5","lon":8.0,"lat":49.0,"nameSize":9},{"name":"TestPOI 6","lon":8.0,"lat":49.0,"nameSize":9},
	                                                                           {"name":"TestPOI 7","lon":8.0,"lat":49.0,"nameSize":9},{"name":"TestPOI 8","lon":8.0,"lat":49.0,"nameSize":9},]})
	assertNotNull(categoryTest); 
	assertEquals(8, categoryTest.markers.length);
	assertTrue(categoryTest.visibility);
	assertEquals("Test Category", categoryTest.name);                                                                           
};

/**
 * Tests the createMarker() method.
 */
MapTest.prototype.testCreateMarker = function() {
	this.initMap();
	
	var gerthsenPoi = {"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258};
	var mensaPoi = {"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Freßtempel für alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198};
	var audimaxPoi = {"id":3,"icon":"..\/hoersaal\/audimax.jpg","lon":8.41583,"description":"Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. Der Hörsaal hat zwei Beamerflächen.","name":"Audimax","nameSize":7,"lat":49.01272};
	var hsafPoi = {"id":4,"icon":"..\/icons\/hsaf.jpg","lon":8.42036,"description":"Der Hörsaal am Fasanengarten war früher eine Turnhalle.","name":"Hörsaal am Fasanengarten","nameSize":24,"lat":49.01481};
	var testPoi = {"name":"TestPOI","lon":8.0,"lat":49.0,"nameSize":7,"description":"This is a test POI"};
	
	var gerthsen = createMarker(gerthsenPoi);
	var mensa = createMarker(mensaPoi);
	var audimax = createMarker(audimaxPoi);
	var hsaf = createMarker(hsafPoi);
	var test = createMarker(testPoi);
	
	/* two popups per POI */
	assertEquals(10, map.popups.length);
	for (var i = 0; i < map.popups.length; i++) {
		var popup = map.popups[i];
		assertFalse(popup.visible());
	}
	
	assertNotNull(gerthsen);
	assertEquals(createLonLat(gerthsenPoi.lon, gerthsenPoi.lat), gerthsen.lonlat);
	assertNotNull(gerthsen.events.listeners["mousedown"]);
	assertNotNull(gerthsen.events.listeners["mouseover"]);
	assertNotNull(gerthsen.events.listeners["mouseout"]);
	
	assertNotNull(mensa);
	assertEquals(createLonLat(mensaPoi.lon, mensaPoi.lat), mensa.lonlat);
	assertNotNull(mensa.events.listeners["mousedown"]);
	assertNotNull(mensa.events.listeners["mouseover"]);
	assertNotNull(mensa.events.listeners["mouseout"]);
	
	assertNotNull(audimax);
	assertEquals(createLonLat(audimaxPoi.lon, audimaxPoi.lat), audimax.lonlat);
	assertNotNull(audimax.events.listeners["mousedown"]);
	assertNotNull(audimax.events.listeners["mouseover"]);
	assertNotNull(audimax.events.listeners["mouseout"]);
	
	assertNotNull(hsaf);
	assertEquals(createLonLat(hsafPoi.lon, hsafPoi.lat), hsaf.lonlat);
	assertNotNull(hsaf.events.listeners["mousedown"]);
	assertNotNull(hsaf.events.listeners["mouseover"]);
	assertNotNull(hsaf.events.listeners["mouseout"]);
	
	assertNotNull(test);
	assertEquals(createLonLat(testPoi.lon, testPoi.lat), test.lonlat);
	assertNotNull(test.events.listeners["mousedown"]);
	assertNotNull(test.events.listeners["mouseover"]);
	assertNotNull(test.events.listeners["mouseout"]);
};

/**
 * Tests the createPopupContent() method.
 */
MapTest.prototype.testShowPOI = function() {
	var gerthsenPoi = {"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258};
	var mensaPoi = {"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Freßtempel für alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198};

	createMap();
	showPOI();
	
	/* "Gerthsen Hörsaal" is current POI in DOM, see setUp() */
	var current = gerthsenPoi;
	
	assertEquals(current.name, currentPOI.name);
	assertEquals(current.nameSize, currentPOI.nameSize);
	assertEquals(current.id, currentPOI.id);
	assertEquals(current.lon, currentPOI.lon);
	assertEquals(current.lat, currentPOI.lat);
	assertEquals(current.description, currentPOI.description);
	assertEquals(current.icon, currentPOI.icon);
	assertTrue(POIFeatures[current.name].popup.visible());
	assertEquals(createLonLat(current.lon, current.lat), map.center);
	
	for (var poi in POIFeatures) {
		if (poi != current.name) {
			assertFalse(POIFeatures[poi].popup.visible());
		}
	}
	
	/* change currentPOI to "Mensa am Adenauerring" */
	document.getElementById("test:search:current-poi").innerHTML = "{\"id\":2,\"icon\":\"..\/images\/icons\/mensa.jpg\",\"lon\":8.41684,\"description\":\"Das ist die Mensa. Freßtempel für alle Studenten.\",\"name\":\"Mensa am Adenauerring\",\"nameSize\":21,\"lat\":49.01198}";
	showPOI();
	var current = mensaPoi;
	
	assertEquals(current.name, currentPOI.name);
	assertEquals(current.nameSize, currentPOI.nameSize);
	assertEquals(current.id, currentPOI.id);
	assertEquals(current.lon, currentPOI.lon);
	assertEquals(current.lat, currentPOI.lat);
	assertEquals(current.description, currentPOI.description);
	assertEquals(current.icon, currentPOI.icon);
	assertTrue(POIFeatures[current.name].popup.visible());
	assertEquals(createLonLat(current.lon, current.lat), map.center);
	
	for (var poi in POIFeatures) {
		if (poi != current.name) {
			assertFalse(POIFeatures[poi].popup.visible());
		}
	}

};

/**
 * Tests the hidePOI() method.
 */
MapTest.prototype.testHidePOI = function() {
	createMap();
	
	var gerthsenPoi = {"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258};

	showPOI();
	assertNotNull(currentPOI);
	assertTrue(POIFeatures[gerthsenPoi.name].popup.visible());
	
	hidePOI();
	assertNull(currentPOI);
	assertFalse(POIFeatures[gerthsenPoi.name].popup.visible());

	/* second call does not change something */
	hidePOI();
	assertNull(currentPOI);
	assertFalse(POIFeatures[gerthsenPoi.name].popup.visible());
	
};

/**
 * Tests the showRoute() method.
 */
MapTest.prototype.testShowRoute = function() {
	var audimaxPoi = {"id":3,"icon":"..\/hoersaal\/audimax.jpg","lon":8.41583,"description":"Der größte Hörsaal am KIT. Fasst etwa 750 Studenten. Die Sitzplätze sind in zwei Halbkreisen angeordnet. Der Hörsaal hat zwei Beamerflächen.","name":"Audimax","nameSize":7,"lat":49.01272};
	var hsafPoi = {"id":4,"icon":"..\/icons\/hsaf.jpg","lon":8.42036,"description":"Der Hörsaal am Fasanengarten war früher eine Turnhalle.","name":"Hörsaal am Fasanengarten","nameSize":24,"lat":49.01481};
	var gerthsenPoi = {"id":1,"icon":"..\/images\/icons\/hoersaal.jpg","lon":8.41152,"description":"Gerthsen Hörsaal, 400 Sitzplätze, 3 Beamer, Physiker-Hörsaal, ...","name":"Gerthsen Hörsaal","nameSize":16,"lat":49.01258};
	var mensaPoi = {"id":2,"icon":"..\/images\/icons\/mensa.jpg","lon":8.41684,"description":"Das ist die Mensa. Freßtempel für alle Studenten.","name":"Mensa am Adenauerring","nameSize":21,"lat":49.01198};

	this.initMap();
	showRoute();
	
	assertEquals('visible', document.getElementById("test:route:hide-route").style.visibility);
	assertEquals(1, routeLayer.features.length);
	
	var feature = routeLayer.features[0];
	
	/* Route from "Hörsaal am Fasanengarten" to "Audimax" has 17 points */
	assertEquals(17, feature.geometry.components.length);
	assertEquals(createLonLat(hsafPoi.lon, hsafPoi.lat).lon, feature.geometry.components[0].x);
	assertEquals(createLonLat(hsafPoi.lon, hsafPoi.lat).lat, feature.geometry.components[0].y);
	assertEquals(createLonLat(audimaxPoi.lon, audimaxPoi.lat).lon, feature.geometry.components[16].x);
	assertEquals(createLonLat(audimaxPoi.lon, audimaxPoi.lat).lat, feature.geometry.components[16].y);

	/* Route from "Gerthsen Hörsaal" to "Mensa am Adenauerring" */
	document.getElementById("test:route:current-route").innerHTML = "[{\"lon\":8.41152,\"lat\":49.01258},{\"lon\":8.41151,\"lat\":49.0124},{\"lon\":8.41142,\"lat\":49.01174},{\"lon\":8.41186,\"lat\":49.01157},{\"lon\":8.41251,\"lat\":49.01133},{\"lon\":8.41276,\"lat\":49.01127},{\"lon\":8.41318,\"lat\":49.01112},{\"lon\":8.41368,\"lat\":49.01098},{\"lon\":8.41404,\"lat\":49.01096},{\"lon\":8.41406,\"lat\":49.01116},{\"lon\":8.41408,\"lat\":49.01129},{\"lon\":8.41483,\"lat\":49.01126},{\"lon\":8.4152,\"lat\":49.01127},{\"lon\":8.41552,\"lat\":49.01128},{\"lon\":8.41569,\"lat\":49.01136},{\"lon\":8.41578,\"lat\":49.01149},{\"lon\":8.41578,\"lat\":49.01161},{\"lon\":8.41609,\"lat\":49.01156},{\"lon\":8.41663,\"lat\":49.0115},{\"lon\":8.41681,\"lat\":49.0115},{\"lon\":8.41688,\"lat\":49.01184},{\"lon\":8.41684,\"lat\":49.01198}]";
	
	showRoute();
	
	assertEquals('visible', document.getElementById("test:route:hide-route").style.visibility);
	assertEquals(1, routeLayer.features.length);
	
	feature = routeLayer.features[0];
	
	assertEquals(22, feature.geometry.components.length);
	assertEquals(createLonLat(gerthsenPoi.lon, gerthsenPoi.lat).lon, feature.geometry.components[0].x);
	assertEquals(createLonLat(gerthsenPoi.lon, gerthsenPoi.lat).lat, feature.geometry.components[0].y);
	assertEquals(createLonLat(mensaPoi.lon, mensaPoi.lat).lon, feature.geometry.components[21].x);
	assertEquals(createLonLat(mensaPoi.lon, mensaPoi.lat).lat, feature.geometry.components[21].y);
};

/**
 * Tests the hideRoute() method.
 */
MapTest.prototype.testHideRoute = function() {
	this.initMap();
	showRoute();
	
	hideRoute();
	
	assertEquals(0, routeLayer.features.length);
	assertEquals('hidden', document.getElementById("test:route:hide-route").style.visibility);
};

/**
 * Tests the createPositionMarker() method.
 */
MapTest.prototype.testCreatePositionMarker = function() {
	this.initMap();
	
	assertEquals(0, positionMarkerLayer.markers.length);
	
	createPositionMarker("testname", 10.0, 20.0);
	assertEquals(1, positionMarkerLayer.markers.length);
	assertEquals(createLonLat(10.0, 20.0), positionMarkerLayer.markers[0].lonlat);
	
	createPositionMarker("testname2", 15.0, 25.0);
	assertEquals(1, positionMarkerLayer.markers.length);
	assertEquals(createLonLat(15.0, 25.0), positionMarkerLayer.markers[0].lonlat);
	
};

/**
 * Initializes the map.
 */
MapTest.prototype.initMap = function() {
	/* Initialise map */
	var leftBottom = createLonLat(8.4025,49.008);
    var rightTop = createLonLat(8.426,49.0173);
    var extent = new OpenLayers.Bounds(leftBottom.lon, leftBottom.lat, rightTop.lon, rightTop.lat);
    
    var options = {
        projection: MercatorProjection, 
        displayProjection: WGS1984, 
        maxExtent: extent, 
        restrictedExtent: extent, 
        numZoomLevel: 3, 
        units: 'meters'
    };
	map = new OpenLayers.Map('map', options);
	map.addControl(new OpenLayers.Control.ScaleLine());
	map.minZoomLevel = 15;
	map.maxZoomLevel = 18;
	map.restrictedExtent = extent;
	
    mapLayer = new OpenLayers.Layer.OSM("New Layer", "resources/tiles/campus/${z}/${x}/${y}.png");
    map.addLayer(mapLayer);
    setMyCenter(8.41356, 49.01202, 15);
	
	positionMarkerLayer = createPOICategoryLayer({pois: {}});
    map.addLayer(positionMarkerLayer);
    
    routeLayer = new OpenLayers.Layer.Vector("route", null);
    map.addLayer(routeLayer);
    
    POIFeatures = new Object();
};


