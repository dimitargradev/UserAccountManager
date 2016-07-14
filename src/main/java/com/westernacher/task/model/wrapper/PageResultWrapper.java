package com.westernacher.task.model.wrapper;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageResultWrapper<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<T> entities;

	private int numberOfPages;

	public PageResultWrapper(List<T> listOfEntities) {
		this.entities = listOfEntities;
	}

	public PageResultWrapper(Page<T> page) {
		this.entities = page.getContent();
		this.numberOfPages = page.getTotalPages();
	}

	public List<T> getEntities() {
		return entities;
	}

	public void setEntities(List<T> entities) {
		this.entities = entities;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
}
