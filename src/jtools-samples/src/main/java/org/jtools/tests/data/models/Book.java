package org.jtools.tests.data.models;

/*-
 * #%L
 * Java Tools - Samples
 * %%
 * Copyright (C) 2024 jtools.org
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Date;
// TODO: Auto-generated Javadoc

/**
 * The Class Book.
 */
public class Book {

	/** The title. */
	private String title;
	
	/** The author. */
	private Person author;
	
	/** The date of publication. */
	private Date dateOfPublication;
	
	/** The editor. */
	private String editor;
	
	/** The summary. */
	private String summary;
	
	/** The number of page. */
	private int numberOfPage;
	
	/**
	 * Instantiates a new book.
	 */
	public Book() {
		super();
	}
	
	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Gets the author.
	 *
	 * @return the author
	 */
	public Person getAuthor() {
		return author;
	}
	
	/**
	 * Sets the author.
	 *
	 * @param author the new author
	 */
	public void setAuthor(Person author) {
		this.author = author;
	}
	
	/**
	 * Gets the date of publication.
	 *
	 * @return the date of publication
	 */
	public Date getDateOfPublication() {
		return dateOfPublication;
	}
	
	/**
	 * Sets the date of publication.
	 *
	 * @param dateOfPublication the new date of publication
	 */
	public void setDateOfPublication(Date dateOfPublication) {
		this.dateOfPublication = dateOfPublication;
	}
	
	/**
	 * Gets the editor.
	 *
	 * @return the editor
	 */
	public String getEditor() {
		return editor;
	}
	
	/**
	 * Sets the editor.
	 *
	 * @param editor the new editor
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	/**
	 * Gets the summary.
	 *
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}
	
	/**
	 * Sets the summary.
	 *
	 * @param summary the new summary
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	/**
	 * Gets the number of page.
	 *
	 * @return the number of page
	 */
	public int getNumberOfPage() {
		return numberOfPage;
	}
	
	/**
	 * Sets the number of page.
	 *
	 * @param numberOfPage the new number of page
	 */
	public void setNumberOfPage(int numberOfPage) {
		this.numberOfPage = numberOfPage;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
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
