<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<jsp:include page="header.jsp" />
<div id="page-wrapper">
	<div class="container-fluid">
		<a href="<%=request.getContextPath()%>/project/hoMap/${divId}"
			class="active"><i style="color: green" class="fa fa-map-marker"
			aria-hidden="true"><b> Map View</b></i> </a>
		<div class="row">
			<div class="col-lg-12">
				<h3 class="page-header">
					Nature Of Works List Under <b>${division.divisionName}</b>
				</h3>
			</div>
			<c:forEach items="${natureList}" var="nature" varStatus="counter">
				<div class="col-lg-4 col-md-6">
					<div class="panel panel-primary">
						<div class="panel-heading">
							<div class="row">
								<div class="col-xs-4 text-left">
									<i class="fa fa-folder-open fa-5x"></i>

									<button class="btn btn-primary btn-xs"
										title="Monthly Budget Report" data-title="Amount"
										data-toggle="modal" data-target="#get-amount"
										data-counter="${counter.count}">
										<span class="glyphicon glyphicon-calendar"
											style="color: yellow"></span>
									</button>

									<button class="btn btn-success btn-xs"
										title="Budget Report with Dates" data-title="Report"
										data-toggle="modal" data-target="#natureBudgetReportWithDates"
										data-counter="${counter.count}">
										<span class="fa fa-file-excel-o" style="color: yellow"></span>
									</button>

									<a class="btn btn-success btn-xs"
										title="Floor Wise Status Report"
										href="<%=request.getContextPath()%>/projectFLoorStatus/allProjectFloorStatusReport/${nature.id}/${divId}">
										<span class="fa fa-building" aria-hidden="true"
										style="color: yellow"></span>
									</a>

									<form:form class="form-inline" method="post">
										<div class="modal fade" id="get-amount" tabindex="-1"
											role="dialog" aria-labelledby="myModalLabel"
											aria-hidden="true">
											<div class="modal-dialog">
												<div class="modal-content">

													<div class="modal-header">
														<button type="button" class="close" data-dismiss="modal">&times;</button>
														<h3 class="modal-title" style="color: green;">
															Monthly Budget Report For <strong></strong> in <b>${division.divisionName}</b>
														</h3>
													</div>

													<div class="modal-body">
														<div class="form-group">

															<input id="natureId-${counter.count}" type="hidden"
																value="${nature.id}"> <input
																id="divId-${counter.count}" type="hidden"
																value="${divId}"> <input
																id="natureName-${counter.count}" type="hidden"
																value="${nature.name}"> <label for="month">
																<h4 style="color: black;">Select Month:</h4>
															</label>
															<div class="input-group">
																<input type="text" value="" class="form-control month"
																	id="month-${counter.count}" required="required"
																	placeholder="Select Month">
															</div>
														</div>

														<div class="input-group">
															<button type="button" class="btn btn-primary btn-ok"
																id="amtBtn">Fetch</button>
														</div>

														<div class="form-group">
															<label for="amount"><h3 style="color: black;">
																	Amount :</h3> </label>
															<div class="input-group">
																<input type="text" class="form-control" id="amount"
																	name="amount" value="" readonly="true"
																	placeholder="Amount">
															</div>
														</div>
													</div>
													<div class="modal-footer">
														<button type="button" class="btn btn-default"
															data-dismiss="modal">Cancel</button>
													</div>
												</div>
											</div>
										</div>
									</form:form>

								</div>
								<div class="col-xs-13 text-left">
									<!-- <div class="huge">0</div> -->
									<div>${nature.name}</div>
									<div style="color: yellow">Total Projects Count=
										${nature.runningProjects + nature.handOveredProjectsCount}</div>
									<div style="color: yellow">Running Projects Count=
										${nature.runningProjects}</div>
									<div style="color: yellow">Images Uploaded Projects=
										${nature.uploadedCount}</div>
									<div style="color: yellow">Images Not Uploaded Projects=
										${nature.notUploadedCount}</div>
									<div class="text-center" style='padding-left: 65px'>
										<b>Total Amount = ${nature.workAmount}</b>
									</div>
									<div class="text-center" style='padding-left: 80px'>
										<b>Current Month Amount = ${nature.montlyWorkAmount}</b>
									</div>
								</div>
							</div>
						</div>
						<a
							href="<%=request.getContextPath()%>/project/getProjectsByNatureAndDivision/${nature.id}/${divId}">
							<div class="panel-footer">
								<span class="pull-left">View Projects</span> <span
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

<link
	href="<%=application.getContextPath()%>/resources/css/MonthPicker.min.css"
	rel="stylesheet" type="text/css" />
<script
	src="<%=application.getContextPath()%>/resources/js/MonthPicker.js"></script>
<script
	src="<%=application.getContextPath()%>/resources/js/MonthPicker.min.js"></script>
<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});
		
	});

	$('#get-amount').on('show.bs.modal', function(e) {

		var counter = $(e.relatedTarget).data('counter');
		//alert("counter=" + counter);
		
		var natureName = $("#natureName-" + counter).val();
		//alert("natureId=" + natureId);
		
		$("strong").text(natureName);
		
		$('.month').MonthPicker({
			Button : false,
			OnAfterChooseMonth: function() {
				var date=$(this).val(); 
		        //alert(date);
		        $("#month-" + counter).val(date);
		        
		    } 
		});
		
		$('#amtBtn').click(function() {
			$(document).ready(function() {
				var natureId = $("#natureId-" + counter).val();
				//alert("natureId=" + natureId);
				var divId = $("#divId-" + counter).val();
				//alert("divId=" + divId);
				var month = $("#month-" + counter).val();
				//alert("month=" + month);
				if(month==""){
					alert('Please Select Month');
					return false;
					}
				$.ajax({
					url : '<%=application.getContextPath()%>
	/project/getMonthlyBudgetForNatureOfWork',
																			dataType : "json",
																			type : "GET",
																			contentType : 'application/json',
																			mimeType : 'application/json',
																			data : {
																				"divId" : divId,
																				"natureId" : natureId,
																				"date" : month
																			},
																			success : function(
																					response) {
																				if (response.responseText != null) {
																					var amnt = $("#amount");
																					var num = (response.responseText)
																							.replace(
																									/["']/g,
																									"");
																					amnt
																							.empty()
																							.append(
																									num);

																				}
																			},
																			error : function(
																					response) {
																				if (response.responseText != null) {
																					var amnt = $("#amount");
																					var num = (response.responseText)
																							.replace(
																									/["']/g,
																									"");
																					amnt
																							.empty()
																							.val(
																									num);

																				}
																			}
																		});

															});
										});
					});

	$('#get-amount').on('hidden.bs.modal', function(e) {
		location.reload();
		$('#get-amount').show();
	})
</script>
<jsp:include page="natureBudgetReportWithDates.jsp" />
<jsp:include page="footer.jsp" />