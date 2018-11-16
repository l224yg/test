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

    public static final Result SERVER_ERROR= new Result(500100, "服务端异常", null);
    public static final Result ILLEGAL_REQUEST= new Result(500100, "非法请求", null);

    //登录模块异常5002XX

    public static final Result SESSION_ERROR=new Result(500210,"Session不存在或已失效",null);
    public static final Result PASSWORD_EMPTY= new Result(500211,"密码不能为空",null);
    public static final Result MOBILE_EMPTY= new Result(500212,"手机号码不能为空",null);
    public static final Result MOBILE_ERROR= new Result(500213,"手机号码格式不正确",null);
    public static final Result MOBILE_NOT_EXIST= new Result(500214,"手机号码不存在",null);
    public static final Result PASSWORD_ERROR= new Result(500215,"密码不正确",null);
    public static Result PARAM_ERROR=new Result(500201,"参数校验错误",null);
    public static final Result NOTEXIST= new Result(500216,"用户不存在",null);
    public static final Result NOTLOGIN= new Result(500217,"用户未登录",null);
    //商品模块异常5003XX
    //订单模块异常5004XX
    public static final Result ORDER_NOT_EXIST=  new Result(500401,"没有订单",null);
    //秒杀模块异常5005XX
    public static final Result MIAOSHAOVER_ERROR= new Result(500501,"商品已经被秒完",null);
    public static final Result MIAOSHAREPEATED_ERROR= new Result(500502,"已经抢到了商品",null);
    public static final Result MIAOSHAFAIL_ERROR= new Result(500502,"秒杀失败",null);


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

    public Result fillMsg(String msg){
        this.msg =this.msg+":"+msg;
        return this;
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
