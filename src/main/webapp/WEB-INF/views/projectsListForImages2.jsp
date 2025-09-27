<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<c:choose>
						<c:when test="${not empty projList}">
				Projects List Under <b>${projList.get(0).getNatureOfProject().getName()}</b>
					In <b>${projList.get(0).getDivisions().getDivisionName()}</b>
						</c:when>
					</c:choose>
				</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
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
														style="width: 207px;">Assigned User</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Contact No</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Email</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Amount</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Remarks</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Last Updated On</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">View Images</th>
													<th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Floors</th>
													<!-- <th class="sorting" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-label="Browser: activate to sort column ascending"
														style="width: 207px;">Running / HandOvered</th> -->


												</tr>
											</thead>
											<tbody>

												<c:forEach var="project" items="${projList}"
													varStatus="counter">


													<tr class="gradeA odd" role="row">
														<td class="sorting_1">${counter.count}</td>
														<td>${project.projectName}</td>
														<td>${project.projectDefination}</td>
														<td>${project.assignTo.firstName}
															${project.assignTo.lastName}</td>
														<td>${project.assignTo.phoneNum}</td>
														<td style="word-break: break-all;">${project.assignTo.emailId}</td>
														<td>${project.amount}</td>
														<td style="word-break: break-all;">${project.comments}</td>
														<td>${project.lastUpdated}</td>

														<%-- <td class="center"><a class="btn-primary btn-sm"
														href="<%=request.getContextPath()%>/project/getImagesOfProject/${project.id}">
															<i class="fa">View Images </i>
													</a></td> --%>

														<td class="center"><c:choose>
																<c:when test="${project.imagesPaths ne null }">
																	<a class="btn-primary btn-sm"
																		href="<%=request.getContextPath()%>/project/getImagesOfProject/${project.id}">
																		<i class="fa fa-picture-o" aria-hidden="true"
																		title="View Images"></i>
																	</a>
																</c:when>
																<c:otherwise>No Images Available</c:otherwise>
															</c:choose></td>

														<td><a class="btn-primary btn-sm"
															href="<%=request.getContextPath()%>/projectFLoorStatus/getAllFloors/${project.id}">
																<i class="fa fa-building" aria-hidden="true"
																title="All Floors"></i>
														</a></td>

														<%-- <td class="center"><c:choose>
																<c:when test="${project.isHandovered=='Y'}">
																	<b><i style="colo: green">HandOvered</i></b>
																</c:when>
																<c:otherwise>
																	<b><i style="colo: blue">Running</i></b>
																	<br />
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
			"paging" : true,
			"ordering" : true,
			"info" : false,
			stateSave : true
		});
	});
</script>
<jsp:include page="footer.jsp" />
