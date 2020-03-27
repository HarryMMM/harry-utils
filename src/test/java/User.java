import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author Harry
 * @since 2019/8/12 15:48
 */
@Data
public class User {
    private String name;
    private String age;
    private List<String> hobby;
    private Map<String,Object> other;

}
