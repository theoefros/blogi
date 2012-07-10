<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${ !empty teade}">
	<div id="teated" style="color:${teade.tekstiVarv}; height: 20% ; width: 70%; float: left;">
		${teade.tekst}
	</div>
</c:if>



