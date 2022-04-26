package kw.chat.dao.base;

import kw.chat.bean.UserBean;

public interface UserDao {
    public UserBean findUserBean(String username);

}
