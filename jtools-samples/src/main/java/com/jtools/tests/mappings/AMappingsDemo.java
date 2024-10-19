/**
 * 
 */
package com.jtools.tests.mappings;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.ToolTipManager;

import com.jtools.generic.data.actions.CreateDataEditorAction;
import com.jtools.generic.data.actions.LoadDataAction;
import com.jtools.generic.data.provider.DataProviderChangeSupport;
import com.jtools.generic.data.provider.IDataProvider;
import com.jtools.mappings.editors.actions.block.BlockMappingCreateAction;
import com.jtools.mappings.editors.actions.block.BlockMappingExportToExcelAction;
import com.jtools.mappings.editors.actions.block.BlockMappingImportFromExcelAction;
import com.jtools.mappings.editors.actions.block.BlockMappingLoadAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingCreateAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingExportToExcelAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingExportToStdOutputAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingImportFromExcelAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingLoadAction;
import com.jtools.tests.actions.ClearStdOutputAction;
import com.jtools.tests.actions.ExitAction;
import com.jtools.tests.actions.ShowDefaultDataProviderAction;
import com.jtools.tests.actions.ShowStdOutputAction;
import com.jtools.tests.data.DataProviderSelector;
import com.jtools.tests.data.DefaultDataProvider;
import com.jtools.utils.gui.components.CascadeDesktopPane;

/**
 * @author j4ckk0
 *
 */
public abstract class AMappingsDemo extends JFrame implements PropertyChangeListener {

	// //////////////////////////////
	//
	// Attributes and constants
	//
	// //////////////////////////////

	private static final long serialVersionUID = 7125491173800101032L;

	private final JDesktopPane desktopPane;
	
	private final DataProviderSelector dataProviderSelector;
	
	private final DefaultDataProvider defaultDataProvider;

	private final CreateDataEditorAction createDataTableAction;
	private final LoadDataAction loadDataTableAction;

	private final SimpleMappingCreateAction createSimpleMappingAction;
	private final SimpleMappingLoadAction loadSimpleMappingAction;

	private final BlockMappingCreateAction createBlockMappingAction;
	private final BlockMappingLoadAction loadBlockMappingAction;

	private final SimpleMappingExportToStdOutputAction simpleMappingExportToStdOutputAction;
	private final SimpleMappingExportToExcelAction simpleMappingExportToExcelAction;

	private final BlockMappingExportToExcelAction blockMappingExportToExcelAction;

	private final SimpleMappingImportFromExcelAction simpleMappingImportFromExcelAction;

	private final BlockMappingImportFromExcelAction blockMappingImportFromExcelAction;

	// //////////////////////////////
	//
	// Methods
	//
	// //////////////////////////////

	public AMappingsDemo(Class<?>[] testObjectClasses) {
		super("Demo");

		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);

