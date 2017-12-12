package vip.onno.breaker.pojo.weibo;

import vip.onno.breaker.pojo.weibo.CommentList;

public class CommentResponse {
    private Integer ok;
    private String msg;
    private CommentList data;

    public CommentList getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getOk() {
        return ok;
    }

    public void setData(CommentList data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOk(Integer ok) {
        this.ok = ok;
    }

    @Override
    public String toString() {
        return "CommentResponse [ok=" + ok + ", msg=" + msg + ", data=" + data + "]";
    }

}
