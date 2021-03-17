package org.geektimes.projects.user.management;

/**
 * UserInfoManage
 *
 * @author qrXun on 2021/3/16
 */
public class UserInfoManage implements UserInfoManageMBean{

    private UserInfo userInfo;

    public UserInfoManage(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public String getName() {
        return userInfo.getName();
    }

    @Override
    public void setName(String name) {
        userInfo.setName(name);
    }

    @Override
    public String getAddress() {
        return userInfo.getAddress();
    }

    @Override
    public void setAddress(String address) {
        userInfo.setAddress(address);
    }

    @Override
    public Integer getAge() {
        return userInfo.getAge();
    }

    @Override
    public void setAge(Integer age) {
        userInfo.setAge(age);
    }

    @Override
    public Integer getSex() {
        return userInfo.getSex();
    }

    @Override
    public void setSex(Integer sex) {
        userInfo.setSex(sex);
    }

    @Override
    public String toString() {
        return "UserInfoManage{" +
                "userInfo=" + userInfo.toString() +
                '}';
    }
}
