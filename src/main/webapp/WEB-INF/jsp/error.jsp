<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <c:choose>
      <c:when test="${ex.message == null}">
        <h1 align="center">
          <font color="#FF0000">${ex}
          </font>
        </h1>
      </c:when>
      <c:otherwise>
        <h1 align="center">
          <font color="#FF0000">${ex.message}
          </font>
        </h1>
      </c:otherwise>
    </c:choose>
    <form align="center" action="controller" method="post">
      <input name="command" value="goToStartingPage" type="hidden">
      <input type="submit" value="<fmt:message key='form.to_main_page'/>">
    </form>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>