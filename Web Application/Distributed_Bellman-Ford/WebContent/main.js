var xhReq = new XMLHttpRequest();
xhReq.open("HEAD", "./data.js", false);
xhReq.send(null);
var lastModified = xhReq.getResponseHeader("Last-Modified");

var control = new Array();
var mymap;

var nodes = JSON.parse(data);
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
			console.log(lastModified + "- " + lastModifiedNew +"-"+lastModified!==lastModifiedNew);
			if(lastModified!==lastModifiedNew){
				document.getElementById("source_node").value = pathList[0][0];
				document.getElementById("start_form").submit();
				//lastModified=lastModifiedNew;
				alert("Shortest Path(s) Have Changed!");
			}
		}	
		timeout();
	}, 5000)
}

function checkboxClick(click){
	if(pathList.length>0){
		createMap(nodes, pathList, true);
	}
}

function getNode(key) {
    var found = null;
    for (var i = 0; i < nodes.length; i++) {
        var element = nodes[i];
        if (element.ID == key) {
           found = element;
           break;
       } 
    }
    return found;
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