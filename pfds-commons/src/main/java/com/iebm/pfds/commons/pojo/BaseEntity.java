package com.iebm.pfds.commons.pojo;

import java.io.Serializable;

import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@SuppressWarnings("serial")
//@MappedSuperclass//持久化时，基类属性会映射给子类
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "JavassistLazyInitializer", "handler" })
public abstract class BaseEntity implements Serializable {
	public static final int DEFAULT_PAGE = 1;
	public static final int DEFAULT_ROWS = 10;
//	protected long id;// 主键ID，通过JPA的@MappedSuperclass注解，使每个子类都能映射到这个ID属性，在父类中同一主键获取策略
	protected long[] ids;
	protected int page = 1;
	protected int rows = 10;
	protected int total;
	protected String draw = "1";// 阻止缓存;
	protected String sort;// 排序列
	protected String order;// 排序方式(desc or asc)
	protected int totalPage = 1;
	protected final static String ASC = "ASC";
	protected final static String DESC = "DESC";

	/**
	 * 通过在父类中加入JPA的@MappedSuperclass注解，使每个子类都能映射到这个ID属性，在父类中统一主键获取策略。
	 * 
	 * @return 主键ID
	 */
	/*@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, updatable = false, unique = true)
	public long getId() {
		return id;
	}
*/
	@Transient
	@JsonIgnore
	public long[] getIds() {
		return ids;
	}

	@Transient
	@JsonIgnore
	public int getPage() {
		if (page < 1) {
			this.page = 1;
		}
		// spring data jpa start with 0
		return page - 1;
	}

	@Transient
	@JsonIgnore
	public int getRows() {
		if(rows < 1) {
			rows = 1;
		}
		return rows;
	}

	@Transient
	@JsonIgnore
	public int getTotal() {
		return total;
	}

	@Transient
	@JsonIgnore
	public String getDraw() {
		return draw;
	}

	@Transient
	@JsonIgnore
	public String getSort() {
		return sort;
	}

	@Transient
	@JsonIgnore
	public String getOrder() {
		return order;
	}

	@Transient
	@JsonIgnore
	public int getTotalPage() {
		if (total != 0) {
			totalPage = (int) Math.ceil((float) total / (float) rows);
		}
		return totalPage;
	}

	/*public void setId(long id) {
		this.id = id;
	}*/

	public void setIds(long[] ids) {
		this.ids = ids;
	}

	public void setPage(int page) {
		if (page < 1) {
			this.page = 1;
			return;
		}
		this.page = page;

	}

	public void setRows(int rows) {
		if (rows < 1) {
			this.rows = 10;
			return;
		}
		this.rows = rows;
	}

	public void setTotal(int total) {
		if (total < 0) {
			this.total = 0;
			return;
		}
		this.total = total;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

}