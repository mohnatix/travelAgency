<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
  <%@ include file="/WEB-INF/include/name.jspf"%>
    <center>
      <form action="controller" method="post">
        <input name="command" value="register" type="hidden">
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
        <label for="passwordRepeat" class="form-label">
          <fmt:message key='label.password_repeat'/>:
        </label>
        <br>
        <br>
        <input name="passwordRepeat" placeholder="<fmt:message key='placeholder.password'/>" type="password">
        <br>
        <br>
        <label for="email" class="form-label">
          <fmt:message key='label.email'/>:
        </label>
        <br>
        <br>
        <input name="email" placeholder="<fmt:message key='placeholder.email'/>" >
        <br>
        <br>
        <label for="name" class="form-label">
          <fmt:message key='label.name'/>:
        </label>
        <br>
        <br>
        <input name="name" placeholder="<fmt:message key='placeholder.name'/>" >
        <br>
        <br>
        <label for="surname" class="form-label">
          <fmt:message key='label.surname'/>:
        </label>
        <br>
        <br>
        <input name="surname" placeholder="<fmt:message key='placeholder.surname'/>" >
        <br>
        <br>
        <input type="submit" value="<fmt:message key='form.submit_register'/>">
      </form>
    </center>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>