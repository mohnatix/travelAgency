<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <c:if test="${currentUser==null||currentUser.userStatus=='blocked'}">
      <c:redirect url = "controller?command=goToAccessDeniedPage"/>
    </c:if>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <center>
      <table border='1'>
        <tr>
          <td bgcolor="#00FF00"> Order id
          </td>
          <td bgcolor="#00FF00"> Tour name
          </td>
          <td bgcolor="#00FF00"> Price, $
          </td>
          <td bgcolor="#00FF00"> Discount, %
          </td>
          <td bgcolor="#00FF00"> Resulting price, $
          </td>
          <td bgcolor="#00FF00"> Order status
          </td>
        </tr>
        <c:forEach var="userOrder" items="${myOrders}">
          <tr>
            <td>${userOrder.id}
            </td>
            <td>
              <a href="controller?command=showTourInfo&tourId=${userOrder.tourId}">${userOrder.tourName}
              </a>
            </td>
            <td>${userOrder.priceFixed}
            </td>
            <td>${userOrder.discountFixed}
            </td>
            <td>${userOrder.priceFixed*(100-userOrder.discountFixed)/100}
            </td>
            <td>${userOrder.orderStatus}
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