package hello.core.discount;

import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class RateDiscountPolicyTest {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP 10% 할인 적용")
    void vip_o(){
        //given
        Member vip = new Member(1L, "VIP", Member.Grade.VIP);
        //when
        int discount = discountPolicy.discount(vip, 10000);
        //then
        Assertions.assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP아니면 할인 적용X")
    void vip_x(){
        //given
        Member nomal = new Member(1L, "NOMAL", Member.Grade.NOMAL);
        //when
        int discount = discountPolicy.discount(nomal, 10000);
        //then
        Assertions.assertThat(discount).isEqualTo(0);
    }

}