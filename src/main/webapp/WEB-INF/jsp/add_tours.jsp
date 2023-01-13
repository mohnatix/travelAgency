<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <c:if test="${currentUser==null||currentUser.role!='administrator'||currentUser.userStatus=='blocked'}">
      <c:redirect url = "controller?command=goToAccessDeniedPage"/>
    </c:if>
    <%@ include file="/WEB-INF/include/name.jspf"%>
    <center>
      <form action="controller" method="post" enctype="multipart/form-data">
        <input name="command" value="addTour" type="hidden">
        <label for="tourName" class="form-label">
          <fmt:message key='label.tour_name'/>:
        </label>
        <input name="tourName" placeholder="<fmt:message key='label.tour_name'/>" >
        <br>
        <br>
        <label for="tourType" class="form-label">
          <fmt:message key='label.tour_type'/>:
        </label>
        <select name="tourType">
          <option value="new">new tour type
          </option>
          <c:forEach items="${tour_types}" var="tourType" varStatus="loop">
            <option value="${tourType.type}" >${tourType.type}
            </option>
          </c:forEach>
          <input name="tourTypeNew" placeholder="<fmt:message key='label.tour_type'/>">
        </select>
        <br>
        <br>
        <label for="numberOfPersons" class="form-label">
          <fmt:message key='label.persons_number'/>:
        </label>
        <input name="numberOfPersons" placeholder="<fmt:message key='label.persons_number'/>">
        <br>
        <br>
        <label for="duration" class="form-label">
          <fmt:message key='label.duration'/>:
        </label>
        <input name="duration" placeholder="<fmt:message key='label.duration'/>" >
        <br>
        <br>
        <label for="accommodation" class="form-label">
          <fmt:message key='label.accommodation'/>:
        </label>
        <select name="accommodation">
          <option value="new">new accommodation type
          </option>
          <c:forEach items="${accommodations}" var="accommodation" varStatus="loop">
            <option value="${accommodation.type}">${accommodation.type}
            </option>
          </c:forEach>
          <input name="accommodationNew" placeholder="<fmt:message key='label.accommodation'/>" >
        </select>
        <br>
        <br>
        <label for="price" class="form-label">
          <fmt:message key='label.price'/>, $:
        </label>
        <input name="price" placeholder="<fmt:message key='label.price'/>" >
        <br>
        <br>
        <label for="imageUpload" class="form-label">
          <fmt:message key='label.image_upload'/>:
        </label>
        <input type="file" name="imageUpload" accept=".jpg, .jpeg, .png" />
        <br>
        <br>
        <label for="tourStatus" class="form-label">
          <fmt:message key='label.tour_status'/>:
        </label>
        <select name="tourStatus">
          <c:forEach items="${tour_statuses}" var="tourStatus" varStatus="loop">
            <option value="${tourStatus.status}">${tourStatus.status}
            </option>
          </c:forEach>
        </select>
        <br>
        <br>
        <input type="submit" value="<fmt:message key='form.submit_save'/>">
      </form>
    </center>
    <form align="center" action="controller" method="post">
      <input name="command" value="goToStartingPage" type="hidden">
      <input type="submit" value="<fmt:message key='form.to_main_page'/>">
    </form>
    <%@ include file="/WEB-INF/include/footer.jspf"%>
  </body>
</html>