/*
 * @(#)$Id$
 *
 * Author		:	Ueli Kurmann, igesture@uelikurmann.ch
 *                  
 *
 * Purpose		: 
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date				Who			Reason
 *
 * 08.04.2008			ukurmann	Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2009 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.tool.util;

import java.util.ArrayList;
import java.util.List;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.SampleDescriptor3D;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.core.Controller;
import org.ximtec.igesture.tool.explorer.NodeInfoImpl;
import org.ximtec.igesture.tool.explorer.SeparatorAction;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;
import org.ximtec.igesture.tool.view.admin.action.AddGestureClassAction;
import org.ximtec.igesture.tool.view.admin.action.AddGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.AddSampleDescriptor3DAction;
import org.ximtec.igesture.tool.view.admin.action.AddSampleDescriptorAction;
import org.ximtec.igesture.tool.view.admin.action.AddTextDescriptorAction;
import org.ximtec.igesture.tool.view.admin.action.CreateTestSetStructureAction;
import org.ximtec.igesture.tool.view.admin.action.ExportGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.ExportPDFGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.ImportGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveDescriptorAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureClassAction;
import org.ximtec.igesture.tool.view.admin.action.RemoveGestureSetAction;
import org.ximtec.igesture.tool.view.admin.panel.DefaultPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureClassPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureSetPanel;
import org.ximtec.igesture.tool.view.admin.panel.GestureSetsPanel;
import org.ximtec.igesture.tool.view.admin.panel.SampleDescriptor3DPanel;
import org.ximtec.igesture.tool.view.admin.panel.SampleDescriptorPanel;
import org.ximtec.igesture.tool.view.admin.panel.TextDescriptorPanel;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.tool.view.devicemanager.IDeviceManager;
import org.ximtec.igesture.tool.view.testbench.action.AddConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.action.ExportConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.action.RemoveConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.panel.AlgorithmListPanel;
import org.ximtec.igesture.tool.view.testbench.panel.AlgorithmWrapperPanel;
import org.ximtec.igesture.tool.view.testbench.panel.ConfigurationPanel;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;
import org.ximtec.igesture.tool.view.testset.action.AddTestClassAction;
import org.ximtec.igesture.tool.view.testset.action.ConvertGestureSetAction;
import org.ximtec.igesture.tool.view.testset.action.DeleteTestClassAction;
import org.ximtec.igesture.tool.view.testset.action.DeleteTestSetAction;
import org.ximtec.igesture.tool.view.testset.action.ExportTestSetAction;
import org.ximtec.igesture.tool.view.testset.action.ImportTestSetAction;
import org.ximtec.igesture.tool.view.testset.action.NewTestSetAction;
import org.ximtec.igesture.tool.view.testset.panel.TestClassPanel;
import org.ximtec.igesture.tool.view.testset.panel.TestSetPanel;
import org.ximtec.igesture.tool.view.testset.panel.TestSetsPanel;
import org.ximtec.igesture.tool.view.testset.wrapper.TestSetList;

/**
 * Comment
 * 
 * @version 1.0 08.04.2008
 * @author Ueli Kurmann
 */
public class NodeInfoFactory {

