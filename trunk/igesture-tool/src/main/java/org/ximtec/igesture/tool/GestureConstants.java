/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *
 * Purpose		:   Constants for the GUI application.
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * Nov 15, 2006     ukurmann    Initial Release
 * Mar 24, 2007     bsigner     Cleanup
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool;


/**
 * Constants for the GUI application.
 * 
 * @version 1.0, Nov 2006
 * @author Ueli Kurmann, igesture@uelikurmann.ch
 * @author Beat Signer, signer@inf.ethz.ch
 */
public class GestureConstants {

  public static final String GESTURE_CLASS_ADD = "AddGestureClassAction";
  public static final String GESTURE_SET_ADD = "AddGestureSetAction";
  public static final String TEST_SET_EXPORT = "ExportCreateTestSetAction";
  public static final String GESTURE_SET_EXPORT = "ExportGestureSetAction";
  public static final String GESTURE_CLASS_DEL = "RemoveGestureClassAction";
  public static final String GESTURE_SET_DEL = "DeleteGestureSetAction";
  public static final String GESTURE_SET_PDF = "ExportPDFGestureSetAction";
  public static final String GESTURE_SET_TEST_SET = "CreateTestSet";
  public static final String GESTURE_SET_FORM = "ExportIPaperFormAction";
  public static final String DESCRIPTOR_DEL = "DeleteDescriptorAction";
  public static final String GESTURE_SET_IMPORT = "ImportGestureSetAction";
  public static final String SAMPLE_DESCRIPTOR_ADD = "AddSampleDescriptorAction";
  public static final String SAMPLE_DESCRIPTOR_3D_ADD = "AddSampleDescriptor3DAction";
  public static final String COMPOSITE_DESCRIPTOR_ADD = "AddCompositeDescriptorAction";
  public static final String TEXT_DESCRIPTOR_ADD = "AddTextDescriptorAction";
  public static final String GESTURE_SAMPLE_ADD = "AddGestureSampleAction";
  public static final String GESTURE_SAMPLE_DEL = "DelGestureSampleAction";
  public static final String GESTURE_SAMPLE_CLEAR = "ClearGestureSampleAction";
  public static final String CONFIGURATION_ADD = "AddConfigurationAction";
  public static final String CONFIGURATION_DEL = "RemoveConfigurationAction";
  public static final String CONFIGURATION_EXPORT = "ConfigExportAction";
  public static final String RECOGNISE = "RecogniseAction";
  public static final String APPLICATION_ROOT = "ApplicationRoot";

  public static final String FILE_MENU = "FileMenu";
  public static final String OPEN_PROJECT = "OpenProjectAction";
  public static final String CLOSE_PROJECT = "CloseProjectAction";
  public static final String SAVE = "SaveAction";
  public static final String SAVE_AS = "SaveAsAction";
  public static final String EXIT = "ExitAction";
  
  public static final String DEVICE_MENU = "DeviceMenu";
  public static final String DEVICE_MANAGER = "DeviceManagerAction";

  public static final String HELP_MENU = "HelpMenu";
  public static final String ABOUT = "AboutAction";

  public static final String CONFIGURATION_PANEL_NAME = "ConfigurationPanelName";
  public static final String CONFIGURATION_PANEL_PARAMETERS = "ConfigurationPanelParameters";

  public static final String GESTURE_CLASS_PANEL_NAME = "GestureClassPanelName";
  public static final String GESTURE_CLASS_PANEL_GESTURE = "GestureClassPanelGesture";
  public static final String GESTURE_CLASS_PANEL_NSA = "GestureClassPanelNsa";
  public static final String GESTURE_CLASS_PANEL_NOD = "GestureClassPanelNod";
  public static final String GESTURE_CLASS_PANEL_NCA = "GestureClassPanelNca";
  public static final String GESTURE_CLASS_PANEL_DESCRIPTOR_NAME = "GestureClassPanelDescriptorName";
  public static final String SAMPLE_DESCRIPTOR_TITLE = "SampleDescriptorTitle";
  public static final String SAMPLE_DESCRIPTOR_3D_TITLE = "SampleDescriptor3DTitle";
  public static final String COMPOSITE_DESCRIPTOR_TITLE = "CompositeDescriptorTitle";

  public static final String ALGORITHM_LIST_NAME = "AlgorithmListName";

  public static final String GESTURE_SET_PANEL_NAME = "GestureSetPanelName";
  public static final String GESTURE_SET_PANEL_NOGC = "GestureSetPanelNogc";
  public static final String GESTURE_SET_PANEL_NSA = "GestureSetPanelNsa";
  public static final String GESTURE_SET_PANEL_NCA = "GestureSetPanelNca";

  public static final String GESTURE_SETS_PANEL_TITLE = "GestureSetsPanelTitle";

  public static final String GESTURE_SET_VIEW = "GestureSetView";
  public static final String TEST_BENCH_VIEW = "TestBenchView";

  public static final String MAIN_CONTROLLER_DIALOG_EXIT = "MainControllerDialogExit";
  public static final String MAIN_CONTROLLER_DIALOG_SAVE = "MainControllerDialogSave";

