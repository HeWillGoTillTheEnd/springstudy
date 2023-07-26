package hello.core.discount;

import hello.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {

    private int discountFixAmount = 1000;

    @Override
    public int discount(Member member, int price) {
        if(member.getGrade().equals(Member.Grade.VIP)) return discountFixAmount;
        else return 0;
    }
}
