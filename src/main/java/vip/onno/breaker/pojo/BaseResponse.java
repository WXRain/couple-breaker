package vip.onno.breaker.pojo;

import vip.onno.breaker.common.dict.ResponseStatus;

public class BaseResponse {
    private Integer code;
    private String msg;

    public BaseResponse(ResponseStatus status) {
        this.setStatus(status);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setStatus(ResponseStatus status) {
        code = status.getCode();
        msg = status.getMsg();
    }

    @Override
    public String toString() {
        return "BaseResponse [code=" + code + ", msg=" + msg + "]";
    }

}
