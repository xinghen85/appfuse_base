package com.btxy.basis.dao.common;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.btxy.basis.common.model.PaginatedListHelper;

public class Dao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public interface PageData<T> {
		public List<T> getList(long offset, long limit);
		public Long getTotleSize();
	}
	public <T> PaginatedListHelper<T> find(int currentPage, int pageSize,Sql sql,RowMapper<T> rowMapper){
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();

		Long totalSize = jdbcTemplate.queryForLong(sql.getSql(),sql.getObject());
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);

		if (totalSize > (currentPage - 1) * pageSize) {
			long offset=((currentPage - 1) * pageSize);
			long limit=pageSize;

			ph.setList(jdbcTemplate.query(sql.getLimitSql(),sql.getLimitObject(offset, limit),rowMapper));

			return ph;
		} else {
			return ph;
		}
	}
	public <T> PaginatedListHelper<T> find(int currentPage, int pageSize,PageData<T> pageData){
		PaginatedListHelper<T> ph = new PaginatedListHelper<T>();

		Long totalSize = pageData.getTotleSize();
		ph.setFullListSize(totalSize.intValue());
		ph.setPageNumber(currentPage);
		ph.setObjectsPerPage(pageSize);

		if (totalSize > (currentPage - 1) * pageSize) {
			long offset=((currentPage - 1) * pageSize);
			long limit=pageSize;

			ph.setList( pageData.getList(offset, limit));

			return ph;
		} else {
			return ph;
		}
	}
	public java.sql.Date c(java.util.Date date) {
		if(date!=null)
			return new java.sql.Date(date.getTime());
		else
			return null;
	}

	protected Timestamp t(java.util.Date date) {
		if(date!=null)
			return new java.sql.Timestamp(date.getTime());
		else
			return null;
	}
	protected long l(Long long_) {
		if(long_==null)
			return 0L;
		else
			return long_;
	}

	protected <T> T queryForObjectCanNull(String sql, Object[] args, RowMapper<T> rowMapper) {
		List<T> list = jdbcTemplate.query(sql,args,rowMapper);
		if(list.size()>1)
		{
			//String sqlMsg="sql:"+sql+args==null?"":args.toString();
			//log.error(sqlMsg);
			throw new RuntimeException("sql exe result size>1");
		}
		else if(list.size()==0)
			return null;
		else
			return list.get(0);
	}
}
