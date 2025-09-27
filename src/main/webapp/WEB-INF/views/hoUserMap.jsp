<!DOCTYPE html>
<html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<head>
<title>Projects Locations</title>
<meta name="viewport" content="initial-scale=1.0">
<meta charset="utf-8">
<style>
/* Always set the map height explicitly to define the size of the div
       * element that contains the map. */
#map {
	height: 100%;
}
/* Optional: Makes the sample page fill the window. */
html, body {
	height: 100%;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body>
	<div id="map"></div>
	<script>
      var map;
      var markers = [
    	  <c:forEach var="marker" items="${projList}">
    	     ["<c:out value="${marker.projectName}" />",
    	      "<c:out value="${marker.projectDefination}" />", // wrapped single quotes if string
    	       <c:out value="${marker.latitude}" />,
    	       <c:out value="${marker.langitude}" />,
    	     ],
    	  </c:forEach>   ]; 
      function initMap() { 
    	  var map = new google.maps.Map(document.getElementById('map'), {
    	      zoom: 5,
    	      center: new google.maps.LatLng(20.5937, 78.9629),
    	      mapTypeId: google.maps.MapTypeId.ROADMAP
    	    });
    	   var infowindow = new google.maps.InfoWindow();
    	  for (i = 0; i < markers.length; i++) {
    	     var position = new google.maps.LatLng(markers[i][2], markers[i][3]);
    	     marker = new google.maps.Marker({
    	         position: position,
    	         map: map,
    	         title: markers[i][0]+", "+markers[i][1]
    	     });
    	     google.maps.event.addListener(marker, 'click', (function (marker, i) {

    	       return function () {
    	    	   infowindow.setContent("Name: "+markers[i][0]+"<br> Definition: "+markers[i][1]);
    	           infowindow.open(map, marker);
    	       }
    	     })(marker, i));
    	  }
    	}
    </script>

	<script
		src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBQpFbmzPDds7vp64lBQD888ywKb_GWaM0&callback=initMap"
		async defer></script>
</body>
</html>