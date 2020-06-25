//create the map
function createMap(nodes, pathList, mapExist){
		
	if(!mapExist) {
		var h = window.innerHeight - 100;
		  document.getElementById('mapid').innerHTML = "<div id='mapid' style='width: 100%; height:" + h + "px;'></div>";
		  
		  mapboxgl.accessToken = 'pk.eyJ1IjoiY2Rhb3UiLCJhIjoiY2tidDQwYWNnMDY5NzJzc3djcnN2ZnM1ZCJ9.ZLt_MRSRqFQ5lWK4t3eq3Q';
		  map = new mapboxgl.Map({
		  container: 'mapid',
		  style: 'mapbox://styles/mapbox/streets-v11',
		  center: [22.95, 40.635],
		  zoom: 13
		  });
		  
		  //create markers for traffic lights
		  for(var i=0;i<nodes.length; i++){
		    var marker = new mapboxgl.Marker()
		    					.setLngLat([nodes[i].longitude, nodes[i].latitude])
		    					.addTo(map);
		    
		    marker.setPopup(new mapboxgl.Popup().setHTML("<center><b>ID: " + nodes[i].ID + " </b><br>" + nodes[i].name + "</center>")) ;
		  }
		  
		  var locationMarker = new mapboxgl.Marker();
	
		  // Add geolocate control to the map.
		  map.addControl(
			  new mapboxgl.GeolocateControl({
			  positionOptions: {
				  enableHighAccuracy: true
			  },
			  	trackUserLocation: true
			  })
		  );
		  //DRAW PATHS
		  map.on('load', function() {
			  for(var i=0;i<paths.length;i++){
				  var last = pathList[i].length - 1;
				  var pathID = 'route'+i; 
				  var destination = pathList[i][last];
				  if(destination !== undefined){
					  if(document.getElementById(destination).checked===true && typeof map.getLayer(pathID) === 'undefined'){
						  map.addSource(pathID, {
							  'type': 'geojson',
							  'data': {
								  'type': 'Feature',
								  'properties': {},
								  'geometry': {
									  'type': 'LineString',
									  'coordinates': paths[i]
									  }
							  	}
						  });
						  map.addLayer({
							  'id': pathID,
							  'type': 'line',
							  'source': pathID,
							  'layout': {
							  'line-join': 'round',
							  'line-cap': 'round'
							  },
							  'paint': {
							  'line-color': color[i],
							  'line-width': weight[i]
							  }
						  });  
					  }
				  }
				  
			  }
		  });
	}
}	

function updatePaths(pathList, pathIndex){
	//UPDATE PATHS	
	if(pathList[0].length>0){
		var pathID = 'route'+pathIndex;
		var last = pathList[pathIndex].length - 1;
		var pathLayer = map.getLayer(pathID);
		  
		if(document.getElementById(pathList[pathIndex][last]).checked === false){
			if(typeof pathLayer !== 'undefined'){
				try {
					map.removeLayer(pathID);
					map.removeSource(pathID);		
				}
				catch(err) {
					alert("Error!");
				}
			}					
		}else{
			if(typeof pathLayer === 'undefined'){
				map.addSource(pathID, {
					'type': 'geojson',
					'data': {
						'type': 'Feature',
						'properties': {},
						'geometry': {
							'type': 'LineString',
							'coordinates': paths[pathIndex]
						}
					}
				});
				map.addLayer({
					'id': pathID,
					'type': 'line',
					'source': pathID,
					'layout': {
						'line-join': 'round',
						'line-cap': 'round'
					},
					'paint': {
						'line-color': color[pathIndex],
						'line-width': weight[pathIndex]
					}
				});	
			}
		}
	  }	    
}