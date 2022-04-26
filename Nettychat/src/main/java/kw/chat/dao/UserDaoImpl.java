package kw.chat.dao;

import kw.chat.bean.UserBean;
import kw.chat.dao.base.UserDao;

import java.util.HashMap;

public class UserDaoImpl implements UserDao {
    private HashMap<String,String> hashMap = new HashMap<>();
    {
        hashMap.put("zhangsan","123456");
        hashMap.put("lisi","123456");
        hashMap.put("wangwu","123456");
        hashMap.put("zhaoliu","123456");
        hashMap.put("wangshi","123456");
        hashMap.put("xiaohua","123456");
        hashMap.put("xiaoming","123456");
    }
    @Override
    public UserBean findUserBean(String username) {
        String password = hashMap.get(username);
        UserBean bean = new UserBean();
        bean.setUsername(username);
        bean.setPassword(password);
        return bean;
    }
}
