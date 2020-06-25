var xhReq = new XMLHttpRequest();
xhReq.open("HEAD", "./data.js", false);
xhReq.send(null);
var lastModified = xhReq.getResponseHeader("Last-Modified");
var map;
var nodes = JSON.parse(data);

var paths = getPaths(pathList);
//IF DESTINATIONS ARE MORE THAN THREE CHANGE CODE HERE--->
var color = ['red', 'yellow', 'blue'];
var weight = [7, 5, 2];

createMap(nodes, pathList, false);

if(pathList[0].length>0){
	var path_code = "";
	var nodes_code = "&#8226 " + "<a style='color:brown;'><b>START: </b></a><a>" + pathList[0][0];
	for(var j=0;j<pathList.length;j++){
		var sum = 0;
		for(var i=1; i<pathList[j].length; i++){
			sum += getWeight(getNode(pathList[j][i-1]).Edge, pathList[j][i])
		}
		if(j!=0)
			path_code = path_code + " &#124 " + "<a style='color:brown;font-style: normal;'><b>PATH: </b></a><a>" + pathList[j] + " &#8226 " + "</a><a style='color:brown;font-style: normal;'><b>DIST: </b></a>" + sum + "</a>";
		else
			path_code = path_code + "<a style='color:brown;font-style: normal;'><b>PATH: </b></a><a>" + pathList[j] + " &#8226 " + "</a><a style='color:brown;font-style: normal;'><b>DIST: </b></a>" + sum + "</a>";	
	}
	document.getElementById('nodes_text').innerHTML = nodes_code;
	document.getElementById('path_text').innerHTML = path_code;
}

timeout();

function timeout() {
	setTimeout(function(){
		if(pathList[0].length>0){
			var xhReq = new XMLHttpRequest();
			xhReq.open("HEAD", "./data.js", false);
			xhReq.send(null);
			var lastModifiedNew = xhReq.getResponseHeader("Last-Modified");
			//console.log(lastModified + " " + lastModifiedNew);
			//console.log(lastModified===lastModifiedNew);
			if(lastModified!==lastModifiedNew){
				document.getElementById("source_node").value = pathList[0][0];
				document.getElementById("start_form").submit();
				alert("Shortest Path(s) Have Changed!");
			}
		}	
		timeout();
	}, 5000)
}

function checkboxClick(value){
	if(pathList.length>0){
		updatePaths(pathList, value)
	}
}

function getNode(key) {
    var found = null;
    for (var i = 0; i < nodes.length; i++) {
        var element = nodes[i];
        if (element.ID == key) {
           found = element;
       } 
    }
    return found;
}

function getPaths(pathList){
	var paths = [];
	for(var i=0;i<pathList.length;i++){
		var points = [];
		  for(var j=1;j<=pathList[i].length;j++){ 
			  points.push([getNode(pathList[i][j-1]).longitude, getNode(pathList[i][j-1]).latitude]);	  
		  }
		  paths.push(points);
	}
	return paths;
}

function getWeight(Edge, key) {
    var found = null;
    for (var i = 0; i < Edge.length; i++) {
        var element = Edge[i];
        if (element.destination_id == key) {
           found = element.weight;
       } 
    }
    return found;
}