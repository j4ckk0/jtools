/**
 * 
 */
package com.jtools.tests.mappings;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.ToolTipManager;
import javax.swing.WindowConstants;

import com.formdev.flatlaf.FlatLightLaf;
import com.jtools.data.actions.CreateDataEditorAction;
import com.jtools.data.actions.LoadDataAction;
import com.jtools.data.gui.desktop.DataProviderSelector;
import com.jtools.data.gui.desktop.DefaultDataProvider;
import com.jtools.data.gui.desktop.ShowDataProviderSelectorAction;
import com.jtools.data.gui.desktop.ShowDefaultDataProviderAction;
import com.jtools.data.provider.DataProviderPubSub;
import com.jtools.data.provider.DataProviderRegistry;
import com.jtools.data.provider.IDataProvider;
import com.jtools.gui.desktop.ClearStdOutputAction;
import com.jtools.gui.desktop.ShowStdOutputAction;
import com.jtools.mappings.editors.actions.block.BlockMappingCreateAction;
import com.jtools.mappings.editors.actions.block.BlockMappingExportToExcelAction;
import com.jtools.mappings.editors.actions.block.BlockMappingImportFromExcelAction;
import com.jtools.mappings.editors.actions.block.BlockMappingLoadAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingCreateAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingExportToExcelAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingExportToStdOutputAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingImportFromExcelAction;
import com.jtools.mappings.editors.actions.simple.SimpleMappingLoadAction;
import com.jtools.utils.actions.ExitAction;
import com.jtools.utils.gui.components.CascadeDesktopPane;
import com.jtools.utils.logging.LoggingUtils;
import com.jtools.utils.messages.pubsub.DefaultPubSubBus;
import com.jtools.utils.messages.pubsub.PubSubMessageListener;

import jakarta.jms.JMSException;
import jakarta.jms.Message;

/**
 * @author j4ckk0
 *
 */
public abstract class AMappingsDemo extends JFrame implements PubSubMessageListener {

	private static final long serialVersionUID = 7125491173800101032L;

	// //////////////////////////////
	//
	// I18N resources constants
	//
	// //////////////////////////////

	private static final String DEMO = "Demo";
	private static final String MENU = "Menu";
	private static final String EXIT = "Exit";
	private static final String SHOW_STD_OUTPUT = "Show std output";
	private static final String CLEAR_STD_OUTPUT = "Clear std output";
	private static final String SHOW_HIDE_POSSIBLE_DATA_CLASSES = "Show/hide possible data classes";
	private static final String SHOW_HIDE_DATA_PROVIDERS = "Show/hide data providers";
	private static final String CREATE_DATA_TABLE = "Create data table";
	private static final String LOAD_DATA_TABLE = "Load data table";
	private static final String CREATE_SIMPLE_MAPPING = "Create simple mapping";
	private static final String LOAD_SIMPLE_MAPPING = "Load simple mapping";
	private static final String EXPORT_DATA_TO_STANDARD_OUTPUT = "Export data to standard output";
	private static final String CREATE_BLOCK_MAPPING = "Create block mapping";
	private static final String LOAD_BLOCK_MAPPING = "Load block mapping";
	private static final String EXPORT_DATA_TO_EXCEL = "Export data to Excel";
	private static final String IMPORT_DATA_FROM_EXCEL = "Import data from Excel";
	private static final String CONSOLE = "Console";
	private static final String TEST_DATA = "Test data";
	private static final String SIMPLE_MAPPING = "Simple mapping";
	private static final String BLOCK_MAPPING = "Block mapping";

	// //////////////////////////////
	//
	// Attributes
	//
	// //////////////////////////////

	private final JTabbedPane tabbedPane;

	private final JDesktopPane testDataDesktopPane;
	private final JDesktopPane simpleMappingDesktopPane;
	private final JDesktopPane blockMappingDesktopPane;
	private final JDesktopPane consoleDesktopPane;

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

	protected AMappingsDemo(Class<?>[] testObjectClasses) {
		super(DEMO);

		// Logging
		LoggingUtils.loadDefaultConfig();
		
		// L&F
		FlatLightLaf.setup();

		//
		// Start broker for messages between components
		//
		try {
			DefaultPubSubBus.instance().start();
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}

		//
		// Build the GUI
		//

		ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);

