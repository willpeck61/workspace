map = new OpenLayers.Map("mapdiv");
map.addLayer(new OpenLayers.Layer.OSM());

epsg4326 =  new OpenLayers.Projection("EPSG:4326"); //WGS 1984 projection
projectTo = map.getProjectionObject(); //The map projection (Spherical Mercator)
     
function Get(url){
	var Httpreq = new XMLHttpRequest(); // a new request
	Httpreq.open("GET",url,false);
	Httpreq.send(null);
	return Httpreq.responseText;
}

function getCoordsByPostCode(postcode){
var reqURL = JSON.parse(Get("https://maps.googleapis.com/maps/api/geocode/json?address="+postcode));
return reqURL.results[0].geometry.location;
}
var lonLat = new OpenLayers.LonLat( getCoordsByPostCode("LE17RH").lng, getCoordsByPostCode("LE17RH").lat ).transform(epsg4326, projectTo);

var listOfLocations = [];
var listOfLocObjs = [];

var zoom=14;
map.setCenter (lonLat, zoom);

var vectorLayer = new OpenLayers.Layer.Vector("Overlay");

function createVectorLocation(postcode, description){
var feature = new OpenLayers.Feature.Vector(
    new OpenLayers.Geometry.Point( getCoordsByPostCode(postcode).lng, getCoordsByPostCode(postcode).lat ).transform(epsg4326, projectTo),
    {description: description} ,
    {externalGraphic: 'marker.png', graphicHeight: 25, graphicWidth: 21, graphicXOffset:-12, graphicYOffset:-25  }
);    
vectorLayer.addFeatures(feature);
listOfLocations.push(postcode);
listOfLocObjs.push(new OpenLayers.LonLat( getCoordsByPostCode(postcode).lng, getCoordsByPostCode(postcode).lat ).transform(epsg4326, projectTo));
}

console.log(listOfLocObjs);

function setC(loc){
map.panTo(listOfLocObjs[loc]);
}

map.addLayer(vectorLayer);


//Add a selector control to the vectorLayer with popup functions
var controls = {
selector: new OpenLayers.Control.SelectFeature(vectorLayer, { onSelect: createPopup, onUnselect: destroyPopup })
};

function createPopup(feature) {
feature.popup = new OpenLayers.Popup.FramedCloud("pop",
  feature.geometry.getBounds().getCenterLonLat(),
  null,
  '<div class="markerContent">'+feature.attributes.description+'</div>',
  null,
  true,
  function() { controls['selector'].unselectAll(); }
);
//feature.popup.closeOnMove = true;
map.addPopup(feature.popup);
}

function destroyPopup(feature) {
feature.popup.destroy();
feature.popup = null;
}


map.addControl(controls['selector']);
controls['selector'].activate();
