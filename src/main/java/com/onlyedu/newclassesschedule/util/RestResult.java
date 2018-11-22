package com.onlyedu.newclassesschedule.util;

/**
 * @author Andy
 * @date 2018/11/22 12:38
 */
public class RestResult {

    private static final int FAIL = 500;
    private static final int SUCCESS = 200;

    private int status;
    private String message;
    private Object data;

    public static RestResult fail(String message){
        RestResult result = new RestResult();
        result.setStatus(FAIL);
        result.setMessage(message);
        return result;
    }

    public static RestResult success(Object data){
        RestResult result = new RestResult();
        result.setStatus(SUCCESS);
        result.setData(data);
        return result;
    }

    public static RestResult success(){
        return success(null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RestResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
