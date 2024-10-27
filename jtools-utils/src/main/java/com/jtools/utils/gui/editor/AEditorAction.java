/**
 * 
 */
package com.jtools.utils.gui.editor;

import java.awt.Container;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;

/**
 * @author j4ckk0
 *
 */
public abstract class AEditorAction extends AbstractAction {

	private static final long serialVersionUID = 3954850547522284177L;

	protected JDesktopPane desktopPane;

	protected AEditorAction() {
		super();
	}

	protected AEditorAction(String name) {
		super(name);
	}

	protected AEditorAction(String name, Icon icon) {
		super(name, icon);
	}

	public JDesktopPane getDesktopPane() {
		return desktopPane;
	}

	public void setDesktopPane(JDesktopPane desktopPane) {
		this.desktopPane = desktopPane;
	}

	public void showEditor(AEditor mappingEditor) {
		if (desktopPane != null) {
			JInternalFrame editorFrame = mappingEditor.showEditorAsInternalFrame();
			
			desktopPane.add(editorFrame);
			
			Container parent = desktopPane.getParent();
			if(parent instanceof JTabbedPane) {
				((JTabbedPane)parent).setSelectedComponent(desktopPane);
			}
			
			editorFrame.moveToFront();
		} else {
			mappingEditor.showEditorAsDialog(null, true);
		}
	}

}
