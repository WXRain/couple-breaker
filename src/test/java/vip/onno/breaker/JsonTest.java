package vip.onno.breaker;

import com.alibaba.fastjson.JSON;

import vip.onno.breaker.pojo.Commit;

public class JsonTest {

    public static void main(String[] args) {
        String string = "{\"id\":4183093692817163, \"created_at\":\"21小时前\", "
            + "\"source\":\"Weibo.intl\", \"user\":{ \"id\":2565158094, "
            + "\"screen_name\":\"誓言拆开的语言\"}";
        Commit commit = JSON.parseObject(string, Commit.class);
        System.out.println(commit);
    }

}
