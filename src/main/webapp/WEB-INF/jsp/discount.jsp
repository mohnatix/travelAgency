<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <c:if test="${currentUser==null||currentUser.role=='customer'||currentUser.userStatus=='blocked'}">
      <c:redirect url = "controller?command=goToAccessDeniedPage"/>
    </c:if>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <h3 align="center">Note: max value of discount should be multiple of the step (step is integer)
    </h3>
    <center>
      <table border='1'>
        <tr>
          <td> Name
          </td>
          <td> Step, %
          </td>
          <td> Max, %
          </td>
          <td> Save
          </td>
        </tr>
        <c:forEach items="${discounts}" var="discount">
          <tr>
            <form action="controller" method="post">
              <input name="command" value="changeDiscount" type="hidden">
              <input type="hidden" name="discountId" value="${discount.id}">
              <td> ${discount.name}
              </td>
              <td> ${discount.step}
                <br>
                <input name="discountStep" placeholder=${discount.step} size="3" >
              </td>
              <td>  ${discount.max}
                <br>
                <input name="discountMax" placeholder=${discount.max} size="3" >
              </td>
              <td>
                <input type="submit" value="<fmt:message key='form.submit_save'/>">
              </td>
            </form>
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