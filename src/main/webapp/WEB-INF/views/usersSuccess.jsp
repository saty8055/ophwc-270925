<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
	
	<h1 style="color: red">Users Created Successfully</h1>
		<div>
			<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
			<a class="btn-info btn"
				href="<%=request.getContextPath()%>/user/latestUsers">
				<i class="fa fa-check-circle" aria-hidden="true"> </i> OK
			</a>
		</div>

		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>
<jsp:include page="footer.jsp" />
