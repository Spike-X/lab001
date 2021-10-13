/**
 * 2021-01-19
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Service("1")
public class ValServiceImpl implements TransService {

    @Override
    public boolean transform() {
        log.info("val转换成功");
        return true;
    }
}
