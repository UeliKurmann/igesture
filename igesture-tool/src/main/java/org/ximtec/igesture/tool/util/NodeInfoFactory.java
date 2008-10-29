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
 * Copyright 1999-2008 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */


package org.ximtec.igesture.tool.util;

import hacks.IconLoader;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;

import org.sigtec.graphix.IconTool;
import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.util.Constant;
import org.sigtec.util.Decorator;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.core.TestClass;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.core.TextDescriptor;
import org.ximtec.igesture.tool.explorer.NodeInfoImpl;
import org.ximtec.igesture.tool.explorer.core.NodeInfo;
import org.ximtec.igesture.tool.view.admin.action.AddGestureClassAction;
import org.ximtec.igesture.tool.view.admin.action.AddGestureSetAction;
import org.ximtec.igesture.tool.view.admin.action.AddSampleDescriptorAction;
import org.ximtec.igesture.tool.view.admin.action.AddTextDescriptorAction;
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
import org.ximtec.igesture.tool.view.admin.panel.SampleDescriptorPanel;
import org.ximtec.igesture.tool.view.admin.panel.TextDescriptorPanel;
import org.ximtec.igesture.tool.view.admin.wrapper.GestureSetList;
import org.ximtec.igesture.tool.view.testbench.action.AddConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.action.ExportConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.action.RemoveConfigurationAction;
import org.ximtec.igesture.tool.view.testbench.panel.AlgorithmListPanel;
import org.ximtec.igesture.tool.view.testbench.panel.AlgorithmWrapperPanel;
import org.ximtec.igesture.tool.view.testbench.panel.ConfigurationPanel;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmList;
import org.ximtec.igesture.tool.view.testbench.wrapper.AlgorithmWrapper;
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
 * @version 1.0 08.04.2008
 * @author Ueli Kurmann
 */
public class NodeInfoFactory {

   public static List<NodeInfo> createAdminNodeInfo() {
      List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

      List<Class< ? extends BasicAction>> rootActions = new ArrayList<Class< ? extends BasicAction>>();
      rootActions.add(ImportGestureSetAction.class);
      rootActions.add(AddGestureSetAction.class);

      List<Class< ? extends BasicAction>> setActions = new ArrayList<Class< ? extends BasicAction>>();
      setActions.add(AddGestureClassAction.class);
      setActions.add(RemoveGestureSetAction.class);
      setActions.add(ExportGestureSetAction.class);
      setActions.add(ExportPDFGestureSetAction.class);

      List<Class< ? extends BasicAction>> classActions = new ArrayList<Class< ? extends BasicAction>>();
      classActions.add(RemoveGestureClassAction.class);
      classActions.add(AddSampleDescriptorAction.class);
      classActions.add(AddTextDescriptorAction.class);

      List<Class< ? extends BasicAction>> descriptorActions = new ArrayList<Class< ? extends BasicAction>>();
      descriptorActions.add(RemoveDescriptorAction.class);

      Icon setIcon = IconTool.getIcon(IconLoader.PACKAGE,
            Decorator.SIZE_16);

      nodeInfos = new ArrayList<NodeInfo>();
      nodeInfos.add(new NodeInfoImpl(GestureSetList.class, "name", "sets",
            GestureSetsPanel.class, rootActions, null));
      nodeInfos.add(new NodeInfoImpl(GestureSet.class, "name", "gestureClasses",
            GestureSetPanel.class, setActions, setIcon));
      nodeInfos.add(new NodeInfoImpl(GestureClass.class, "name", "descriptors",
            GestureClassPanel.class, classActions, null));
      nodeInfos.add(new NodeInfoImpl(SampleDescriptor.class, "name", null,
            SampleDescriptorPanel.class, descriptorActions, null));
      nodeInfos.add(new NodeInfoImpl(TextDescriptor.class, null, null,
            TextDescriptorPanel.class, descriptorActions, null));
      nodeInfos.add(new NodeInfoImpl(GestureSample.class, "name", null,
            DefaultPanel.class, null, null));
      nodeInfos.add(new NodeInfoImpl(String.class, Constant.EMPTY_STRING, null,
            DefaultPanel.class, null, null));

      return nodeInfos;
   }


   public static List<NodeInfo> createTestBenchNodeInfo() {

      List<Class< ? extends BasicAction>> algorithmListActions = new ArrayList<Class< ? extends BasicAction>>();
      List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

      List<Class< ? extends BasicAction>> algorithmWrapperActions = new ArrayList<Class< ? extends BasicAction>>();
      algorithmWrapperActions.add(AddConfigurationAction.class);

      List<Class< ? extends BasicAction>> configurationActions = new ArrayList<Class< ? extends BasicAction>>();
      configurationActions.add(RemoveConfigurationAction.class);
      configurationActions.add(ExportConfigurationAction.class);

      nodeInfos = new ArrayList<NodeInfo>();
      nodeInfos.add(new NodeInfoImpl(AlgorithmList.class, "name", "algorithms",
            AlgorithmListPanel.class, algorithmListActions, null));

      nodeInfos.add(new NodeInfoImpl(AlgorithmWrapper.class, "name",
            "configurations", AlgorithmWrapperPanel.class,
            algorithmWrapperActions, null));

      nodeInfos.add(new NodeInfoImpl(Configuration.class, "name", null,
            ConfigurationPanel.class, configurationActions, null));

      return nodeInfos;

   }


   public static List<NodeInfo> createTestSetNodeInfo() {
      List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();

      List<Class< ? extends BasicAction>> rootActions = new ArrayList<Class< ? extends BasicAction>>();
      rootActions.add(ImportTestSetAction.class);
      rootActions.add(NewTestSetAction.class);
      rootActions.add(ConvertGestureSetAction.class);

      List<Class< ? extends BasicAction>> setActions = new ArrayList<Class< ? extends BasicAction>>();
      setActions.add(DeleteTestSetAction.class);
      setActions.add(ExportTestSetAction.class);

      List<Class< ? extends BasicAction>> classActions = new ArrayList<Class< ? extends BasicAction>>();
      classActions.add(DeleteTestClassAction.class);
      
      
      Icon setIcon = IconTool.getIcon("mimetypes/package-x-generic",
            Decorator.SIZE_16);

      nodeInfos = new ArrayList<NodeInfo>();
      
      nodeInfos.add(new NodeInfoImpl(TestSetList.class, "name",
            TestSetList.PROPERTY_SETS, TestSetsPanel.class, rootActions, null));
      
      nodeInfos.add(new NodeInfoImpl(TestSet.class, "name", "testClasses",
            TestSetPanel.class, setActions, setIcon));
      
      nodeInfos.add(new NodeInfoImpl(TestClass.class, "name", null, TestClassPanel.class, classActions, null));

      // nodeInfos.add(new NodeInfoImpl(GestureSample.class, "name", null,
      // DefaultPanel.class, null, null));

      return nodeInfos;
   }

}
