
<div class="modal fade" id="budgetReportWithDates" tabindex="-1"
	role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">

			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h3 class="modal-title" style="color: green;">
					Budget Report For <strong></strong>
				</h3>
			</div>

			<div class="modal-body">
				<div class="panel-body">
					<form class="form-inline" method="get" id="report_form" action="">
						<div class="form-group">

							<label for="startDate">From Date </label>
							<div class="input-group">
								<input type="text" class="form-control startdate" id="startdate"
									value="" required="required" name="startdate"
									placeholder="From Date">
							</div>
						</div>
						<div class="form-group">
							<label for="endDate">To Date </label>
							<div class="input-group">
								<input type="text" class="form-control enddate" id="enddate"
									value="" required="required" name="enddate"
									placeholder="To Date">
							</div>
						</div>

						<input id="divId-${counter.count}" type="hidden"
							class="form-control" value="${division.id}">

						<div class="input-group" style="align-content: center;">
							<button type="button" class="btn btn-primary btn-ok" id="rptBtn">Fetch</button>
						</div>

					</form>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
			</div>
		</div>
	</div>
</div>

<script>
	$(document).ready(function() {
		$('#dataTables-example').DataTable({
			responsive : true
		});

	});

	$('#budgetReportWithDates')
			.on(
					'show.bs.modal',
					function(e) {

						var counter = $(e.relatedTarget).data('counter');
						//alert("counter=" + counter);

						var divName = $("#divName-" + counter).val();
						//alert("divName=" + divName);

						$("strong").text(divName);

						$(".startdate").datepicker(
								{
									// dateFormat: "yy-M-dd",
									dateFormat : "dd-M-yy",
									maxDate : new Date(),
									onSelect : function(date) {
										var date2 = $('.startdate').datepicker(
												'getDate');
										$('.enddate').datepicker('option',
												'minDate', date2);
										$('.enddate').datepicker('option',
												'maxDate', new Date());
									},
									onSelect : function() {
										var fDate = $(this).val();
										//alert(counter);
										$("#startdate-" + counter).val(fDate);
									}
								});
						$('.enddate')
								.datepicker(
										{
											dateFormat : "dd-M-yy",
											//dateFormat: "yy-M-dd",
											maxDate : new Date(),
											minDate : $('.startdate')
													.datepicker('getDate'),
											onClose : function() {
												var dt1 = $('.startdate')
														.datepicker('getDate');
												var dt2 = $('.enddate')
														.datepicker('getDate');
												//check to prevent a user from entering a date below date of dt1
												if (dt2 < dt1) {
													$('.enddate')
															.datepicker(
																	'setDate',
																	$(
																			'.startdate')
																			.datepicker(
																					'option',
																					'minDate'));
												}
											},
											onSelect : function() {
												var tDate = $(this).val();
												//alert(tDate);
												$("#enddate-" + counter).val(
														tDate);
											}
										});

						$('#rptBtn').click(function() {
							$(document).ready(function() {
								var divId = $("#divId-" + counter).val();
								var sDate = $("#startdate").val();
								var eDate = $("#enddate").val();
								if(sDate==""){
									alert("Please Select From Date");
									return false;
									}
								if(eDate==""){
									alert("Please Select To Date");
									return false;
									}
								
								window.location.href="<%=request.getContextPath()%>/project/generatedBudgetReportWithDates?divId="
																		+ divId
																		+ "&startdate="
																		+ sDate
																		+ "&enddate="
																		+ eDate;
															});
										});

					});

	$('#budgetReportWithDates').on('hidden.bs.modal', function(e) {
		location.reload();
		$('#budgetReportWithDates').show();
	})
</script>