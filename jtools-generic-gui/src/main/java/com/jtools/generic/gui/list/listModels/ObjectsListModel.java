/**
 * 
 */
package com.jtools.generic.gui.list.listModels;

import java.util.Collections;
import java.util.List;

import javax.swing.DefaultListModel;

/**
 * @author j4ckk0
 *
 */
public class ObjectsListModel<E extends Object> extends DefaultListModel<E> {

	private static final long serialVersionUID = -890234908906634753L;

	public List<E> getElements() {
		return Collections.list(elements());
	}

	@Override
	public int getSize() {
		return size();
	}

	@Override
	public E getElementAt(int index) {
		return get(index);
	}

}
