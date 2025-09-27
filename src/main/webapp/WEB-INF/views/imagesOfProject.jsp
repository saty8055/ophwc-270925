<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<style>
ul.gallery {
	margin-left: 3vw;
	margin-right: 3vw;
}

.zoom {
	-webkit-transition: all 0.35s ease-in-out;
	-moz-transition: all 0.35s ease-in-out;
	transition: all 0.35s ease-in-out;
	cursor: -webkit-zoom-in;
	cursor: -moz-zoom-in;
	cursor: zoom-in;
}

.zoom:hover, .zoom:active, .zoom:focus {
	/**adjust scale to desired size, 
add browser prefixes**/
	-ms-transform: scale(2.5);
	-moz-transform: scale(2.5);
	-webkit-transform: scale(2.5);
	-o-transform: scale(2.5);
	transform: scale(2.5);
	position: relative;
	z-index: 100;
}

/**To keep upscaled images visible on mobile, 
increase left & right margins a bit**/
@media only screen and (max-width: 768px) {
	ul.gallery {
		margin-left: 15vw;
		margin-right: 15vw;
	}

	/**TIP: Easy escape for touch screens,
give gallery's parent container a cursor: pointer.**/
	.DivName {
		cursor: pointer
	}
}
</style>
<div id="page-wrapper">
	<div class="container-fluid">
		<a href="<%=request.getContextPath()%>/project/projectInMap/${projId}"
			class="active"><i style="color: green" class="fa fa-map-marker"
			aria-hidden="true"><b> Map View</b></i> </a>
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Images</h3>
			</div>
			<ul class="list-inline gallery">
				<c:forEach items="${imagesList}" var="image">
					<li><img class="thumbnail zoom" src="${image.imagePath}"
						width="300" height="300" /> <a href="${image.imagePath}">Click
					</a></li>
				</c:forEach>
			</ul>
		</div>

		<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
			<button type="button" class="btn btn-primary" onclick="goBack()">Back</button>
		</div>
	</div>
</div>

<script>
	function goBack() {
		window.history.back();
	}
</script>
<jsp:include page="footer.jsp" />