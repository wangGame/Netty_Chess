package kw.chat.group;

import java.util.Collections;
import java.util.Set;

public class Group {
    private String name;
    private Set<String> members;
    public static final Group EMPTY_GROUP = new Group("empty", Collections.emptySet());

    public Group(String name,Set<String> members){
        this.name = name;
        this.members = members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getMembers() {
        return members;
    }

    public String getName() {
        return name;
    }
}
