package vip.onno.breaker;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class OptionalTest {

    public static void main(String[] args) {
        String a = null;
        // System.out.println(test(a));
        // test2();
        test3();
    }

    public static boolean test(String a) {
        Optional<String> option = Optional.ofNullable(a);
        option.ifPresent(System.out::println);
        return option.isPresent();
    }

    public static void test2() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(5, 6, 7, 8, 9);
        Arrays.asList(list1, list2).stream()
            .flatMap(new Function<List<Integer>, Stream<Integer>>() {

                @Override
                public Stream<Integer> apply(List<Integer> t) {
                    return Stream.of(0);
                }

            })
            .forEach(System.out::print);
    }

    public static void test3() {
        String url = "https://m.weibo.cn/api/comments/show?id=4157476058612012&page=1";
        Matcher matcher = Pattern.compile("\\d+").matcher(url);
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
        // System.out.println(Pattern.compile("\\d+").matcher(url).group(0));
    }

}
