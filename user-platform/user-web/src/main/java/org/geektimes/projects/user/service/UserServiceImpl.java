package org.geektimes.projects.user.service;

import org.geektimes.projects.user.domain.User;
import org.geektimes.projects.user.repository.DatabaseUserRepository;
import org.geektimes.projects.user.sql.DBConnectionManager;

/**
 * UserServiceImpl
 *
 * @author qrXun on 2021/3/2
 */
public class UserServiceImpl implements UserService{

    private DatabaseUserRepository databaseUserRepository = new DatabaseUserRepository(new DBConnectionManager());

    @Override
    public boolean register(User user) {
        return databaseUserRepository.save(user);
    }

    @Override
    public boolean deregister(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return databaseUserRepository.update(user);
    }

    @Override
    public User queryUserById(Long id) {
        return databaseUserRepository.getById(id);
    }

    @Override
    public User queryUserByNameAndPassword(String name, String password) {
        return databaseUserRepository.getByNameAndPassword(name, password);
    }
}
