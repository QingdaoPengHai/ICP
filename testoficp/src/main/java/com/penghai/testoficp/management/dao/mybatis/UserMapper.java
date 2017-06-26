package com.penghai.testoficp.management.dao.mybatis;

import java.util.List;

import com.penghai.testoficp.management.model.User;

/**
 * 用户数据访问接口
 * @author 刘晓强
 * @Date 2017年5月5日 
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    /**
     * 用户查询自己的信息
     * @author 高源
     * @Date 2017年4月22日 下午1:39:24
     * @see 
     * @param id
     * @return User
     */
    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 查询有无该条用户（邮箱）记录
     * @author 高源
     * @Date 2017年4月22日 下午1:34:27
     * @see 
     * @param record
     * @return 0无此用户;1用户已存在
     */
    int checkAddUnique(User record);
    
    /**
     * 验证用户账号密码
     * @author 高源
     * @Date 2017年4月22日 下午1:37:26
     * @see 
     * @param record(email,password)
     * @return 0验证失败；1验证成功
     */
    int checkAuth(User record);
    
    /**
     * 根据条件查询用户（管理员用）
     * @author 高源
     * @Date 2017年4月22日 下午1:38:24
     * @see 
     * @param user
     * @return 目前因条件不明确，返回所有用户
     */
    List<User> selectByCondition(User user);
    
    /**
     * 按条件查询用户总数
     * @author 高源
     * @Date 2017年4月22日 下午1:39:02
     * @see 
     * @param user
     * @return 用户总数
     */
    int selectCountByCondition(User user);
}