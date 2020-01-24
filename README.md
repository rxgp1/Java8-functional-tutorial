
Java 8 Functinal Programming Tutorial. 

---


Java 8 은 Lambda 표현식과 Stream API 를 이용하여 Functinal 프로그래밍을 지원한다. 
이 튜토리얼에서 사전에 정의된 Functional interfaces와 Collctions API 그리고 Stream API를 통해 
Java8 이 어떤식으로 Functioanl 프로그래밍을 지원하는지 알아본다.

- <div id="index">목차
- [1. Java8 Functional Programming 소개](#Introduction)
- [2. 사용법](#Technologies)
  - [2.1. Maven Project](#Maven)
- [3. 사전 정의된 Functional InterFaces](#PreInterface)
  - [3.1. Function](#Function)
  - [3.2. Predicate](#Predicate)
  - [3.3. Supplier](#Supplier)
  - [3.4. Consumer](#Consumer)
  - [3.5. UnaryOperator](#UnaryOperator)
  - [3.6. BinaryOperator](#BinaryOperator)
- [4. Functional Inteface 들을 상횡에 맞게 사용](#Customized)  
  - [4.1. IntegerCaculator](#IntegerCaculator)
  - [4.2. GreetingFunction](#GreetingFunction)
  - [4.3. Demo](#Demo)
- [5. Java 8 확장](#Enhancements)    
  - [5.1. Collections 과 Comparator](#Collections-Comparator)
  - [5.2. Stream](#Stream)
- [6. 예제](#Real-Example)    
  - [6.1. Contact](#Contact)
  - [6.2. LoginUser](#LoginUser)  
  - [6.3. DataUtil](#DataUtil)  
  - [6.4. ContactDao](#ContactDao)  
    - [6.4.1. ContactDaoTest](#ContactDaoTest)  
  - [6.5. Data Mapper](#Data-Mapper)    
    - [6.5.1. DataMapperTest](#DataMapperTest)  
  - [6.6. LoginUserService](#LoginUserService)      
    - [6.6.1. LoginUserServiceTest](#LoginUserServiceTest)  
- [7. 요약](#Summary)       
- [8. 원문](#References)   
- [9. 다운로드 예제 코드](#Download)  
---

### <div id="Introduction">1. Java8 Functional Programming 소개

Java 는 객체 지향 프로그래밍 언어이다.  
Java8 은 Lambda 표현식을 통해 Functional programming 을 지원한다. 

람다 표현식은 다음의 문법으로 특정 지어 진다.
`(콤마로 구분된 여러가지의 파라메터) -> {하나 이상의 바디 처리}`

JDK 의 컴파일러가 type interface 를 지원하기에 람다 표현식은 두가지 방법으로 짧은 표현이 가능하다. 

- 파라메터의 타입을 제외 할수 있다. 컴파일러는 파라메터의 값으로 타입을 유추 할수 있다.
- 람다 처리시 바디가 단일 표현식이면 `return` 키워드를 제외시킬수 있다.

또한 다음의 상황에서 좀 더 단순표현이 가능하다. 

- 단일 파라메터에서 괄호를 생략 할수 있다. 
- 표현식의 바디가 단일 처리일 경우 중괄호는 생략 할수 있다. 

Functional programming 은 파라메터나 Function 자체로 반환하여 전달하는 고기능 Function을 지원한다. 
또한 새로운 `stream` API 또한 고기능 Function을 지원한다. 

해당 튜토리얼에서는 Java8 에서 Functional 프로그래밍을 기본적으로 사전에 정의된 
인터페이스들과 `Collections`, 그리고 `Stream` API 로 설명한다.

### <div id="Technologies">2. 사용법

현재 글의 예제는 다음의 버전으로 빌드되고 실행된다. 

* Java 1.8.101 
* Maven 3.3.9
* Eclipse Oxygen 
* JUnit 4.12 

#### <div id="Maven">2.1. Maven Project
이번에는 Junit 라이브러리를 포함한 Maven Project 를 생성한다. 

`pom.xml`

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"<br>
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"<br>
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"><br>
    <modelVersion>4.0.0</modelVersion><br>
    <groupId>zheng.jcg.demo</groupId><br>
    <artifactId>java8-demo</artifactId><br>
    <version>0.0.1-SNAPSHOT</version><p></p>
<p> <dependencies><br>
        <dependency><br>
            <groupId>junit</groupId><br>
            <artifactId>junit</artifactId><br>
            <version>4.12</version><br>
            <scope>test</scope><br>
        </dependency><br>
    </dependencies><br>
    <build><br>
        <plugins><br>
            <plugin><br>
                <artifactId>maven-compiler-plugin</artifactId><br>
                <version>3.3</version><br>
                <configuration><br>
                    <source>1.8</source><br>
                    <target>1.8</target><br>
                </configuration><br>
            </plugin><br>
        </plugins><br>
    </build><br>
</project></p>
```


### <div id="PreInterface">3. 사전 정의된 Functional Interfaces

Functional Interface (FI) 는 `java.lang.object` 로부터 상속받지 않으며 하나의 abstract 메소드를 
가진 인터페이스 이다. 

Functional Programming 의 한가지 특징은 순수 Function 이다. 
순수 Function 은 입력과 출력을 처리한다. 
또한 해당 처리는 어떠한 상황에서도 변하지 않는 것을 목적으로 한다.  
그러므로 해당 처리는 어떠한 사이드 이팩트도 발생하지 않는다.  
항상 동일한 출력과 입력을 처리한다. 

Java 8 은 40가지 이상의 사전에 정의된 Functional interfaces 를 제공한다. `Consumer` 를 제외한 모든 FI 는 순수 Function들이다. 

Java8 참조 메소드는 람다식의 단 한가지 실행을 위해 짧게 표현할수 있다. 
개발자는 람다식이나 참조 메소드를 FI 로 사용할수 있다. 
Java8 Stream API 는 사전 정의된 FI들을 Steam 을 위해 사용할수 있다. 

이번 장에서는 공통 Functional Interface들을 어떤식으로 사용할지 JUnit 클래스를 생성하여 보여준다. 


#### <div id="Function">3.1. Function

Function FI 는 하나의 파라메터 받아 들이며 하나의 결과를 반환한다. 
Function FI 는 abstract 메소드인 `apply(Object)` 를 호출한다. 

Java8 은 원시 데이터 타입을 위해 각각 사용하기 용이한 FI들을 제공한다:  IntFunction, DoubleFunction, IntToDoubleFunction, IntToLongFunction, DoubleToIntFunction, DoubleToLongFunction, LongToDoubleFunction, 그리고 LongToIntFunction.

BitFunction FI 는 두가지의 파라메터를 매개변수로 받으며 처리된 결과를 리턴한다. 
BitFunction FI 는 abrtract 메소드인 `apply(Object, Object)` 를 호출한다. 

Java8 은 또한 두가지 파라메터를 매게 변수로 받으며 처리된 결과를 Double값, Int값, Long 값을 반환하는 
ToDoubleBiFunction, ToIntBiFunction, ToLongBiFunction 를 제공한다.

이번 장에서는 `FunctionTest.java` 클래스를 다음을 설명하기 위해 생성하였다. 

* `Integer` 클래스를 `String` 클래스로 변환
* 문자열의 길이를 `Integer` 형으로 반환
* 두가지 Function을 새로운 Function으로 결합
* 리스트의 속성들을 `Stream`-`map(Function <T, R>)` 를 통하여 변환한다.
* `IntFunction`, `DoubleFunction`, 등등의 사용법을 보여준다. 

`FunctionTest.java`

```java
 
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
 
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.DoubleToLongFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntToLongFunction;
import java.util.function.LongToDoubleFunction;
import java.util.function.LongToIntFunction;
import java.util.function.ToDoubleBiFunction;
import java.util.function.ToIntBiFunction;
import java.util.function.ToLongBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
 
import org.junit.Test;
 
import com.zheng.demo.DataUtil;
import com.zheng.demo.model.LoginUser;
 
public class FunctionTest {
 
    @Test
    public void BiFunction_concat_two_String() {
        BiFunction<String, String, String> concat = (a, b) -> a + b;
        String combinedStr = concat.apply("Today is", " a wonderful day");
        assertEquals("Today is a wonderful day", combinedStr);
    }
 
    @Test
    public void BiFunction_multiple_two_int() {
        BiFunction<Integer, Integer, Integer> concat = (a, b) -> a * b;
        Integer product = concat.apply(3, 4);
        assertEquals(12, product.intValue());
    }
 
    @Test
    public void DoubleFunction_convertDoubleToString_via_lambda() {
        DoubleFunction<String> doubleToString = num -> Double.toString(num);
 
        assertEquals("123.456", doubleToString.apply(123.456));
    }
 
    @Test
    public void DoubleToIntFunction_convertDoubleToInt_via_lambda() {
        DoubleToIntFunction doubleToInt = num -> (int) num;
 
        assertEquals(123, doubleToInt.applyAsInt(123.456));
    }
 
    @Test
    public void DoubleToLongFunction_convertDoubleToLong_via_lambda() {
        DoubleToLongFunction doubleToLongFunc = num -> (long) num;
 
        assertEquals(123789008080l, doubleToLongFunc.applyAsLong(123789008080.456));
    }
 
    @Test
    public void Function_combine_TwoFunctions() {
        Function<LoginUser, String> getUser = LoginUser::getUsertName;
        Function<String, String> toUpper = String::toUpperCase;
 
        Function<LoginUser, String> userNameMustBeUppercase = getUser.andThen(toUpper);
 
        assertEquals("MARY", userNameMustBeUppercase.apply( DataUtil.buildLoginUser("Mary", "pwd123")));
    }
 
    @Test
    public void Function_convertStringToInteger_via_methodReference() {
        Function<String, Integer> convertToWordCount = String::length;
        List<String> words = Arrays.asList("The", "That", "John", "Thanks");
 
        List<Integer> wordsCounts = words.stream().map(convertToWordCount).collect(Collectors.toList());
 
        assertEquals(3, wordsCounts.get(0).intValue());
        assertEquals(4, wordsCounts.get(1).intValue());
        assertEquals(4, wordsCounts.get(2).intValue());
        assertEquals(6, wordsCounts.get(3).intValue());
    }
 
    @Test
    public void IntFunction_convertIntegerToString_via_lambda() {
        IntFunction<String> intToString = num -> Integer.toString(num);
 
        assertEquals("123", intToString.apply(123));
    }
 
    @Test
    public void IntFunction_via_lambda() {
        IntFunction<Integer> powerValue = num -> num * num;
 
        assertEquals(9, powerValue.apply(3).intValue());
    }
 
    @Test
    public void IntToDoubleFunction_convertIntToDouble_via_lambda() {
        IntToDoubleFunction intToDoubleFunc = num -> (double) num;
 
        assertEquals(123, intToDoubleFunc.applyAsDouble(123), 0.1);
    }
 
    @Test
    public void IntToLongFunction_convertIntToLong_via_lambda() {
        IntToLongFunction intToLongFunc = num -> (long) num;
 
        assertEquals(123456, intToLongFunc.applyAsLong(123456));
    }
 
    @Test
    public void LongToDoubleFunction_convertLongToDouble_via_lambda() {
        LongToDoubleFunction longToDoubleFunc = num -> (double) num;
 
        assertEquals(123456, longToDoubleFunc.applyAsDouble(123456), 0.1);
    }
     
    @Test
    public void LongToIntFunction_convertLongToInt_via_lambda() {
        LongToIntFunction longToIntFun = num -> (int) num;
 
        assertEquals(123456, longToIntFun.applyAsInt(123456));
    }
    @Test
    public void stream_map_via_methodReference() {
        Map<String, List<String>> awards = new HashMap<>();
        awards.put("Mary", Arrays.asList("Math", "Spelling Bee"));
        awards.put("Tom", Arrays.asList("Basketball", "Spelling Bee"));
        awards.put("Allen", Arrays.asList("English", "Spelling Bee"));
 
        Function<String, String> convertKeyToUppercase = String::toUpperCase;
 
        List<String> uppercaseKeys = awards.entrySet().stream().map(e -> convertKeyToUppercase.apply(e.getKey()))
                .collect(Collectors.toList());
 
        assertTrue(uppercaseKeys.contains("MARY"));
        assertTrue(uppercaseKeys.contains("TOM"));
        assertTrue(uppercaseKeys.contains("ALLEN"));
    }
 
    @Test
    public void stream_map_with_lambda() {
        List<String> collected = Stream.of("Java", "Rocks").map(string -> string.toUpperCase())
                .collect(Collectors.toList());
 
        assertTrue(collected.contains("JAVA"));
        assertTrue(collected.contains("ROCKS"));
    }
 
    @Test
    public void ToDoubleBiFunction_power_two_int() {
        ToDoubleBiFunction<Integer, Integer> concat = (a, b) -> Math.pow(a, b);
        double powerRet = concat.applyAsDouble(5, 3);
        assertEquals(125.0, powerRet, 0.1);
    }
 
    @Test
    public void ToIntBiFunction_multiple_two_int() {
        ToIntBiFunction<Integer, Integer> concat = (a, b) -> a * b;
        Integer product = concat.applyAsInt(3, 4);
        assertEquals(12, product.intValue());
    }
     
    @Test
    public void ToLongBiFunction_power_two_int() {
        ToLongBiFunction<Integer, Integer> concat = (a, b) -> (long) Math.pow(a, b);
        Long powerRet = concat.applyAsLong(5, 3);
        assertEquals(125, powerRet.intValue());
    }
}
```

#### <div id="Predicate">3.2. Predicate

Predicate FI 는 하나의 매게변수를 받아 들이며 `Boolean` 값을 반환한다. 
Predicate FI 의 abstract 메소드는 `test(Object)` 이다.
BiPredicate FI 는 두가지의 매게 변수를 받아들이며 `Boolean` 값을 반환한다. 
Java8 은 또한 원시 데이터 타입을 위한  IntPredicate, LongPredicate, 그리고 DoublePredicate 을 제공한다. 

이번 장에서는 다음을 설명하기 위해 `PredicateTest.java` 를 생성한다. 

* `Integer`가 숫자 형태가 맞는지 체크한다. 
*  리스트의 속성을 `Stream` - `filter(Predicate <T, R>)` 을 이용하여 필터링 한다. 
* 두가지 Predicate를 하나의 Predicate로 합친다. 
* `Long` 값을 3으로 나눌수 있는지 체크한다. 
* `Double` 값을 값에 맞게 처리되는지 체크한다. 
* 최초 매게 변수 `Integer` 값이 두번째 매게변수 값보다 큰지 체크한다. 
* `IntPredicate` 와 `DoublePredicate` 의 사용법을 보여준다.

`PredicateTest.java`

```java
 
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
 
import java.util.function.BiPredicate;
import java.util.function.DoublePredicate;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;
 
import org.junit.Test;
 
public class PredicateTest {
 
    @Test
    public void BiPredicate_whichIsBigger() {       
        BiPredicate<Integer, Integer> isBigger = (x, y) -> x > y;
        assertTrue(isBigger.test(5, 4));
        assertTrue(isBigger.negate().test(4, 5));
    }
 
    @Test
    public void DoublePredicate_test_isPositive() {
        DoublePredicate isPositive = x -> x > 0;
        assertTrue(isPositive.test(1.5));
        assertFalse(isPositive.test(-1.7));
    }
 
    @Test
    public void IntPredicate_test_isNagative() {
        IntPredicate isNagative = x -> x < 0;
        assertTrue(isNagative.test(-1));
        assertFalse(isNagative.test(1));
    }
 
    @Test
    public void LongPredicate_test_isDivisibleByThree() {
        LongPredicate isDivisibleBy3 = x -> x % 3 == 0;
 
        assertTrue(isDivisibleBy3.test(12));
        assertFalse(isDivisibleBy3.test(11));
    }
 
    @Test
    public void Predicate_combine_two_predicates() {
        // takes one argument and return a boolean
        Predicate<String> stringIsLongerThanTen = s -> s.length() > 10;
        assertTrue(stringIsLongerThanTen.test("This string is longer than 10"));
        assertFalse(stringIsLongerThanTen.test("short"));
 
        Predicate<String> stringStartWithA = s -> s.startsWith("A");
        assertTrue(stringStartWithA.test("Apple is a fruit"));
 
        Predicate<String> startWithAandLongerThan10 = stringIsLongerThanTen.and(stringStartWithA);
        assertTrue(startWithAandLongerThan10.test("Apple is a fruit which grows everywhere."));
    }
 
    @Test
    public void Predicate_test_integer_isEven() {
        Predicate<Integer> isEven = s -> s % 2 == 0;
        assertTrue(isEven.test(4));
        assertFalse(isEven.test(5));
    }
 
    @Test
    public void stream_filter_via_lambda() {
        Stream.of("Apple", "Pear", "Banana", "Cherry", "Apricot").filter(fruit -> {
            System.out.println("filter:" + fruit);
            return fruit.startsWith("A");
        }).forEach(fruit -> System.out.println("Started with A:" + fruit));
    }
     
}
```


#### <div id="Supplier">3.3. Supplier

Supplier FI 는 매게변수가 없으나 결과 값은 반환한다. 
Supplier FI 의 abstract method 는 `get()` 이다. 
보통, Java8 에서 원시 데이터 타입을 사용하기 편하게 Interface를 제공한다 : IntSupplier, DoubleSupplier, BooleanSupplier, 그리고 LongSupplier.

이번장에서는 `SupplierTest.java` 클래스를 다음을 설명하기 위해 생성한다. 

* `string` 값을 반환한다. 
* `true` 값을 반환한다. 
* 최대 `Integer` 값을 반환한다. 
* 최대 `Long` 값을 반환한다. 
* `pi` 값을 반환한다. 

`SupplierTest.java`

```java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
 
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
 
import org.junit.Test;
 
public class SupplierTest {
 
    @Test
    public void BooleanSupplier_getAsBoolean() {
        BooleanSupplier booleanSupplier = () -> true;
        assertTrue(booleanSupplier.getAsBoolean());
    }
 
    @Test
    public void DoubleSupplier_getAsDouble() {
        DoubleSupplier pi = () -> Math.PI;
        assertEquals(3.14, pi.getAsDouble(), 0.01);
    }
 
    @Test
    public void IntSupplier_getAsInt() {
        IntSupplier maxInteger = () -> Integer.MAX_VALUE;
        assertEquals(2147483647, maxInteger.getAsInt());
    }
     
    @Test
    public void LongSupplier_getAsLong() {
        LongSupplier maxLongValue = () -> Long.MAX_VALUE;
        assertEquals(9223372036854775807l, maxLongValue.getAsLong());
    }
     
    @Test
    public void Supplier_AString() {
        Supplier<String> message = () -> "Mary is fun";
        assertEquals("Mary is fun", message.get());
    }
}

```

#### <div id="Consumer">3.4. Consumer

Consumer FI 는 하나의 매게 변수를 가지지만 결과값을 반환하지 않는다. 
Consumer FI 의 abstract method 는 `accept(Object)` 이다. 
보통, Java 또한 원시 데이터 타입을 사용하기 편하게 Interface를 제공한다: IntConsumer, LongConsumer, DoubleConsumer, BiConsumer, ObjtIntConsumer, ObjLongConsumer, and ObjDoubleconsumer.

주의: XX`Consumer` FI들은 사이드 이팩트를 허용하게끔 설계 되어 있다.

이번 장에서는 `ConsumerTest.java` 클래스를 다음을 설명하기 위해 생성한다. 

* 소문자를 변환한 이후 문자열을 출력한다. 
* 문자열을 출력한다. 
* 두가지의 문자열을 출력한다. 
* 사람들과 만난 일 이후의 년도를 출력한다. 
* 구의 둘레를 계산한다. 

`ConsumerTest.java`

```java
import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
 
import org.junit.Test;
 
import com.zheng.demo.DataUtil;
import com.zheng.demo.model.Contact;
 
public class ConsumerTest {
 
    @Test
    public void BiConsumer_printout() {
        BiConsumer<String, String> echo = (x, y) -> {
            System.out.println(x);
            System.out.println(y);
        };
        echo.accept("This is first line.", "Here is another line");
    }
 
    @Test
    public void Consumer_convertToLowercase_via_lambda() {
        Consumer<String> convertToLowercase = s -> System.out.println(s.toLowerCase());
        convertToLowercase.accept("This Will convert to all lowercase");
    }
 
    @Test
    public void Consumer_print_prefix() {
        Consumer<String> sayHello = name -> System.out.println("Hello, " + name);
        for (String name : Arrays.asList("Mary", "Terry", "John")) {
            sayHello.accept(name);
        }
    }
 
    @Test
    public void Consumer_print_via_methodreferce() {
        Consumer<String> output = System.out::println;
        output.accept("This will be printed out.");
    }
 
    @Test
    public void DoubleConsumer_printout() {
        DoubleConsumer echo = System.out::println;
        echo.accept(3.3);
    }
 
    @Test
    public void IntConsumer_printout() {
        IntConsumer echo = System.out::println;
        echo.accept(3);
    }
 
    @Test
    public void LongConsumer_printout() {
        LongConsumer echo = System.out::println;
        echo.accept(3l);
    }
 
    @Test
    public void ObjDoubleConsumer_caculate_circle_circumference() {
        ObjDoubleConsumer<Double> circleCircumference = (r, p) -> System.out.println("Circumference: " + 2 * r * p);
 
        circleCircumference.accept(new Double(4.0), Math.PI);
    }
 
    @Test
    public void ObjIntConsumer_alterContactAge() {
        ObjIntConsumer<Contact> addThreeYear = (c, a) -> {
            c.setAge(c.getAge() + a);
            System.out.println("Updated contact" + c);
        };
 
        addThreeYear.accept(DataUtil.buildContact("mzheng", "pwd", 40), 3);
 
    }
 
    @Test
    public void ObjLongConsumer() {
        ObjLongConsumer<String> appendex = (m, l) -> {
            System.out.println("Append " + m + l);
        };
        appendex.accept("test message", 10l);
    }
 
}
```

#### <div id="UnaryOperator">3.5. UnaryOperator

UnaryOperator FI 는 특별히 피 연산자와 결과가 동일한 Function 이다.
UnaryOperator FI 의 abstract method 는 `apply(Object)` 이다. 
보통, Java8은 원시 타입을 위해 각각의 클래스를 제공한다.: IntUnaryOperator, DoubleUnaryOperator, 그리고 LongUnaryOperator.

이번 장에서는 `UnaryOperatorTest.java` 클래스를 다음을 설명하기 위해 생성한다. 

* 문자열을 대문자 포멧으로 변환한다.
* 문자열을 다른 문자열과 합친다. 
* 숫자열 값을 Double 값으로 반환한다. 
* 매게변수의 값을 연산이후 Long 형값으로 반환한다.
* 매게변수의 값을 연산이후 Double 형값으로 반환한다.


`UnaryOperatorTest.java`
```java

import static org.junit.Assert.assertEquals;
 
import java.util.function.DoubleUnaryOperator;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;
import java.util.function.UnaryOperator;
 
import org.junit.Test;
 
public class UnaryOperatorTest {
 
    @Test
    public void UnaryOperator_convertToUppdercase_via_lamdba() {
        UnaryOperator<String> convertToUppercase = msg -> msg.toUpperCase();
         
        String uppString = convertToUppercase.apply("this will be all uppercase");
         
        assertEquals("THIS WILL BE ALL UPPERCASE", uppString);
    }
 
    @Test
    public void UnaryOperator_concatString_via_methodReference() {
        UnaryOperator<String> sayHi = "Hi, "::concat;
         
        String concatString = sayHi.apply("Mary");
         
        assertEquals("Hi, Mary", concatString);
    }
     
    @Test
    public void IntUnaryOperator_doubleIt() {
        IntUnaryOperator doubledIt = x -> x * 2;
        assertEquals(24, doubledIt.applyAsInt(12));
    }
     
    @Test
    public void LongUnaryOperator_squareIt() {
        LongUnaryOperator squareIt = x -> x * x;
        assertEquals(144, squareIt.applyAsLong(12));
    }
     
    @Test
    public void DoubleUnaryOperator_squareIt() {
        DoubleUnaryOperator squareIt = x -> x * x;
        assertEquals(144, squareIt.applyAsDouble(12), 0.1);
    }
 
}
```


#### <div id="BinaryOperator">3.6. BinaryOperator

BinaryOperator FI 는 특별히 피 연산자와 결과가 동일한 Function 이다.
BinaryOperator FI 의 abstract method 는 `apply(Object)` 이다. 
보통, Java8은 `int`, `long`, `double` 각각의 데이터 타입으로서 IntBinaryOperator, LongBinaryOperator, 그리고 DoubleBinaryOperator 를 제공한다. 

이번 장에서는 `BinaryOperatorTest.java` 클래스를 다음을 설명하기 위해 생성한다. 

* 두개의 숫자를 더한다. 
* 다양한 숫자 타입을 더한다. 
* 두 숫자의 거듭 제곱을 처리한다. 

`BinaryOperatorTest.java`
```java
import static org.junit.Assert.assertEquals;
 
import java.util.function.BinaryOperator;
import java.util.function.IntBinaryOperator;
import java.util.function.LongBinaryOperator;
import java.util.function.DoubleBinaryOperator;
 
import org.junit.Test;
 
public class BinaryOperatorTest {
 
    @Test
    public void BinaryOperator_add_via_lambda() {
        BinaryOperator<Integer> add = (a, b) -> a + b;
 
        Integer sum = add.apply(10, 12);
 
        assertEquals(22, sum.intValue());
    }
 
    @Test
    public void IntBinaryOperator_add_two_numbers() {
        IntBinaryOperator add2 = (a, b) -> a + b;
 
        int sum = add2.applyAsInt(10, 12);
 
        assertEquals(22, sum);
    }
 
    @Test
    public void LongBinaryOperator_mutiple_two_numbers() {
        LongBinaryOperator add2 = (a, b) -> a * b;
 
        long product = add2.applyAsLong(10, 12);
 
        assertEquals(120, product);
    }
 
    @Test
    public void DoubleBinaryOperator_power_two_number() {
        DoubleBinaryOperator add2 = (a, b) -> Math.pow(a, b);
 
        double powerRet = add2.applyAsDouble(10, 2);
 
        assertEquals(100, powerRet, 001);
    }
 
}

```

### <div id="Customized">4. Functional Inteface 들을 상횡에 맞게 사용

Java8 은 인터페이스를 FI 로 표시하는 새로운 어노테이션 `@FunctionalInterface` 를 제공한다. 
Java 컴파일러는 표시된 인터페이스가 하나이상의 abstract 메소드를 가질때 예외를 던진다. 

이번 장에서는 두개의 FI를 생성하여 Java 어플리케이션에서 사용되는지 다음의 사례로 보여진다.

* `IntegerCalculator` 클래스는 `@FunctionalInterface` 어노테이션을 가진다. 
* `GreetFunction` 클래스는 `@FunctionalInterface` 어노테이션 없이 처리된다. 

#### <div id="IntegerCaculator">4.1. IntegerCaculator

이번 장에서는 `IntegerCalcuator.java` 를 하나의 메소드인 `calcuate` 와 함께 생성한다. 


`IntegerCalcuator.java`
```java
 
@FunctionalInterface
public interface IntegerCalculator {    
    int caculate(int x, int y); 
}

```

#### <div id="GreetingFunction">4.2. GreetingFunction

이번 장에서는 `GreetingFunction.java` 를 하나의 메소드인 `speak` 와 함께 생성한다. 
Java 컴파일러는 해당 클래스를  `@FunctionalInterface` 어노테이션 없이 FI 로 취급한다. 

`GreetingFunction.java`

```java
public interface GreetingFunction {
    void speak(String message);
}
```

#### <div id="Demo">4.3. Demo

이번 장에서는 `FPDemo.java` 클래스를 다음을 설명하기 위해 생성한다. 

* 두개의 숫자가 더하기, 빼기, 곱하기 계산이 되는지에 대해 처리 
* 어떤식으로 사람을 초대하는지 

`FPDemo.java`

```java
public class FPDemo {
    public static void main(String[] args) {
        GreetingFunction greeting = message -> System.out.println("Hello " + message + "!");
        greeting.speak("Tom");
        greeting.speak("Mary");
 
        caculateTwoNumbers(3, 4);
        caculateTwoNumbers(3, 0);
    }
 
    private static void caculateTwoNumbers(int x, int y) {
        IntegerCalculator add = (a, b) -> a + b;
        IntegerCalculator diff = (a, b) -> a - b;
        IntegerCalculator divide = (a, b) -> (b == 0 ? 0 : a / b);
 
        System.out.println(x + " + " + y + " = " + add.caculate(x, y));
        System.out.println(x + " - " + y + " = " + diff.caculate(x, y));
        System.out.println(x + " / " + y + " = " + divide.caculate(x, y));
    }
}
```


### <div id="Enhancements">5. Java8 확장.

Java8 은 `Collection` 클래스를 `sort`, `max`, `min` 메소드를 활용하여 확장 한다. 
해당 메소드 들은 `comparator` 를 파라메터로 Functional interface 를 처리할수 있다. 

Java8 `Stream` 클래스 API 는 매게 변수로 Functional interface 를 받아 들이는 메소드인 
`map`, `filter`, `sorted`, `min`, `max`, `reduce` 를 제공한다.   


#### <div id="Collections-Comparator">5.1. Collections 과 Comparator

Collections 클래스와 Comparator 인터페이스는 Java8 에서 확장 되었다. 
`Comparator` 는  `@FunctionalInterface` 어노테이션이 포함되었다. 
또한  `Collections`의 `sort` 메소드는 `Comparator`의 매게 변수로 처리 된다. 

이번 장에서는 `CollectionsTest.java` 를 다음의 경우를 설명하기 위해 생성한다. 

* 연락처를 나이순으로 정렬한다. 
* 문자열의 리스트를 정렬한다. 
* JDK7에서의 정렬처리와 비교한다. 

`CollectionsTest.java`

```java
import static org.junit.Assert.assertEquals;
 
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
 
import org.junit.Test;
 
import com.zheng.demo.dao.ContactDao;
import com.zheng.demo.model.Contact;
 
public class CollectionsTest {
    private ContactDao contDao = new ContactDao();
 
    @Test
    public void Collections_sort_by_contact_age() {
        Comparator<Contact> contactComparator = Comparator.comparing(Contact::getAge);
        List<Contact> contacts = contDao.findAllContacts();
 
        Collections.sort(contacts, contactComparator);
 
        System.out.println("Sorted contact");
        contacts.stream().forEach(System.out::println);
         
        Contact oldertContact = Collections.max(contacts, contactComparator );
        assertEquals(57, oldertContact.getAge());
         
        Contact youngestContact = Collections.min(contacts, contactComparator );
        assertEquals(21, youngestContact.getAge());
 
    }
 
    @Test
    public void Collections_sortWithInferType() {
        List<String> names = Arrays.asList("Allen", "Matt", "Mary", "Megan", "Alex");
        Collections.sort(names, (a, b) -> a.compareTo(b));
        System.out.println("Sorted names: " + names);       
    }
 
    @Test
    public void sortBeforeJava8() {
        List<String> names = Arrays.asList("Allen", "Matt", "Mary", "Megan", "Alex");
     
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
    }
 
}

```


#### <div id="Stream">5.2. Stream

Java8 Stream API 는 속성들의 컬렉션 처리와 반복문을 직관적으로 처리한다. 
개발자는 더이상 find, search, 객체의 속성을 컬렉션에서 필터링 하는것을 사용하지 않아도 된다. 

이번 장에서는 `StreamTest.java` 클래스를 다음을 설명하기 위해 생성한다. 

* `For` 반복문으로 객체 속성들을 반복처리 한다. 
* `Iterator` 로 객체 속성들을 반복 처리한다. 
* `Stream` API 의 `foreach(Consumer<T>)` 로 처리한다. 
* 객체의 리스트를 특정 객체 속성으로 필터링 처리한다. 
* 객체의 리스트 안에 있는 객체 속성들을 변경한다. 
* 객체의 리스트에서 최소값과 최대값의 값들을 필터링 하고 또한 정렬한다. 

`StreamTest.java`

```java

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
 
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
 
import org.junit.Before;
import org.junit.Test;
 
public class StreamTest {
    private List<String> userNames;
 
    @Test
    public void search() {
        Predicate<String> startWithA = name -> name.startsWith("a");
        List<String> startWithANames = userNames.stream().filter(startWithA).collect(Collectors.toList());
        assertEquals("aWang", startWithANames.get(0));
    }
 
    @Test
    public void IntStream_sum() {
        int sum = IntStream.of(1, 3, 5, 7, 9).sum();
        assertEquals(25, sum);
    }
 
    @Test
    public void tranform() {
        List<String> uppercaseNames = userNames.stream().map(String::toUpperCase).collect(Collectors.toList());
        assertTrue(uppercaseNames.contains("MZHENG"));
        assertTrue(uppercaseNames.contains("AWANG"));
        assertTrue(uppercaseNames.contains("TCHANG"));
    }
 
    @Test
    public void min() {
        Comparator<String> comparator =  Comparator.comparing(String::length);
        Optional<String> shortestName = userNames.stream().min(comparator );
        assertTrue(shortestName.isPresent());
        assertEquals("aWang", shortestName.get());
         
        Optional<String> longestName = userNames.stream().max(comparator );
        assertTrue(longestName.isPresent());
        assertEquals("mzheng", longestName.get());
         
    }
 
    @Test
    public void print_elelments_via_loop() {
        for (String name : userNames) {
            System.out.println(name);
        }
    }
 
    @Test
    public void print_elements_via_Iterator() {
        Iterator<String> i = userNames.iterator();
        while (i.hasNext()) {
            System.out.println(i.next());
        }
    }
 
    @Test
    public void print_elemetns_via_Stream() {
        // Internal iteration
        userNames.stream().forEach(System.out::println);
    }
 
    @Before
    public void setup() {
        userNames = Stream.of("mzheng", "tChang", "aWang").collect(Collectors.toList());
    }
 
    @Test
    public void sort() {
        List<String> sortedNames = userNames.stream().sorted().collect(Collectors.toList());
        assertEquals("aWang", sortedNames.get(0));
        assertEquals("mzheng", sortedNames.get(1));
        assertEquals("tChang", sortedNames.get(2));
    }
 
}
```

### <div id="Real-Example">6. 예제

해당 블로그 글에서 각각의 Java 사전 정의된 Function interface 들을 테스트 했었고, 
두개의 각각 수정된 Functiona interface 들을 테스트 했으며, 
`Comparator`, `Collections`, 그리고 `Stream` 의 처리를 확인했다. 
이번 장에서는 실제로 사용되는 사례를 설명 한다. 

해당 예제의 어플리케이션에서는 다음 두가지의 요구사항이 있다고 가정한다. 

* 데이터 베이스에서 연락처를 가져온다. 
* 로그인 유저의 연락처를 변경한다. 


#### <div id="Contact">6.1. Contact

이번 장에서는 성, 이름, 나이, 유저 이름, 비밀번호를 포함한 `Contact.java` 클래스를 생성한다. 

`Contact.java`

```java
public class Contact {
 
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private int age;
 
    public Contact() {
        super();
    }
 
    public Contact(String firstName, String lastName, String userName, String password, int age) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.age = age;
    }
 
    public String getFirstName() {
        return firstName;
    }
 
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
 
    public String getLastName() {
        return lastName;
    }
 
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
 
    public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public int getAge() {
        return age;
    }
 
    public void setAge(int age) {
        this.age = age;
    }
 
    @Override
    public String toString() {
        return "Contact [firstName=" + firstName + ", lastName=" + lastName + ", userName=" + userName + ", password="
                + password + ", age=" + age + "]";
    }
 
}
```

#### <div id="LoginUser">6.2. LoginUser

이번 장에서는 username 과 password를 포함한 `LoginUser.java` 클래스를 생성한다.

`LoginUser.java` 

```java
public class LoginUser {
    private String userName;
    private String password;
 
    public String getUsertName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    @Override
    public String toString() {
        return "LoginUser [userName=" + userName + ", password=" + password + "]";
    }
}

```


#### <div id="DataUtil">6.3. DataUtil

이번 장에서는 `DataUtil.java` 클래스를 생성한다. 

`DataUtil.java`

```java

import java.util.ArrayList;
import java.util.List;
 
public class DataUtil {
    public static List<Contact> getListOfContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("Becky", "Zheng", "bzheng", "pwd1234@", 48));
        contacts.add(new Contact("Alex", "Change", "aChange", "pwd987$", 21));
        contacts.add(new Contact("Caleb", "Wang", "cWang", "pwd2345#", 57));
        return contacts;
    }
 
    public static Contact buildContact(String username, String pwd, int age) {
        Contact cnt = new Contact();
        cnt.setUserName(username);
        cnt.setPassword(pwd);
        cnt.setAge(age);
        return cnt;
    }
     
    public static LoginUser buildLoginUser(String userName, String pwd) {
        LoginUser user = new LoginUser();
        user.setUserName(userName);
        user.setPassword(pwd);
        return user;
    }
     
    public static LoginUser toUser(Contact contact) {
        LoginUser user = new LoginUser();
        user.setPassword(contact.getPassword());
        user.setUserName(contact.getUserName().toUpperCase());
        return user;
    }
}
```


#### <div id="ContactDao">6.4. ContactDao

이번 장에서는 모든 연락처를 찾는 메소드를 포함한  `ConctactDao.java` 클래스를 생성한다. 

`ContactDao.java`

```java
package com.zheng.demo.dao;
 
import java.util.List;
 
import com.zheng.demo.model.Contact;
import com.zheng.demo.model.DataUtil;
 
public class ContactDao {
 
    public List<Contact> findAllContacts(){
        return DataUtil.getListOfContacts();
    }
}

```


#### <div id="ContactDaoTest">6.4.1. ContactDaoTest

이번 장에서는 `ContactDaoTest.java` 클래스를 생성한다. 

`ContactDaoTest.java`

```java
import static org.junit.Assert.assertEquals;
 
import java.util.List;
 
import org.junit.Test;
 
import com.zheng.demo.model.Contact;
 
public class ContactDaoTest {
 
    private ContactDao testClass = new ContactDao();
 
    @Test
    public void findAllContacts() {
        List<Contact> allContacts = testClass.findAllContacts();
        assertEquals(3, allContacts.size());
    }
}
```

#### <div id="Data-Mapper">6.5. Data Mapper

이번 장에서는 `Contact` 클래스를 `LoginUser` 클래스로 변환하는 `DataMapper` 클래스를 생성한다. 

`DataMapper.java` 

```java
import com.zheng.demo.model.Contact;
import com.zheng.demo.model.DataUtil;
import com.zheng.demo.model.LoginUser;
 
public class DataMapper {
    public LoginUser toUser(Contact contact) {      
        return DataUtil.toUser(contact);
    }
}
```

#### <div id="DataMapperTest">6.5.1. DataMapperTest

이번 장에서는 `DataMapperTest.java` 클래스를 생성한다. 

`DataMapperTest.java` 

```java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
 
import org.junit.Test;
 
import com.zheng.demo.model.Contact;
import com.zheng.demo.model.LoginUser;
 
public class DataMapperTest {
 
    private DataMapper dto = new DataMapper();
 
    @Test
    public void toUser() {
        Contact contact = new Contact("firstName", "lastName", "userName", "password", 40);
        LoginUser user = dto.toUser(contact);
        assertNotNull(user);
        assertEquals("USERNAME", user.getUsertName());
        assertEquals("password", user.getPassword());
    }
}
```


#### <div id="LoginUserService">6.6. LoginUserService

이번 장에서는 `Contact` 클래스를 `LoginUser` 클래스로 변환할수 있는 여러가지 방법이 처리되는 `LoginUserService.java` 클래스를 생성한다. 
해당 클래스는 각각의 기능이 있다. 

* Java 8 메소드 참조 (세가지 포멧이 포함된 버전)
* Java 8 람다 표현식 (세가지 포멧이 포함된 버전)
* Java 8 람다 표현식으로 명명된 기능 
* `Java For` 반복문


`LoginUserService.java`

```java
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
 
import com.zheng.demo.dao.ContactDao;
import com.zheng.demo.model.Contact;
import com.zheng.demo.model.DataUtil;
import com.zheng.demo.model.LoginUser;
 
public class LoginUserService {
 
    ContactDao contactDao = new ContactDao();
    DataMapper dto = new DataMapper();
 
    public List<LoginUser> getAllUser_java8Style_Lambda_1() {
        return contactDao.findAllContacts().stream().map(contact -> {
            LoginUser user = new LoginUser();
            user.setPassword(contact.getPassword());
            user.setUserName(contact.getUserName().toUpperCase());
            return user;
        }).collect(Collectors.toList());
    }
     
    public List<LoginUser> getAllUser_java8Style_Lambda_2() {
        return contactDao.findAllContacts().stream().map(c -> {
            return toUser(c);
        }).collect(Collectors.toList());
    }
     
    public List<LoginUser> getAllUser_java8Style_Lambda_3() {
        return contactDao.findAllContacts().stream().map(c -> toUser(c)).collect(Collectors.toList());
    }
 
    public List<LoginUser> getAllUser_java8Style_methodReference_1() {
        return contactDao.findAllContacts().stream().map(DataUtil::toUser).collect(Collectors.toList());
    }
 
    public List<LoginUser> getAllUser_java8Style_methodReference_2() {
        return contactDao.findAllContacts().stream().map(this::toUser).collect(Collectors.toList());
    }
 
    public List<LoginUser> getAllUser_java8Style_methodReference_best() {
        return contactDao.findAllContacts().stream().map(dto::toUser).collect(Collectors.toList());
    }
 
    public List<LoginUser> getAllUser_java8Style_namedLambda() {
        Function<Contact, LoginUser> convertContactToLoginUser = contact -> {
            return toUser(contact);
        };
        return contactDao.findAllContacts().stream().map(convertContactToLoginUser).collect(Collectors.toList());
    }
 
    public List<LoginUser> getAllUser_loopStyle() {
        List<Contact> allContacts = contactDao.findAllContacts();
        List<LoginUser> allUser = new ArrayList<>();
        for (Contact contact : allContacts) {
            allUser.add(toUser(contact));
        }
        return allUser;
    }
 
    private LoginUser toUser(Contact contact) {
        LoginUser user = new LoginUser();
        user.setPassword(contact.getPassword());
        user.setUserName(contact.getUserName().toUpperCase());
        return user;
    }
 
}
```

#### <div id="LoginUserServiceTest">6.6.1. LoginUserServiceTest

이번 장에서는 `LoginUserServiceTest.java` 클래스를 생성한다. 

`LoginUserServiceTest.java`

```java
import static org.junit.Assert.assertTrue;
 
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
 
import org.junit.Test;
 
import com.zheng.demo.model.LoginUser;
 
public class LoginUserServiceTest {
     
    private LoginUserService testService = new LoginUserService();
 
    @Test
    public void getAllUser_java8Style_Lambda_1() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_Lambda_1();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_java8Style_Lambda_2() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_Lambda_2();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_java8Style_Lambda_3() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_Lambda_3();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_java8Style_methodReference_1() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_methodReference_1();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_java8Style_methodReference_2() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_methodReference_2();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_java8Style_methodReference_best() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_methodReference_best();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_java8Style_namedLambda() {
        List<LoginUser> allusers = testService.getAllUser_java8Style_namedLambda();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
     
    @Test
    public void getAllUser_loopStyle() {
        List<LoginUser> allusers = testService.getAllUser_loopStyle();
        assertTrue(allusers.size() == 3);
         
        validate(allusers);     
    }
 
    private void validate(List<LoginUser> allusers) {
        Consumer<LoginUser> printOutUser = System.out::println;
        allusers.stream().forEach(printOutUser );
         
        Predicate<LoginUser> foundMary = e -> e.getUsertName().equalsIgnoreCase("bzheng") ;
        List<LoginUser> foundusers = allusers.stream().filter(foundMary ).collect(Collectors.toList());
        assertTrue(foundusers.size() == 1);
    }
}
```

### <div id="Summary">7. 요약.
해당 튜토리얼에서는 사전에 정의된 functional interface 들을 어떤식으로 사용하는지에 대해 보여줬으며, 
또한 각각 맞춤 Functional interface 의 사용법을 보여줬다. 

이후 `Stream` API 를 다루어 보았으며, 이후 실 처리에 사용할 수 있는 예제들을 처리했다. 

Functional interface 를 지원하는 Java 8 은 2014년 3월 18일에 릴리즈 되었다.  
Java 는 원래 Functional 프로그램을 지원하지 않았다. 

Functional programming 은 Function 에서 결과를 리턴하는것에 포커싱 되어 있으며 객체 지향보다 
좀 더 좋은 성능을 보여준다. 


### <div id="References">8. 원문

[Java 8 Functional Programming Tutorial By Mary Zheng](https://examples.javacodegeeks.com/core-java/java-8-functional-programming-tutorial)

영어 공부를 위해 퍼왔습니다. 저작권이 문제가 된다면 삭제 하겠습니다. 



### <div id="Download">9. 다운로드 예제 코드

해당 예제는 메이븐 프로젝트로 구성되어 있으며, JUnit test와 사전 정의된 Functional interface와 
Collections, Stream API 가 포함되어 있다. 

[GitHub](https://github.com/rxgp1/Java8-functional-tutorial)


