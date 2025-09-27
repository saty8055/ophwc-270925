<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Projects List</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>Projects List</h5>
					</div>
					<div class=" pull-right">
						<!--<a href="#" class="btn btn-success btn-sm"><i class="fa fa-user-plus pull-right"> Add Employee</i></a>-->
						<a class="btn-info btn"
							href="<%=request.getContextPath()%>/project/projectCreation">
							<i class="fa fa-user-plus"> </i> Add Project
						</a>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<div class="table-responsive">
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
														style="width: 171px;">Project Name</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Project Definition</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Division</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">NatureOfWork</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Latitude</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Longitude</th>

													<th style="width: 80px;">Budget Info</th>
													<th style="width: 70px;">Edit</th>
													<th style="width: 80px;">Delete</th>
													<th style="width: 80px;">HandOver</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="project" items="${projList}"
													varStatus="counter">


													<tr class="gradeA odd" role="row">
														<td class="sorting_1">${counter.count}</td>
														<td>${project.projectName}</td>
														<td>${project.projectDefination}</td>
														<td>${project.divisions.divisionName}</td>
														<td>${project.natureOfProject.name}</td>
														<td>${project.latitude}</td>
														<td>${project.langitude}</td>
														<td class="center"><a class="btn-success btn-sm"
															href="<%=request.getContextPath()%>/project/getBudgetDetails/${project.id}">
																<i class="fa fa-rupee"></i>
														</a></td>
														<td class="center"><a class="btn-info btn-sm"
															href="<%=request.getContextPath()%>/project/editProject/${project.id}">
																<i class="fa fa-edit"> </i>
														</a></td>
														<td class="center"><a class="btn-danger btn-sm"
															href="<%=request.getContextPath()%>/project/deleteProject/${project.id}"
															onclick="return confirm('Are you sure you want to Delete?');">
																<i class="fa fa-trash"></i>
														</a></td>
														<td class="center"><c:choose>
																<c:when test="${project.isHandovered=='Y'}">
																	<b><i style="colo: green">HandOvered</i></b>
																</c:when>
																<c:otherwise>
																	<a class="btn-danger btn-sm"
																		href="<%=request.getContextPath()%>/project/handOverProject/${project.id}"
																		onclick="return confirm('Are you sure you want to HandOver this Project?');">
																		<i> HandOver </i>
																	</a>
																	<br />

																</c:otherwise>
															</c:choose></td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
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
			stateSave : true
		});
	});
</script>
<jsp:include page="footer.jsp" />
