package kw.chat.factory;

import kw.chat.dao.UserDaoImpl;
import kw.chat.dao.base.UserDao;

public class UserDaoFactory {
    public static UserDao userDao = new UserDaoImpl();
}
