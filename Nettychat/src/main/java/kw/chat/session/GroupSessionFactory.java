package kw.chat.session;

public class GroupSessionFactory {

    private static GroupSession session = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession() {
        return session;
    }
}
