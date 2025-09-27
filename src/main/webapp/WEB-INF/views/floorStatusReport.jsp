<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					<b>${floor.floorName}</b> status for <b>${proj.projectDefination}</b>
				</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h5>
							<b>${floor.floorName}</b> status for <b>${proj.projectDefination}</b>
						</h5>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div id="dataTables-example_wrapper"
							class="dataTables_wrapper form-inline dt-bootstrap no-footer">
							<div class="row">
								<div class="col-sm-12">
									<div align="right">
										<%-- <a
											href="<%=request.getContextPath()%>/projectFLoorStatus/exportFloorStatusreport/${proj.id}/${floor.id}"
											class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;">Export
											Excel</a> </br> --%>
										<a href="javascript:window.print();" class="btn"
											style="color: #fff; background-color: #5bc0de; border-color: #46b8da;">Print</a>
										</br>
									</div>
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
													aria-label="Employee Name: activate to sort column descending"
													style="width: 171px;">S.No</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Floor Work</th>
												<th class="sorting" tabindex="0"
													aria-controls="dataTables-example" rowspan="1" colspan="1"
													aria-label="Browser: activate to sort column ascending"
													style="width: 207px;">Work Status</th>

											</tr>
										</thead>
										<tbody>

											<c:forEach var="work" items="${worksList}"
												varStatus="counter">


												<tr class="gradeA odd" role="row">
													<td class="sorting_1">${counter.count}</td>
													<td>${work.workType}</td>

													<td class="center"><c:choose>
															<c:when test="${work.projectStatus eq null }">
																Not Started
															</c:when>
															<c:otherwise>
																${work.projectStatus.status}
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
			"paging": false,
			responsive : true
		});
	});
</script>
<jsp:include page="footer.jsp" />
