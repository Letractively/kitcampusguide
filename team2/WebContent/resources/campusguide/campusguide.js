/**
 * 
 */
 var map = null;
                var options = {
                    controls: [new OpenLayers.Control.KeyboardDefaults()]
                };
                map = new OpenLayers.Map('map', options);
                var wms = new OpenLayers.Layer.WMS(
                    "OpenLayers WMS",
                    "http://vmap0.tiles.osgeo.org/wms/vmap0?",
                    {layers: 'basic'}
                );
                map.addLayer(wms);
                map.zoomToMaxExtent();