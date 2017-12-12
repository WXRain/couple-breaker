package vip.onno.breaker;

import com.alibaba.fastjson.JSON;

import vip.onno.breaker.pojo.weibo.Comment;
import vip.onno.breaker.pojo.weibo.CommentResponse;

public class JsonTest {

    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        String string = "{\"id\":4183093692817163, \"created_at\":\"21小时前\", "
            + "\"source\":\"Weibo.intl\", \"user\":{ \"id\":2565158094, "
            + "\"screen_name\":\"誓言拆开的语言\"}";
        Comment comment = JSON.parseObject(string, Comment.class);
        System.out.println(comment);
    }

    private static void test2() {
        String source = "{}";
        CommentResponse response = JSON.parseObject(source, CommentResponse.class);
        System.out.println(response);
    }

}
