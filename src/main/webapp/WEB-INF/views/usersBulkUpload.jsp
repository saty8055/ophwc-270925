<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<style>
span a {
	font-size: 120%;
}

span b {
	font-size: 1.4em;
}

.image-preview-input {
	position: relative;
	overflow: hidden;
	margin: 0px;
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}

.image-preview-input input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity = 0);
}

.loader {
	/* position: fixed;
	left: 0px;
	top: 0px; */
	width: 100%;
	height: 70px;
	/* z-index: 9999; */
	background:
		url('${pageContext.request.contextPath}/resources/img/TaPz.gif') 50%
		50% no-repeat rgb(249, 249, 249);
	display: none;
	/* opacity: .8; */
}

.image-preview-input-title {
	margin-left: 2px;
}
</style>

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default" style="margin-top: 50px;">
					<div class="panel-heading">
						<h4>Users Bulk Upload</h4>
					</div>
				</div>

				<div class=" pull-right">
					<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
					<a class="btn-info btn"
						href="<%=request.getContextPath()%>/user/sampleUsersFile">
						<i class="fa fa-download" aria-hidden="true"> </i> Download Sample
						File
					</a>
				</div>

				<div
					class="col-xs-12 col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
					<h3 class="page-header">Import Users</h3>


					<!-- image-preview-filename input [CUT FROM HERE]-->
					<form id="myForm"
						action="${pageContext.request.contextPath}/user/uploadUsers"
						method="post" enctype="multipart/form-data">
						<div class="form-group input-group image-preview">
							<input type="text" class="form-control image-preview-filename"
								id="filename" disabled="disabled">
							<!-- don't give a name === doesn't send on POST/GET -->
							<span class="input-group-btn"> <!-- image-preview-clear button -->
								<button type="button"
									class="btn btn-default image-preview-clear"
									style="display: none;">
									<span class="glyphicon glyphicon-remove"></span> Clear
								</button> <!-- image-preview-input -->
								<div class="btn btn-default image-preview-input">
									<span class="glyphicon glyphicon-folder-open"></span> <span
										class="image-preview-input-title">Browse</span> <input
										type="file" required="required" accept=".xlsx, .xls"
										name="myFile" id="myFile" />
									<!-- rename it -->


								</div>
							</span>
						</div>
						<!-- /input-group image-preview [TO HERE]-->
						<div class="form-group">
							<br />
							<c:choose>
								<c:when test="${headerCheck ==0}">
									<span><a> <b style="color: red;">*Invalid File
												Format:</b> Following Headers are Missed: ${headres}
									</a></span>
									<br />
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${user==0}">
											<span><a> <b style="color: red;">*Invalid
														Data:</b> User Name is Mandatory
											</a></span>
											<br />
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${type==0}">
													<span><a> <b style="color: red;">*Invalid
																Data:</b> User Type is Not Valid for the User: ${userName}
													</a></span>
													<br />
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${userData==0}">
															<span><a><b style="color: red;">*Submitted
																		Users are already Exists, Please Verify the bulk
																		upload file</b></a></span>
															<br />
														</c:when>
													</c:choose>
												</c:otherwise>
											</c:choose>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							<br /> <span id="lblError" style="color: red;"></span> <br /> <input
								type="submit" class="btn btn-primary" value="Submit"
								onclick="return ValidateExtension()" />

						</div>
						<div class="loader"></div>
					</form>

				</div>
			</div>
			<!-- ... Your content goes here ... -->

		</div>
	</div>
</div>

<script type="text/javascript">
	function ValidateExtension() {
		var allowedFiles = [ ".xlsx" ];
		var fileUpload = document.getElementById("myFile");
		var lblError = document.getElementById("lblError");
		var regex = new RegExp("([a-zA-Z0-9\s_\\.\-:])+("
				+ allowedFiles.join('|') + ")$");
		if (!regex.test(fileUpload.value.toLowerCase())) {
			lblError.innerHTML = "<b>*Please upload only Excel formated file (having extension '.xlsx')</b>";
			return false;
		}
		lblError.innerHTML = "";
		myLoader();
		return true;
	}
</script>

<script>
	$(document).on('click', '#close-preview', function() {
		$('.image-preview').popover('hide');
		// Hover befor close the preview
		$('.image-preview').hover(function() {
			$('.image-preview').popover('show');
		}, function() {
			$('.image-preview').popover('hide');
		});
	});

	$(function() {
		// Create the close button
		var closebtn = $('<button/>', {
			type : "button",
			text : 'x',
			id : 'close-preview',
			style : 'font-size: initial;',
		});
		closebtn.attr("class", "close pull-right");
		// Set the popover default content
		$('.image-preview').popover({
			trigger : 'manual',
			html : true,
			title : "<strong>Preview</strong>" + $(closebtn)[0].outerHTML,
			content : "There's no image",
			placement : 'bottom'
		});
		// Clear event
		$('.image-preview-clear').click(function() {
			$('.image-preview').attr("data-content", "").popover('hide');
			$('.image-preview-filename').val("");
			$('.image-preview-clear').hide();
			$('.image-preview-input input:file').val("");
			$(".image-preview-input-title").text("Browse");
		});
		// Create the preview image
		$(".image-preview-input input:file").change(function() {
			var img = $('<img/>', {
				id : 'dynamic',
				width : 250,
				height : 200
			});
			var file = this.files[0];
			var reader = new FileReader();
			// Set preview image into the popover data-content
			reader.onload = function(e) {
				$(".image-preview-input-title").text("Change");
				$(".image-preview-clear").show();
				$(".image-preview-filename").val(file.name);
				img.attr('src', e.target.result);
				//$(".image-preview").attr("data-content",$(img)[0].outerHTML).popover("show");
			}
			reader.readAsDataURL(file);
		});
	});
</script>

<script type="text/javascript">
	function myLoader() {
		$(".loader").show();
		/* $(".loader").fadeOut("slow"); */
	}
</script>

<jsp:include page="footer.jsp" />
