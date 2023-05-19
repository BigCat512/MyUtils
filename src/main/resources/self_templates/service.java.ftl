package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
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
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
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
