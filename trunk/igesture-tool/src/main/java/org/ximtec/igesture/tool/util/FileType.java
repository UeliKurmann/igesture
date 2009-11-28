package org.ximtec.igesture.tool.util;

import java.io.File;

public enum FileType {
  
  compressedWorkbench("igz", "iGesture Workspace Compressed"),
  xstreamWorkbench("igx", "iGesture Workspace XStream"),
  db4oWorkbench("igd", "iGesture Workspace db4o"),
  gestureSet("igs", "iGesture Gesture Set"),
  testSet("igt", "iGesture Test Set"),
  algorithmConfiguration("igc", "iGesture Recogniser Configuration"),
  pdf ("pdf", "Portable Document Format"),
  igb("igb", "iGesture Batch Configuration");
  
  private String extension;
  private String description;
  private ExtensionFileFilter filter;
  
  /**
   * Constructor
   * @param extension
   * @param description
   */
  private FileType(String extension, String description){
    this.extension = extension;
    this.description = description;
    this.filter = new ExtensionFileFilter(this.extension, this.description);
  }
  
  /**
   * Returns the file type extension
   * @return the file type extension
   */
  public String getExtension(){
    return this.extension;
  }
  
  /**
   * Returns the file type description
   * @return the file type description
   */
  public String getDescription(){
    return this.description;
  }

  /**
   * Returns the extension file filter 
   * @return the extension file filter 
   */
  public ExtensionFileFilter getFilter(){
    return this.filter;
  }
  
  /**
   * Adds the extension if it is not already there.
   * @param file
   * @return file with extension
   */
  public File addExtension(File file){
    if(file != null && !file.getName().endsWith(this.extension) ){
      File parent = file.getParentFile();
      return new File(parent, String.format("%s.%s", file.getName(), extension));
    }
    
    return file;
  }
}
