/*
 *  Copyright 2007,2008 Ueli Kurmann, bbv Software Services AG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package org.ximtec.igesture.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.sigtec.util.Constant;


public class ZipFS {

   public final static String SEPERATOR = "/";

   private ZipFile zipFile;
   private File file;
   private File tmpFile;
   private ZipOutputStream zipOutput;
   private Map<String, Map<String, ZipEntry>> fileIndex;
   private Set<String> toIgnore;


   public ZipFS(File file) throws IOException {
      this.file = file;

      fileIndex = new HashMap<String, Map<String, ZipEntry>>();

      if (this.file.exists()) {
         zipFile = new ZipFile(this.file);
         indexFile();
      }

      tmpFile = File.createTempFile(
            Long.toHexString(System.currentTimeMillis()), null);

      zipOutput = new ZipOutputStream(new FileOutputStream(tmpFile));

      toIgnore = new HashSet<String>();
   }


   private synchronized void indexFile() throws IOException {
      fileIndex.clear();
      
      Enumeration< ? extends ZipEntry> enumerator = zipFile.entries();
      fileIndex.put(Constant.EMPTY_STRING, new HashMap<String, ZipEntry>());
      while (enumerator.hasMoreElements()) {
         ZipEntry entry = enumerator.nextElement();
         addEntryToIndex(entry);
      }
   }


   private void addEntryToIndex(ZipEntry zipEntry) {
      String parentPath = getParentPath(zipEntry);
      createDirectoryEntry(fileIndex, parentPath);
      if (zipEntry.isDirectory()) {
         fileIndex.get(parentPath).put(getName(zipEntry), zipEntry);
      }
      else {
         fileIndex.get(parentPath).put(getName(zipEntry), zipEntry);
      }
   }


   private void createDirectoryEntry(Map<String, Map<String, ZipEntry>> index,
         String path) {
      path = normalizePath(path);
      if (path.contains(SEPERATOR)) {
         createDirectoryEntry(index, path.substring(0, path.lastIndexOf(SEPERATOR)));
      }
      if (!index.containsKey(path)) {
         index.put(path, new HashMap<String, ZipEntry>());
         String parentPath = getParentPath(path);
         index.get(parentPath).put(getName(path), new ZipEntry(path+SEPERATOR));
      }
   }


   public static String getName(ZipEntry zipEntry) {
      return getName(zipEntry.getName());
   }


   public static String getName(String path) {
      path = normalizePath(path);
      int index = path.lastIndexOf(SEPERATOR) + 1;
      if (index == 0) {
         return path;
      }
      else {
         return path.substring(index);
      }
   }


   private static String normalizePath(String path) {
      while (path.endsWith(SEPERATOR)) {
         path = path.substring(0, path.length() - 1);
      }

      while (path.startsWith(SEPERATOR)) {
         path = path.substring(1);
      }

      return path;
   }


   public static String getParentPath(ZipEntry zipEntry) {
      return getParentPath(zipEntry.getName());
   }


   public static String getParentPath(String path) {
      if (path == null) {
         return null;
      }

      path = normalizePath(path);

      int lastIndex = path.lastIndexOf(SEPERATOR);

      if (lastIndex == -1) {
         return Constant.EMPTY_STRING;
      }
      else {
         return path.substring(0, lastIndex);
      }
   }

   public List<ZipEntry> listFiles(String path) {
      path = normalizePath(path);
      if(fileIndex.containsKey(path)){
         return new ArrayList<ZipEntry>(fileIndex.get(path).values());
      }
      return new ArrayList<ZipEntry>();
   }


   public InputStream getInputStream(String path) throws IOException {
      return zipFile.getInputStream(getEntry(path));
   }
   
   public InputStream getInputStream(ZipEntry entry) throws IOException{
      return getInputStream(entry.getName());
   }


   public ZipEntry getEntry(String path) throws IOException {
      String parentPath = getParentPath(path);
      String name = getName(path);
      Map<String, ZipEntry> entries = fileIndex.get(parentPath);
      ZipEntry entry = null;

      if (entries != null) {
         entry = entries.get(name);
      }

      if (entry == null || entry.isDirectory()) {
         throw new FileNotFoundException(path);
      }

      return entry;
   }


   public ZipFS(String fileName) throws IOException {
      this(new File(fileName));
   }


   public void delete(String path) {
      toIgnore.add(normalizePath(path));
   }


   public void store(String path, InputStream stream) throws IOException {
      toIgnore.add(normalizePath(path));
      ZipEntry entry = new ZipEntry(path);
      zipOutput.putNextEntry(entry);
      IOUtils.copy(stream, zipOutput);
      zipOutput.closeEntry();
   }


   private void copyEntries() {
      if (zipFile != null) {
         Enumeration< ? extends ZipEntry> enumerator = zipFile.entries();
         while (enumerator.hasMoreElements()) {
            ZipEntry entry = enumerator.nextElement();
            if (!entry.isDirectory()
                  && !toIgnore.contains(normalizePath(entry.getName()))) {
               ZipEntry originalEntry = new ZipEntry(entry.getName());
               try {
                  zipOutput.putNextEntry(originalEntry);
                  IOUtils.copy(getInputStream(entry.getName()), zipOutput);
                  zipOutput.closeEntry();
               }
               catch (IOException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   }


   public void close() throws IOException {
      copyEntries();

      zipOutput.close();
      if (zipFile != null) {
         zipFile.close();
         file.delete();
      }
      FileUtils.moveFile(tmpFile, file);
   }
}
