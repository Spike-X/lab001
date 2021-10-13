/**
 * 2021-07-16
 *
 * @author tao.zhang
 * @since 1.0
 */
public class Mobile {
    public static void main(String[] args) {
        I5Power v5PowerAdapter = new V5PowerAdapter(new I220PowerImpl());
        Mobile mobile = new Mobile();
        mobile.inputPower(v5PowerAdapter);
    }

    private void inputPower(I5Power power) {
        int result = power.provide5Power();
        System.out.println("手机（客户端）：我需要5V电压充电，现在是-->" + result + "V");
    }
}
