

package org.ximtec.igesture.tool.view.admin.action;

import java.awt.event.ActionEvent;

import org.sigtec.graphix.widget.BasicAction;
import org.sigtec.ink.Note;
import org.ximtec.igesture.core.GestureSample;
import org.ximtec.igesture.core.SampleDescriptor;
import org.ximtec.igesture.io.InputDeviceClient;
import org.ximtec.igesture.tool.GestureConstants;
import org.ximtec.igesture.tool.locator.Locator;
import org.ximtec.igesture.tool.service.GuiBundleService;
import org.ximtec.igesture.tool.service.InputDeviceClientService;


public class AddGestureSampleAction extends BasicAction {

   SampleDescriptor descriptor;


   public AddGestureSampleAction(SampleDescriptor descriptor) {
      super(GestureConstants.GESTURE_SAMPLE_ADD, Locator.getDefault()
            .getService(GuiBundleService.IDENTIFIER, GuiBundleService.class));
      this.descriptor = descriptor;
   }


   @Override
   public void actionPerformed(ActionEvent arg0) {
      InputDeviceClient client = Locator.getDefault().getService(
            InputDeviceClientService.IDENTIFIER, InputDeviceClient.class);

      Note note = client.createNote(0, Integer.MAX_VALUE, 50);
      GestureSample sample = new GestureSample("SupiDupi", note);
      descriptor.addSample(sample);
   }

}
