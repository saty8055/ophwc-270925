<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />


<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Create User</h3>
				<div class="col-md-6">
					<form:form class="form-horizontal" modelAttribute="userRequest"
						method="POST" enctype="multipart/form-data"
						action="${pageContext.request.contextPath}/user/saveUser">

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> User Type</label>
							<div class="col-sm-7">
								<form:select path="userTypes.id" id="userTypeId"
									name="userTypeId" class="form-control" title="Select User Type"
									required="required">
									<option value="">Select User Type</option>
									<c:forEach var="type" items="${userTypeList}">
										<option value="${type.id}">${type.typeName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> User Name
							</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="username" id="userName"
									placeholder="User Name" required="required" onchange="checkUserName()"/>
									<span class="status" id="userId"></span>
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Password </label>
							<div class="col-sm-7">
								<form:password class="form-control disabled " path="password"
									placeholder="Password" data-toggle="password"
									required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> First Name
							</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="firstName"
									placeholder="First Name" required="required" />
							</div>
						</div>
						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Last Name
							</label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="lastName"
									placeholder="Last Name" required="required" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Mobile
								Number </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="phoneNum"
									placeholder="Mobile Number" required="required" />
							</div>
						</div>

						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Email </label>
							<div class="col-sm-7">
								<form:input class="form-control disabled " path="emailId"
									placeholder="Email Id" required="required" />
							</div>
						</div>

						<%-- <div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Select
								Profile </label>
							<div class="col-sm-7">
								<form:input type="file" name="file" id="file" class="form-control disabled " path="userImage" />
								<input type="file" name="file" id="file"
									class="form-control disabled ">
							</div>
						</div> --%>

						<div class="form-group" id="divisions">
							<label for=" " class="col-sm-5 control-label"> Division </label>
							<div class="col-sm-7">
								<form:select path="divisions.id" id="divisionId"
									name="divisionId" class="form-control" title="Select Division">
									<option value="">Select Division</option>
									<c:forEach var="division" items="${divisionsList}">
										<option value="${division.id}">${division.divisionName}</option>
									</c:forEach>
								</form:select>
							</div>
						</div>


						<div class="form-group">
							<label for=" " class="col-sm-5 control-label"> Status </label>
							<div class="col-sm-7">
								<form:checkbox class="form-control disabled" cheked="true"
									data-toggle="toggle" data-on="Active" data-off="In Active"
									value="Y" data-onstyle="success" data-offstyle="danger"
									path="status" required="required" />
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
	$('#userTypeId').on('change', function() {
		if ($(this).val() == 2 || $(this).val() == 4) {
			$("#divisions").show()
		} else {
			$("#divisions").hide()
		}
	});

	$("#password").password('toggle');
</script>

<script>
function checkUserName(){
	var userName=$("#userName").val();
	//alert("projName="+projName);
		$.ajax({
	        url: '<%=application.getContextPath()%>/user/checkUserName',
					dataType : "json",
					type : "GET",
					contentType : 'application/json',
					mimeType : 'application/json',
					data : {
						"userName" : userName
					},
					success : function(response) {
						if (response.responseText != null
								&& response.responseText != '') {
							$(".status").html(response.responseText);
							$("#userName").val('');
						} else
							$(".status").html(response.responseText);
					},
					error : function(response) {
						if (response.responseText != null
								&& response.responseText != '') {
							$(".status").html(response.responseText);
							$("#userName").val('');
						} else
							$(".status").html(response.responseText);
					}
				});
	}
</script>
<jsp:include page="footer.jsp" />
