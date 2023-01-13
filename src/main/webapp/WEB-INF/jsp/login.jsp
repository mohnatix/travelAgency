<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <center>
      <form action="controller" method="post">
        <input name="command" value="login" type="hidden">
        <label for="login" class="form-label">
          <fmt:message key='label.login'/>:
        </label>
        <br>
        <br>
        <input name="login" placeholder="<fmt:message key='placeholder.login'/>" >
        <br>
        <br>
        <label for="password" class="form-label">
          <fmt:message key='label.password'/>:
        </label>
        <br>
        <br>
        <input name="password" placeholder="<fmt:message key='placeholder.password'/>" type="password">
        <br>
        <br>
        <input type="submit" value="<fmt:message key='form.submit_login'/>">
      </form>
    </center>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>