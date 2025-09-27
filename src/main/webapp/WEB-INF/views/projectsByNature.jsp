<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="header.jsp" />
<style>
.image-preview-input {
	position: relative;
	overflow: hidden;
	margin: 0px;
	color: #333;
	background-color: #fff;
	border-color: #ccc;
}

.image-preview-input input[type=file] {
	position: absolute;
	top: 0;
	right: 0;
	margin: 0;
	padding: 0;
	font-size: 20px;
	cursor: pointer;
	opacity: 0;
	filter: alpha(opacity = 0);
}

.loader {
	/* position: fixed;
	left: 0px;
	top: 0px; */
	width: 100%;
	height: 70px;
	/* z-index: 9999; */
	background:
		url('${pageContext.request.contextPath}/resources/img/TaPz.gif') 50%
		50% no-repeat rgb(249, 249, 249);
	display: none;
	/* opacity: .8; */
}

.image-preview-input-title {
	margin-left: 2px;
}
</style>

<div id="page-wrapper">
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">Projects List</h3>
				<div class="col-md-6"></div>
			</div>
		</div>
		<form class="form-inline" method="post">
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h5>Projects List</h5>
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
											class="table table-striped table-bordered table-hover dataTable no-footer dtr-inline table-responsive"
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
														aria-label="Employee NameBrowser: activate to sort column descending"
														style="width: 171px;"><input type="checkbox"
														id="checkBoxAll" />Select All</th>
													<th class="sorting_asc" tabindex="0"
														aria-controls="dataTables-example" rowspan="1" colspan="1"
														aria-sort="ascending"
														aria-label="Employee Name: activate to sort column descending"
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
														style="width: 207px;">Location</th>

												</tr>
											</thead>
											<tbody>

												<c:forEach var="project" items="${projList}"
													varStatus="counter">
													<tr class="gradeA odd" role="row">
														<td class="sorting_1">${counter.count}</td>
														<td><c:choose>
																<c:when test="${project.status == 'N'}">
																	<input type="checkbox" id="projId"
																		class="chkCheckBoxId" name="projId"
																		value="${project.id}" checked>
																</c:when>
																<c:otherwise>
																	<input type="checkbox" id="projId"
																		class="chkCheckBoxId" name="projId"
																		value="${project.id}">
																</c:otherwise>
															</c:choose></td>
														<td>${project.projectName}</td>
														<td>${project.projectDefination}</td>
														<td>${project.divisions.divisionName}</td>
														<td>${project.natureOfProject.name}</td>
														<td>${project.projectLocation}</td>

													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="loader"></div>
										<div class="col-sm-offset-4 col-sm-8"
											style="margin-top: 10px;">
											<input type="button" class="btn btn-primary"
												onclick="getSelectedChbox()" value="Assign / Remove">

											<%-- <button type="submit" class="btn btn-danger" formaction="<%=request.getContextPath()%>/Resource/removeMultipleResources/${projId}">Remove</button> --%>
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
var oTable;
	$(document).ready(function() {
		 oTable = $('#dataTables-example').dataTable({
			responsive : true
		});

		var allPages = oTable.fnGetNodes();

		$('body').on('click', '#checkBoxAll', function() {
			if ($(this).hasClass('allChecked')) {
				$('input[type="checkbox"]', allPages).prop('checked', false);
			} else {
				$('input[type="checkbox"]', allPages).prop('checked', true);
			}
			$(this).toggleClass('allChecked');
		})
	});

	function getSelectedChbox() {
		var selected = new Array();
		var user=document.getElementById("userId").value;
		$("input:checkbox[name=projId]:checked",oTable.fnGetNodes()).each(function() {
			selected.push($(this).val());
		});

		$.ajax({
		    type : "POST",
		    url : "<%=request.getContextPath()%>/project/assignNatureAndProjects",
		    data : {
		    	selected: selected,
		    	user: user 
		    },
		    beforeSend: function(){
		        // Show image container
		    	$(".loader").show();
		       },
		    success : function(response) {
			    window.location="<%=request.getContextPath()%>/project/assignProject";

		    },
		    error : function(e) {
		       alert('Please select at least one Project');
		    },
		    complete:function(data){
		        // Hide image container
		    	$(".loader").hide();
		       }
		}); 
				
		
		<%-- window.location="<%=request.getContextPath()%>/project/assignNatureAndProjects/"+selected; --%>
	}
</script>

<jsp:include page="footer.jsp" />
