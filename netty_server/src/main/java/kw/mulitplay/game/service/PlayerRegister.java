package kw.mulitplay.game.service;

import io.netty.channel.Channel;
import kw.mulitplay.game.dao.PlayerDao;

import java.util.ArrayList;

public class PlayerRegister {
    public static PlayerRegister register;
    public static PlayerRegister playerRegister(){
        if (register == null) {
            register = new PlayerRegister();
        }
        return register;
    }

    private PlayerDao dao = PlayerDao.getInstance();

    public void rigister(String uuid, Channel channel){
        dao.resgister(uuid,channel);
    }

    public ArrayList<String> getUserList(){
        return dao.getUserList();
    }

    public boolean connect(String msg){
        boolean b = dao.hasUserInfo(msg);
        return b;
    }
}
