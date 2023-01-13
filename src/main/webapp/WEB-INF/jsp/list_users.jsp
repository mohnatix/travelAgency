<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <c:if test="${currentUser==null||currentUser.role!='administrator'||currentUser.userStatus=='blocked'}">
      <c:redirect url = "controller?command=goToAccessDeniedPage"/>
    </c:if>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <center>
      <table border='1'>
        <tr>
          <td bgcolor="#00FF00"> Login
          </td>
          <td bgcolor="#00FF00"> E-mail
          </td>
          <td bgcolor="#00FF00"> Name
          </td>
          <td bgcolor="#00FF00"> Surname
          </td>
          <td bgcolor="#00FF00"> Role
          </td>
          <td bgcolor="#00FF00"> Status
          </td>
        </tr>
        <c:forEach var="user" items="${users}">
          <tr>
            <td>${user.login}
            </td>
            <td>${user.email}
            </td>
            <td>${user.name}
            </td>
            <td>${user.surname}
            </td>
            <td>
              ${user.role}
              <c:if test="${user.id != currentUser.id}">
                <br>
                <form action="controller" method="post">
                  <input name="command" value="changeUserRole" type="hidden">
                  <select name="userRoleSelect">
                    <c:forEach items="${roles}" var="role" varStatus="loop">
                      <c:set var="selected" value="${role.name == user.role ? 'selected' : '' }"/>
                      <option value="${role.name}" ${selected}>${role.name}
                      </option>
                    </c:forEach>
                  </select>
                  <br>
                  <input type="hidden" name="userId" value="${user.id}">
                  <input type="submit" value="<fmt:message key='form.submit_save'/>">
                </form>
              </c:if>
            </td>
            <td>
              <c:choose>
                <c:when test="${user.id == currentUser.id}">
                  ${user.userStatus}
                </c:when>
                <c:when test="${user.userStatus == 'unblocked'}">
                  <form action="controller" method="post">
                    <input name="command" value="blockUser" type="hidden">
                    <input type="hidden" name="userId" value="${user.id}">
                    <input type="submit" value="<fmt:message key='form.submit_block'/>">
                  </form>
                </c:when>
                <c:when test="${user.userStatus == 'blocked'}">
                  <form action="controller" method="post">
                    <input name="command" value="unblockUser" type="hidden">
                    <input type="hidden" name="userId" value="${user.id}">
                    <input type="submit" value="<fmt:message key='form.submit_unblock'/>">
                  </form>
                </c:when>
              </c:choose>
            </td>
          </tr>
        </c:forEach>
      </table>
    </center>
    <br>
    <form align="center" action="controller" method="post">
      <input name="command" value="goToStartingPage" type="hidden">
      <input type="submit" value="<fmt:message key='form.to_main_page'/>">
    </form>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>