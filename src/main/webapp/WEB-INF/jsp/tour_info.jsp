<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <center>
      <img  src="images/${tour.imageName}" height='70%' width='50%'>
    </center>
    <h3 align="center">
      <fmt:message key='label.tour_name'/>: ${tour.name}
    </h3>
    <h3 align="center">
      <fmt:message key='label.tour_type'/>: ${tour.tourType}
    </h3>
    <h3 align="center">
      <fmt:message key='label.persons_number'/>: ${tour.personsNumber}
    </h3>
    <h3 align="center">
      <fmt:message key='label.duration'/>: ${tour.duration}
    </h3>
    <h3 align="center">
      <fmt:message key='label.accommodation'/>: ${tour.accommodation}
    </h3>
    <h3 align="center">
      <fmt:message key='label.price'/>, $: ${tour.price}
    </h3>
    <h3 align="center">
      <fmt:message key='label.tour_status'/>: ${tour.tourStatus}
    </h3>
    <form align="center" action="controller" method="post">
      <input name="command" value="goToStartingPage" type="hidden">
      <input type="submit" value="<fmt:message key='form.to_main_page'/>">
    </form>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>