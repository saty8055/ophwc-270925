<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header"> Create Division </h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="divisionRequest"
						method="GET"
						action="${pageContext.request.contextPath}/division/saveDivision">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Division Name </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="divisionName" />
							</div>
						</div>
						
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Status </label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active" value="Y"
									data-onstyle="success" data-offstyle="danger" path="status" />
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
<jsp:include page="footer.jsp" />
