package org.geektimes.projects.user.management;

/**
 * UserInfoManagMBean
 *
 * @author qrXun on 2021/3/16
 */
public interface UserInfoManageMBean {

    String getName();

    void setName(String name);

    String getAddress();

    void setAddress(String address);

    Integer getAge();

    void setAge(Integer age);

    Integer getSex();

    void setSex(Integer sex);

    String toString();
}