		JFrame.setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setPreferredSize(new Dimension(screenSize.width * 90 / 100, screenSize.height * 90 / 100));

		this.tabbedPane = new JTabbedPane();
		setContentPane(tabbedPane);

		this.testDataDesktopPane = new CascadeDesktopPane();
		tabbedPane.addTab(TEST_DATA, testDataDesktopPane);

		this.simpleMappingDesktopPane = new CascadeDesktopPane();
		tabbedPane.addTab(SIMPLE_MAPPING, simpleMappingDesktopPane);

		this.blockMappingDesktopPane = new CascadeDesktopPane();
		tabbedPane.addTab(BLOCK_MAPPING, blockMappingDesktopPane);

		this.consoleDesktopPane = new CascadeDesktopPane();
		tabbedPane.addTab(CONSOLE, consoleDesktopPane);

		this.dataProviderSelector = new DataProviderSelector();

		this.defaultDataProvider = new DefaultDataProvider(testObjectClasses);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu menu
		JMenu menuMenu = new JMenu(MENU);
		menuBar.add(menuMenu);

		JMenuItem exitItem = new JMenuItem(new ExitAction(EXIT));
		menuMenu.add(exitItem);

		// Console menu
		JMenu consoleMenu = new JMenu(CONSOLE);
		menuBar.add(consoleMenu);

		ShowStdOutputAction showStdOutputAction = new ShowStdOutputAction(SHOW_STD_OUTPUT, consoleDesktopPane);
		JMenuItem showStdOutputItem = new JMenuItem(showStdOutputAction);
		consoleMenu.add(showStdOutputItem);

		ClearStdOutputAction clearStdOutputAction = new ClearStdOutputAction(CLEAR_STD_OUTPUT, showStdOutputAction);
		JMenuItem clearStdOutputItem = new JMenuItem(clearStdOutputAction);
		consoleMenu.add(clearStdOutputItem);

		// Data menu
		JMenu dataMenu = new JMenu(TEST_DATA);
		menuBar.add(dataMenu);

		ShowDefaultDataProviderAction showDataTypesProviderAction = new ShowDefaultDataProviderAction(
				SHOW_HIDE_POSSIBLE_DATA_CLASSES, testDataDesktopPane, defaultDataProvider);
		JMenuItem showDataTypesProviderItem = new JMenuItem(showDataTypesProviderAction);
		dataMenu.add(showDataTypesProviderItem);

		dataMenu.add(new JSeparator());

		ShowDataProviderSelectorAction showDataProviderSelectorAction = new ShowDataProviderSelectorAction(
				SHOW_HIDE_DATA_PROVIDERS, testDataDesktopPane, dataProviderSelector);
		JMenuItem showDataProviderSelectorItem = new JMenuItem(showDataProviderSelectorAction);
		dataMenu.add(showDataProviderSelectorItem);

		dataMenu.add(new JSeparator());

		this.createDataTableAction = new CreateDataEditorAction(CREATE_DATA_TABLE, testObjectClasses);
		JMenuItem createDataTableItem = new JMenuItem(createDataTableAction);
		dataMenu.add(createDataTableItem);

		this.loadDataTableAction = new LoadDataAction(LOAD_DATA_TABLE, testObjectClasses);
		JMenuItem loadDataTableItem = new JMenuItem(loadDataTableAction);
		dataMenu.add(loadDataTableItem);

		// Simple mapping menu
		JMenu simpleMappingMenu = new JMenu(SIMPLE_MAPPING);
		menuBar.add(simpleMappingMenu);

		this.createSimpleMappingAction = new SimpleMappingCreateAction(CREATE_SIMPLE_MAPPING);
		JMenuItem createSimpleMappingItem = new JMenuItem(createSimpleMappingAction);
		simpleMappingMenu.add(createSimpleMappingItem);

		this.loadSimpleMappingAction = new SimpleMappingLoadAction(LOAD_SIMPLE_MAPPING);
		JMenuItem loadSimpleMappingItem = new JMenuItem(loadSimpleMappingAction);
		simpleMappingMenu.add(loadSimpleMappingItem);

		simpleMappingMenu.add(new JSeparator());

		this.simpleMappingExportToStdOutputAction = new SimpleMappingExportToStdOutputAction(
				EXPORT_DATA_TO_STANDARD_OUTPUT);
		JMenuItem simpleMappingExportToStdOutputItem = new JMenuItem(simpleMappingExportToStdOutputAction);
		simpleMappingMenu.add(simpleMappingExportToStdOutputItem);

