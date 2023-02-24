package xyz.ganghua.vo;

/**
 * The Class BaseResp.
 *
 * @param <T> the generic type
 */
public abstract class BaseView<T> {

    /**
     * 成功, 对应前端会回调
     */
    public static final String SUCCESS = "0000";

    /**
     * 失败, 对应前端会提示错误
     */
    public static final String NOTIFY = "0001";

    /**
     * 失败通知, 不会提示错误
     */
    public static final String SILENCE = "0002";

    /**
     * 失败, 对应前端还是会进入回调
     */
    public static final String FORCE = "0003";

    /** 状态码. */
    private String rspCode;

    /** 返回信息. */
    private String rspMsg;

    /** 返回数据. */
    private T rspData;

    /**
     * 取 状态码.
     *
     * @return the 状态码
     */
    public String getRspCode() {
        return rspCode;
    }

    /**
     * 设置 状态码.
     *
     * @param rspCode the new 状态码
     */
    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    /**
     * 取 返回信息.
     *
     * @return the 返回信息
     */
    public String getRspMsg() {
        return rspMsg;
    }

    /**
     * 设置 返回信息.
     *
     * @param rspMsg the new 返回信息
     */
    public void setRspMsg(String rspMsg) {
        this.rspMsg = rspMsg;
    }

    /**
     * 取 返回数据.
     *
     * @return the 返回数据
     */
    public T getRspData() {
        return rspData;
    }

    /**
     * 设置 返回数据.
     *
     * @param rspData the new 返回数据
     */
    protected void setRspData(T rspData) {
        this.rspData = rspData;
    }

    /** 设置默认成功消息 */
    public void success() {
        this.setRspCode(SUCCESS);
        this.setRspMsg("查询成功");
    }

    /** 设置返回状态为成功，自定义返回消息 */
    public void success(T rspData) {
        this.setRspCode(SUCCESS);
        this.setRspMsg("查询成功");
        this.setRspData(rspData);
    }

    public void success(String rspMsg, T rspData) {
        this.setRspCode(SUCCESS);
        this.setRspMsg(rspMsg);
        this.setRspData(rspData);
    }

    /** 设置返回状态为失败，自定义返回消息 */
    public void fail(String rspMsg) {
        this.setRspCode(NOTIFY);
        this.setRspMsg(rspMsg);
    }

    public void silence(String rspMsg) {
        this.setRspCode(SILENCE);
        this.setRspMsg(rspMsg);
    }

    public void force(String rspMsg) {
        this.setRspCode(FORCE);
        this.setRspMsg(rspMsg);
    }

    /** 设置返回状态为1001 not found */
    public void notFound() {
        this.setRspCode("1001");
        this.setRspMsg("not found");
    }

    /** 设置返回状态为1002 bad request */
    public void badRequest() {
        this.setRspCode("1002");
        this.setRspMsg("parameter(s) missing");
    }

}
