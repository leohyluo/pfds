package com.iebm.pfds.commons.datasource;

/**
 * Created by lhkong on 2017/8/16.
 */
public @interface DataSource {
    /**
     * 数据源名字，与配置信息spring.datasource.alias 对应
     * @return
     */
    String name();

    /**
     * 该数据源对应的dao包路径
     * @return
     */
    String basePackage();
}
