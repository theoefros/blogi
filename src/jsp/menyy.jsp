<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="header" style="height: 25%; width: 100%">
	<h1 style="text-align: center;">Blogi</h1>
</div>
<div id="menu" style="height: 65% px; width: 20%; float: left;">
	
	<a href="pealeht">Postitused</a>
	<br />
	
	<c:if test="${ !empty sessionScope.sessiooniKasutaja}">
		<a href="postitus?mode=lisamiseVormile">Lisa uus postitus</a>
		<br />
	</c:if>
	
	<c:if test="${ empty sessionScope.sessiooniKasutaja}">
		<a href="login?mode=sisselogimiseVorm">Logi sisse</a>
	<br />
	</c:if>
	<c:if test="${ !empty sessionScope.sessiooniKasutaja}">
		<a href="login?mode=logiValja">Logi välja</a>
	</c:if>

</div>