		this.simpleMappingExportToExcelAction = new SimpleMappingExportToExcelAction(EXPORT_DATA_TO_EXCEL);
		JMenuItem simpleMappingExportToExcelItem = new JMenuItem(simpleMappingExportToExcelAction);
		simpleMappingMenu.add(simpleMappingExportToExcelItem);

		simpleMappingMenu.add(new JSeparator());

		this.simpleMappingImportFromExcelAction = new SimpleMappingImportFromExcelAction(IMPORT_DATA_FROM_EXCEL);
		JMenuItem simpleMappingImportFromExcelItem = new JMenuItem(simpleMappingImportFromExcelAction);
		simpleMappingMenu.add(simpleMappingImportFromExcelItem);

		// Block mapping menu
		JMenu blockMappingMenu = new JMenu(BLOCK_MAPPING);
		menuBar.add(blockMappingMenu);

		this.createBlockMappingAction = new BlockMappingCreateAction(CREATE_BLOCK_MAPPING);
		JMenuItem createBlockMappingItem = new JMenuItem(createBlockMappingAction);
		blockMappingMenu.add(createBlockMappingItem);

		this.loadBlockMappingAction = new BlockMappingLoadAction(LOAD_BLOCK_MAPPING);
		JMenuItem loadBlockMappingItem = new JMenuItem(loadBlockMappingAction);
		blockMappingMenu.add(loadBlockMappingItem);

		blockMappingMenu.add(new JSeparator());

		this.blockMappingExportToExcelAction = new BlockMappingExportToExcelAction(EXPORT_DATA_TO_EXCEL);
		JMenuItem blockMappingExportToExcelItem = new JMenuItem(blockMappingExportToExcelAction);
		blockMappingMenu.add(blockMappingExportToExcelItem);

		blockMappingMenu.add(new JSeparator());

		this.blockMappingImportFromExcelAction = new BlockMappingImportFromExcelAction(IMPORT_DATA_FROM_EXCEL);
		JMenuItem blockMappingImportFromExcelItem = new JMenuItem(blockMappingImportFromExcelAction);
		blockMappingMenu.add(blockMappingImportFromExcelItem);

		//
		// Build frame
		//
		createDataTableAction.setDesktopPane(testDataDesktopPane);
		loadDataTableAction.setDesktopPane(testDataDesktopPane);

		createSimpleMappingAction.setDesktopPane(simpleMappingDesktopPane);
		loadSimpleMappingAction.setDesktopPane(simpleMappingDesktopPane);
		simpleMappingImportFromExcelAction.setDesktopPane(simpleMappingDesktopPane);

		createBlockMappingAction.setDesktopPane(blockMappingDesktopPane);
		loadBlockMappingAction.setDesktopPane(blockMappingDesktopPane);
		blockMappingImportFromExcelAction.setDesktopPane(blockMappingDesktopPane);

		//
		// Subscribe to pub/sub
		//
		DefaultPubSubBus.instance().addListener(this, DataProviderPubSub.DATA_PROVIDER_CHANGED);

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
		showDataProviderSelectorAction.actionPerformed(null);
	}

	@Override
	public void onMessage(String topicName, Message message) {
		try {

			String providerName = DataProviderPubSub.readMessage(message);

			if (topicName.equals(DataProviderPubSub.DATA_PROVIDER_CHANGED)) {
				IDataProvider dataProvider = DataProviderRegistry.instance().get(providerName);

				if (dataProvider != null) {
					simpleMappingExportToStdOutputAction.setDataProvider(dataProvider);
					createSimpleMappingAction.setDataProvider(dataProvider);
					simpleMappingExportToExcelAction.setDataProvider(dataProvider);

					createBlockMappingAction.setDataProvider(dataProvider);
					blockMappingExportToExcelAction.setDataProvider(dataProvider);
				}

				// Avoid having no data provider
				else {
					simpleMappingExportToStdOutputAction.setDataProvider(defaultDataProvider);
					createSimpleMappingAction.setDataProvider(defaultDataProvider);
					simpleMappingExportToExcelAction.setDataProvider(defaultDataProvider);
					createBlockMappingAction.setDataProvider(defaultDataProvider);
				}
			}

		} catch (JMSException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

}
