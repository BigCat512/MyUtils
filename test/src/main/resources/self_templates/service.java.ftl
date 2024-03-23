package ${package.Service};

import java.util.List;
import org.example.domain.dto.${entity}DTO;

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
public interface ${table.serviceName}{

    /**
     * 批量保存
     *
     * @param dto {@link List<${entity}DTO>}
     * @return {@link Integer}
     * @author ${author}
     * @since ${date}
     **/
    Integer insertBatch(List<${entity}DTO> dto);

}
</#if>
