package kw.chat.service;

import kw.chat.bean.UserBean;
import kw.chat.factory.UserDaoFactory;
import kw.chat.service.base.UserService;

public class UserServiceImpl implements UserService {
    @Override
    public boolean login(String username, String password) {
        UserBean userBean = UserDaoFactory.userDao.findUserBean(username);
        if (userBean.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
