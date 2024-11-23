package org.jtools.tests.mappings;

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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.UUID;
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

import org.jtools.data.actions.CreateDataEditorAction;
import org.jtools.data.actions.LoadDataAction;
import org.jtools.data.gui.desktop.DataProviderSelector;
import org.jtools.data.gui.desktop.DefaultDataProvider;
import org.jtools.data.provider.DataProviderPubSub;
import org.jtools.data.provider.DataProviderRegistry;
import org.jtools.data.provider.IDataProvider;
import org.jtools.gui.desktop.ClearStdOutputAction;
import org.jtools.gui.desktop.ShowComponentSelectorAction;
import org.jtools.gui.desktop.StdOutputFrame;
import org.jtools.mappings.block.BlockMapping;
import org.jtools.mappings.common.IMapping;
import org.jtools.mappings.editors.actions.block.BlockMappingCreateAction;
import org.jtools.mappings.editors.actions.block.BlockMappingExportToExcelAction;
import org.jtools.mappings.editors.actions.block.BlockMappingImportFromExcelAction;
import org.jtools.mappings.editors.actions.block.BlockMappingLoadAction;
import org.jtools.mappings.editors.actions.simple.SimpleMappingCreateAction;
import org.jtools.mappings.editors.actions.simple.SimpleMappingExportToExcelAction;
import org.jtools.mappings.editors.actions.simple.SimpleMappingExportToStdOutputAction;
import org.jtools.mappings.editors.actions.simple.SimpleMappingImportFromExcelAction;
import org.jtools.mappings.editors.actions.simple.SimpleMappingLoadAction;
import org.jtools.mappings.editors.common.MappingPubSub;
import org.jtools.mappings.editors.common.MappingRegistry;
import org.jtools.mappings.gui.desktop.MappingSelector;
import org.jtools.mappings.simple.SimpleMapping;
import org.jtools.utils.actions.ExitAction;
import org.jtools.utils.gui.components.CascadeDesktopPane;
import org.jtools.utils.logging.LoggingUtils;
import org.jtools.utils.messages.pubsub.DefaultPubSubBus;
import org.jtools.utils.messages.pubsub.PubSubMessageListener;

import com.formdev.flatlaf.FlatLightLaf;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
// TODO: Auto-generated Javadoc

/**
 * The Class AMappingsDemo.
 */
