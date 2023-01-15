package com.my.command;

import static org.junit.Assert.*;

import com.my.AppException;
import com.my.db.DBException;
import com.my.db.dto.TourDTO;
import com.my.db.dto.UserDTO;
import com.my.db.dto.UserOrderDTO;
import com.my.db.entity.*;
import com.my.logic.Service;
import org.junit.*;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CommandTest extends Mockito {
    MockedStatic<Service> serviceMockedStatic;

    @Before
    public void setUp() {
        serviceMockedStatic = mockStatic(Service.class);
    }

    @Test
    public void testUnblockUser() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new UnblockUserCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("manager");
        assertEquals("controller?command=goToAccessDeniedPage", new UnblockUserCommand().execute(req, resp));
        when(user.getRole()).thenReturn("administrator");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new UnblockUserCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("userId")).thenReturn("13");
        when(user.getId()).thenReturn(13);
        assertEquals("controller?command=goToAccessDeniedPage", new UnblockUserCommand().execute(req, resp));
        when(user.getId()).thenReturn(10);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).unblockUser(13);
        assertEquals("controller?command=showListOfUsers", new UnblockUserCommand().execute(req, resp));
    }

    @Test
    public void testBlockUser() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new BlockUserCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("manager");
        assertEquals("controller?command=goToAccessDeniedPage", new BlockUserCommand().execute(req, resp));
        when(user.getRole()).thenReturn("administrator");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new BlockUserCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("userId")).thenReturn("13");
        when(user.getId()).thenReturn(13);
        assertEquals("controller?command=goToAccessDeniedPage", new BlockUserCommand().execute(req, resp));
        when(user.getId()).thenReturn(10);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).blockUser(13);
        assertEquals("controller?command=showListOfUsers", new BlockUserCommand().execute(req, resp));
    }

    @Test
    public void testChangeUserRole() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeUserRoleCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("manager");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeUserRoleCommand().execute(req, resp));
        when(user.getRole()).thenReturn("administrator");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeUserRoleCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("userRoleSelect")).thenReturn("manager");
        when(req.getParameter("userId")).thenReturn("13");
        when(user.getId()).thenReturn(13);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeUserRoleCommand().execute(req, resp));
        when(user.getId()).thenReturn(10);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).changeUserRole(10, "manager");
        assertEquals("controller?command=showListOfUsers", new ChangeUserRoleCommand().execute(req, resp));
    }

    @Test
    public void testChangePrice() throws DBException, AppException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangePriceCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("manager");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangePriceCommand().execute(req, resp));
        when(user.getRole()).thenReturn("administrator");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangePriceCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("tourId")).thenReturn("13");
        when(req.getParameter("price")).thenReturn("200,0");
        assertThrows(AppException.class, () -> new ChangePriceCommand().execute(req, resp));
        when(req.getParameter("price")).thenReturn("200.0");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).changePrice(13, 200.0);
        assertEquals("controller?command=goToStartingPage", new ChangePriceCommand().execute(req, resp));
    }

    @Test
    public void testDeleteTour() throws DBException, AppException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new DeleteTourCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("manager");
        assertEquals("controller?command=goToAccessDeniedPage", new DeleteTourCommand().execute(req, resp));
        when(user.getRole()).thenReturn("administrator");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new DeleteTourCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("tourId")).thenReturn("13");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).deleteTour(13);
        assertEquals("controller?command=goToStartingPage", new DeleteTourCommand().execute(req, resp));
    }

    @Test
    public void testAddTour() throws DBException, AppException, ServletException, IOException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new AddTourCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("manager");
        assertEquals("controller?command=goToAccessDeniedPage", new AddTourCommand().execute(req, resp));
        when(user.getRole()).thenReturn("administrator");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new AddTourCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        Part filePart = mock(Part.class);
        when(req.getPart("imageUpload")).thenReturn(filePart);
        when(filePart.getSubmittedFileName()).thenReturn("image.jpg");
        InputStream is = mock(InputStream.class);
        ServletContext sc = mock(ServletContext.class);
        when(req.getServletContext()).thenReturn(sc);
        when(sc.getRealPath("/images")).thenReturn("realpath/images");
        when(sc.getAttribute("imagesAddress")).thenReturn("C:Users/User/images");
        when(req.getParameter("tourName")).thenReturn("some tour");
        when(req.getParameter("tourType")).thenReturn("new");
        when(req.getParameter("tourTypeNew")).thenReturn("wine");
        when(req.getParameter("numberOfPersons")).thenReturn("3");
        when(req.getParameter("duration")).thenReturn("7");
        when(req.getParameter("accommodation")).thenReturn("hostel");
        when(req.getParameter("accommodationNew")).thenReturn("");
        when(req.getParameter("price")).thenReturn("500,55");
        assertThrows(AppException.class, () -> new AddTourCommand().execute(req, resp));
        when(req.getParameter("price")).thenReturn("500.00");
        when(req.getParameter("tourStatus")).thenReturn("hot");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        when(instance.isExistingTourImageName("image.jpg")).thenReturn(true).thenReturn(false);
        MockedStatic<Files> filesMockedStatic = mockStatic(Files.class);
        filesMockedStatic.when(() -> Files.copy(is, Paths.get("C:Users/User/images/0image.jpg"),
                StandardCopyOption.REPLACE_EXISTING)).thenReturn(1L);
        filesMockedStatic.when(() -> Files.copy(Paths.get("C:Users/User/images/0image.jpg"),
                Paths.get("realpath/images/0image.jpg"),
                StandardCopyOption.REPLACE_EXISTING)).thenReturn(Paths.get("realpath/images/0image.jpg"));
        doNothing().when(instance).addTour("some tour", "new", "wine", 3, 7,
                "hostel", "", 500.00, "hot", "0image.jpg");
        assertEquals("controller?command=goToStartingPage", new AddTourCommand().execute(req, resp));
        filesMockedStatic.close();
    }

    @Test
    public void testChangeDiscount() throws DBException, AppException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeDiscountCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("customer");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeDiscountCommand().execute(req, resp));
        when(user.getRole()).thenReturn("manager");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeDiscountCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("discountId")).thenReturn("1");
        when(req.getParameter("discountStep")).thenReturn("2");
        when(req.getParameter("discountMax")).thenReturn("11.55");
        assertThrows(AppException.class, () -> new ChangeDiscountCommand().execute(req, resp));
        when(req.getParameter("discountMax")).thenReturn("12");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).changeDiscount(1, 2, 12);
        assertEquals("controller?command=showDiscounts", new ChangeDiscountCommand().execute(req, resp));
    }

    @Test
    public void testChangeTourStatus() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeTourStatusCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("customer");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeTourStatusCommand().execute(req, resp));
        when(user.getRole()).thenReturn("manager");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeTourStatusCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("tourStatusSelect")).thenReturn("hot");
        when(req.getParameter("tourId")).thenReturn("2");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        doNothing().when(instance).changeTourStatus(2, "hot");
        assertEquals("controller?command=goToStartingPage", new ChangeTourStatusCommand().execute(req, resp));
    }

    @Test
    public void testChangeOrderStatus() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeOrderStatusCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getRole()).thenReturn("customer");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeOrderStatusCommand().execute(req, resp));
        when(user.getRole()).thenReturn("manager");
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeOrderStatusCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("userOrderId")).thenReturn("5");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        UserOrderDTO userOrder = mock(UserOrderDTO.class);
        when(instance.findOrderById(5)).thenReturn(userOrder);
        when(userOrder.getUserId()).thenReturn(13);
        when(user.getId()).thenReturn(13);
        assertEquals("controller?command=goToAccessDeniedPage", new ChangeOrderStatusCommand().execute(req, resp));
        when(user.getId()).thenReturn(10);
        assertEquals("controller?command=showListOfOrders", new ChangeOrderStatusCommand().execute(req, resp));
    }

    @Test
    public void testBook() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new BookCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new BookCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        when(req.getParameter("tourId")).thenReturn("5");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        TourDTO tour = mock(TourDTO.class);
        when(instance.findTourById(5)).thenReturn(tour);
        when(tour.getTourStatus()).thenReturn("archived");
        assertEquals("controller?command=goToAccessDeniedPage", new BookCommand().execute(req, resp));
        when(tour.getTourStatus()).thenReturn("hot");
        when(user.getId()).thenReturn(13);
        doNothing().when(instance).createOrder(13, 5);
        assertEquals("controller?command=showMyOrders", new BookCommand().execute(req, resp));
    }

    @Test
    public void testLogin() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("login")).thenReturn("user");
        when(req.getParameter("password")).thenReturn("userpass");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        when(instance.findUser("user", "userpass")).thenReturn(null);
        assertEquals("controller?command=goToFailedLogRegPage", new LoginCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(instance.findUser("user", "userpass")).thenReturn(user);
        when(user.getUserStatus()).thenReturn("blocked");
        assertEquals("controller?command=goToAccessDeniedPage", new LoginCommand().execute(req, resp));
        when(user.getUserStatus()).thenReturn("unblocked");
        doNothing().when(session).setAttribute("currentUser", user);
        assertEquals("controller?command=goToStartingPage", new LoginCommand().execute(req, resp));
    }

    @Test
    public void testRegistration() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("login")).thenReturn("user");
        when(req.getParameter("password")).thenReturn("userpass");
        when(req.getParameter("passwordRepeat")).thenReturn("userpass1");
        when(req.getParameter("email")).thenReturn("user@gmail.com");
        when(req.getParameter("name")).thenReturn("Aaa");
        when(req.getParameter("surname")).thenReturn("Bbb");
        new RegistrationCommand().execute(req, resp);
        when(req.getParameter("passwordRepeat")).thenReturn("userpass");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        when(instance.createUser("user", "userpass", "user@gmail.com", "Aaa", "Bbb")).thenReturn(null);
        assertEquals("controller?command=goToFailedLogRegPage", new RegistrationCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(instance.createUser("user", "userpass", "user@gmail.com", "Aaa", "Bbb")).thenReturn(user);
        doNothing().when(session).setAttribute("currentUser", user);
        assertEquals("controller?command=goToStartingPage", new RegistrationCommand().execute(req, resp));
    }

    @Test
    public void testShowMyOrders() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(session.getAttribute("currentUser")).thenReturn(null);
        assertEquals("controller?command=goToAccessDeniedPage", new ShowMyOrdersCommand().execute(req, resp));
        UserDTO user = mock(UserDTO.class);
        when(session.getAttribute("currentUser")).thenReturn(user);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        when(user.getId()).thenReturn(1);
        List<UserOrderDTO> userOrders = new ArrayList<>();
        when(instance.findAllUserOrdersForUser(1)).thenReturn(userOrders);
        doNothing().when(session).setAttribute("myOrders", userOrders);
        assertEquals("WEB-INF/jsp/user_order.jsp", new ShowMyOrdersCommand().execute(req, resp));
    }

    @Test
    public void testChangeLocale() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("locale")).thenReturn("uk");
        assertEquals("controller?command=goToChangeLocalePage&locale=uk", new ChangeLocaleCommand().execute(req, resp));
    }

    @Test
    public void testChangePageSize() throws DBException, AppException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("pageSize")).thenReturn("15");
        doNothing().when(session).setAttribute("currentPageSize", "15");
        assertEquals("controller?command=goToStartingPage", new ChangePageSizeCommand().execute(req, resp));
    }

    @Test
    public void testGoToFailedLogRegPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/failed_login_or_register_page.jsp", new GoToFailedLogRegPageCommand().execute(req, resp));
    }

    @Test
    public void testGoToAccessDeniedPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/access_denied_page.jsp", new GoToAccessDeniedPageCommand().execute(req, resp));
    }

    @Test
    public void testGoToChangeLocalePage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/change_locale.jsp", new GoToChangeLocalePageCommand().execute(req, resp));
    }

    @Test
    public void testGoToErrorPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/error.jsp", new GoToErrorPageCommand().execute(req, resp));
    }

    @Test
    public void testGoToLoginPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/login.jsp", new GoToLoginPageCommand().execute(req, resp));
    }

    @Test
    public void testGoToRegistrationPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/register.jsp", new GoToRegistrationPageCommand().execute(req, resp));
    }

    @Test
    public void testShowUserInfo() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        assertEquals("WEB-INF/jsp/user_info.jsp", new ShowUserInfoCommand().execute(req, resp));
    }

    @Test
    public void testShowTourInfo() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("tourId")).thenReturn("7");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        TourDTO tour = new TourDTO();
        when(instance.findTourById(7)).thenReturn(tour);
        doNothing().when(session).setAttribute("tour", tour);
        assertEquals("WEB-INF/jsp/tour_info.jsp", new ShowTourInfoCommand().execute(req, resp));
    }

    @Test
    public void testShowListOfUsers() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        List<UserDTO> users = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        when(instance.findAllUsers()).thenReturn(users);
        when(instance.findAllRoles()).thenReturn(roles);
        doNothing().when(session).setAttribute("users", users);
        doNothing().when(session).setAttribute("roles", roles);
        assertEquals("WEB-INF/jsp/list_users.jsp", new ShowListOfUsersCommand().execute(req, resp));
    }

    @Test
    public void testShowListOfOrders() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        List<UserOrderDTO> userOrders = new ArrayList<>();
        List<OrderStatus> orderStatuses = new ArrayList<>();
        when(instance.findAllUserOrders()).thenReturn(userOrders);
        when(instance.findAllOrderStatuses()).thenReturn(orderStatuses);
        doNothing().when(session).setAttribute("userOrders", userOrders);
        doNothing().when(session).setAttribute("orderStatuses", orderStatuses);
        assertEquals("WEB-INF/jsp/list_user_orders.jsp", new ShowListOfOrdersCommand().execute(req, resp));
    }

    @Test
    public void testShowDiscounts() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        List<Discount> discounts = new ArrayList<>();
        when(instance.findAllDiscounts()).thenReturn(discounts);
        doNothing().when(session).setAttribute("discounts", discounts);
        assertEquals("WEB-INF/jsp/discount.jsp", new ShowDiscountsCommand().execute(req, resp));
    }

    @Test
    public void testShowAddToursPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        List<TourType> tourTypes = new ArrayList<>();
        List<TourStatus> tourStatuses = new ArrayList<>();
        List<Accommodation> accommodations = new ArrayList<>();
        when(instance.findAllTourTypes()).thenReturn(tourTypes);
        when(instance.findAllTourStatuses()).thenReturn(tourStatuses);
        when(instance.findAllAccommodations()).thenReturn(accommodations);
        doNothing().when(session).setAttribute("tour_types", tourTypes);
        doNothing().when(session).setAttribute("tour_statuses", tourStatuses);
        doNothing().when(session).setAttribute("accommodations", accommodations);
        assertEquals("WEB-INF/jsp/add_tours.jsp", new ShowAddToursPageCommand().execute(req, resp));
    }

    @Test
    public void testListUsers() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        List<UserDTO> users = new ArrayList<>();
        when(instance.findAllUsers()).thenReturn(users);
        doNothing().when(session).setAttribute("users", users);
        assertEquals("WEB-INF/jsp/list_users.jsp", new ListUsersCommand().execute(req, resp));
    }

    @Test
    public void testPressLogout() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        doNothing().when(session).invalidate();
        assertEquals("controller?command=goToStartingPage", new PressLogoutCommand().execute(req, resp));
    }

    @Test
    public void testCommandContainer() {
        assertEquals(BookCommand.class, CommandContainer.getCommand("book").getClass());
    }

    @Test
    public void testListTours() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("tourTypeSearch")).thenReturn("all");
        when(req.getParameter("personsNumberSearch")).thenReturn("NONE");
        when(req.getParameter("durationSearch")).thenReturn("NONE");
        when(req.getParameter("accommodationSearch")).thenReturn("all");
        when(req.getParameter("priceSearch")).thenReturn("NONE");
        when(req.getParameter("page")).thenReturn("3");
        when(req.getParameter("pageSize")).thenReturn("10");
        Service instance = mock(Service.class);
        when(Service.getInstance()).thenReturn(instance);
        List<TourType> tourTypes = new ArrayList<>();
        List<TourStatus> tourStatuses = new ArrayList<>();
        List<Accommodation> accommodations = new ArrayList<>();
        when(instance.findAllTourTypes()).thenReturn(tourTypes);
        when(instance.findAllTourStatuses()).thenReturn(tourStatuses);
        when(instance.findAllAccommodations()).thenReturn(accommodations);
        when(instance.countAllTours("NONE", "NONE")).thenReturn(13);
        List<TourDTO> tours = new ArrayList<>();
        when(instance.findAllTours("all", "NONE", "NONE",
                "all", "NONE", 5, 10)).thenReturn(tours);
        doNothing().when(session).setAttribute("tour_types", tourTypes);
        doNothing().when(session).setAttribute("tour_statuses", tourStatuses);
        doNothing().when(session).setAttribute("accommodations", accommodations);
        doNothing().when(session).setAttribute("tours", tours);
        doNothing().when(session).setAttribute("currentTourTypeSearch", "all");
        doNothing().when(session).setAttribute("currentPersonsNumberSearch", "NONE");
        doNothing().when(session).setAttribute("currentDurationSearch", "NONE");
        doNothing().when(session).setAttribute("currentAccommodationSearch", "all");
        doNothing().when(session).setAttribute("currentPriceSearch", "NONE");
        doNothing().when(session).setAttribute("currentPage", "2");
        doNothing().when(session).setAttribute("currentPageSize", "10");
        doNothing().when(session).setAttribute("pageCount", "2");
        assertEquals("WEB-INF/jsp/home.jsp", new ListToursCommand().execute(req, resp));
    }

    @Test
    public void testGoToStartingPage() throws DBException {
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(req.getSession()).thenReturn(session);
        when(req.getParameter("tourTypeSearch")).thenReturn(null);
        when(req.getParameter("personsNumberSearch")).thenReturn(null);
        when(req.getParameter("durationSearch")).thenReturn(null);
        when(req.getParameter("accommodationSearch")).thenReturn(null);
        when(req.getParameter("priceSearch")).thenReturn(null);
        when(req.getParameter("page")).thenReturn(null);
        when(req.getParameter("pageSize")).thenReturn(null);
        when(session.getAttribute("currentTourTypeSearch")).thenReturn(null);
        when(session.getAttribute("currentPersonsNumberSearch")).thenReturn(null);
        when(session.getAttribute("currentDurationSearch")).thenReturn(null);
        when(session.getAttribute("currentAccommodationSearch")).thenReturn(null);
        when(session.getAttribute("currentPriceSearch")).thenReturn(null);
        when(session.getAttribute("currentPage")).thenReturn(null);
        when(session.getAttribute("currentPageSize")).thenReturn(null);
        assertEquals("controller?command=listTours&tourTypeSearch=all&personsNumberSearch=NONE&durationSearch=NONE" +
                "&accommodationSearch=all&priceSearch=NONE&page=1&pageSize=15", new GoToStartingPageCommand().execute(req, resp));
        when(session.getAttribute("currentTourTypeSearch")).thenReturn("active");
        when(session.getAttribute("currentPersonsNumberSearch")).thenReturn("NONE");
        when(session.getAttribute("currentDurationSearch")).thenReturn("NONE");
        when(session.getAttribute("currentAccommodationSearch")).thenReturn("tent");
        when(session.getAttribute("currentPriceSearch")).thenReturn("NONE");
        when(session.getAttribute("currentPage")).thenReturn("2");
        when(session.getAttribute("currentPageSize")).thenReturn("5");
        assertEquals("controller?command=listTours&tourTypeSearch=active&personsNumberSearch=NONE&durationSearch=NONE" +
                "&accommodationSearch=tent&priceSearch=NONE&page=2&pageSize=5", new GoToStartingPageCommand().execute(req, resp));
    }

    @After
    public void tearDown() {
        serviceMockedStatic.close();
    }
}
