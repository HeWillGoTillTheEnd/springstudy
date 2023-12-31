package hello.core.discount;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.member.Member;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{

    private final int discountRate = 10;
    @Override
    public int discount(Member member, int price) {
        if(member.getGrade().equals(Member.Grade.VIP)) return price * discountRate/100;
        else return 0;

    }
}
