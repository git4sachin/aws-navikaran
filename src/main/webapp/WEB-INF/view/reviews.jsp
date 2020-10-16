<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript">
	window.onload = function() {
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
			chart1.render();
		}

		$.getJSON("/restfull-service/reviewCommentsCaregory.json",
				addData);
	}
</script>
</head>
<body>
	Status: ${message}
	<form method="POST" action="/getExcel">
		<input type="submit" value="Do you want output excel file?">
	</form>
	<div id="chartContainer" style="width: 100%; height: 400px;"></div>

	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
</body>
</html>
