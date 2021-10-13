/**
 * 2021-07-16
 *
 * @author tao.zhang
 * @since 1.0
 */
public class I220PowerImpl implements I220Power {
    @Override
    public int provide220Power() {
        System.out.println("我提供220V交流电压。");
        return 220;
    }
}
