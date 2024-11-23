package org.jtools.utils.gui.components;

/*-
 * #%L
 * Java Tools - Utils
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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
// TODO: Auto-generated Javadoc

/**
 * The Class ButtonColumn.
 */
public class ButtonColumn extends AbstractCellEditor
	implements TableCellRenderer, TableCellEditor, ActionListener, MouseListener
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7504937710534535059L;
	
	/** The table. */
	private JTable table;
	
	/** The action. */
	private Action action;
	
	/** The mnemonic. */
	private int mnemonic;
	
	/** The original border. */
	private Border originalBorder;
	
	/** The focus border. */
	private Border focusBorder;

	/** The render button. */
	private JButton renderButton;
	
	/** The edit button. */
	private JButton editButton;
	
	/** The editor value. */
	private Object editorValue;
	
	/** The is button column editor. */
	private boolean isButtonColumnEditor;
	
	/**
	 * Instantiates a new button column.
	 *
	 * @param table the table
	 * @param action the action
	 * @param column the column
	 */
	public ButtonColumn(JTable table, Action action, int column)
	{
		this.table = table;
		this.action = action;

		renderButton = new JButton();
		editButton = new JButton();
		editButton.setFocusPainted( false );
		editButton.addActionListener( this );
		originalBorder = editButton.getBorder();
		setFocusBorder( new LineBorder(Color.BLUE) );

		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(column).setCellRenderer( this );
		columnModel.getColumn(column).setCellEditor( this );
		table.addMouseListener( this );
	}

	/**
	 * Gets the focus border.
	 *
	 * @return the focus border
	 */
	public Border getFocusBorder()
	{
		return focusBorder;
	}
	
	/**
	 * Sets the focus border.
	 *
	 * @param focusBorder the new focus border
	 */
	public void setFocusBorder(Border focusBorder)
	{
		this.focusBorder = focusBorder;
		editButton.setBorder( focusBorder );
	}

	/**
	 * Gets the mnemonic.
	 *
	 * @return the mnemonic
	 */
	public int getMnemonic()
	{
		return mnemonic;
	}
	
	/**
	 * Sets the mnemonic.
	 *
	 * @param mnemonic the new mnemonic
	 */
	public void setMnemonic(int mnemonic)
	{
		this.mnemonic = mnemonic;
		renderButton.setMnemonic(mnemonic);
		editButton.setMnemonic(mnemonic);
	}

	/**
	 * Gets the table cell editor component.
	 *
	 * @param table the table
	 * @param value the value
	 * @param isSelected the is selected
	 * @param row the row
	 * @param column the column
	 * @return the table cell editor component
	 */
	@Override
	public Component getTableCellEditorComponent(
		JTable table, Object value, boolean isSelected, int row, int column)
	{
		if (value == null)
		{
			editButton.setText( "" );
			editButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			editButton.setText( "" );
			editButton.setIcon( (Icon)value );
		}
		else
		{
			editButton.setText( value.toString() );
			editButton.setIcon( null );
		}

		this.editorValue = value;
		return editButton;
	}

	/**
	 * Gets the cell editor value.
	 *
	 * @return the cell editor value
	 */
	@Override
	public Object getCellEditorValue()
	{
		return editorValue;
	}

//
//  Implement TableCellRenderer interface
/**
 * Gets the table cell renderer component.
 *
 * @param table the table
 * @param value the value
 * @param isSelected the is selected
 * @param hasFocus the has focus
 * @param row the row
 * @param column the column
 * @return the table cell renderer component
 */
//
	public Component getTableCellRendererComponent(
		JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		if (isSelected)
		{
			renderButton.setForeground(table.getSelectionForeground());
	 		renderButton.setBackground(table.getSelectionBackground());
		}
		else
		{
			renderButton.setForeground(table.getForeground());
			renderButton.setBackground(UIManager.getColor("Button.background"));
		}

		if (hasFocus)
		{
			renderButton.setBorder( focusBorder );
		}
		else
		{
			renderButton.setBorder( originalBorder );
		}

//		renderButton.setText( (value == null) ? "" : value.toString() );
		if (value == null)
		{
			renderButton.setText( "" );
			renderButton.setIcon( null );
		}
		else if (value instanceof Icon)
		{
			renderButton.setText( "" );
			renderButton.setIcon( (Icon)value );
		}
		else
		{
			renderButton.setText( value.toString() );
			renderButton.setIcon( null );
		}

		return renderButton;
	}

//
//  Implement ActionListener interface
//
	/**
 * Action performed.
 *
 * @param e the e
 */
/*
	 *	The button has been pressed. Stop editing and invoke the custom Action
	 */
	public void actionPerformed(ActionEvent e)
	{
		int row = table.convertRowIndexToModel( table.getEditingRow() );
		fireEditingStopped();

		//  Invoke the Action

		ActionEvent event = new ActionEvent(
			table,
			ActionEvent.ACTION_PERFORMED,
			"" + row);
		action.actionPerformed(event);
	}

//
//  Implement MouseListener interface
//
	/**
 * Mouse pressed.
 *
 * @param e the e
 */
/*
	 *  When the mouse is pressed the editor is invoked. If you then then drag
	 *  the mouse to another cell before releasing it, the editor is still
	 *  active. Make sure editing is stopped when the mouse is released.
	 */
    public void mousePressed(MouseEvent e)
    {
    	if (table.isEditing()
		&&  table.getCellEditor() == this)
			isButtonColumnEditor = true;
    }

    /**
     * Mouse released.
     *
     * @param e the e
     */
    public void mouseReleased(MouseEvent e)
    {
    	if (isButtonColumnEditor
    	&&  table.isEditing())
    		table.getCellEditor().stopCellEditing();

		isButtonColumnEditor = false;
    }

    /**
     * Mouse clicked.
     *
     * @param e the e
     */
    public void mouseClicked(MouseEvent e) {}
	
	/**
	 * Mouse entered.
	 *
	 * @param e the e
	 */
	public void mouseEntered(MouseEvent e) {}
    
    /**
     * Mouse exited.
     *
     * @param e the e
     */
    public void mouseExited(MouseEvent e) {}
}
