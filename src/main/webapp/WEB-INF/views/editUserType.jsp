<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper" class="bg">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Edit User Type</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="typeReq"
						method="POST"
						action="${pageContext.request.contextPath}/userType/updateUserType">
						<form:hidden class="form-control disabled" path="id"
							value="${userType.getId()}" readonly="true" />

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label">User Type</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled" id="typeName"
									name="username" value="${userType.typeName}" path="typeName" />
							</div>
						</div>
						

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Status </label>
							<div class="col-sm-7">
								<c:choose>
									<c:when test="${userType.status=='Y'}">
										<form:checkbox class="form-control disabled" checked="checked"
											id="role" size="1" path="status" value="${userType.status}" />
									</c:when>
									<c:otherwise>
										<form:checkbox class="form-control disabled" id="role" value="N"
											size="1" path="status" />
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
<jsp:include page="footer.jsp" />







