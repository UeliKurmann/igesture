package hacks;

import org.sigtec.graphix.GuiBundle;
import org.ximtec.igesture.tool.locator.Service;

public class GuiBundleService extends GuiBundle implements Service{

  public static final String IDENTIFIER = "guiBundle";

  public GuiBundleService(String resourceBundleName) {
    super(resourceBundleName);
  }

  @Override
  public void reset() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void start() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void stop() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String getIdentifier() {
    return IDENTIFIER;
  }

}
