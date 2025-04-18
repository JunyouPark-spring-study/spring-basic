package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Bean memberService -> new MemoryMemberRepository
//@Bean orderService -> new MemoryMemberRepository
//위의 호출은 마치 싱글톤이 깨지는 것처럼 보임
//하지만, @Configuration을 사용하면 CGLIB을 사용하여 싱글톤을 보장함. ex: AppConfig$$SpringCGLIB$$0
//결론 -> 이미 존재하는 Bean은 추가로 생성하지 않는다!

@Configuration
public class AppConfig {
    //Ioc, Inversion of Control 제어의 역전
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(
                memberRepository(),
                discountPolicy());
    }
    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
