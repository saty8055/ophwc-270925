<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Create Project</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="projReq"
						method="POST" enctype="multipart/form-data"
						action="${pageContext.request.contextPath}/project/saveProject">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Project
								Name </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="projectName"
									id="projectName" onchange="checkProjectName()"
									placeholder="Project Name" required="required" />
								<span class="status" id="projId"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Project
								Definition </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled "
									path="projectDefination" placeholder="Project Definition"
									required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Division
								Of project </label>
							<div class="col-sm-7">
								<form:select path="divisions.id" id="divisionId"
									name="divisionId" class="form-control" title="Select Division"
									required="required">
									<option value="">Select Division</option>
									<c:forEach var="division" items="${divisionsList}">
										<option value="${division.id}">${division.divisionName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Nature Of
								Work </label>
							<div class="col-sm-7">
								<form:select path="natureOfProject.id" id="natureId"
									name="natureId" class="form-control"
									title="Select Nature Of Work" required="required">
									<option value="">Select Nature Of Work</option>
									<c:forEach var="nature" items="${natureList}">
										<option value="${nature.id}">${nature.name}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Client
								Name </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="clientName"
									placeholder="Client Name" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Location </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled "
									path="projectLocation" placeholder="Location" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Latitude </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="latitude"
									placeholder="Latitude" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Longitude
							</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="langitude"
									placeholder="Longitude" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Comments </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="comments"
									placeholder="Comments" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Status </label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active"
									value="Y" data-onstyle="success" data-offstyle="danger"
									path="status" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Create</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>

<script>
function checkProjectName(){
	var projName=$("#projectName").val();
	//alert("projName="+projName);
		$.ajax({
	        url: '<%=application.getContextPath()%>/project/checkProjName',
					dataType : "json",
					type : "GET",
					contentType : 'application/json',
					mimeType : 'application/json',
					data : {
						"projName" : projName
					},
					success : function(response) {
						if (response.responseText != null
								&& response.responseText != '') {
							$(".status").html(response.responseText);
							$("#projectName").val('');
						} else
							$(".status").html(response.responseText);
					},
					error : function(response) {
						if (response.responseText != null
								&& response.responseText != '') {
							$(".status").html(response.responseText);
							$("#projectName").val('');
						} else
							$(".status").html(response.responseText);
					}
				});
	}
</script>
<jsp:include page="footer.jsp" />
