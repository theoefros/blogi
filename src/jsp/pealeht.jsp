<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="theoefros.blogi.model.Postitus"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="static/css/blogi.css" />
<script type="text/javascript" src="static/js/blogi.js"></script>
<title>BLOGI</title>
</head>
<body>
	
	<jsp:include page="menyy.jsp" />

	<div id="postitused" style="height: 65%; width: 70%; float: left;">
		
		<jsp:include page="teated.jsp" />
		<br />
		<c:forEach var="postitus" items="${postitused}">
			<div id="yksik_postitus">
				<h2><a href="postitus?mode=vaata&postituseNumber=${postitus.postituseNumber}">${postitus.pealkiri}</a></h2>
				<p>${postitus.sisu}</p>
				<p id="kuupaev">Lisatud: ${postitus.kuupaev}</p>
			</div>
		</c:forEach>
	</div>
	
</body>
</html>