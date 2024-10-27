/**
 * 
 */
package com.jtools.gui.list;

import java.util.List;

import javax.swing.JList;

import com.jtools.gui.list.listModels.ObjectsListModel;

/**
 * @author j4ckk0
 *
 */
public class ObjectsList<E extends Object> extends JList<E> {

	private static final long serialVersionUID = -759785412202035062L;

	protected final Class<E> objectClass;

	public ObjectsList(Class<E> objectClass, List<E> initialValues) {
		this.objectClass = objectClass;

		ObjectsListModel<E> model = new ObjectsListModel<E>();
		setModel(model);

		if(initialValues != null) {
			model.addAll(initialValues);
		}
	}

	public List<E> getDataList() {
		return ((ObjectsListModel<E>)getModel()).getElements();
	}

	public Class<E> getDataClass() {
		return objectClass;
	}

}
