<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Nature Of Project List</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form class="form-inline" method="post">
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>Nature Of Projects</h5>
						</div>

						<!-- /.panel-heading -->
						<div class="panel-body">
							<div id="dataTables-example_wrapper"
								class="dataTables_wrapper form-inline dt-bootstrap no-footer">
								<div class="row">
									<div class="col-sm-12">
										<input type="hidden" value="${userId }" name="userId"
											id="userId">
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
														style="width: 171px;"><input type="checkbox"
														id="checkBoxAll" />Select All</th>
													<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;">S.No</th>
													<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
														style="width: 171px;">Nature Of Work</th>


												</tr>
											</thead>
											<tbody>

												<c:forEach var="nature" items="${natureList}">

													<tr class="gradeA odd" role="row">
														<td><c:choose>
																<c:when test="${nature.status == 'N'}">
																	<input type="checkbox" id="natureId"
																		class="chkCheckBoxId" name="natureId"
																		value="${nature.id}" checked>
																</c:when>
																<c:otherwise>
																	<input type="checkbox" id="natureId"
																		class="chkCheckBoxId" name="natureId"
																		value="${nature.id}">
																</c:otherwise>
															</c:choose></td>
														<td class="sorting_1">${nature.id}</td>
														<td class="sorting_1">${nature.name}</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="col-sm-offset-4 col-sm-8"
											style="margin-top: 10px;">
											<button type="submit" class="btn btn-primary"
												formaction="<%=request.getContextPath()%>/project/getProjectsByNature">
												Next</button>
											<button type="submit" class="btn btn-danger"
												formaction="<%=request.getContextPath()%>/project/removeAllUserAssignments"
												onclick="return confirm('Are you sure you want to Remove?');">Remove</button>
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
		</form>
		<!-- /.col-lg-12 -->
	</div>
	<!-- ... Your content goes here ... -->

</div>
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
	});
</script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#checkBoxAll').click(function() {
			if ($(this).is(":checked"))
				$('.chkCheckBoxId').prop('checked', true);
			else
				$('.chkCheckBoxId').prop('checked', false);

		});
	});
</script>
<jsp:include page="footer.jsp" />
