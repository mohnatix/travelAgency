<%@ include file="/WEB-INF/include/head.jspf"%>

<html>
  <body>
    <center>
      <table border='1'>
        <tr>
          <form action="controller" method="post">
            <input name="command" value="goToStartingPage" type="hidden">
            <input type="hidden" name="page" value="1">
            <td>Tour name
            </td>
            <td>
              <select name="tourTypeSearch">
                <option value="all">All types
                </option>
                <c:forEach items="${tour_types}" var="tourType" varStatus="loop">
                  <c:set var="selected" value="${tourType.type == currentTourTypeSearch ? 'selected' : '' }"/>
                  <option value="${tourType.type}" ${selected}>${tourType.type}
                  </option>
                </c:forEach>
              </select>
            </td>
            <td>
              <select name="personsNumberSearch">
                <c:set var="selected1" value="${'DESC' == currentPersonsNumberSearch ? 'selected' : '' }"/>
                <c:set var="selected2" value="${'ASC' == currentPersonsNumberSearch ? 'selected' : '' }"/>
                <option value="NONE">Number of persons
                </option>
                <option value="DESC" ${selected1}>Number of persons/DESC
                </option>
                <option value="ASC" ${selected2}>Number of persons/ASC
                </option>
              </select>
            </td>
            <td>
              <select name="durationSearch">
                <c:set var="selected1" value="${'DESC' == currentDurationSearch ? 'selected' : '' }"/>
                <c:set var="selected2" value="${'ASC' == currentDurationSearch ? 'selected' : '' }"/>
                <option value="NONE">Duration
                </option>
                <option value="DESC" ${selected1}>Duration/DESC
                </option>
                <option value="ASC" ${selected2}>Duration/ASC
                </option>
              </select>
            </td>
            <td>
              <select name="accommodationSearch">
                <option value="all">All accommodations
                </option>
                <c:forEach items="${accommodations}" var="accommodation" varStatus="loop">
                  <c:set var="selected" value="${accommodation.type == currentAccommodationSearch ? 'selected' : '' }"/>
                  <option value="${accommodation.type}" ${selected}>${accommodation.type}
                  </option>
                </c:forEach>
              </select>
            </td>
            <td>
              <select name="priceSearch">
                <c:set var="selected1" value="${'DESC' == currentPriceSearch ? 'selected' : '' }"/>
                <c:set var="selected2" value="${'ASC' == currentPriceSearch ? 'selected' : '' }"/>
                <option value="NONE">Price,$
                </option>
                <option value="DESC" ${selected1}>Price,$/DESC
                </option>
                <option value="ASC" ${selected2}>Price,$/ASC
                </option>
              </select>
            </td>
            <td>
              <input type="submit" value="<fmt:message key='form.submit_search'/>">
            </td>
            <td>Status
            </td>
            <c:if test="${currentUser != null}">
              <td>Book
              </td>
            </c:if>
            <c:if test="${currentUser != null&&currentUser.role=='administrator'}">
              <td>Delete
              </td>
            </c:if>
          </form>
        </tr>
        <c:forEach var="tour" items="${tours}">
          <tr>
            <td>${tour.name}
            </td>
            <td> ${tour.tourType}
            </td>
            <td>${tour.personsNumber} persons
            </td>
            <td>${tour.duration} days
            </td>
            <td>${tour.accommodation}
            </td>
            <td>${tour.price}
              <c:if test="${currentUser != null&&currentUser.role=='administrator'}">
                <br>
                <form action="controller" method="post">
                  <input name="command" value="changePrice" type="hidden">
                  <input name="price" placeholder=${tour.price} size="10" >
                  <input type="hidden" name="tourId" value="${tour.id}">
                  <input type="submit" value="<fmt:message key='form.submit_save'/>">
                </form>
              </c:if>
            </td>
            <td>
              <img src="images/${tour.imageName}" height=300 width=400>
            </td>
            <td>
              ${tour.tourStatus}
              <c:if test="${currentUser != null&&currentUser.role!='customer'}">
                <br>
                <form action="controller" method="post">
                  <input name="command" value="changeTourStatus" type="hidden">
                  <select name="tourStatusSelect">
                    <c:forEach items="${tour_statuses}" var="tourStatus" varStatus="loop">
                      <c:set var="selected" value="${tourStatus.status == tour.tourStatus ? 'selected' : '' }"/>
                      <option value="${tourStatus.status}" ${selected}>${tourStatus.status}
                      </option>
                    </c:forEach>
                  </select>
                  <input type="hidden" name="tourId" value="${tour.id}">
                  <input type="submit" value="<fmt:message key='form.submit_save'/>">
                </form>
              </c:if>
            </td>
            <c:if test="${currentUser != null}">
              <c:if test="${tour.tourStatus!='archived'}">
                <td>
                  <form action="controller" method="post">
                    <input name="command" value="book" type="hidden">
                    <input type="hidden" name="tourId" value="${tour.id}">
                    <input type="submit" value="<fmt:message key='form.submit_book'/>">
                  </form>
                </td>
              </c:if>
              <c:if test="${tour.tourStatus=='archived'}">
                <td>
                </td>
              </c:if>
            </c:if>
            <c:if test="${currentUser != null&&currentUser.role=='administrator'}">
              <td>
                <form action="controller" method="post">
                  <input name="command" value="pressDelete" type="hidden">
                  <input type="hidden" name="tourId" value="${tour.id}">
                  <input type="submit" value="<fmt:message key='form.submit_delete'/>">
                </form>
              </td>
            </c:if>
          </tr>
        </c:forEach>
      </table>
    </center>
    <form action="controller" method="post">
      <input name="command" value="changePageSize" type="hidden">
      <label for="pageSize" class="form-label">
        <fmt:message key='label.page_size'/>:
      </label>
      <select name="pageSize">
        <c:set var="selected1" value="${5 == currentPageSize ? 'selected' : '' }"/>
        <c:set var="selected2" value="${10 == currentPageSize ? 'selected' : '' }"/>
        <c:set var="selected3" value="${15 == currentPageSize ? 'selected' : '' }"/>
        <option value="5" ${selected1}>5
        </option>
        <option value="10" ${selected2}>10
        </option>
        <option value="15" ${selected3}>15
        </option>
      </select>
      <input type="hidden" name="page" value="1">
      <input type="submit" value="<fmt:message key='form.submit_save'/>">
      <label for="pageSize" class="form-label">
        <fmt:message key='label.page'/> ${currentPage}
        <fmt:message key='label.max_page'/> ${pageCount}
      </label>
    </form>
    <c:choose>
      <c:when test="${currentPage - 1 > 0}">
        <a href="controller?command=goToStartingPage&page=${currentPage-1}">
          <fmt:message key='label.previous'/>
        </a>
      </c:when>
      <c:otherwise>
        <fmt:message key='label.previous'/>
      </c:otherwise>
    </c:choose>
    <c:forEach var="p" begin="${1}" end="${pageCount}">
      <c:choose>
        <c:when test="${currentPage == p}">${p}
        </c:when>
        <c:otherwise>
          <a href="controller?command=goToStartingPage&page=${p}">${p}
          </a>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <c:choose>
      <c:when test="${currentPage + 1 <= pageCount}">
        <a href="controller?command=goToStartingPage&page=${currentPage+1}">
          <fmt:message key='label.next'/>
        </a>
      </c:when>
      <c:otherwise>
        <fmt:message key='label.next'/>
      </c:otherwise>
    </c:choose>
  </body>
</html>