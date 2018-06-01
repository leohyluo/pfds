package com.iebm.pfds.commons.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultipleDataSourceRouter extends AbstractRoutingDataSource  {
	
	private static final ThreadLocal<String> dataSourceKey = new InheritableThreadLocal<String>();

	private MultipleDataSourceRouter(){};

	private static MultipleDataSourceRouter instance = new MultipleDataSourceRouter();


	public static MultipleDataSourceRouter getInstance(){
		return instance;
	}

	public static void setDataSourceKey(String dataSource) {
		dataSourceKey.set(dataSource);
	}

	public static void clearDataSourceKey(){
		dataSourceKey.remove();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKey.get();
	}

}
