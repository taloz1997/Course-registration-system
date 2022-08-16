package bgu.spl.net.impl.BGRSServer;

import java.util.concurrent.atomic.AtomicBoolean;

public  class User {
    private String userName;
    private String password;
    private AtomicBoolean isLoggedIn;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        isLoggedIn = new AtomicBoolean(false);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn() {
        return isLoggedIn.get();
    }

    public void setIsLoggedIn (boolean isLogged) {
        isLoggedIn.set(isLogged);
    }

}