public abstract class AMappingsDemo extends JFrame implements PubSubMessageListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 7125491173800101032L;

	// //////////////////////////////
	//
	// I18N resources constants
	//
	// //////////////////////////////

	/** The Constant DEMO. */
	private static final String DEMO = "Demo";
	
	/** The Constant MENU. */
	private static final String MENU = "Menu";
	
	/** The Constant EXIT. */
	private static final String EXIT = "Exit";
	
	/** The Constant SHOW_STD_OUTPUT. */
	private static final String SHOW_STD_OUTPUT = "Show std output";
	
	/** The Constant CLEAR_STD_OUTPUT. */
	private static final String CLEAR_STD_OUTPUT = "Clear std output";
	
	/** The Constant SHOW_HIDE_POSSIBLE_DATA_CLASSES. */
	private static final String SHOW_HIDE_POSSIBLE_DATA_CLASSES = "Show/hide possible data classes";
	
	/** The Constant SHOW_HIDE_DATA_PROVIDERS. */
	private static final String SHOW_HIDE_DATA_PROVIDERS = "Show/hide data providers";
	
	/** The Constant SHOW_HIDE_SIMPLE_MAPPINGS. */
	private static final String SHOW_HIDE_SIMPLE_MAPPINGS = "Show/hide simple mappings";
	
	/** The Constant SHOW_HIDE_BLOCK_MAPPINGS. */
	private static final String SHOW_HIDE_BLOCK_MAPPINGS = "Show/hide block mappings";
	
	/** The Constant CREATE_DATA_TABLE. */
	private static final String CREATE_DATA_TABLE = "Create data table";
	
	/** The Constant LOAD_DATA_TABLE. */
	private static final String LOAD_DATA_TABLE = "Load data table";
	
	/** The Constant CREATE_SIMPLE_MAPPING. */
	private static final String CREATE_SIMPLE_MAPPING = "Create simple mapping";
	
	/** The Constant LOAD_SIMPLE_MAPPING. */
	private static final String LOAD_SIMPLE_MAPPING = "Load simple mapping";
	
	/** The Constant EXPORT_DATA_TO_STANDARD_OUTPUT. */
	private static final String EXPORT_DATA_TO_STANDARD_OUTPUT = "Export data to standard output";
	
	/** The Constant CREATE_BLOCK_MAPPING. */
	private static final String CREATE_BLOCK_MAPPING = "Create block mapping";
	
	/** The Constant LOAD_BLOCK_MAPPING. */
	private static final String LOAD_BLOCK_MAPPING = "Load block mapping";
	
	/** The Constant EXPORT_DATA_TO_EXCEL. */
	private static final String EXPORT_DATA_TO_EXCEL = "Export data to Excel";
	
	/** The Constant IMPORT_DATA_FROM_EXCEL. */
	private static final String IMPORT_DATA_FROM_EXCEL = "Import data from Excel";
	
	/** The Constant CONSOLE. */
	private static final String CONSOLE = "Console";
	
	/** The Constant TEST_DATA. */
	private static final String TEST_DATA = "Test data";
	
	/** The Constant SIMPLE_MAPPING. */
	private static final String SIMPLE_MAPPING = "Simple mapping";
	
	/** The Constant BLOCK_MAPPING. */
	private static final String BLOCK_MAPPING = "Block mapping";

	// //////////////////////////////
	//
	// Attributes
	//
	// //////////////////////////////

	/** The tabbed pane. */
	private final JTabbedPane tabbedPane;

	/** The test data desktop pane. */
	private final JDesktopPane testDataDesktopPane;
	
	/** The simple mapping desktop pane. */
	private final JDesktopPane simpleMappingDesktopPane;
	
	/** The block mapping desktop pane. */
	private final JDesktopPane blockMappingDesktopPane;
	
	/** The console desktop pane. */
	private final JDesktopPane consoleDesktopPane;

	/** The std output frame. */
	private final StdOutputFrame stdOutputFrame;

	/** The data provider selector. */
	private final DataProviderSelector dataProviderSelector;

	/** The simple mapping selector. */
	private final MappingSelector simpleMappingSelector;

	/** The block mapping selector. */
	private final MappingSelector blockMappingSelector;

	/** The default data provider. */
	private final DefaultDataProvider defaultDataProvider;

	/** The create data table action. */
	private final CreateDataEditorAction createDataTableAction;
	
	/** The load data table action. */
	private final LoadDataAction loadDataTableAction;

	/** The create simple mapping action. */
	private final SimpleMappingCreateAction createSimpleMappingAction;
	
	/** The load simple mapping action. */
	private final SimpleMappingLoadAction loadSimpleMappingAction;

	/** The create block mapping action. */
	private final BlockMappingCreateAction createBlockMappingAction;
	
	/** The load block mapping action. */
	private final BlockMappingLoadAction loadBlockMappingAction;

	/** The simple mapping export to std output action. */
	private final SimpleMappingExportToStdOutputAction simpleMappingExportToStdOutputAction;
	
	/** The simple mapping export to excel action. */
	private final SimpleMappingExportToExcelAction simpleMappingExportToExcelAction;

	/** The block mapping export to excel action. */
	private final BlockMappingExportToExcelAction blockMappingExportToExcelAction;

	/** The simple mapping import from excel action. */
	private final SimpleMappingImportFromExcelAction simpleMappingImportFromExcelAction;

	/** The block mapping import from excel action. */
	private final BlockMappingImportFromExcelAction blockMappingImportFromExcelAction;

	// //////////////////////////////
	//
	// Methods
	//
	// //////////////////////////////

	/**
	 * Instantiates a new a mappings demo.
	 *
	 * @param testObjectClasses the test object classes
	 */
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

		this.stdOutputFrame = new StdOutputFrame();

		this.dataProviderSelector = new DataProviderSelector();

		this.simpleMappingSelector = new MappingSelector(SimpleMapping.class);

		this.blockMappingSelector = new MappingSelector(BlockMapping.class);

		this.defaultDataProvider = new DefaultDataProvider(testObjectClasses);

		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Menu menu
		JMenu menuMenu = new JMenu(MENU);
		menuBar.add(menuMenu);

		JMenuItem exitItem = new JMenuItem(new ExitAction(EXIT));
		menuMenu.add(exitItem);

		//
		// Console menu
		//

		JMenu consoleMenu = new JMenu(CONSOLE);
		menuBar.add(consoleMenu);

		ShowComponentSelectorAction showStdOutputAction = new ShowComponentSelectorAction(SHOW_STD_OUTPUT, consoleDesktopPane, stdOutputFrame);
		JMenuItem showStdOutputItem = new JMenuItem(showStdOutputAction);
		consoleMenu.add(showStdOutputItem);

		ClearStdOutputAction clearStdOutputAction = new ClearStdOutputAction(CLEAR_STD_OUTPUT, stdOutputFrame);
		JMenuItem clearStdOutputItem = new JMenuItem(clearStdOutputAction);
		consoleMenu.add(clearStdOutputItem);

		//
		// Data menu
		//

		JMenu dataMenu = new JMenu(TEST_DATA);
		menuBar.add(dataMenu);

		ShowComponentSelectorAction showDataTypesProviderAction = new ShowComponentSelectorAction(SHOW_HIDE_POSSIBLE_DATA_CLASSES, testDataDesktopPane, defaultDataProvider);
		JMenuItem showDataTypesProviderItem = new JMenuItem(showDataTypesProviderAction);
		dataMenu.add(showDataTypesProviderItem);

		dataMenu.add(new JSeparator());

		ShowComponentSelectorAction showDataProviderSelectorAction = new ShowComponentSelectorAction(SHOW_HIDE_DATA_PROVIDERS, testDataDesktopPane, dataProviderSelector);
		JMenuItem showDataProviderSelectorItem = new JMenuItem(showDataProviderSelectorAction);
		dataMenu.add(showDataProviderSelectorItem);

		dataMenu.add(new JSeparator());

		this.createDataTableAction = new CreateDataEditorAction(CREATE_DATA_TABLE, testObjectClasses);
		JMenuItem createDataTableItem = new JMenuItem(createDataTableAction);
		dataMenu.add(createDataTableItem);

		this.loadDataTableAction = new LoadDataAction(LOAD_DATA_TABLE, testObjectClasses);
		JMenuItem loadDataTableItem = new JMenuItem(loadDataTableAction);
		dataMenu.add(loadDataTableItem);

		//
		// Simple mapping menu
		//

		JMenu simpleMappingMenu = new JMenu(SIMPLE_MAPPING);
		menuBar.add(simpleMappingMenu);

		ShowComponentSelectorAction showSimpleMappingSelectorAction = new ShowComponentSelectorAction(SHOW_HIDE_SIMPLE_MAPPINGS, simpleMappingDesktopPane, simpleMappingSelector);
		JMenuItem showSimpleMappingSelectorItem = new JMenuItem(showSimpleMappingSelectorAction);
		simpleMappingMenu.add(showSimpleMappingSelectorItem);

		simpleMappingMenu.add(new JSeparator());

		this.createSimpleMappingAction = new SimpleMappingCreateAction(CREATE_SIMPLE_MAPPING);
		JMenuItem createSimpleMappingItem = new JMenuItem(createSimpleMappingAction);
		simpleMappingMenu.add(createSimpleMappingItem);

		this.loadSimpleMappingAction = new SimpleMappingLoadAction(LOAD_SIMPLE_MAPPING);
		JMenuItem loadSimpleMappingItem = new JMenuItem(loadSimpleMappingAction);
		simpleMappingMenu.add(loadSimpleMappingItem);

		simpleMappingMenu.add(new JSeparator());

		this.simpleMappingExportToStdOutputAction = new SimpleMappingExportToStdOutputAction(EXPORT_DATA_TO_STANDARD_OUTPUT);
		JMenuItem simpleMappingExportToStdOutputItem = new JMenuItem(simpleMappingExportToStdOutputAction);
		simpleMappingMenu.add(simpleMappingExportToStdOutputItem);

		this.simpleMappingExportToExcelAction = new SimpleMappingExportToExcelAction(EXPORT_DATA_TO_EXCEL);
		JMenuItem simpleMappingExportToExcelItem = new JMenuItem(simpleMappingExportToExcelAction);
		simpleMappingMenu.add(simpleMappingExportToExcelItem);

		simpleMappingMenu.add(new JSeparator());

		this.simpleMappingImportFromExcelAction = new SimpleMappingImportFromExcelAction(IMPORT_DATA_FROM_EXCEL);
		JMenuItem simpleMappingImportFromExcelItem = new JMenuItem(simpleMappingImportFromExcelAction);
		simpleMappingMenu.add(simpleMappingImportFromExcelItem);

		//
		// Block mapping menu
		//

		JMenu blockMappingMenu = new JMenu(BLOCK_MAPPING);
		menuBar.add(blockMappingMenu);

		ShowComponentSelectorAction showBlockMappingSelectorAction = new ShowComponentSelectorAction(SHOW_HIDE_BLOCK_MAPPINGS, blockMappingDesktopPane, blockMappingSelector);
		JMenuItem showBlockMappingSelectorItem = new JMenuItem(showBlockMappingSelectorAction);
		blockMappingMenu.add(showBlockMappingSelectorItem);

		blockMappingMenu.add(new JSeparator());

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
		DefaultPubSubBus.instance().addListener(this, DataProviderPubSub.DATA_PROVIDER_CHANGED, MappingPubSub.MAPPING_CHANGED);

		//
		// Initial state
		//
		showDataTypesProviderAction.actionPerformed(null);
		showBlockMappingSelectorAction.actionPerformed(null);
		showSimpleMappingSelectorAction.actionPerformed(null);
		showDataProviderSelectorAction.actionPerformed(null);
	}

	/**
	 * On message.
	 *
	 * @param topicName the topic name
	 * @param message the message
	 */
	@Override
	public void onMessage(String topicName, Message message) {
		try {

			if (topicName.equals(DataProviderPubSub.DATA_PROVIDER_CHANGED)) {

				String providerName = DataProviderPubSub.readMessage(message);

				IDataProvider dataProvider = DataProviderRegistry.instance().get(providerName);

				// Avoid having no data provider
				if(dataProvider == null) {
					dataProvider = defaultDataProvider;
				}

				//
				// Update "Create mappings" actions
				//
				createSimpleMappingAction.setDataClassProvider(dataProvider);
				createBlockMappingAction.setDataClassProvider(dataProvider);

				//
				// Update "Export" actions 
				// 
				simpleMappingExportToStdOutputAction.setDataProvider(dataProvider);
				simpleMappingExportToExcelAction.setDataProvider(dataProvider);

				//				blockMappingExportToStdOutputAction.setDataProvider(dataProvider);
				blockMappingExportToExcelAction.setDataProvider(dataProvider);
			}

			if(topicName.equals(MappingPubSub.MAPPING_CHANGED)) {

				UUID mappingId = MappingPubSub.readMessage(message);

				IMapping mapping = MappingRegistry.instance().get(mappingId);

				// Simple mapping
				if(mapping instanceof SimpleMapping<?>) {

					//
					// Update "Export" actions 
					// 
					simpleMappingExportToStdOutputAction.setMapping((SimpleMapping<?>)mapping);
					simpleMappingExportToExcelAction.setMapping((SimpleMapping<?>)mapping);

					//
					// Update "Import" actions 
					// 
					simpleMappingImportFromExcelAction.setMapping((SimpleMapping<?>)mapping);
				}

				// Block mapping
				if(mapping instanceof BlockMapping<?>) {

					//
					// Update "Export" actions 
					// 
					//					blockMappingExportToStdOutputAction.setDataProvider(dataProvider);
					blockMappingExportToExcelAction.setMapping((BlockMapping<?>)mapping);

					//
					// Update "Import" actions 
					// 
					blockMappingImportFromExcelAction.setMapping((BlockMapping<?>)mapping);
				}
			}

		} catch (JMSException | ClassCastException e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage());
			Logger.getLogger(getClass().getName()).log(Level.FINE, e.getMessage(), e);
		}
	}

}