  public static List<NodeInfo> createAdminNodeInfo(Controller controller, IDeviceManager deviceManagerController) {
    List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

    List<Class<? extends BasicAction>> rootActions = new ArrayList<Class<? extends BasicAction>>();
    rootActions.add(AddGestureSetAction.class);
    rootActions.add(ImportGestureSetAction.class);

    List<Class<? extends BasicAction>> setActions = new ArrayList<Class<? extends BasicAction>>();
    setActions.add(AddGestureClassAction.class);
    setActions.add(SeparatorAction.class);
    setActions.add(RemoveGestureSetAction.class);
    setActions.add(ExportGestureSetAction.class);
    setActions.add(SeparatorAction.class);
    setActions.add(ExportPDFGestureSetAction.class);
    setActions.add(CreateTestSetStructureAction.class);

    List<Class<? extends BasicAction>> classActions = new ArrayList<Class<? extends BasicAction>>();
    classActions.add(AddSampleDescriptorAction.class);
    classActions.add(AddSampleDescriptor3DAction.class);
    classActions.add(AddTextDescriptorAction.class);
    classActions.add(SeparatorAction.class);
    classActions.add(RemoveGestureClassAction.class);

    List<Class<? extends BasicAction>> descriptorActions = new ArrayList<Class<? extends BasicAction>>();
    descriptorActions.add(RemoveDescriptorAction.class);

    nodeInfos = new ArrayList<NodeInfo>();
    nodeInfos.add(new NodeInfoImpl(controller, GestureSetList.class, "name", "sets", GestureSetsPanel.class,
        rootActions, GestureConstants.NODE_ICON_GESTURE_SET_LIST));
    nodeInfos.add(new NodeInfoImpl(controller, GestureSet.class, "name", "gestureClasses", GestureSetPanel.class,
        setActions, GestureConstants.NODE_ICON_GESTURE_SET));
    nodeInfos.add(new NodeInfoImpl(controller, GestureClass.class, "name", "descriptors", GestureClassPanel.class,
        classActions, GestureConstants.NODE_ICON_GESTURE_CLASS));
    nodeInfos.add(new NodeInfoImpl(controller, SampleDescriptor.class, "name", null, SampleDescriptorPanel.class,
        descriptorActions, GestureConstants.NODE_ICON_GESTURE_SAMPLE_DESCRIPTOR, deviceManagerController));
    nodeInfos.add(new NodeInfoImpl(controller, SampleDescriptor3D.class, "name", null, SampleDescriptor3DPanel.class,
        descriptorActions, GestureConstants.NODE_ICON_GESTURE_SAMPLE_DESCRIPTOR, deviceManagerController));
    nodeInfos.add(new NodeInfoImpl(controller, TextDescriptor.class, "name", null, TextDescriptorPanel.class,
        descriptorActions, GestureConstants.NODE_ICON_GESTURE_TEXT_DESCRIPTOR));
    nodeInfos
        .add(new NodeInfoImpl(controller, GestureSample.class, "name", null, DefaultPanel.class, null, GestureConstants.NODE_ICON_GESTURE_SAMPLE));
    nodeInfos.add(new NodeInfoImpl(controller, String.class, Constant.EMPTY_STRING, null, DefaultPanel.class, null,
        null));

    return nodeInfos;
  }

  public static List<NodeInfo> createTestBenchNodeInfo(Controller controller, IDeviceManager deviceManagerController) {

    List<Class<? extends BasicAction>> algorithmListActions = new ArrayList<Class<? extends BasicAction>>();
    List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

    List<Class<? extends BasicAction>> algorithmWrapperActions = new ArrayList<Class<? extends BasicAction>>();
    algorithmWrapperActions.add(AddConfigurationAction.class);

    List<Class<? extends BasicAction>> configurationActions = new ArrayList<Class<? extends BasicAction>>();
    configurationActions.add(ExportConfigurationAction.class);
    configurationActions.add(SeparatorAction.class);
    configurationActions.add(RemoveConfigurationAction.class);

    nodeInfos = new ArrayList<NodeInfo>();
    nodeInfos.add(new NodeInfoImpl(controller, AlgorithmList.class, "name", "algorithms", AlgorithmListPanel.class,
        algorithmListActions, GestureConstants.NODE_ICON_ALGORITHM_LIST));

    nodeInfos.add(new NodeInfoImpl(controller, AlgorithmWrapper.class, "name", "configurations",
        AlgorithmWrapperPanel.class, algorithmWrapperActions, GestureConstants.NODE_ICON_ALGORITHM));

    nodeInfos.add(new NodeInfoImpl(controller, Configuration.class, "name", null, ConfigurationPanel.class,
        configurationActions, GestureConstants.NODE_ICON_CONFIGURATION, deviceManagerController));

    return nodeInfos;

  }

  public static List<NodeInfo> createTestSetNodeInfo(Controller controller, IDeviceManager deviceManagerController) {
    List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

    List<Class<? extends BasicAction>> rootActions = new ArrayList<Class<? extends BasicAction>>();
    rootActions.add(ImportTestSetAction.class);
    rootActions.add(NewTestSetAction.class);
    rootActions.add(ConvertGestureSetAction.class);

    List<Class<? extends BasicAction>> setActions = new ArrayList<Class<? extends BasicAction>>();
    setActions.add(AddTestClassAction.class);
    setActions.add(DeleteTestSetAction.class);
    setActions.add(SeparatorAction.class);
    setActions.add(ExportTestSetAction.class);
    

    List<Class<? extends BasicAction>> classActions = new ArrayList<Class<? extends BasicAction>>();
    classActions.add(DeleteTestClassAction.class);


    nodeInfos = new ArrayList<NodeInfo>();

    nodeInfos.add(new NodeInfoImpl(controller, TestSetList.class, "name", TestSetList.PROPERTY_SETS,
        TestSetsPanel.class, rootActions, GestureConstants.NODE_ICON_TEST_SET_LIST));

    nodeInfos.add(new NodeInfoImpl(controller, TestSet.class, "name", "testClasses", TestSetPanel.class, setActions,
        GestureConstants.NODE_ICON_TEST_SET));

    nodeInfos.add(new NodeInfoImpl(controller, TestClass.class, "name", null, TestClassPanel.class, classActions,
        GestureConstants.NODE_ICON_TEST_CLASS, deviceManagerController));

    return nodeInfos;
  }

}
