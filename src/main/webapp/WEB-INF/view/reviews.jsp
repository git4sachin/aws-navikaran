<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv='cache-control' content='no-cache'>
<meta http-equiv='expires' content='0'>
<meta http-equiv='pragma' content='no-cache'>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script type="text/javascript">
	window.onload = function() {

		$("#get-excel").hide();
		var dataPoints = [];
		var chart1 = new CanvasJS.Chart("chartContainer", {
			theme : "light2", // "light1", "dark1", "dark2"
			animationEnabled : true,
			title : {
				text : "Review comments Categorization"
			},
			axisY : {
				title : "Counts",
				titleFontSize : 24,
				includeZero : true
			},
			data : [ {
				type : "doughnut",
				yValueFormatString : "#,### Counts",
				dataPoints : dataPoints
			} ]
		});
		function addData(data) {
			for (var i = 0; i < data.length; i++) {
				dataPoints.push({
					label : data[i].category,
					y : data[i].count
				});
			}

			window.setTimeout(function() {
				$(".alert").fadeTo(1000, 0).slideUp(1000, function() {
					$("#get-excel").show();
					$(this).remove();
				});
			}, 1000);

			chart1.render();
		}
		
		$.ajax({
			type : "GET",
			url : "/restfull-service/reviewCommentsCaregory.json",
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			timeout : 5000,
			success : addData,
			error : function(jqXHR, textStatus, errorThrown) {
				window.setTimeout(function() {
					$(".alert").fadeTo(300, 0).slideUp(300, function() {
						$(this).remove();
					});
				}, 100);

				$("#get-excel").hide();

				alert("Some error on the server, please check the console logs."+ jqXHR.responseText);
			}
		});
	}	
	
</script>
</head>
<body>
	<div id="chartContainer" style="width: 100%; height: 400px;"></div>
	<p></p>
	<div class="alert alert-danger" align="center">Status: ${message}</div>
	<div id="get-excel" style="display: none" align="center">
		<form method="POST" action="/navikaran/getExcel">
			<input type="submit" value="Do you want output excel file?">
		</form>
	</div>
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

</body>
</html>