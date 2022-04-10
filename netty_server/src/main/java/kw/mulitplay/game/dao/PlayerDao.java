package kw.mulitplay.game.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import kw.mulitplay.game.service.PlayerRegister;

public class PlayerDao {
    private static PlayerDao dao;
    public static PlayerDao getInstance(){
        return dao;
    }


    private ArrayList<String> userList = new ArrayList<>();
    private HashMap<String, Channel> hashMap = new HashMap<>();
    public PlayerDao(){

    }

    public void resgister(String uuid, Channel channel){
        hashMap.put(uuid,channel);
        userList.add(uuid);
    }

    public ArrayList<String> getUserList(){
        return userList;
    }
}