		JFrame.setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension(screenSize.width * 90 / 100, screenSize.height * 90 / 100));

		this.desktopPane = new CascadeDesktopPane();

		this.dataProviderSelector = new DataProviderSelector();
		desktopPane.add(dataProviderSelector);
		
		this.defaultDataProvider = new DefaultDataProvider(testObjectClasses);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu menu
		JMenu menuMenu = new JMenu("Menu");
		menuBar.add(menuMenu);

		JMenuItem exitItem = new JMenuItem(new ExitAction("Exit"));
		menuMenu.add(exitItem);

		// View menu
		JMenu viewMenu = new JMenu("View");
		menuBar.add(viewMenu);

		ShowStdOutputAction showStdOutputAction = new ShowStdOutputAction("Show std output", desktopPane);
		JMenuItem showStdOutputItem = new JMenuItem(showStdOutputAction);
		viewMenu.add(showStdOutputItem);

		ClearStdOutputAction clearStdOutputAction = new ClearStdOutputAction("Clear std output", showStdOutputAction);
		JMenuItem clearStdOutputItem = new JMenuItem(clearStdOutputAction);
		viewMenu.add(clearStdOutputItem);

		// Data menu
		JMenu dataMenu = new JMenu("Test data");
		menuBar.add(dataMenu);

		ShowDefaultDataProviderAction showDataTypesProviderAction = new ShowDefaultDataProviderAction(
				"Show/hide possible data classes", desktopPane, defaultDataProvider);
		JMenuItem showDataTypesProviderItem = new JMenuItem(showDataTypesProviderAction);
		dataMenu.add(showDataTypesProviderItem);

		dataMenu.add(new JSeparator());

		this.createDataTableAction = new CreateDataEditorAction("Create data table", testObjectClasses);
		JMenuItem createDataTableItem = new JMenuItem(createDataTableAction);
		dataMenu.add(createDataTableItem);

		this.loadDataTableAction = new LoadDataAction("Load data table", testObjectClasses);
		JMenuItem loadDataTableItem = new JMenuItem(loadDataTableAction);
		dataMenu.add(loadDataTableItem);

		// Simple mapping menu
		JMenu simpleMappingMenu = new JMenu("Simple mapping");
		menuBar.add(simpleMappingMenu);

		this.createSimpleMappingAction = new SimpleMappingCreateAction("Create simple mapping");
		JMenuItem createSimpleMappingItem = new JMenuItem(createSimpleMappingAction);
		simpleMappingMenu.add(createSimpleMappingItem);

		this.loadSimpleMappingAction = new SimpleMappingLoadAction("Load simple mapping");
		JMenuItem loadSimpleMappingItem = new JMenuItem(loadSimpleMappingAction);
		simpleMappingMenu.add(loadSimpleMappingItem);

		simpleMappingMenu.add(new JSeparator());

		this.simpleMappingExportToStdOutputAction = new SimpleMappingExportToStdOutputAction(
				"Export data to standard output");
		JMenuItem simpleMappingExportToStdOutputItem = new JMenuItem(simpleMappingExportToStdOutputAction);
		simpleMappingMenu.add(simpleMappingExportToStdOutputItem);

		this.simpleMappingExportToExcelAction = new SimpleMappingExportToExcelAction("Export data to Excel");
		JMenuItem simpleMappingExportToExcelItem = new JMenuItem(simpleMappingExportToExcelAction);
		simpleMappingMenu.add(simpleMappingExportToExcelItem);

		simpleMappingMenu.add(new JSeparator());

		this.simpleMappingImportFromExcelAction = new SimpleMappingImportFromExcelAction("Import data from Excel");
		JMenuItem simpleMappingImportFromExcelItem = new JMenuItem(simpleMappingImportFromExcelAction);
		simpleMappingMenu.add(simpleMappingImportFromExcelItem);

		// Block mapping menu
		JMenu blockMappingMenu = new JMenu("Block mapping");
		menuBar.add(blockMappingMenu);

		this.createBlockMappingAction = new BlockMappingCreateAction("Create block mapping");
		JMenuItem createBlockMappingItem = new JMenuItem(createBlockMappingAction);
		blockMappingMenu.add(createBlockMappingItem);

		this.loadBlockMappingAction = new BlockMappingLoadAction("Load block mapping");
		JMenuItem loadBlockMappingItem = new JMenuItem(loadBlockMappingAction);
		blockMappingMenu.add(loadBlockMappingItem);

		blockMappingMenu.add(new JSeparator());

		this.blockMappingExportToExcelAction = new BlockMappingExportToExcelAction("Export data to Excel");
		JMenuItem blockMappingExportToExcelItem = new JMenuItem(blockMappingExportToExcelAction);
		blockMappingMenu.add(blockMappingExportToExcelItem);

		blockMappingMenu.add(new JSeparator());

		this.blockMappingImportFromExcelAction = new BlockMappingImportFromExcelAction("Import data from Excel");
		JMenuItem blockMappingImportFromExcelItem = new JMenuItem(blockMappingImportFromExcelAction);
		blockMappingMenu.add(blockMappingImportFromExcelItem);

		//
		// Build frame
		//
		setContentPane(desktopPane);

		createDataTableAction.setDesktopPane(desktopPane);
		createSimpleMappingAction.setDesktopPane(desktopPane);
		loadSimpleMappingAction.setDesktopPane(desktopPane);
		createBlockMappingAction.setDesktopPane(desktopPane);
		loadBlockMappingAction.setDesktopPane(desktopPane);
		loadDataTableAction.setDesktopPane(desktopPane);
		simpleMappingImportFromExcelAction.setDesktopPane(desktopPane);
		blockMappingImportFromExcelAction.setDesktopPane(desktopPane);

		//
		// Register mappings demo as listener of actions
		//
		dataProviderSelector.addPropertyChangeListener(this);
		defaultDataProvider.addPropertyChangeListener(this);
		createDataTableAction.addPropertyChangeListener(this);
		loadDataTableAction.addPropertyChangeListener(this);
		simpleMappingImportFromExcelAction.addPropertyChangeListener(this);
		blockMappingImportFromExcelItem.addPropertyChangeListener(this);

		//
		// Data provider
		//
		simpleMappingExportToStdOutputAction.setDataProvider(defaultDataProvider);
		createSimpleMappingAction.setDataProvider(defaultDataProvider);
		simpleMappingExportToExcelAction.setDataProvider(defaultDataProvider);
		createBlockMappingAction.setDataProvider(defaultDataProvider);
		loadBlockMappingAction.setDataProvider(defaultDataProvider);

		//
		// Initial state
		//
		showDataTypesProviderAction.actionPerformed(null);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {

		if (evt.getPropertyName().equals(DataProviderChangeSupport.DATA_PROVIDER_CHANGED_PROPERTY)) {
			Object newValue = evt.getNewValue();
			if (newValue instanceof IDataProvider) {
				simpleMappingExportToStdOutputAction.setDataProvider((IDataProvider) newValue);
				createSimpleMappingAction.setDataProvider((IDataProvider) newValue);
				simpleMappingExportToExcelAction.setDataProvider((IDataProvider) newValue);

				createBlockMappingAction.setDataProvider((IDataProvider) newValue);
				blockMappingExportToExcelAction.setDataProvider((IDataProvider) newValue);
			}

			// Avoid having no data provider
			if (newValue == null) {
				simpleMappingExportToStdOutputAction.setDataProvider(defaultDataProvider);
				createSimpleMappingAction.setDataProvider(defaultDataProvider);
				simpleMappingExportToExcelAction.setDataProvider(defaultDataProvider);
				createBlockMappingAction.setDataProvider(defaultDataProvider);
			}
		}
	}

}
