package dao;

import domain.Admin;
import domain.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    public List<User> findAll();

    public Admin findUserByUsernameAndPassword(Admin loginUser);

    void add(User user);

    void delete(int id);

    User findById(int id);

    void update(User user);
    //查询总记录
    int findTotalCount(Map<String, String[]> condition);
    //分页查询每页的记录
    List<User> findByPage(int start, int rows, Map<String, String[]> condition);
}
