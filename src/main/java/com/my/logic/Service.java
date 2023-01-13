package com.my.logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.my.db.DBException;
import com.my.db.DBManager;
import com.my.db.dto.*;
import com.my.db.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Service {
    private static Service instance;

    public static synchronized Service getInstance() {
        if (instance == null) {
            instance = new Service();
        }
        return instance;
    }

    private Service() {
        dbManager = DBManager.getInstance();
    }

    private DBManager dbManager;
    final Logger log = LogManager.getLogger(Service.class);

    /**
     * Method adds tour to the database by transaction.
     * New tour type or new accommodation type can be added along with the tour.
     *
     * @param tourName         name of the tour
     * @param tourType         existing type of the tour or value "new"
     * @param tourTypeNew      blank or new type of the tour
     * @param numberOfPersons  number of persons
     * @param duration         duration of the tour
     * @param accommodation    existing type of the accommodation or value "new"
     * @param accommodationNew blank or new type of the accommodation
     * @param price            price of the tour
     * @param tourStatus       status of the tour
     * @param fileName         name of the image corresponding to the tour
     * @throws DBException if tour cannot be added
     */
    public void addTour(String tourName, String tourType, String tourTypeNew, int numberOfPersons,
                        int duration, String accommodation, String accommodationNew, double price,
                        String tourStatus, String fileName) throws DBException {
        Connection con = null;
        try {
            con = dbManager.getConnection();
            //con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            con.setAutoCommit(false);
            int tourTypeId;
            int accommodationId;
            if (tourType.equals("new")) {
                tourTypeId = dbManager.createTourType(con, tourTypeNew);
            } else {
                tourTypeId = dbManager.findTourTypeId(con, tourType);
            }
            if (accommodation.equals("new")) {
                accommodationId = dbManager.createAccommodation(con, accommodationNew);
            } else {
                accommodationId = dbManager.findAccommodationId(con, accommodation);
            }
            int tourStatusId = dbManager.findTourStatusId(con, tourStatus);
            dbManager.createTour(con, tourName, tourTypeId, numberOfPersons, duration, accommodationId,
                    price, tourStatusId, fileName);
            con.commit();
        } catch (SQLException ex) {
            log.error("Cannot add tour", ex);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e) {
                    log.error("Cannot rollback", e);
                }
            }
            throw new DBException("Cannot add tour", ex);
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                log.error("Cannot close connection", e);
            }
        }
    }

    /**
     * Method adds user to the database as unblocked customer.
     *
     * @param login    login of the user
     * @param password password of the user
     * @param email    email of the user
     * @param name     name of the user
     * @param surname  surname of the user
     * @return user data transfer object
     * @throws DBException if user cannot be added
     */
    public UserDTO createUser(String login, String password, String email, String name, String surname) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.createUser(con, login, password, email, name, surname);
        } catch (SQLException ex) {
            log.error("Cannot create user", ex);
            throw new DBException("Cannot create user", ex);
        }
    }

    /**
     * Method finds user in database by login and password.
     *
     * @param login    login of the user
     * @param password password of the user
     * @return user data transfer object
     * @throws DBException if database exception occurs during user search
     */
    public UserDTO findUser(String login, String password) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findUser(con, login, password);
        } catch (SQLException ex) {
            log.error("Cannot find user", ex);
            throw new DBException("Cannot find user", ex);
        }
    }

    public UserDTO findUserById(int id) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findUserById(con, id);
        } catch (SQLException ex) {
            log.error("Cannot find user by id", ex);
            throw new DBException("Cannot find user by id", ex);
        }
    }

    /**
     * Method finds all orders.
     *
     * @return list of orders data transfer objects
     * @throws DBException if database exception occurs during orders search
     */
    public List<UserOrderDTO> findAllUserOrders() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllUserOrders(con);
        } catch (SQLException ex) {
            log.error("Cannot find all orders", ex);
            throw new DBException("Cannot find all orders", ex);
        }
    }

    /**
     * Method finds all orders for particular user.
     *
     * @param userId id of the user
     * @return list of orders data transfer objects for particular user
     * @throws DBException if database exception occurs during particular user orders search
     */
    public List<UserOrderDTO> findAllUserOrdersForUser(int userId) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllUserOrdersForUser(con, userId);
        } catch (SQLException ex) {
            log.error("Cannot find all orders for user", ex);
            throw new DBException("Cannot find all orders for user", ex);
        }
    }

    /**
     * Method finds all users.
     *
     * @return list of users data transfer objects
     * @throws DBException if database exception occurs during users search
     */
    public List<UserDTO> findAllUsers() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllUsers(con);
        } catch (SQLException ex) {
            log.error("Cannot find all users", ex);
            throw new DBException("Cannot find all users", ex);
        }
    }

    /**
     * Method finds tour by it`s id.
     *
     * @param id id of the tour
     * @return tour data transfer object
     * @throws DBException if database exception occurs during tour search
     */
    public TourDTO findTourById(int id) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findTourById(con, id);
        } catch (SQLException ex) {
            log.error("Cannot find tour by id", ex);
            throw new DBException("Cannot find tour by id", ex);
        }
    }

    /**
     * Method finds all tours according to the search parameters to display on particular page with particular size.
     * Hot tours are sorted to be the first and archived tours are sorted to be the last.
     *
     * @param tourTypeSearch      "all" or selected tour type to display
     * @param personsNumberSearch NONE or DESC or ASC order
     * @param durationSearch      NONE or DESC or ASC order
     * @param accommodationSearch "all" or selected accommodation type to display
     * @param priceSearch         NONE or DESC or ASC order
     * @param pageSize            number of tours to display on the page
     * @param offset              offset for the database query, calculated from number of the page and page size
     * @return list of tours data transfer objects
     * @throws DBException if database exception occurs during tours search
     */
    public List<TourDTO> findAllTours(String tourTypeSearch, String personsNumberSearch, String durationSearch, String accommodationSearch, String priceSearch, int pageSize, int offset) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllTours(con, tourTypeSearch, personsNumberSearch, durationSearch, accommodationSearch, priceSearch, pageSize, offset);
        } catch (SQLException ex) {
            log.error("Cannot find all tours", ex);
            throw new DBException("Cannot find all tours", ex);
        }
    }

    /**
     * Method counts all tours with particular tour type and accommodation type
     *
     * @param tourTypeSearch      "all" or selected tour type
     * @param accommodationSearch "all" or selected accommodation type
     * @return number of the tours with particular tour type and accommodation type
     * @throws DBException if database exception occurs during counting
     */
    public int countAllTours(String tourTypeSearch, String accommodationSearch) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.countAllTours(con, tourTypeSearch, accommodationSearch);
        } catch (SQLException ex) {
            log.error("Cannot count all tours", ex);
            throw new DBException("Cannot count all tours", ex);
        }
    }

    /**
     * Method finds all tour types.
     *
     * @return list of tour types
     * @throws DBException if database exception occurs during tour types search
     */
    public List<TourType> findAllTourTypes() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllTourTypes(con);
        } catch (SQLException ex) {
            log.error("Cannot find all tour types", ex);
            throw new DBException("Cannot find all tour types", ex);
        }
    }

    /**
     * Method finds all tour statuses.
     *
     * @return list of tour statuses
     * @throws DBException if database exception occurs during tour statuses search
     */
    public List<TourStatus> findAllTourStatuses() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllTourStatuses(con);
        } catch (SQLException ex) {
            log.error("Cannot find all tour statuses", ex);
            throw new DBException("Cannot find all tour statuses", ex);
        }
    }

    /**
     * Method finds all accommodation types.
     *
     * @return list of accommodation types
     * @throws DBException if database exception occurs during accommodation search
     */
    public List<Accommodation> findAllAccommodations() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllAccommodations(con);
        } catch (SQLException ex) {
            log.error("Cannot find all accommodations", ex);
            throw new DBException("Cannot find all accommodations", ex);
        }
    }

    public List<UserStatus> findAllUserStatuses() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllUserStatuses(con);
        } catch (SQLException ex) {
            log.error("Cannot find all user statuses", ex);
            throw new DBException("Cannot find all user statuses", ex);
        }
    }

    /**
     * Method finds all order statuses.
     *
     * @return list of order statuses
     * @throws DBException if database exception occurs during order statuses search
     */
    public List<OrderStatus> findAllOrderStatuses() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllOrderStatuses(con);
        } catch (SQLException ex) {
            log.error("Cannot find all order statuses", ex);
            throw new DBException("Cannot find all order statuses", ex);
        }
    }

    /**
     * Method finds all user roles.
     *
     * @return list of user roles
     * @throws DBException if database exception occurs during user roles search
     */
    public List<Role> findAllRoles() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllRoles(con);
        } catch (SQLException ex) {
            log.error("Cannot find all roles", ex);
            throw new DBException("Cannot find all roles", ex);
        }
    }

    /**
     * Method finds all discounts.
     *
     * @return list of discounts
     * @throws DBException if database exception occurs during discounts search
     */
    public List<Discount> findAllDiscounts() throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findAllDiscounts(con);
        } catch (SQLException ex) {
            log.error("Cannot find all discounts", ex);
            throw new DBException("Cannot find all discounts", ex);
        }
    }

    /**
     * Method changes tour status.
     *
     * @param tourId     id of the tour
     * @param tourStatus tour status which will be set
     * @throws DBException if tour status change failed
     */
    public void changeTourStatus(int tourId, String tourStatus) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.changeTourStatus(con, tourId, tourStatus);
        } catch (SQLException ex) {
            log.error("Cannot change tour status", ex);
            throw new DBException("Cannot change tour status", ex);
        }
    }

    /**
     * Method changes price of the tour.
     *
     * @param tourId id of the tour
     * @param price  price which will be set for the tour
     * @throws DBException if price change failed
     */
    public void changePrice(int tourId, double price) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.changePrice(con, tourId, price);
        } catch (SQLException ex) {
            log.error("Cannot change tour price", ex);
            throw new DBException("Cannot change tour price", ex);
        }
    }

    /**
     * Method changes discount.
     *
     * @param discountId   id of the discount
     * @param discountStep step which will be set for discount
     * @param discountMax  maximum value which will be set for discount
     * @throws DBException if discount change failed
     */
    public void changeDiscount(int discountId, int discountStep, int discountMax) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.changeDiscount(con, discountId, discountStep, discountMax);
        } catch (SQLException ex) {
            log.error("Cannot change discount", ex);
            throw new DBException("Cannot change discount", ex);
        }
    }

    /**
     * Method change user status to blocked.
     *
     * @param userId id of the user
     * @throws DBException if user status change failed
     */
    public void blockUser(int userId) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.blockUser(con, userId);
        } catch (SQLException ex) {
            log.error("Cannot block user", ex);
            throw new DBException("Cannot block user", ex);
        }
    }

    /**
     * Method change user status to unblocked.
     *
     * @param userId id of the user
     * @throws DBException if user status change failed
     */
    public void unblockUser(int userId) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.unblockUser(con, userId);
        } catch (SQLException ex) {
            log.error("Cannot unblock user", ex);
            throw new DBException("Cannot unblock user", ex);
        }
    }

    /**
     * Method processes booking of the tour by user by creating new order.
     * It fixes price and discount at the moment of booking.
     *
     * @param userId id of the user who is booking the tour
     * @param tourId id of the tour which is booked by the user
     * @throws DBException if order cannot be added to the database
     */
    public void createOrder(int userId, int tourId) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.createOrder(con, userId, tourId);
        } catch (SQLException ex) {
            log.error("Cannot book the tour", ex);
            throw new DBException("Cannot book the tour", ex);
        }
    }

    /**
     * Method changes user role.
     *
     * @param userId   id of the user
     * @param userRole user role which will be set
     * @throws DBException if change of the user role failed
     */
    public void changeUserRole(int userId, String userRole) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.changeUserRole(con, userId, userRole);
        } catch (SQLException ex) {
            log.error("Cannot change user role", ex);
            throw new DBException("Cannot change user role", ex);
        }
    }

    /**
     * Method finds order by it`s id.
     *
     * @param id id of the order
     * @return order data transfer object
     * @throws DBException if database exception occurs during order search
     */
    public UserOrderDTO findOrderById(int id) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.findOrderById(con, id);
        } catch (SQLException ex) {
            log.error("Cannot find order by id", ex);
            throw new DBException("Cannot find order by id", ex);
        }
    }

    /**
     * Method changes order status.
     *
     * @param orderId     id of the order
     * @param orderStatus status of the order which will be set
     * @throws DBException if change of the status of the order failed
     */
    public void changeOrderStatus(int orderId, String orderStatus) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.changeOrderStatus(con, orderId, orderStatus);
        } catch (SQLException ex) {
            log.error("Cannot change order status", ex);
            throw new DBException("Cannot change order status", ex);
        }
    }

    /**
     * Method delete tour if it is not contained in any user order and change tour status to archived otherwise.
     *
     * @param tourId id of the tour
     * @throws DBException if deletion or change of the tour status to archived failed
     */
    public void deleteTour(int tourId) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            dbManager.deleteTour(con, tourId);
        } catch (SQLException ex) {
            log.error("Cannot delete tour", ex);
            throw new DBException("Cannot delete tour", ex);
        }
    }

    /**
     * Method determine whether fileName corresponds to image name of any tour in the database.
     *
     * @param fileName name of the file
     * @return true if such image name is in the database
     * @throws DBException if database exception occurs tour image name search
     */
    public boolean isExistingTourImageName(String fileName) throws DBException {
        try (Connection con = dbManager.getConnection()) {
            return dbManager.isExistingTourImageName(con, fileName);
        } catch (SQLException ex) {
            log.error("Cannot determine is tour image name is already taken", ex);
            throw new DBException("Cannot determine is tour image name is already taken", ex);
        }
    }
}
