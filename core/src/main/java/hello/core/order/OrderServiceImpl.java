package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    //    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //역할과 구현을 충실하게 분리함 (DiscountPolicy-> 역할, Rate, fix -> 구현)
    //다형성 활용, 구현객체 분리
    //OCP, DIP 와 같은 객체지향 설계원칙을 충실히 준수했다-> X
    //DIP: OrderServiceImpl은 DiscountPolicy 인터페이스에 의존하면서 DIP를 지킨 것 같은데?
    //-> 추상(인터페이스)뿐만 아니라 구체(구현)클래스에도 의존 하고있다. DIP위반
    //OrderServiceImpl의 소스코드도 함께 변경해야되기 때문에 OCP원칙도 위반
    //문제 해결을 위해 구현객체를 대신 생성해서 주입해줘야함
    //인터페이스에만 의존하도록 변경했으나 구현체가 없기 때문에 NullPointerException이 발생
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);
        return new Order(memberId,itemName,itemPrice,discountPrice);
    }
    //오더 서비스 입장에서는 할인에 대해서 알 수 없음,할인에 대한것은 discountPolicy에서 다 처리하고 결과만 던져줌 -> 단일책임 원칙을 잘 지켜줌
    //주문쪽을 고칠 필요가 없음, 단일 책임원칙이 잘 지켜지지 않았다면 할인과 관련된 변경을 해도 OrderService에서 해야될 수도 있음
}
