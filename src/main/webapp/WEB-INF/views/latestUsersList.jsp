<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Users List</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Users List</h5>
					</div>
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/user/userCreation">
							<i class="fa fa-user-plus"> </i> Add User
						</a>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<table width="100%"
										class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline"
										id="dataTables-example" role="grid"
										aria-describedby="dataTables-example_info"
										style="width: 100%;">
										<thead>
											<tr role="row" class="bg-warning">
												<th class="sorting_asc" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Browser: activate to sort column descending"
													style="width: 171px;">S.NO</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-sort="ascending"
													aria-label="Browser: activate to sort column descending"
													style="width: 171px;">User Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">First Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Last Name</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Mobile Number</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Email Id</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">User Type</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Division</th>
												<!-- <th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Status</th> -->
												<th style="width: 70px;">Edit</th>
												<th style="width: 80px;">Delete</th>


											</tr>
										</thead>
										<tbody>

											<c:forEach var="user" items="${usersList}"
												varStatus="counter">


												<tr class="gradeA odd" role="row">
													<td class="sorting_1">${counter.count}</td>
													<td>${user.username}</td>
													<td>${user.firstName}</td>
													<td>${user.lastName}</td>
													<td>${user.phoneNum}</td>
													<td style="word-break: break-all;">${user.emailId}</td>
													<td>${user.userTypes.typeName}</td>
													<td><c:choose>
															<c:when
																test="${user.userTypes.id==2 || user.userTypes.id==4}">
																${user.divisions.divisionName}
															</c:when>
														</c:choose></td>
													<%-- <td>${user.status}</td> --%>
													<td class="center"><a class="btn-info btn-sm"
														href="<%=request.getContextPath()%>/user/editUser/${user.id}">
															<i class="fa fa-edit"> </i>
													</a></td>
													<td class="center"><a class="btn-danger btn-sm"
														href="<%=request.getContextPath()%>/user/deleteUser/${user.id}"
														onclick="return confirm('Are you sure you want to Delete?');">
															<i> Delete </i>
													</a></td>

													<%-- <td class="center"><c:choose>
															<c:when test="${user.status=='Y'}">
																<a class="btn-danger btn-sm"
																	href="<%=request.getContextPath()%>/user/deActivate/${user.id}">
																	<i> DeActivate </i>
																</a>
																<br />
															</c:when>
															<c:otherwise><a class="btn-danger btn-sm"
																	href="<%=request.getContextPath()%>/user/activate/${user.id}">
																	<i> Activate </i>
																</a><br />
															</c:otherwise>
														</c:choose></td> --%>

												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</div>

						</div>
					</div>
					<!-- /.table-responsive -->
				</div>
				<!-- /.panel-body -->
			</div>
			<!-- /.panel -->
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true,
			"ordering" : false,
			stateSave: true
		});
	});
</script>
<jsp:include page="footer.jsp" />
