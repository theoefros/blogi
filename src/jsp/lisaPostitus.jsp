<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="static/js/blogi.js"></script>
<title>Lisa uus postitus</title>
</head>
<body>
	<jsp:include page="menyy.jsp" />
	
	<div id="postitused" style="height: 65%; width: 70%; float: left;">
		<form action='postitus?mode=lisaUus' method=POST>
			Pealkiri: <input type="text" name="pealkiri" id="pealkiri"/>
			<br />
			Sisu:
			<br />
			<textarea name="sisu" rows="30" cols="50"></textarea>
			<input type=submit value="Lisa" onClick="return kontrolliPealkirja()" >
		</form>
	</div>

</body>
</html>