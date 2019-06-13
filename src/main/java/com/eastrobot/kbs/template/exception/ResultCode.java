package com.eastrobot.kbs.template.exception;

import io.swagger.annotations.ApiModel;

/**
 * <pre>
 * global response code and it's meaning,
 * {@link GlobalExceptionHandler}
 *
 * NOTE: 3字码,1xxx的四字码保留, 具体需要提醒用户的异常信息从2xxx开始
 * </pre>
 *
 * @author <a href="yogurt_lei@foxmail.com">Yogurt_lei</a>
 * @version v1.0 , 2019-03-02 16:03
 * @see org.springframework.web.client.HttpClientErrorException.BadRequest
 */
@ApiModel
public enum ResultCode {
    /**
     * request handle success
     */
    OK("200", "OK"),
    /**
     * 404 NOT FOUND
     */
    PAGE_NOT_FOUND("404", "404 NOT FOUND"),
    /**
     * general failure, not recommended to use it
     */
    FAILURE("0000", "FAILURE"),
    /**
     * api called failed,
     */
    API_CALLED_FAILED("1000", "API CALLED FAILED"),
    /**
     * controller parameter validate failed
     */
    PARAMETER_VALIDATE_FAILED("1001", "PARAMETER VALIDATE FAILED"),
    /**
     * business execute failed
     */
    BUSINESS_EXECUTE_FAILED("1002", "BUSINESS EXECUTE FAILED"),
    /**
     * database operated failed. note: it will rollback transaction
     */
    DATABASE_OPERATED_FAILED("1003", "DATABASE OPERATED FAILED"),
    /**
     * datasource exception
     */
    DATASOURCE_EXCEPTION("1006", "DATASOURCE EXCEPTION"),
    /**
     * wrong entity id exception
     */
    WRONG_ENTITY_ID_EXCEPTION("1008", "WRONG ENTITY ID EXCEPTION"),
    /**
     * undefined server exception
     */
    UNDEFINED_SERVER_EXCEPTION("9999", "UNDEFINED SERVER EXCEPTION"),

    //**********************************************************************
    //*************** biz exception response error code start **************
    //**********************************************************************
    BIZ_DETAIL_EXCEPTION("2xxx", "具体需要提醒用户的异常信息从2xxx开始"),
    //**********************************************************************
    //*************** biz exception response error code end **************
    //**********************************************************************

    //*********************************************************
    //*************** login authentication start **************
    //*********************************************************
    /**
     * SESSION AUTHENTICATION EXCEPTION/ SESSION LIMIT
     */
    AUTH_SESSION_AUTHENTICATION("100", "SESSION AUTHENTICATION EXCEPTION"),
    /**
     * USER UNAUTHORIZED
     */
    AUTH_USER_UNAUTHORIZED("101", "USER UNAUTHORIZED"),
    /**
     * USER NOT FOUND
     */
    AUTH_USERNAME_NOT_FOUND("102", "USERNAME NOT FOUND EXCEPTION"),
    /**
     * BAD CREDENTIALS EXCEPTION
     */
    AUTH_BAD_CREDENTIALS("103", "BAD CREDENTIALS EXCEPTION"),
    /**
     * UNKNOWN AUTHENTICATE EXCEPTION
     */
    AUTH_ABNORMAL_ACCOUNT_STATUS("104", "ABNORMAL ACCOUNT STATUS"),
    /**
     * jwt: user logout
     */
    JWT_USER_LOGOUT("105", "USER LOGOUT"),
    /**
     * jwt: renew token
     */
    JWT_RENEW_TOKEN("106", "RENEW TOKEN"),
    /**
     * jwt: illegal token
     */
    JWT_ILLEGAL_TOKEN("107", "ILLEGAL TOKEN");
    //*********************************************************
    //*************** login authentication end ****************
    //*********************************************************

    public String code;
    public String meaning;

    ResultCode(String code, String meaning) {
        this.code = code;
        this.meaning = meaning;
    }
}
