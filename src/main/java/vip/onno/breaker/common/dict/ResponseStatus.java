package vip.onno.breaker.common.dict;

public enum ResponseStatus {
    OPERATION_SUCCEED(1001, "操作成功"), OPERATION_FAILED(4001, "操作失败");

    private int code;
    private String msg;

    private ResponseStatus(int code, String msg) {
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
