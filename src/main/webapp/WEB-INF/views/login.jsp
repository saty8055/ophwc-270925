<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="${pageContext.request.contextPath}">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<title>Ophwc</title>

<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap" />
<%-- <spring:url value="/resources/css/loginstyle.css" var="loginstyle" /> --%>
<spring:url value="/resources/css/startmin.css" var="startmin" />
<spring:url value="/resources/css/font-awesome.min.css"
	var="fontawesome" />
<spring:url value="/resources/js/jquery.min.js" var="jquerymin" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapmin" />
<spring:url value="/resources/js/metisMenu.min.js" var="metisMenumin" />
<spring:url value="/resources/js/startmin.js" var="startminjs" />
<%-- <!-- jQuery --> 
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script> 
<!-- Bootstrap Core JavaScript --> 
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script> 
<!-- Metis Menu Plugin JavaScript --> 
<script src="<%=request.getContextPath()%>/js/metisMenu.min.js"></script> 
<!-- Custom Theme JavaScript --> 
<script src="<%=request.getContextPath()%>/js/startmin.js"></script> --%>
<spring:url value="/resources/img" var="img" />
<link href="${bootstrap}" rel="stylesheet" />
<%-- <link href="${loginstyle}" rel="stylesheet" /> --%>
<link href="${startmin}" rel="stylesheet" />
<link href="${fontawesome}" rel="stylesheet" />

<script src="${jquerymin}"></script>
<script src="${bootstrapmin}"></script>
<script src="${metisMenumin}"></script>
<script src="${startminjs}"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-show-password/1.0.3/bootstrap-show-password.min.js"></script>
</head>

<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand"
					href="ophwc/resources/img/ophwclogo.png"> <img
					src="ophwc/resources/img/ophwclogo.png" width="100%"
					height="150%">
				</a>
			</div>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</nav>

		<!-- Page Content -->
		<div id="" style="margin-top: 50px;">
			<div class="container-fluid">
				<div class="row">
					<div class="col-lg-12">
						<div class="col-md-4 col-md-offset-4">
							<h3 class="page-header" style="color: green">Ophwc Login</h3>
							<form:form class="form-horizontal" modelAttribute="userRequest"
								id="form" method="POST"
								action="${pageContext.request.contextPath}/validateUser">
								<div class="form-group">
									<label for="exampleInputEmail1">User Name</label>
									<form:input type="text" class="form-control" path="username"
										id="userName" placeholder="User Name" />

								</div>
								<div class="form-group">
									<label for="exampleInputPassword1">Password</label>
									<form:input type="password" class="form-control"
										path="password" id="password" placeholder="Password"
										data-toggle="password" />

								</div>
								<div class="checkbox">
									<label> <input type="checkbox"> Keep me signed
										in
									</label>
								</div>
								<!--<button type="submit" class="btn btn-default btn-primary">Submit</button>-->
								<button type="submit" class="btn-primary btn v-bg-color">Login</button>
							</form:form>
						</div>
					</div>
				</div>
				<div class="row table-gap">
					<div
						class="col-lg-6 col-xs-10 col-sm-6 col-md-5 col-sm-offset-5 col-md-offset-5 col-xs-offset-1">
						<div id="alert_display_boxParent">
							<div id="alert_display_box">
								<span style="color: red">${sessionScope['scopedTarget.sessionObj'].getStatusMessage()}</span>
							</div>
						</div>
					</div>
				</div>
				<!-- ... Your content goes here ... -->
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$("#password").password('toggle');
	</script>
</body>
</html>
