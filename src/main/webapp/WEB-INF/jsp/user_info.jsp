<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <c:if test="${currentUser==null||currentUser.userStatus=='blocked'}">
      <c:redirect url = "controller?command=goToAccessDeniedPage"/>
    </c:if>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <h3 align="center">
      <fmt:message key='placeholder.login'/>: ${currentUser.login}
    </h3>
    <h3 align="center">
      <fmt:message key='placeholder.email'/>: ${currentUser.email}
    </h3>
    <h3 align="center">
      <fmt:message key='placeholder.name'/>: ${currentUser.name}
    </h3>
    <h3 align="center">
      <fmt:message key='placeholder.surname'/>: ${currentUser.surname}
    </h3>
    <h3 align="center">
      <fmt:message key='text.role'/>:${currentUser.role}
    </h3>
    <form align="center" action="controller" method="post">
      <input name="command" value="goToStartingPage" type="hidden">
      <input type="submit" value="<fmt:message key='form.to_main_page'/>">
    </form>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>
