package org.example.service;

import org.example.dao.UserDAO;
import org.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements BaseService<User> {
    private final UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> readAll() {
        return null;
    }

    @Override
    public User readById(Long id) throws Exception {
        return null;
    }

    @Override
    public User create(User createRequest) {
        return null;
    }

    @Override
    public User update(User updateRequest) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) throws Exception {
        return false;
    }
}
