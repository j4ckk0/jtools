/**
 * 
 */
package com.jtools.tests.data.models;

import java.util.Date;

/**
 * @author j4ckk0
 *
 */
public class Book {

	private String title;
	private Person author;
	private Date dateOfPublication;
	private String editor;
	private String summary;
	private int numberOfPage;
	
	public Book() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Person getAuthor() {
		return author;
	}
	public void setAuthor(Person author) {
		this.author = author;
	}
	public Date getDateOfPublication() {
		return dateOfPublication;
	}
	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	public String getEditor() {
		return editor;
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getNumberOfPage() {
		return numberOfPage;
	}
	public void setNumberOfPage(int numberOfPage) {
		this.numberOfPage = numberOfPage;
	}
	
	@Override
	public String toString() {
		if(title != null && title.length() > 0) {
			if(author != null) {
				return title + " [" + author.toString() + "]";
			}
			return title;
		}
		return super.toString();
	}
}
