<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <h1 align="center">
      <font color="#FF0000">
        <fmt:message key='text.access_denied'/>
      </font>
    </h1>
    <h1 align="center">
      <font color="#FF0000">
        <fmt:message key='text.access_denied_message'/>
      </font>
    </h1>
    <form align="center" action="controller" method="post">
      <input name="command" value="goToStartingPage" type="hidden">
      <input type="submit" value="<fmt:message key='form.to_main_page'/>">
    </form>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>