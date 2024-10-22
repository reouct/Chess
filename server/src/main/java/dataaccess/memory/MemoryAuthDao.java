package dataaccess.memory;

import dataaccess.interfaces.AuthDAO;
import model.AuthData;
import model.UserData;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MemoryAuthDao implements AuthDAO {

    private Set<AuthData> auth = new HashSet<>();

    @Override
    public void clear() { auth = new HashSet<>();
    }

    @Override
    public String createAuth(String username) {
        String newToken = UUID.randomUUID().toString();
        auth.add(new AuthData(newToken, username));
        return newToken;
    }

    @Override
    public void deleteAuth(AuthData data) {
    // needs implement
    }

    @Override
    public AuthData getAuth(AuthData data) {
        for (AuthData a : auth) {
            if(a.authToken().equals(data)){
                return a;
            }
        }
        return null;
    }

    @Override
    public AuthData getAuth(String data) {
//        for (AuthData a : auth){
//            if(a.authToken().equals(data.authToken())){
//                return a;
//            }
        for (AuthData a : auth) {
            if(a.authToken().equals(data)){
                return a;
            }
        }
        return null;
    }
}
