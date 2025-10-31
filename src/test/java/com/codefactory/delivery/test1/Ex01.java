package com.codefactory.delivery.test1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class Ex01 {

    @Test
    @DisplayName("계산기의 더하기 기능을 테스트")
    void calculatorAddTest() {
        Calculator cal = new Calculator();
        int result = cal.add(10, 20);
        assertEquals(30, result);
    }

    @Test
    void sameEqualTest() {
        LocalDate date1 = LocalDate.of(2025, 12, 25);
        LocalDate date2 = LocalDate.of(2025, 12, 25);

        System.out.println(date1 == date2); // 주소 비교, 동일성 비교
        System.out.printf("%s, %s, %s%n",date1.equals(date2), date1.hashCode(), date2.hashCode());
        //assertEquals(date1, date2);// 동등성 비교, equals and hashCode()
        assertSame(date1, date2); // 동일성 비교, 주소 비교
    }

    @Test
    void loginEmailValidationTest() {
        LoginService service = new LoginService();
        BadRequestException thrown = assertThrows(BadRequestException.class, () -> {
            service.process("", null);
        });

        // 필드명에 맞는 예외가 발생했는지 추가 검증
        String message = thrown.getMessage();
        assertTrue(message.contains("이메일"));
    }
}
