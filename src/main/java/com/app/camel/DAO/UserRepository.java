package com.app.camel.DAO;

import com.app.camel.UserStatus;

public interface UserRepository {

    public String getAllUsers();

    public String getUserById(Integer id);

    public void addUser(String firstName, String lastName, String email, UserStatus isActive);

    public void deleteUser(Integer id);

    public void updateUserWithId(Integer id, String firstName, String lastName, String email, UserStatus isActive);

    public String getAllUserWithProject();
}
