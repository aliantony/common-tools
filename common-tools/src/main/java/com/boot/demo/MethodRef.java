package com.boot.demo;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @program common-tools
 * @description 方法引用练习
 * @author wq
 * created on 2020-08-06
 * @version  1.0.0
 */
public class MethodRef {
    /**
     * 因此，所谓方法引用，是指如果某个方法签名和接口恰好一致，就可以直接传入方法引用
     * 方法签名只看参数类型和返回类型，不看方法名称，也不看类的继承关系。
     * @param args
     */
    public static void main(String[] args) throws IOException {
       String[] arr = {"orange", "banana", "lemon", "apple"};
       Arrays.sort(arr, MethodRef::cmp);
        /**
         * compareTo(String anotherString) 等价于public static int compareTo(this, String o);
         * 第一个参数隐含this
         */
       Arrays.sort(arr, String::compareTo);
       System.out.println(String.join(", ",arr));

        class Person {
            String name;
            public Person(String name) {
                this.name = name;
            }
        }
        List<String> names = Lists.newArrayList("Bob", "Alice", "Tim");
        /**
         * 构造函数引用
         * map的参数是Function R apply(T) 和Person构造器入参一致，构造函数隐式返回this==person
         */
        List<Person> persons = names.stream().map(Person::new).collect(Collectors.toList());
        persons.forEach(e -> System.out.println(e.name));
        //流中的每个元素代表文件中的一行内容
        try (Stream<String> lines = Files.lines(Paths.get("F:/out.txt"))) {
             lines.forEach(System.out::println);
        }
        Pattern p = Pattern.compile("\\s+");
        Stream<String> s = p.splitAsStream("The quick brown fox jumps over the lazy dog");
        s.forEach(System.out::println);
        IntStream is = Arrays.stream(new int[] { 1, 2, 3 });
        LongStream ls = Lists.newArrayList("1", "2", "3").stream().mapToLong(Long::parseLong);
        Stream.generate(new LocalDateSupplier()).limit(31).filter(e -> e.getDayOfWeek() == DayOfWeek.SATURDAY || e.getDayOfWeek() == DayOfWeek.SUNDAY)
                .forEach(System.out::println);
    }

    /**
     * 除了方法名，和Comparator<String> int compare(String, String)参数一致返回类型一致
     * @param s1
     * @param s2
     * @return
     */
    public static int cmp(String s1, String s2) {
        return s1.compareTo(s2);
    }
    static class LocalDateSupplier implements Supplier<LocalDate> {
        LocalDate start = LocalDate.of(2020,1,1);
        int n = -1;
        @Override
        public LocalDate get() {
            n++;
            return start.plusDays(n);
        }
    }
}
