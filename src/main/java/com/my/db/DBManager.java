package com.my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.my.db.dto.TourDTO;
import com.my.db.dto.UserDTO;
import com.my.db.dto.UserOrderDTO;
import com.my.db.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBManager {
    private static DBManager instance;

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    private final DataSource ds;
    final Logger log = LogManager.getLogger(DBManager.class);

    private DBManager() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            ds = (DataSource) envContext.lookup("jdbc/travel_agency");
            log.info("dataSource = " + ds);
        } catch (NamingException ex) {
            log.error("Cannot obtain a data source", ex);
            throw new IllegalStateException("Cannot obtain a data source", ex);
        }
    }

    public Connection getConnection() throws SQLException {
        Connection con = null;
        try {
            con = ds.getConnection();
        } catch (SQLException ex) {
            log.error("Cannot obtain a connection", ex);
            throw new IllegalStateException("Cannot obtain a connection", ex);
        }
        return con;
    }

    private static final String FIND_ALL_USERS =
            "SELECT user.id AS id, user.login AS login, user.password AS password, " +
                    "user.email AS email, user.name AS name, user.surname AS surname, " +
                    "role.name AS role, user_status.status as user_status from user " +
                    "LEFT JOIN role ON user.role_id=role.id " +
                    "LEFT JOIN user_status ON user.user_status_id=user_status.id ";

    private static final String FIND_ALL_USER_ORDERS =
            "SELECT user_order.id AS id, user.id AS user_id, tour.id AS tour_id, " +
                    "user.name AS user_name, user.surname AS user_surname, " +
                    "user.email AS user_email, tour.name AS tour_name, " +
                    "user_order.price_fixed AS price_fixed, " +
                    "user_order.discount_fixed AS discount_fixed, " +
                    "order_status.status AS order_status from user_order " +
                    "LEFT JOIN order_status ON user_order.order_status_id=order_status.id " +
                    "LEFT JOIN user ON user_order.user_id=user.id " +
                    "LEFT JOIN tour ON user_order.tour_id=tour.id";

    static final String FIND_ALL_USER_ORDERS_SORTED =
            FIND_ALL_USER_ORDERS + " order by order_status.id ASC, user_order.id DESC";

    private static final String FIND_ALL_USER_ORDERS_FOR_USER =
            FIND_ALL_USER_ORDERS + " where user_order.user_id=? order by user_order.id DESC";

    private static final String FIND_ALL_TOUR_TYPES =
            "select * from tour_type";

    private static final String FIND_TOUR_TYPE =
            "select * from tour_type where type=?";

    private static final String FIND_ALL_TOUR_STATUSES =
            "select * from tour_status";

    private static final String FIND_TOUR_STATUS =
            "select * from tour_status where status=?";

    private static final String FIND_ALL_ACCOMMODATIONS =
            "select * from accommodation";

    private static final String FIND_ACCOMMODATION =
            "select * from accommodation where type=?";

    private static final String FIND_ALL_ROLES =
            "select * from role";

    private static final String FIND_ALL_USER_STATUSES =
            "select * from user_status";

    private static final String FIND_ALL_ORDER_STATUSES =
            "select * from order_status";

    private static final String FIND_ALL_DISCOUNTS =
            "select * from discount";

    private static final String FIND_USER_BY_LOGIN_PASSWORD =
            "SELECT user.id AS id, user.login AS login, user.password AS password, " +
                    "user.email AS email, user.name AS name, user.surname AS surname, " +
                    "role.name AS role, user_status.status as user_status from user " +
                    "LEFT JOIN role ON user.role_id=role.id " +
                    "LEFT JOIN user_status ON user.user_status_id=user_status.id " +
                    "WHERE login=? AND password=?";

    private static final String FIND_USER_BY_ID =
            "SELECT user.id AS id, user.login AS login, user.password AS password, " +
                    "user.email AS email, user.name AS name, user.surname AS surname, " +
                    "role.name AS role, user_status.status as user_status from user " +
                    "LEFT JOIN role ON user.role_id=role.id " +
                    "LEFT JOIN user_status ON user.user_status_id=user_status.id " +
                    "WHERE user.id=?";

    private static final String FIND_TOUR_BY_ID =
            "SELECT tour.id AS id, tour.name AS name, tour_type.type AS tour_type, tour.price AS price, " +
                    "tour.persons_number AS persons_number, tour.duration AS duration, " +
                    "accommodation.type AS accommodation, tour.image_name as image_name, " +
                    "tour_status.status AS tour_status from tour " +
                    "LEFT JOIN tour_status ON tour.tour_status_id=tour_status.id " +
                    "LEFT JOIN tour_type ON tour.tour_type_id=tour_type.id " +
                    "LEFT JOIN accommodation ON tour.accommodation_id=accommodation.id  " +
                    "WHERE tour.id=?";

    private static final String FIND_USER_ORDER_BY_ID =
            FIND_ALL_USER_ORDERS + " where user_order.id=?";

    private static final String CREATE_TOUR_TYPE =
            "insert into tour_type values (DEFAULT, ?)";

    private static final String CREATE_ACCOMMODATION =
            "insert into accommodation values (DEFAULT, ?)";

    private static final String CREATE_TOUR =
            "insert into tour values (DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String CREATE_USER =
            "insert into user values (DEFAULT, ?, ?, ?, ?, ?, (select id from role where name ='customer')," +
                    "(select id from user_status where status ='unblocked'))";

    private static final String CHANGE_USER_ROLE =
            "update user, role set  user.role_id=role.id where user.id=? AND role.name=?";

    private static final String CHANGE_USER_ORDER_STATUS =
            "update user_order, order_status set  user_order.order_status_id=order_status.id " +
                    "where user_order.id=? AND order_status.status=?";

    private static final String CHANGE_TOUR_STATUS =
            "update tour, tour_status set  tour.tour_status_id=tour_status.id where tour.id=? AND tour_status.status=?";

    private static final String CHANGE_TOUR_PRICE =
            "update tour set  tour.price=? where tour.id=?";

    private static final String CHANGE_DISCOUNT =
            "update discount set discount.step=?,discount.max=?  where discount.id=?";

    private static final String BLOCK_USER =
            "update user, user_status set user.user_status_id=user_status.id where user.id=? AND user_status.status='blocked'";

    private static final String UNBLOCK_USER =
            "update user, user_status set user.user_status_id=user_status.id where user.id=? AND user_status.status='unblocked'";

    private static final String CREATE_USER_ORDER =
            "insert into user_order values (DEFAULT, ?, ?, " +
                    "(select tour.price from tour where tour.id =?), " +
                    "(select discountFixed from ((select  (select COUNT(*)  from user_order left join order_status on user_order.order_status_id=order_status.id  where user_order.user_id=? AND order_status.status='paid') as numberOfPaidTours, " +
                    "(select discount.step from discount where discount.name = 'loyalty') as discountStep, " +
                    "(select discount.max  from  discount where discount.name = 'loyalty') as discountMax, " +
                    "(select numberOfPaidTours*discountStep) as result, " +
                    "(select IF(discountMax>result,result,discountMax)) as discountFixed)) as t) , " +
                    "(select id from order_status where status ='registered'))";

    private static final String DELETE_TOUR =
            "delete from tour where id=? and id not in (select tour_id from user_order)";

    private static final String IS_TOUR_IMAGE_NAME =
            "select count(*) from tour where tour.image_name=?";

    private static UserDTO extractUserDTO(ResultSet rs) throws SQLException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(rs.getInt("id"));
        userDTO.setLogin(rs.getString("login"));
        userDTO.setPassword(rs.getString("password"));
        userDTO.setEmail(rs.getString("email"));
        userDTO.setName(rs.getString("name"));
        userDTO.setSurname(rs.getString("surname"));
        userDTO.setRole(rs.getString("role"));
        userDTO.setUserStatus(rs.getString("user_status"));
        return userDTO;
    }

    private static UserStatus extractUserStatus(ResultSet rs) throws SQLException {
        UserStatus userStatus = new UserStatus();
        userStatus.setId(rs.getInt("id"));
        userStatus.setStatus(rs.getString("status"));
        return userStatus;
    }

    private static Role extractRole(ResultSet rs) throws SQLException {
        Role role = new Role();
        role.setId(rs.getInt("id"));
        role.setName(rs.getString("name"));
        return role;
    }

    private static OrderStatus extractOrderStatus(ResultSet rs) throws SQLException {
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setId(rs.getInt("id"));
        orderStatus.setStatus(rs.getString("status"));
        return orderStatus;
    }

    private static Discount extractDiscount(ResultSet rs) throws SQLException {
        Discount discount = new Discount();
        discount.setId(rs.getInt("id"));
        discount.setName(rs.getString("name"));
        discount.setStep(rs.getInt("step"));
        discount.setMax(rs.getInt("max"));
        return discount;
    }

    private static UserOrderDTO extractUserOrderDTO(ResultSet rs) throws SQLException {
        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setId(rs.getInt("id"));
        userOrderDTO.setUserId(rs.getInt("user_id"));
        userOrderDTO.setTourId(rs.getInt("tour_id"));
        userOrderDTO.setUserName(rs.getString("user_name"));
        userOrderDTO.setUserSurname(rs.getString("user_surname"));
        userOrderDTO.setUserEmail(rs.getString("user_email"));
        userOrderDTO.setTourName(rs.getString("tour_name"));
        userOrderDTO.setPriceFixed(rs.getDouble("price_fixed"));
        userOrderDTO.setDiscountFixed(rs.getInt("discount_fixed"));
        userOrderDTO.setOrderStatus(rs.getString("order_status"));
        return userOrderDTO;
    }

    private static TourDTO extractTourDTO(ResultSet rs) throws SQLException {
        TourDTO tourDTO = new TourDTO();
        tourDTO.setId(rs.getInt("id"));
        tourDTO.setName(rs.getString("name"));
        tourDTO.setTourType(rs.getString("tour_type"));
        tourDTO.setPrice(rs.getDouble("price"));
        tourDTO.setPersonsNumber(rs.getInt("persons_number"));
        tourDTO.setDuration(rs.getInt("duration"));
        tourDTO.setAccommodation(rs.getString("accommodation"));
        tourDTO.setImageName(rs.getString("image_name"));
        tourDTO.setTourStatus(rs.getString("tour_status"));
        return tourDTO;
    }

    private static TourType extractTourType(ResultSet rs) throws SQLException {
        TourType tourType = new TourType();
        tourType.setId(rs.getInt("id"));
        tourType.setType(rs.getString("type"));
        return tourType;
    }

    private static TourStatus extractTourStatus(ResultSet rs) throws SQLException {
        TourStatus tourStatus = new TourStatus();
        tourStatus.setId(rs.getInt("id"));
        tourStatus.setStatus(rs.getString("status"));
        return tourStatus;
    }

    private static Accommodation extractAccommodation(ResultSet rs) throws SQLException {
        Accommodation accommodation = new Accommodation();
        accommodation.setId(rs.getInt("id"));
        accommodation.setType(rs.getString("type"));
        return accommodation;
    }

    public UserDTO createUser(Connection con, String login, String password, String email, String name, String surname)
            throws SQLException {
        UserDTO userDTO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
            int k = 0;
            pstmt.setString(++k, login);
            pstmt.setString(++k, password);
            pstmt.setString(++k, email);
            pstmt.setString(++k, name);
            pstmt.setString(++k, surname);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                userDTO = new UserDTO();
                if (rs.next()) {
                    userDTO.setId(rs.getInt(1));
                }
                userDTO.setLogin(login);
                userDTO.setPassword(password);
                userDTO.setEmail(email);
                userDTO.setName(name);
                userDTO.setSurname(surname);
                userDTO.setRole("customer");
                userDTO.setUserStatus("unblocked");
            } else {
                throw new SQLException();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return userDTO;
    }

    public UserDTO findUser(Connection con, String login, String password) throws SQLException {
        UserDTO userDTO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_USER_BY_LOGIN_PASSWORD);
            pstmt.setString(1, login);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userDTO = extractUserDTO(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return userDTO;
    }

    public UserDTO findUserById(Connection con, int id) throws SQLException {
        UserDTO userDTO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_USER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userDTO = extractUserDTO(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return userDTO;
    }

    public List<UserOrderDTO> findAllUserOrders(Connection con) throws SQLException {
        List<UserOrderDTO> userOrders = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_USER_ORDERS_SORTED);
            while (rs.next()) {
                userOrders.add(extractUserOrderDTO(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return userOrders;
    }

    public List<UserOrderDTO> findAllUserOrdersForUser(Connection con, int userId) throws SQLException {
        List<UserOrderDTO> userOrders = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_ALL_USER_ORDERS_FOR_USER);
            pstmt.setInt(1, userId);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                userOrders.add(extractUserOrderDTO(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return userOrders;
    }

    public List<UserDTO> findAllUsers(Connection con) throws SQLException {
        List<UserDTO> users = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_USERS);
            while (rs.next()) {
                users.add(extractUserDTO(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return users;
    }

    public TourDTO findTourById(Connection con, int id) throws SQLException {
        TourDTO tourDTO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_TOUR_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                tourDTO = extractTourDTO(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return tourDTO;
    }

    public List<TourDTO> findAllTours(Connection con, String tourTypeSearch, String personsNumberSearch, String durationSearch,
                                      String accommodationSearch, String priceSearch, int pageSize, int offset) throws SQLException {
        String personsNumberOrdering = "";
        if (!personsNumberSearch.equals("NONE")) {
            personsNumberOrdering = ", persons_number " + personsNumberSearch;
        }
        String durationOrdering = "";
        if (!durationSearch.equals("NONE")) {
            durationOrdering = ", duration " + durationSearch;
        }
        String priceOrdering = "";
        if (!priceSearch.equals("NONE")) {
            priceOrdering = ", price " + priceSearch;
        }

        final String FIND_ALL_TOURS =
                "SELECT tour.id AS id, tour.name AS name, tour_type.type AS tour_type, tour.price AS price, " +
                        "tour.persons_number AS persons_number, tour.duration AS duration, " +
                        "accommodation.type AS accommodation, tour.image_name as image_name, " +
                        "tour_status.status AS tour_status from tour " +
                        "LEFT JOIN tour_status ON tour.tour_status_id=tour_status.id " +
                        "LEFT JOIN tour_type ON tour.tour_type_id=tour_type.id " +
                        "LEFT JOIN accommodation ON tour.accommodation_id=accommodation.id  " +
                        "WHERE tour_type.type=IF('" + tourTypeSearch + "'='all', tour_type.type, '" + tourTypeSearch + "') AND" +
                        " accommodation.type=IF('" + accommodationSearch + "'='all', accommodation.type, '" + accommodationSearch + "')" +
                        "order by tour.tour_status_id ASC" + personsNumberOrdering + durationOrdering + priceOrdering +
                        " LIMIT " + pageSize + " OFFSET " + offset;

        log.debug("search = " + FIND_ALL_TOURS);
        List<TourDTO> tourDTOs = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_TOURS);
            while (rs.next()) {
                tourDTOs.add(extractTourDTO(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return tourDTOs;
    }

    public int countAllTours(Connection con, String tourTypeSearch, String accommodationSearch) throws SQLException {
        int count = 0;

        final String COUNT_ALL_TOURS =
                "SELECT COUNT(*) from tour " +
                        "LEFT JOIN tour_status ON tour.tour_status_id=tour_status.id " +
                        "LEFT JOIN tour_type ON tour.tour_type_id=tour_type.id " +
                        "LEFT JOIN accommodation ON tour.accommodation_id=accommodation.id  " +
                        "WHERE tour_type.type=IF('" + tourTypeSearch + "'='all', tour_type.type, '" + tourTypeSearch + "') AND" +
                        " accommodation.type=IF('" + accommodationSearch + "'='all', accommodation.type, '" + accommodationSearch + "')";

        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(COUNT_ALL_TOURS);
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return count;
    }

    public List<TourType> findAllTourTypes(Connection con) throws SQLException {
        List<TourType> tourTypes = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_TOUR_TYPES);
            while (rs.next()) {
                tourTypes.add(extractTourType(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return tourTypes;
    }

    public List<TourStatus> findAllTourStatuses(Connection con) throws SQLException {
        List<TourStatus> tourStatuses = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_TOUR_STATUSES);
            while (rs.next()) {
                tourStatuses.add(extractTourStatus(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return tourStatuses;
    }

    public List<Accommodation> findAllAccommodations(Connection con) throws SQLException {
        List<Accommodation> accommodations = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_ACCOMMODATIONS);
            while (rs.next()) {
                accommodations.add(extractAccommodation(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return accommodations;
    }

    public List<UserStatus> findAllUserStatuses(Connection con) throws SQLException {
        List<UserStatus> userStatuses = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_USER_STATUSES);
            while (rs.next()) {
                userStatuses.add(extractUserStatus(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return userStatuses;
    }

    public List<OrderStatus> findAllOrderStatuses(Connection con) throws SQLException {
        List<OrderStatus> orderStatuses = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_ORDER_STATUSES);
            while (rs.next()) {
                orderStatuses.add(extractOrderStatus(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return orderStatuses;
    }

    public List<Role> findAllRoles(Connection con) throws SQLException {
        List<Role> roles = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_ROLES);
            while (rs.next()) {
                roles.add(extractRole(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return roles;
    }

    public List<Discount> findAllDiscounts(Connection con) throws SQLException {
        List<Discount> discounts = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(FIND_ALL_DISCOUNTS);
            while (rs.next()) {
                discounts.add(extractDiscount(rs));
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return discounts;
    }

    public void changeTourStatus(Connection con, int tourId, String tourStatus) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CHANGE_TOUR_STATUS);
            int k = 0;
            pstmt.setInt(++k, tourId);
            pstmt.setString(++k, tourStatus);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void changePrice(Connection con, int tourId, double price) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CHANGE_TOUR_PRICE);
            int k = 0;
            pstmt.setDouble(++k, price);
            pstmt.setInt(++k, tourId);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void changeDiscount(Connection con, int discountId, int discountStep, int discountMax) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CHANGE_DISCOUNT);
            int k = 0;
            pstmt.setInt(++k, discountStep);
            pstmt.setInt(++k, discountMax);
            pstmt.setInt(++k, discountId);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void blockUser(Connection con, int userId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(BLOCK_USER);
            int k = 0;
            pstmt.setInt(++k, userId);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void unblockUser(Connection con, int userId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(UNBLOCK_USER);
            int k = 0;
            pstmt.setInt(++k, userId);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void createOrder(Connection con, int userId, int tourId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CREATE_USER_ORDER);
            int k = 0;
            pstmt.setInt(++k, userId);
            pstmt.setInt(++k, tourId);
            pstmt.setInt(++k, tourId);
            pstmt.setInt(++k, userId);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public void changeUserRole(Connection con, int userId, String userRole) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CHANGE_USER_ROLE);
            int k = 0;
            pstmt.setInt(++k, userId);
            pstmt.setString(++k, userRole);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public UserOrderDTO findOrderById(Connection con, int id) throws SQLException {
        UserOrderDTO userOrderDTO = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_USER_ORDER_BY_ID);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                userOrderDTO = extractUserOrderDTO(rs);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return userOrderDTO;
    }

    public void changeOrderStatus(Connection con, int orderId, String orderStatus) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CHANGE_USER_ORDER_STATUS);
            int k = 0;
            pstmt.setInt(++k, orderId);
            pstmt.setString(++k, orderStatus);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public int createTourType(Connection con, String tourTypeNew) throws SQLException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(CREATE_TOUR_TYPE, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, tourTypeNew);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } else {
                throw new SQLException();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return id;
    }

    public int createAccommodation(Connection con, String accommodationNew) throws SQLException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(CREATE_ACCOMMODATION, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, accommodationNew);
            if (pstmt.executeUpdate() > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                }
            } else {
                throw new SQLException();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return id;
    }

    public void createTour(Connection con, String tourName, int tourTypeId, int numberOfPersons, int duration,
                           int accommodationId, double price, int tourStatusId, String fileName) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(CREATE_TOUR);
            int k = 0;
            pstmt.setString(++k, tourName);
            pstmt.setInt(++k, tourTypeId);
            pstmt.setDouble(++k, price);
            pstmt.setInt(++k, numberOfPersons);
            pstmt.setInt(++k, duration);
            pstmt.setInt(++k, accommodationId);
            pstmt.setString(++k, fileName);
            pstmt.setInt(++k, tourStatusId);
            if (!(pstmt.executeUpdate() > 0)) {
                throw new SQLException();
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public int findTourTypeId(Connection con, String tourType) throws SQLException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_TOUR_TYPE);
            pstmt.setString(1, tourType);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = extractTourType(rs).getId();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return id;
    }

    public int findAccommodationId(Connection con, String accommodation) throws SQLException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_ACCOMMODATION);
            pstmt.setString(1, accommodation);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = extractAccommodation(rs).getId();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return id;
    }

    public int findTourStatusId(Connection con, String tourStatus) throws SQLException {
        int id = 0;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(FIND_TOUR_STATUS);
            pstmt.setString(1, tourStatus);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = extractTourStatus(rs).getId();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return id;
    }

    public void deleteTour(Connection con, int tourId) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = con.prepareStatement(DELETE_TOUR);
            pstmt.setInt(1, tourId);
            if (!(pstmt.executeUpdate() > 0)) {
                changeTourStatus(con, tourId, "archived");
            }
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }

    public boolean isExistingTourImageName(Connection con, String fileName) throws SQLException {
        boolean nameIsTaken = false;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = con.prepareStatement(IS_TOUR_IMAGE_NAME);
            pstmt.setString(1, fileName);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt(1) > 0) {
                    nameIsTaken = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
        }
        return nameIsTaken;
    }
}
