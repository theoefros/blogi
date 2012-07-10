<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logi sisse</title>
</head>
<body>
		<jsp:include page="menyy.jsp" />
		<div style="height: 65%; width: 70%; float: left;">
		
		<jsp:include page="teated.jsp" />
		<br />		
		
		<form action='login?mode=logiSisse' method=POST>
			Kasutajanimi: <input type="text" name="kasutajanimi"/>
			<br />
			Parool:
			<input type="password" name="parool"/>
			<br />
			<input type=submit value="Logi sisse">
		</form>
	</div>
</body>
</html>