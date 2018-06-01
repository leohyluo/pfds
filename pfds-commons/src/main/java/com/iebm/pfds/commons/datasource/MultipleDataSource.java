package com.iebm.pfds.commons.datasource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 多数据源配置
 *
 * Created by lhkong on 2017/8/16.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(EnableConfigureationMultipleDataSourceImportSelector.class)
@ConditionalOnMissingBean(MajorMyBatisConfig.class)
public @interface MultipleDataSource {
    /**
     * 数据源数据
     * @return
     */
    DataSource[] value();
}
