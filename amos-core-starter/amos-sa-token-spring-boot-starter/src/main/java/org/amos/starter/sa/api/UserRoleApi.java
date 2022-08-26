package org.amos.starter.sa.api;

import java.util.List;

/**
 * @desc: 用户角色接口
 * @author: liubt
 * @date: 2022-08-17 10:39
 **/
public interface UserRoleApi {
    /**
     * 根据roleId 获取角色对应权限路径
     * @param roleId
     * @return
     */
    List<String> getRolePathByRoleId(String roleId);
}
