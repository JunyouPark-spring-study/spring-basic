package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
    //위와 같은 방법을 사용하면 에러를 체크할 수 있다.
    //코드를 추적하기 쉽다는 장점이 있다.
    //웬만하면 스프링이 제공하는 에노테이션을 사용하는 것이 유지보수가 쉽다.
}
