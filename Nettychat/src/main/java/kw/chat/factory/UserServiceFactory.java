package kw.chat.factory;

import kw.chat.service.UserServiceImpl;
import kw.chat.service.base.UserService;

public class UserServiceFactory {
    public static UserService userService = new UserServiceImpl();
}
