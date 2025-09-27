<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
		<div class="col-lg-12">
				<h3 class="page-header">Projects List</h3>
			</div>
			<c:forEach items="${projList}" var="project">
				<div class="col-lg-4 col-md-6">
					<div class="panel panel-yellow">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-5">
									<i class="fa fa-building-o fa-5x"></i>
								</div>
								<div class="col-xs-12 text-right">
									<!-- <div class="huge">0</div> -->
									<div>${project.projectName}</div>
									<div>${project.projectDefination}</div>
								</div>
							</div>
						</div>
						<a
							href="<%=request.getContextPath()%>/project/getImagesOfProject/${project.id}">
							<div class="panel-footer">
								<span class="pull-left">View Images</span> <span
									class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
								<div class="clearfix"></div>
							</div>
						</a>
					</div>
				</div>
			</c:forEach>
		</div>
	</div>
</div>
<jsp:include page="footer.jsp" />