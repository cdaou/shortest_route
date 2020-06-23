//create the map
function createMap(nodes, pathList, mapExist){
	
	if(mapExist===false) {
		  mymap = L.map('mapid').setView([40.635, 22.95], 16);
		  L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
				attribution: 'Maps by &copy; <a href="http://osm.org/copyright">OpenStreetMap</a>. Routes from <a href="http://project-osrm.org/">OSRM</a>, data uses <a href="http://opendatacommons.org/licenses/odbl/">ODbL</a> license'
		  }).addTo(mymap);
		  //create markers for traffic lights
		  for(var i=0;i<nodes.length; i++){
		    var marker = L.marker([nodes[i].latitude, nodes[i].longitude]).addTo(mymap);
		    marker.bindPopup("<center><b>ID: " + nodes[i].ID + " </b><br>" + nodes[i].name) + "</center>";
		  }
		  
		  var locationMarker = L.marker();
	
		  mymap.locate({ watch: true, maxZoom: 20});
		  function onLocationFound(e) {
		    mymap.removeLayer(locationMarker);
		    var userLocation = L.icon({
		      iconUrl: './red-location-icon-map-png-4.png',
		      iconSize:     [35.9, 46.4], // size of the icon
		      iconAnchor:   [15, 35.4], // point of the icon which will correspond to marker's location
		      popupAnchor:  [5, -35.4] // point from which the popup should open relative to the iconAnchor
		    });
		    locationMarker = L.marker([e.latitude, e.longitude], {icon: userLocation}).bindPopup('You are here!');
		    mymap.addLayer(locationMarker);
		  }
		  mymap.on('locationfound', onLocationFound);
	}
			
  //draw lines in path	
  if(pathList[0].length>0){
	  for(var i=0;i<pathList.length;i++){
		  var last = pathList[i].length - 1;
		  if(document.getElementById(pathList[i][last]).checked===true && (control[i]===undefined||control[i]===null)){
			  var color = "red";
			  var weight = 7;
			  //IF DESTINATIONS ARE MORE THAN THREE CHANGE CODE HERE--->
			  if(i==1){
				  color = "yellow";
				  weight = 5;
			  }else if(i==2){
				  color = "blue";
				  weight = 2;
			  }
			  var wp = [];
			  for(var j=1;j<=pathList[i].length;j++){ 
				  wp.push(L.latLng(getNode(pathList[i][j-1]).latitude, getNode(pathList[i][j-1]).longitude));	  
			  }
			  control[i] = L.Routing.control({
				  waypoints:  wp,
				  createMarker: function() { return null; },
				  lineOptions: {
				      styles: [{color: color, opacity: 1, weight: weight}]
				  },
				  draggableWaypoints: false,
			      addWaypoints: false,
			      routeWhileDragging: false
				}).addTo(mymap);
		  }
	  }
  }
  if(mapExist===true){
	 for(var i=0;i<pathList.length;i++){
		 var last = pathList[i].length - 1;
		 if(document.getElementById(pathList[i][last]).checked===false && control[i]!==null){
			 for(var j=0;j<control[i].getWaypoints().length;j++){
				 control[i].spliceWaypoints(j, 1, [,]);
			 }
			 control[i]=null;
		 }	
	 }
	}
}