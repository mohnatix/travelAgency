package com.my.command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {
    private static final Map<String, Command> commands;

    static {
        commands = new HashMap<>();
        commands.put("goToLoginPage", new GoToLoginPageCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegistrationCommand());
        commands.put("goToRegistrationPage", new GoToRegistrationPageCommand());
        commands.put("listUsers", new ListUsersCommand());
        commands.put("listTours", new ListToursCommand());
        commands.put("changeLocale", new ChangeLocaleCommand());
        commands.put("goToChangeLocalePage", new GoToChangeLocalePageCommand());
        commands.put("goToStartingPage", new GoToStartingPageCommand());
        commands.put("goToFailedLogRegPage", new GoToFailedLogRegPageCommand());
        commands.put("goToAccessDeniedPage", new GoToAccessDeniedPageCommand());
        commands.put("pressLogout", new PressLogoutCommand());
        commands.put("book", new BookCommand());
        commands.put("showMyOrders", new ShowMyOrdersCommand());
        commands.put("changeTourStatus", new ChangeTourStatusCommand());
        commands.put("changePrice", new ChangePriceCommand());
        commands.put("goToErrorPage", new GoToErrorPageCommand());
        commands.put("showUserInfo", new ShowUserInfoCommand());
        commands.put("showDiscounts", new ShowDiscountsCommand());
        commands.put("changeDiscount", new ChangeDiscountCommand());
        commands.put("showListOfUsers", new ShowListOfUsersCommand());
        commands.put("blockUser", new BlockUserCommand());
        commands.put("unblockUser", new UnblockUserCommand());
        commands.put("showTourInfo", new ShowTourInfoCommand());
        commands.put("showListOfOrders", new ShowListOfOrdersCommand());
        commands.put("changeUserRole", new ChangeUserRoleCommand());
        commands.put("changeOrderStatus", new ChangeOrderStatusCommand());
        commands.put("showAddToursPage", new ShowAddToursPageCommand());
        commands.put("addTour", new AddTourCommand());
        commands.put("pressDelete", new DeleteTourCommand());
        commands.put("changePageSize", new ChangePageSizeCommand());
    }

    private CommandContainer() {
    }

    public static Command getCommand(String commandName) {
        return commands.get(commandName);
    }
}
