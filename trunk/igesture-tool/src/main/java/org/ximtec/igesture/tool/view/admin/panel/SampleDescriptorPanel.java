package org.ximtec.igesture.tool.view.admin.panel;

import hacks.JNote;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.sigtec.input.BufferedInputDeviceEventListener;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.io.MouseReader;
import org.ximtec.igesture.io.MouseReaderEventListener;
import org.ximtec.igesture.util.GestureTool;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class SampleDescriptorPanel extends AbstractAdminPanel {

  public SampleDescriptorPanel(SampleDescriptor descriptor) {

    setTitle(descriptor.toString());

    FormLayout layout = new FormLayout(
        "pref, 4dlu, pref, 4dlu, pref, 4dlu, pref,4dlu, pref",
        "pref, pref, pref, pref, pref, pref,  pref,  pref,  pref,  pref,  pref,  pref");

    DefaultFormBuilder builder = new DefaultFormBuilder(layout);
    builder.setDefaultDialogBorder();

    builder.append(new JLabel("Descriptor has "
        + descriptor.getSamples().size() + " samples."));
    builder.nextLine(4);

    for (GestureSample sample : descriptor.getSamples()) {
      JLabel label = new JLabel(new ImageIcon(GestureTool.createNoteImage(
          sample.getNote(), 100, 100)));
      builder.append(label);
    }

    JPanel panel = builder.getPanel();
    panel.setOpaque(false);
    panel.setAutoscrolls(true);

    setCenter(panel);

    JNote note = new JNote(200, 200);
    InputDeviceClient penClient = new InputDeviceClient(new MouseReader(),
        new BufferedInputDeviceEventListener(new MouseReaderEventListener(),
            100));
    penClient.addInputHandler(note);
    penClient.init();
    setBottom(note);

  }
}
