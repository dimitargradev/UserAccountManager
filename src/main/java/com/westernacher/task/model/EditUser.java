package com.westernacher.task.model;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A class that represents only the editable fields of the user model.
 */
public class EditUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;

	@Length(min = 5)
	@NotEmpty
	private String name;

	@Email
	@NotEmpty
	private String email;

	private boolean isEnabled = true;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
