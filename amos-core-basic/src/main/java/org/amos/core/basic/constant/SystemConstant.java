package org.amos.core.basic.constant;

public interface SystemConstant {

    /**
     * 根包名
     */
    String SYS_PACKAGE = "org.amos";

    /******************************************* 系统参数 *******************************************/
    /**
     * 默认删除标记
     */
    Integer SYS_DELETE_FLAG_DEFAULT = 0;
    /**
     * 已经删除标记
     */
    Integer SYS_DELETE_FLAG_ALREADY = 1;

    /**
     * 默认分页参数，当前页
     */
    Integer SYS_PAGINATION_PAGE = 1;
    /**
     * 默认分页参数，每页显示数据量
     */
    Integer SYS_PAGINATION_SIZE = 10;
    /**
     * 排序字段，倒序默认
     */
    String SYS_PAGE_ORDER_DESC = "create_time";
    /******************************************* 系统用户状态 *******************************************/
    /**
     * 用户默认状态，未激活
     */
    Integer SYS_SYSTEM_USER_STATUS_DEFAULT = 0;
    /**
     * 用户默认状态，已激活
     */
    Integer SYS_SYSTEM_USER_STATUS_ALREADY_ACTIVE = 1;
    /**
     * 用户默认状态，已锁定
     */
    Integer SYS_SYSTEM_USER_STATUS_ALREADY_LOCK = 2;
    /**
     * 用户默认状态，已禁用
     */
    Integer SYS_SYSTEM_USER_STATUS_ALREADY_FORBIDDEN = 3;

    /**
     * 系统用户默认密码
     */
    String SYS_DEFAULT_PWD = "123456";

    /******************************************* Quartz 任务状态 *******************************************/
    /**
     * 正常状态
     */
    Integer QUARTZ_JOB_STATUS_NORMAL = 0;
    /**
     * 停止状态
     */
    Integer QUARTZ_JOB_STATUS_DISABLE = 1;
}
