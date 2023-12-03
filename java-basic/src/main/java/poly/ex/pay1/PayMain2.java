package poly.ex.pay1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PayMain2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PayService payService = new PayService();

        while (true) {
            System.out.println("결제를 시작합니다. 결제 수단을 선택해주세요.");
            System.out.println("1. 카카오페이");
            System.out.println("2. 네이버페이");
            System.out.println("3. 종료");
            System.out.print("선택: ");
            String option = br.readLine();

            if (option.equals("1")) {
                option = "kakao";
            } else if (option.equals("2")) {
                option = "naver";
            } else if (option.equals("3")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다.");
                continue;
            }

            System.out.print("결제 금액을 입력해주세요: ");
            int amount = Integer.parseInt(br.readLine());

            payService.processPay(option, amount);
        }
    }
}
