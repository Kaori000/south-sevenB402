package cn.yznu.common.utils;

public enum MessageCode {

    /**
     * login code
     */
    LOGIN_VERIFICATION_CODE_ERROR(5001, "验证码错误"),
    LOGIN_USER_NOT_EXIT(5002, "账户不存在"),
    LOGIN_USER_NAME_OR_PASSWORD_INCORRECT(5003, "账号或密码不正确"),
    LOGIN_UNER_ACCOUNT_FROZEN(5004, "账户锁定"),
    LOGIN_USER_ACCOUNT_VERIFICATION_ERROR(5005, "账户验证失败"),
    LOGIN_OLD_PASSWORD_ERROR(5006, "原密码错误"),

    /**
     * user operation code
     */
    USER_OPERATION_SYSTEM_ADMIN_CANNOT_DELETE(5100, "系统管理员不能删除"),
    USER_OPERATION_CURRENT_USER_CANNOT_DELETE(5101, "当前用户不能删除"),
    USER_OPERATION_DELETE_ALL_SUBDIVISION_WARNNING(5102, "删除部门有子部门提示"),

    /**
     * menu operation code
     */
    MENU_OPERATION_NAME_IS_EMPTY(5103, "菜单名称不能为空"),
    MENU_OPERATION_PREVIOUS_MENU_EMPTY(5104, "上级菜单不能为空"),
    MENU_OPERATION_URL_IS_EMPTY(5105, "菜单URL不能为空"),
    MENU_OPERATION_PREVIOUS_ONLY_BE_MENU(5106, "上级菜单只能为目录类型(目录、菜单)"),
    MENU_OPERATION_PREVIOUS_ONLY_BE_BUTTON(5107, "上级菜单只能为菜单类型(按钮)"),
    MENU_OPERATION_SYSTEM_MENU_CANNOT_DELETE(5108, "系统菜单，不能删除"),
    MENU_OPERATION_FIRST_DELETE_PREVIOUS_MENU(5109, "请先删除子菜单或按钮"),

    /**
     * file operation code
     */
    FIEL_OPERATION_IS_EMPTY(5200, "文件不能为空"),
    FILE_OPERTION_URL_IS_EMPTY(5201, "文件/路径不存在"),
    FILE_OPERATION_OTHER_IO_EXCIPTION(5202, "其他IO错误"),

    /**
     * platform code
     */
    PLATFORM_PROFILE_IS_EXIT(5301,"应用下存在profile，请检查"),
    PLATFORM_APPLICATION_IS_EXIT(5302,"应用下存在profile，请检查"),

    /**
     * device code
     */
    DEVICE_ENTITY_IS_EMPTY(5400,"设备信息不存在"),
    DEIVCE_PLATFORM_ADD_ERROR(5401,"OC平台添加设备出错"),
    DEVICE_PLATFORM_ADD_FAILED(5402,"OC平台添加设备失败"),
    DEVICE_DELETE_FAILED(5403,"删除设备失败"),


    SUCCESS_CODE(0,"操作成功");

    private int code;
    private String msg;

    MessageCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
