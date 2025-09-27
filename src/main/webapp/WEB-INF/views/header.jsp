
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<%-- <jsp:useBean id="sesionObj" class="com.gcs.controller.SessionData" scope="session" /> --%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<title>Ophwc</title>
<spring:url value="/resources/css/bootstrap.min.css" var="bootstrap" />
<spring:url value="/resources/css/startmin.css" var="startmincss" />
<spring:url value="/resources/css/font-awesome.min.css"
	var="fontawesome" />
<spring:url value="/resources/css/pmo-track-css.css" var="pmotrack" />
<spring:url value="/resources/css/jquery-ui.css" var="jqueryuicss" />

<spring:url value="/resources/js/jquery.min.js" var="jquerymin" />
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapmin" />
<spring:url value="/resources/js/metisMenu.min.js" var="metisMenumin" />
<spring:url value="/resources/js/startmin.js" var="startmin" />
<spring:url value="/resources/js/jquery-ui.js" var="jqueryuijs" />
<spring:url value="/resources/js/reports.js" var="reportsjs" />
<spring:url value="/resources/js/country_state_city.js"
	var="country_state_cityjs" />
<spring:url value="/resources/js/validate.js" var="validatejs" />

<spring:url value="/resources/img" var="img" />

<spring:url
	value="/resources/js/vendor/datatables/js/jquery.dataTables.min.js"
	var="jquerytablemin" />
<spring:url
	value="/resources/js/vendor/datatables-plugins/dataTables.bootstrap.min.js"
	var="tablebootstrapmin" />
<spring:url
	value="/resources/js/vendor/datatables-responsive/dataTables.responsive.js"
	var="tableresponsive" />

<link href="${bootstrap}" rel="stylesheet" />
<link href="${startmincss}" rel="stylesheet" />
<link href="${fontawesome}" rel="stylesheet" />
<link href="${pmotrack}" rel="stylesheet" />
<link href="${jqueryuicss}" rel="stylesheet" />
<link href="${validatejs}" rel="stylesheet" />

<script src="${jquerymin}"></script>
<script src="${bootstrapmin}"></script>
<script src="${metisMenumin}"></script>
<script src="${startmin}"></script>
<script src="${jquerytablemin}"></script>
<script src="${tablebootstrapmin}"></script>
<script src="${tableresponsive}"></script>
<script src="${jqueryuijs}"></script>
<script src="${reportsjs}"></script>
<script src="${country_state_cityjs}"></script>
<script src="${validatejs}"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-show-password/1.0.3/bootstrap-show-password.min.js"></script>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyA21w3EhOQ2hEwoPei9fVOkDa0VJiuVKdA"
	type="text/javascript"></script>
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="navbar-header">
				<a class="navbar-brand" href="${img}/ophwclogo.png"> <img
					src="${img}/ophwclogo.png" width="100%" height="150%">
				</a>
			</div>
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>

			<!-- Top Navigation: Left Menu -->

			<!-- Top Navigation: Right Menu -->


			<ul class="nav navbar-right navbar-top-links">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <i class="fa fa-user fa-fw"></i>
						${sessionScope['scopedTarget.sessionObj'].getUser().getUsername()}
						<b class="caret"></b>
				</a>
					<ul class="dropdown-menu dropdown-user">
						<%-- <li><a
							href="<%=request.getContextPath()%>/changePassword"><i
								class="fa fa-user fa-fw"></i> Change Password </a></li> --%>

						<li class="divider"></li>
						<li><a
							href="<%=request.getContextPath()%>/logout"><i
								class="fa fa-sign-out fa-fw"></i> Logout </a></li>
					</ul></li>

			</ul>


			<!-- Sidebar -->
			<c:choose>
				<c:when
					test="${sessionScope['scopedTarget.sessionObj'].getUser().getUserTypes().getId()==1}">
					<div class="navbar-default sidebar" role="navigation">
						<div class="sidebar-nav navbar-collapse">
							<ul class="nav" id="side-menu" style="margin-top: 16px;">

								<li><a
									href="<%=request.getContextPath()%>/admin_dashboard"
									class="active"><i class="fa fa-dashboard fa-fw"></i>
										Dashboard </a></li>
								<li><a href="#"><i class="fa fa-list fa-fw"></i> User
										Type Management <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/userType/createUserType"><i
												class="fa fa-user-plus fa-fw"></i> Create User Type </a></li>
										<li><a
											href="<%=request.getContextPath()%>/userType/searchUserType"><i
												class="fa fa-list fa-fw"></i> User Types List </a></li>
									</ul></li>

								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Division Management <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/division/divisionCreation"><i
												class="fa fa-user-plus fa-fw"></i> Create Division </a></li>
										<li><a
											href="<%=request.getContextPath()%>/division/searchDivisions"><i
												class="fa fa-list fa-fw"></i> Search Divisions </a></li>
									</ul></li>

								<li><a href="#"><i class="fa fa-list fa-fw"></i> User
										Management <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/user/userCreation"><i
												class="fa fa-user-plus fa-fw"></i> Create User </a></li>
										<li><a
											href="<%=request.getContextPath()%>/user/searchUsers"><i
												class="fa fa-list fa-fw"></i> Search Users </a></li>
										<li><a
											href="<%=request.getContextPath()%>/user/usersUpload"><i
												class="fa fa-upload fa-fw"></i> Users Bulk Upload </a></li>
										<li><a
											href="<%=request.getContextPath()%>/project/assignProject"><i
												class="fa fa-arrow-right fa-fw"></i> Assign Projects </a></li>

									</ul></li>

								<li><a href="#"><i class="fa fa-list fa-fw"></i>
										Projects Management <span class="fa arrow"></span></a>
									<ul class="nav nav-second-level">
										<li><a
											href="<%=request.getContextPath()%>/project/projectCreation"><i
												class="fa fa-user-plus fa-fw"></i> Create Project </a></li>
										<li><a
											href="<%=request.getContextPath()%>/project/searchProjects"><i
												class="fa fa-list fa-fw"></i> Projects List </a></li>
												
												<li><a
											href="<%=request.getContextPath()%>/project/handOveredProject"><i
												class="fa fa-list fa-fw"></i>HandOvered Projects List </a></li>

										<li><a
											href="<%=request.getContextPath()%>/project/projectsUpload"><i
												class="fa fa-upload fa-fw"></i> Projects Bulk Upload </a></li>

									</ul></li>

							</ul>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when
							test="${sessionScope['scopedTarget.sessionObj'].getUser().getUserTypes().getId()==3}">
							<div class="navbar-default sidebar" role="navigation">
								<div class="sidebar-nav navbar-collapse">
									<ul class="nav" id="side-menu" style="margin-top: 16px;">

										<li><a
											href="<%=request.getContextPath()%>/hoDashboard"
											class="active"><i class="fa fa-dashboard fa-fw"></i> HO
												User_Dashboard </a></li>
												
												<li><a
											href="<%=request.getContextPath()%>/project/handOveredProject"><i
												class="fa fa-list fa-fw"></i>HandOvered Projects List </a></li>
									</ul>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when
									test="${sessionScope['scopedTarget.sessionObj'].getUser().getUserTypes().getId()==4}">
									<div class="navbar-default sidebar" role="navigation">
										<div class="sidebar-nav navbar-collapse">
											<ul class="nav" id="side-menu" style="margin-top: 16px;">

												<li><a
													href="<%=request.getContextPath()%>/superDivisionDashboard"
													class="active"><i class="fa fa-dashboard fa-fw"></i>
														SuperDivision User_Dashboard </a></li>
											</ul>
										</div>
									</div>
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</nav>
	</div>
	<!-- Page Content -->