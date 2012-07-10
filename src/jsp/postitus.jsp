<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="theoefros.blogi.model.Postitus"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	
			<div id="yksik_postitus">
				<h2>${postitus.pealkiri}</h2>
				<p>${postitus.sisu}</p>
				<p id="kuupaev">Lisatud: ${postitus.kuupaev}</p>
				
				<c:if test="${ !empty sessionScope.sessiooniKasutaja}">
					<form name="muuda" action="postitus?mode=muutmisvormile" method="POST">
						<input type="hidden" name="postituseNumber" value="${postitus.postituseNumber}" />
						<input type="submit" value="Muuda postitust" />
					</form>
					
					<form name="kustuta" action="postitus?mode=kustuta" method="POST">
						<input type="hidden" name="postituseNumber" value="${postitus.postituseNumber}" />
						<input type="submit" value="Kustuta postitus" onClick="return kinnitaKustutamine()" />
					</form>
				</c:if>
			</div>
	</div>
	
</body>
</html>