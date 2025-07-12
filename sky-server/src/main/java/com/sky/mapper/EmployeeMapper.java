package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.PasswordEditDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @AutoFill(value = OperationType.INSERT)
    @Insert("INSERT INTO employee(username, name, phone, password, id_number, sex,create_time,update_time,create_user,update_user) " +
            "VALUES(#{username},#{name},#{phone},#{password},#{idNumber},#{sex},#{createTime},#{updateTime},#{createUser},#{updateUser}) ")
    void addEmployee(Employee employee);


    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    //@AutoFill(value = OperationType.UPDATE)
    @Update("UPDATE employee set status = #{status} where id = #{id}")
    void setStatus(String status,String id);

    //@AutoFill(value = OperationType.UPDATE)
    @Update("UPDATE employee SET password = #{newPassword} WHERE id = #{empId}")
    void editPassword(PasswordEditDTO passwordEditDTO);

    @Select("select * From employee WHERE id = #{id}")
    Employee queryEMployee(String id);

    @AutoFill(value = OperationType.UPDATE)
    @Update("update employee set id_number = #{idNumber},sex = #{sex},phone =#{phone},name = #{name},username = #{username} WHERE id = #{id}")
    void editEmployee(Employee employeeDTO);
}
