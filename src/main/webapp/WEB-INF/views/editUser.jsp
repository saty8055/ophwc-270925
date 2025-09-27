<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Edit User</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="userReq"
						method="POST"
						action="${pageContext.request.contextPath}/user/updateUser">
						<form:hidden class="form-control disabled" path="id"
							value="${user.getId()}" readonly="true" />

						<%-- <form:hidden class="form-control disabled" path="password"
							value="${user.password}" readonly="true" /> --%>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> User Type</label>
							<div class="col-sm-7">
								<form:select class="form-control disabled" id="userTypeId"
									name="userTypeId" path="userTypes.id" required="required">
									<option value="">Select User Type</option>
									<c:forEach var="type" items="${userTypeList}">
										<c:choose>
											<c:when test="${type.id eq user.userTypes.id}">
												<option value="${type.id}" selected>${type.typeName}
												</option>
											</c:when>
											<c:otherwise>
												<option value="${type.id}">${type.typeName}</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">User Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="username"
									name="username" value="${user.username}" path="username"
									required="required" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Password</label>
							<div class="col-sm-7">
								<form:password class="form-control disabled" id="password"
									name="password" value="${user.password}" path="password"
									data-toggle="password" required="required" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">First Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="firstName"
									name="firstName" value="${user.firstName}" path="firstName" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Last Name</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="lastName"
									name="lastName" value="${user.lastName}" path="lastName" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Mobile
								Number</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="phoneNum"
									name="phoneNum" value="${user.phoneNum}" path="phoneNum" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">Email Id</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="emailId"
									name="emailId" value="${user.emailId}" path="emailId" />
							</div>
						</div>

						<%-- <c:choose>
							<c:when
								test="${user.userTypes.id eq 2 || user.userTypes.id eq 4}">
								<div class="form-group" id="divisions">
									<label for=" " class="col-sm-5 control-label"> Division</label>
									<div class="col-sm-7">
										<form:select class="form-control disabled" id="divisionId"
											name="divisionId" path="divisions.id">
											<option value="">Select Division</option>
											<c:forEach var="division" items="${divisionsList}">
												<c:choose>
													<c:when test="${division.id eq user.divisions.id}">
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
							</c:when>
						</c:choose> --%>

						<div class="form-group" id="divisions">
							<label for=" " class="col-sm-5 control-label"> Division</label>
							<div class="col-sm-7">
								<form:select class="form-control disabled" id="divisionId"
									name="divisionId" path="divisions.id">
									<option value="">Select Division</option>
									<c:forEach var="division" items="${divisionsList}">
										<c:choose>
											<c:when test="${division.id eq user.divisions.id}">
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
							<label for=" " class="col-sm-5 control-label"> Status </label>
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${user.status=='Y'}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="role" size="1" path="status" value="${user.status}" />
									</c:when>
									<c:otherwise>
										<form:checkbox class="form-control disabled" id="role"
											value="N" size="1" path="status" />
									</c:otherwise>
								</c:choose>
							</div>
						</div>

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

<script>
	$(document).ready(function() {
		divisionWithUserType();
		$('#userTypeId').on('change', function() {
			if ($(this).val() == 2 || $(this).val() == 4) {
				$("#divisions").show()
			} else {
				$("#divisions").hide()
			}
		});
	});

	$("#password").password('toggle');

	function divisionWithUserType() {
		if ($('#userTypeId').val() == 2 || $('#userTypeId').val() == 4) {
			$("#divisions").show()
		} else {
			$("#divisions").hide()
		}
	}
</script>

<jsp:include page="footer.jsp" />







