/**
 * 2021-01-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Component
public class TransFactory {
    @Resource
    private final Map<String, TransService> map = new ConcurrentHashMap<>(3);

    public TransService getResult(String type) {
        TransService transService = map.get(type);
        Assert.notNull(transService, "输入参数:%s,错误!", type);
        return transService;
    }
}
