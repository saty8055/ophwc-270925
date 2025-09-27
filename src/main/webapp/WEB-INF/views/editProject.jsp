<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Edit Project</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="projReq"
						method="POST"
						action="${pageContext.request.contextPath}/project/updateProject">
						<form:hidden class="form-control disabled" path="id"
							value="${proj.getId()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="projname"
									name="username" value="${proj.projectName}" path="projectName" required="required"/>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Project Definition</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="projectDefination"
									name="firstName" value="${proj.projectDefination}" path="projectDefination" required="required"/>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Division</label>
							<div class="col-sm-7">
								<form:select class="form-control disabled"
									id="divisions" name="divisions"
									path="divisions.id" required="required">
									<option value="">Select Division</option>
									<c:forEach var="division" items="${divisionsList}">
										<c:choose>
											<c:when test="${division.id eq proj.divisions.id}">
												<option value="${division.id}" selected>${division.divisionName}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${division.id}">${division.divisionName}
												</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Nature Of Work </label>
							<div class="col-sm-7">
								<form:select class="form-control disabled" id="natureOfWork"
									name="natureOfWork" path="natureOfProject.id" required="required">
									<option value="">Select Nature Of Work</option>
									<c:forEach var="nature" items="${natureList}">
										<c:choose>
											<c:when test="${nature.id eq proj.natureOfProject.id}">
												<option value="${nature.id}" selected>${nature.name}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${nature.id}">${nature.name}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Client</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="clientName"
									name="clientName" value="${proj.clientName}" path="clientName" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Location</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="projectLocation"
									name="projectLocation" value="${proj.projectLocation}" path="projectLocation" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Latitude</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="latitude"
									name="latitude" value="${proj.latitude}" path="latitude" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Longitude</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="langitude"
									name="langitude" value="${proj.langitude}" path="langitude" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Comments</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="comments"
									name="comments" value="${proj.comments}" path="comments" />
							</div>
						</div>
						
						<form:hidden class="form-control disabled" id="isHandovered"
									name="isHandovered" value="${proj.isHandovered}" path="isHandovered" />

						<div class="form-group">
							<div class="col-sm-offset-4 col-sm-8" style="margin-top: 10px;">
								<button type="submit" class="btn btn-primary">Submit</button>
							</div>
						</div>
					</form:form>
				</div>
			</div>
		</div>

		<!-- ... Your content goes here ... -->

	</div>
</div>
<jsp:include page="footer.jsp" />







