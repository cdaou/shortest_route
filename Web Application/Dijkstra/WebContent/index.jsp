<!DOCTYPE html>
<html>
<%@ page import ="java.util.ArrayList"%>
<%@ page import ="java.util.List"%>
  <head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.4.0/dist/leaflet.css"
      integrity="sha512-puBpdR0798OZvTTbP4A8Ix/l+A4dHDD0DGqYW6RQ+9jxkRFclaxxQb/SJAWZfWAkuyeQUytO7+7N4QKrDh+drA=="
      crossorigin=""/>
    <script src="https://unpkg.com/leaflet@1.4.0/dist/leaflet.js"
    integrity="sha512-QVftwZFqvtRNi0ZyCtsznlKSWOStnDORoefr1enyq5mVL4tmKB3S/EnC3rRJcxCPavG10IcrVGSmPh6Qw5lwrg=="
    crossorigin=""></script> 
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.2.0/dist/leaflet.css" />
	<link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css" />
	<script src='https://api.mapbox.com/mapbox-gl-js/v1.11.0/mapbox-gl.js'></script>
	<link href='https://api.mapbox.com/mapbox-gl-js/v1.11.0/mapbox-gl.css' rel='stylesheet' />
	<script src="https://unpkg.com/leaflet@1.2.0/dist/leaflet.js"></script>
	<script src="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.js"></script>
	<style>
	fieldset{
    -moz-box-sizing: border-box;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
    padding: 3px;
    margin:0;
    float: left;
	}
	#mapid {
    	float: left;
    	width:100%;
	}
	#field1{
	    width: 220px;
	    height: 35px;
	}
	#field2 {
	    width: calc(100% - 283px);
	    min-height: 35px;
	    min-width: 305px;
	}
	#field3 {
	    width: calc(100% - 63px);
	    min-height: 35px;
	}
	#field4 {
	    width: 60px;
	    min-height: 70px;
	    float:right;
	}
	 .leaflet-control-container .leaflet-top.leaflet-right {
			   display: none;
		}
  	</style>
  </head>
  <body>
  
    <form id="start_form" action="dijkstra_servlet" method="get">
   	  <fieldset id="field4">
      	<img src="./loading.png" style="height:50px;">
      </fieldset>
      <fieldset id="field1"><div style="margin-top:2px;">
        Source Node: <input id="source_node" type="text" name="source"  pattern="\d{4}" size="3" required>
        <input type="submit" value="Submit">
      </div></fieldset>
      <fieldset id="field2"><div style="margin-top:2px;">
        <a style="color:brown;margin-left: 5px;" id="checkboxes"><b> DESTINATIONS:</b></a> <a style="color:black;">
		        <label><input id="1023" onclick="checkboxClick(this.value);" type="checkbox" value="0" checked>1023</label>
	 			<label><input id="1013" onclick="checkboxClick(this.value);" type="checkbox" value="1" checked>1013</label>
	 			<label><input id="1028" onclick="checkboxClick(this.value);" type="checkbox" value="2" checked>1028</label>
        </a><a id="nodes_text"></a> 
      </div></fieldset>
      <fieldset id="field3"><div style="margin-top:3px;">
      	<a style="font-style:italic; color:grey;" id="path_text"> The path will show up here! </a>
      </div></fieldset>
    </form>
    <%  
    	ArrayList<ArrayList<Integer>> tempList = new ArrayList<>();
    	tempList = (ArrayList<ArrayList<Integer>>) request.getAttribute("paths_"); 
    %>
    <script type="text/javascript"> 
    		//IF DESTINATIONS ARE MORE THAN THREE CHANGE PATHLIST SIZE HERE--->
    		var pathList = [[],[],[]];
    		<%if (request.getAttribute("paths_") != null){%>
    			<%for(int j=0;j<tempList.size();j++){%>
	    			<%for(int i=0;i<tempList.get(j).size();i++){%>
	        	    	pathList[<%=j%>].push(<%= tempList.get(j).get(i) %>);
	        		<%}%>
        		<%}%>
        	<%}%>
    </script>
    <p id="mapid" style = "height: 595px;"></p>
    <script type="text/javascript" src="./data.js"></script>
    <script type="text/javascript" src="./map.js"></script>
    <script type="text/javascript" src="./main.js"></script>
    <script type="text/javascript" src="./test.js"></script>
	<a id="time">  </a>
     <script type="text/javascript">
     var timeElapsed = ${elapsedTime};
     	if( timeElapsed >0 ){
     		timeElapsed = timeElapsed/1000000;
     	}
	     	document.getElementById('time').innerHTML = "The time to calculate the shortest path in mili seconds: " + (Math.round(timeElapsed * 100) / 100).toFixed(3);
   	</script>
  </body>
</html>