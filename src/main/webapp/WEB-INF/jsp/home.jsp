<%@ include file="/WEB-INF/include/head.jspf"%>
<%@ taglib prefix="mylib" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="mylib2" uri="http://com.my.tags/mylib2" %>

<html>
  <head>
    <title>Home page
    </title>
  </head>
  <body>
    <form align="left" action="controller" method="post">
      <input name="command" value="changeLocale" type="hidden">
      <fmt:message key="label.set_locale"/>:
      <select name="locale">
        <c:forEach items="${applicationScope.locales}" var="locale">
          <c:set var="selected" value="${locale.key == currentLocale ? 'selected' : '' }"/>
          <option value="${locale.key}" ${selected}>${locale.value}
          </option>
        </c:forEach>
      </select>
      <input type="submit" value="<fmt:message key='form.submit_save'/>">
    </form>
    <c:if test="${currentUser == null}">
      <form align="left" action="controller" method="get">
        <input name="command" value="goToLoginPage" type="hidden">
        <input type="submit" value="<fmt:message key='form.submit_login'/>">
      </form>
      <form align="left" action="controller" method="get">
        <input name="command" value="goToRegistrationPage" type="hidden">
        <input type="submit" value="<fmt:message key='form.submit_register'/>">
      </form>
    </c:if>
    <c:if test="${currentUser != null}">
      <form align="left" action="controller" method="post">
        <input name="command" value="pressLogout" type="hidden">
        <input type="submit" value="<fmt:message key='form.submit_logout'/>">
      </form>
      <h3 align="left">
        <fmt:message key='text.greetings'/>, ${currentUser.login}!
      </h3>
    </c:if>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <c:if test="${currentUser != null}">
      <center>
        <table border='1'>
          <tr>
            <td>
              <a href="controller?command=showUserInfo">
                <fmt:message key='link.my_info'/>
              </a>
            </td>
            <td>
              <a href="controller?command=showMyOrders">
                <fmt:message key='link.my_orders'/>
              </a>
            </td>
            <c:if test="${currentUser.role != 'customer'}">
              <td>
                <a href="controller?command=showListOfOrders">
                  <fmt:message key='link.list_of_orders'/>
                </a>
              </td>
              <td>
                <a href="controller?command=showDiscounts">
                  <fmt:message key='link.discount_management'/>
                </a>
              </td>
              <c:if test="${currentUser.role != 'manager'}">
                <td>
                  <a href="controller?command=showListOfUsers">
                    <fmt:message key='link.list_of_users'/>
                  </a>
                </td>
                <td>
                  <a href="controller?command=showAddToursPage">
                    <fmt:message key='link.add_tours'/>
                  </a>
                </td>
              </c:if>
            </c:if>
          </tr>
        </table>
      </center>
    </c:if>
    <br>
    <jsp:include page="list_tours.jsp"/>
    <br>
    <br>
    <mylib:quote text="The Answer to the Great Question... Of Life, the Universe and Everything... Is... Forty-two."/>
    <br>
    <br>
    <mylib2:citation text="A towel, is about the most massively useful thing an interstellar hitchhiker can have."/>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>