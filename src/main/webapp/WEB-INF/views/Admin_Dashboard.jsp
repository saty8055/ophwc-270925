<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">

			<p class="page-header"></p>
			<div class="col-lg-4 col-md-6">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<i class="fa fa-users fa-5x"></i>
							</div>
							<div class="col-xs-12 text-right">
								<!-- <div class="huge">0</div> -->
								<div>Users</div>

							</div>
						</div>
					</div>
					<a href="<%=request.getContextPath()%>/user/searchUsers">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			
			<div class="col-lg-4 col-md-6">
				<div class="panel panel-green">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<i class="fa fa-building fa-5x"></i>
							</div>
							<div class="col-xs-12 text-right">
								<!-- <div class="huge">0</div> -->
								<div>Projects</div>

								<!--  <div class="huge">0</div>
                                    <div> Status</div> -->

							</div>
						</div>
					</div>
					<a
						href="<%=request.getContextPath()%>/project/searchProjects">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>
			
			<div class="col-lg-4 col-md-6">
				<div class="panel panel-yellow">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-5">
								<i class="fa fa-tasks fa-5x"></i>
							</div>
							<div class="col-xs-12 text-right">
								<!-- <div class="huge">0</div> -->
								<div>Assigned Projects</div>

								<!--  <div class="huge">0</div>
                                    <div> Status</div> -->

							</div>
						</div>
					</div>
					<a
						href="<%=request.getContextPath()%>/project/getAssignedProjectsForUser">
						<div class="panel-footer">
							<span class="pull-left">View Details</span> <span
								class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
							<div class="clearfix"></div>
						</div>
					</a>
				</div>
			</div>

		</div>
	</div>


	<jsp:include page="footer.jsp" />