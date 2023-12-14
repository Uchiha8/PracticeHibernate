package org.example.service;

import org.example.dao.UserDAO;
import org.example.domain.User;
import org.example.utils.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class UserService implements BaseService<User> {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> readAll() {
        List<User> userList = userDAO.readAll();
        if (userList.isEmpty()) {
            throw new RuntimeException("User table is empty");
        }
        return userList;
    }

    @Override
    public User readById(Long id) throws Exception {
        User user = userDAO.readById(id);
        if (user == null) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        return user;
    }

    @Override
    public User create(User createRequest) {
        if (validParam(createRequest)) {
            createRequest.setUsername(usernameGenerator(createRequest.getFirstName(), createRequest.getLastName()));
            createRequest.setPassword(passwordGenerator());
            return userDAO.create(createRequest);
        }
        throw new RuntimeException("Provide valid params");
    }

    @Override
    public User update(User updateRequest) {
        if (validParam(updateRequest)) {
            updateRequest.setUsername(usernameGenerator(updateRequest.getFirstName(), updateRequest.getLastName()));
            updateRequest.setPassword(passwordGenerator());
            return userDAO.update(updateRequest);
        }
        throw new RuntimeException("Provide valid params");
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        if (userDAO.deleteById(id)) {
            return true;
        }
        return false;
    }

    public String usernameGenerator(String firstName, String lastName) {
        try {
            List<User> userList = readAll();
            String username = firstName + "." + lastName;
            int serialNumber = 0;
            for (User user : userList) {
                if (user.getUsername().equals(username)) {
                    serialNumber++;
                }
            }
            if (serialNumber > 0) {
                username = username + "_" + serialNumber;
            }
            return username;
        } catch (RuntimeException e) {
            return firstName + "." + lastName;
        }
    }

    public String passwordGenerator() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }

    public boolean validParam(User user) {
        if (user.getFirstName() == null || user.getLastName() == null || user.getActive() == null) {
            return false;
        }
        return true;
    }

}