  public static final String TEST_SET_VIEW = "TestSetView";
  public static final String TESTSET_ADD = "TestSetAdd";
  public static final String TESTSET_IMPORT = "TestSetImport";
  public static final String TESTSET_CONVERT_GESTURESET = "TestSetConvertGestureSet";
  public static final String TESTSET_REMOVE = "TestSetRemove";
  public static final String TESTSET_SAMPLE_ADD = "TestSetSampleAdd";
  public static final String TESTSET_SAMPLE_REMOVE = "TestSetSampleRemove";
  public static final String TESTSET_NEW = "TestSetNew";
  public static final String TESTSET_EXPORT = "TestSetExport";
  public static final String TESTSET_NAME = "TestSetName";
  public static final String TESTSET_ADD_CLASS = "TestClassAdd";

  public static final String BATCH_PROCESSING_VIEW = "BatchProcessingView";
  public static final String BATCH_GESTURESET = "BatchGestureSet";
  public static final String BATCH_TESTSET = "BatchTestSet";
  public static final String BATCH_BROWSE_CONFIG = "BatchBrowseConfig";
  public static final String BATCH_CONFIG = "BatchConfig";
  public static final String BATCH_OUTPUT_DIR = "BatchOutputDir";
  public static final String BATCH_BROWSE_OUTPUT = "BatchBrowseOutput";
  public static final String BATCH_RUN = "BatchRun";
  public static final String BATCH_CANCEL = "BatchCancel";

  public static final String USER_DIR = "user.dir";
  public static final String RESOURCE_BUNDLE = "igestureMenu";
  public static final String PROPERTIES = "properties.xml";

  public static final String NODE_ICON_GESTURE_SET = "GestureSetNodeIcon";
  public static final String NODE_ICON_GESTURE_SET_LIST = "GestureSetListNodeIcon";
  public static final String NODE_ICON_GESTURE_CLASS = "GestureClassNodeIcon";
  public static final String NODE_ICON_GESTURE_SAMPLE_DESCRIPTOR = "GestureSampleDescriptorIcon";
  public static final String NODE_ICON_GESTURE_TEXT_DESCRIPTOR = "GestureTextDescriptorIcon";
  public static final String NODE_ICON_GESTURE_SAMPLE = "GestureSampleNodeIcon";
  public static final String NODE_ICON_ALGORITHM = "TestBenchAlgorithmIcon";
  public static final String NODE_ICON_CONFIGURATION = "TestBenchConfigurationIcon";
  public static final String NODE_ICON_ALGORITHM_LIST = "TestBenchAlgorithmListIcon";
  public static final String NODE_ICON_TEST_SET_LIST = "TestSetListIcon";
  public static final String NODE_ICON_TEST_CLASS = "TestClassIcon";
  public static final String NODE_ICON_TEST_SET = "TestSetIcon";
  public static final String SWING_MOUSE_READER = "SwingMouseReader";
  public static final String TESTCLASS_DELETE = "TestClassDelete";
  public static final String TESTCLASS_NAME = "TestClassName";
  public static final String TEXT_DESCRIPTOR_TEXT = "TextDescriptorText";

  public static final String ADD_USER = "AddUserAction";
  public static final String ADD_DEVICE = "AddDeviceAction";
  public static final String ASSOCIATE_USER = "AssociateUserAction";
  public static final String REMOVE_DEVICE = "RemoveDeviceAction";
  public static final String REMOVE_USER = "RemoveUserAction";
  public static final String RECONNECT_DEVICE = "ReconnectDeviceAction";
  public static final String DISCONNECT_DEVICE = "DisconnectDeviceAction";
  public static final String USER_PANEL = "UserPanel";
  public static final String DEVICES_PANEL = "DevicesPanel";
  public static final String SAVE_DEVICE_CONFIGURATION="SaveDeviceConfigurationAction";
  public static final String LOAD_DEVICE_CONFIGURATION="LoadDeviceConfigurationAction";
  
  public static final String USER_NAME = "Name";
  public static final String USER_INITIALS = "Initials";
  public static final String DEVICE_NAME = "Name";
  public static final String DEVICE_ID = "DeviceID";
  public static final String DEVICE_TYPE = "DeviceType";
  public static final String DEVICE_USER = "User";
  public static final String DEVICE_CONNECTION = "ConnectionType";
  public static final String DEVICE_CONNECTED = "Connected";

  public static final String XML_DISCOVERY = "connections.xml";
  public static final String XML_BLUETOOTH = "bluetoothdevices.xml";
  public static final String XML_TUIO = "tuiodevices.xml";

  public static final String CONSTRAINT_CHOOSER = "ConstraintChooserDialog";
  public static final String ADD_GESTURE_CLASS_TO_CONSTRAINT = "AddGestureClassToConstraintAction";
  public static final String REMOVE_GESTURE_CLASS_FROM_CONSTRAINT = "RemoveGestureClassFromConstraintAction";
  public static final String REMOVE_ALL_GESTURE_CLASSES_FROM_CONSTRAINT = "RemoveAllGestureClassesFromConstraintAction";

  public static final String COMPOSITE_TEST_BENCH_VIEW = "CompositeTestBenchView";
  public static final String COMPOSITE_ADD_RECOGNISER_ACTION = "CompositeAddRecogniserAction";
  public static final String COMPOSITE_RESET_ACTION = "CompositeResetAction";
  public static final String COMPOSITE_RECOGNISE_ACTION = "CompositeRecogniseAction";
}
