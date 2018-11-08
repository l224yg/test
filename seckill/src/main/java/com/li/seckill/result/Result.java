package com.li.seckill.result;

/**
 * @Desciption
 * 封装结果类
 * @Auther Liyg
 * @Date Created in 2018/10/29 21:15
 */
public class Result<T> {
    //状态码
    private int code;
    //错误信息
    private String msg;
    //内容
    private T data;

    private Result(){}

    private Result(int code,String msg,T data){
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
    //定义单例错误
    //通用异常5001XX
    public static Result SERVER_ERROR= new Result(500100, "服务端异常", null);

    //登录模块异常5002XX
    //商品模块异常5003XX
    //订单模块异常5004XX
    //秒杀模块异常5005XX

    /**
     * 成功时的调用方法
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result(0,null,data);
    }

    /**
     * 失败时的调用方法
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code,String msg){
        return new Result(code,msg,null);
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }
}
