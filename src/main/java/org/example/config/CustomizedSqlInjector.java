package org.example.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * <p>
 * <a href="https://blog.csdn.net/qq_39162487/article/details/122019120?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-0-122019120-blog-126038809.235">...</a>^v32^pc_relevant_increate_t0_download_v2&spm=1001.2101.3001.4242.1&utm_relevant_index=3
 * <p>
 *
 * @author XJH
 * @version 1.0
 * @since 2023/4/20
 */
public class CustomizedSqlInjector extends DefaultSqlInjector {
    /**
     * 如果只需增加方法，保留mybatis plus自带方法，
     * 可以先获取super.getMethodList()，再添加add
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        // 保留mybatis plus自带方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new InsertBatchMethod());
        methodList.add(new UpdateBatchMethod());
        return methodList;
    }
}
