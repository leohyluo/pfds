package com.iebm.pfds.commons.datasource;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;

/**
 * Created by lhkong on 2017/8/17.
 */
public class EnableConfigureationMultipleDataSourceImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        resolveDataSource(metadata);
        return new String[]{DataSourceBeanPostProcessor.class.getName(), MultipleDataSourceAspect.class.getName(), MultipleDataSourceConfig.class.getName()};
    }

    private void resolveDataSource(AnnotationMetadata metadata){
        MultipleDataSource source = AnnotationUtils.findAnnotation(((StandardAnnotationMetadata) metadata).getIntrospectedClass(),MultipleDataSource.class);
        DataSource[] dataSources = source.value();
        for( DataSource data: source.value()){
            MultipleDataSourceAspect.DATASOURCE_MAPPING.put(data.basePackage(),data.name());
        }
    }
}
