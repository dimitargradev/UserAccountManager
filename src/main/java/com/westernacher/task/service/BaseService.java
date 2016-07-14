package com.westernacher.task.service;

import java.util.List;

public interface BaseService<T, Serializable> {

	public T findOne(Serializable id);

	public List<T> findAll();

	public T save(T entity);

	public void delete(Serializable id);
}
