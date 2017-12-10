package vip.onno.breaker.pojo;

import java.util.List;

import vip.onno.breaker.common.dict.ResponseStatus;

public class ListResponse extends BaseResponse {
    private List<?> data;

    public ListResponse(List<?> data, ResponseStatus status) {
        super(status);
        this.data = data;
    }

    public ListResponse(ResponseStatus status) {
        super(status);
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

}
