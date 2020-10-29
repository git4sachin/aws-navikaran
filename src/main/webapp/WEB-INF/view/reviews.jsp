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
<script type="text/javascript">
	window.onload = function() {

		$("#get-excel").hide();
		var dataPoints = [];
		var chart1 = new CanvasJS.Chart("chartContainer", {
			theme : "light2", // "light1", "dark1", "dark2"
			animationEnabled : true,
			title : {
				text : "AI based code review comments categorization"
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
			$("#get-excel").show();
			$("#wait").hide();

			chart1.render();
		}

		$.ajax({
			type : "GET",
			url : "/restfull-service/reviewCommentsCaregory.json",
			dataType : "json",
			timeout : 15000,
			success : addData,
			error : function(jqXHR, textStatus, errorThrown) {
				$("#wait").hide();
				$("#get-excel").hide();
				alert("Some error on the server, please check the console logs. "
						+ "Status: "
						+ textStatus
						+ " Error: "
						+ errorThrown);
			}
		});
	}
</script>
</head>
<body>
	<div id="chartContainer" style="width: 100%; height: 400px;"></div>
	<p></p>
	<div id="wait" align="center">
		Status: ${message} 
	</div>
	<p></p>
	<div id="get-excel" style="display: none" align="center">
		<form method="POST" action="/getExcel">
			<input type="submit" value="Do you want output excel file?">
		</form>
	</div>
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>

</body>
</html>