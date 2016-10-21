package com.app.camel.DAO;

import com.app.camel.UserStatus;

public interface UserRepository {

    public String getAllUsers() throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    public String getUserById(Integer id) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    public void addUser(String firstName, String lastName, String email, UserStatus isActive) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    public void deleteUser(Integer id) throws ClassNotFoundException, IllegalAccessException, InstantiationException;

    public void updateUserWithId(Integer id, String firstName, String lastName, String email, UserStatus isActive) throws ClassNotFoundException, IllegalAccessException, InstantiationException;
}
