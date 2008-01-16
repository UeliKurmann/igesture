 # Auto-generated by EclipseNSIS Script Wizard
# Jan 7, 2008 2:55:14 PM

Name Geco

# Defines
!define REGKEY "SOFTWARE\$(^Name)"
!define VERSION 1.0
!define COMPANY ""
!define URL ""

# MUI defines
!define MUI_FINISHPAGE_NOAUTOCLOSE
!define MUI_STARTMENUPAGE_REGISTRY_ROOT HKLM
!define MUI_STARTMENUPAGE_REGISTRY_KEY ${REGKEY}
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME StartMenuGroup
!define MUI_STARTMENUPAGE_DEFAULTFOLDER Geco

!define MUI_FINISHPAGE_RUN
#!define MUI_FINISHPAGE_RUN_TEXT "Start a shortcut"
#!define MUI_FINISHPAGE_RUN $INSTDIR\bin
!define MUI_FINISHPAGE_RUN_FUNCTION "LaunchGeco"

# Included files
!include Sections.nsh
!include MUI.nsh

# Variables
Var StartMenuGroup

# Installer pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_STARTMENU Application $StartMenuGroup
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_PAGE_FINISH
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

# Installer languages
!insertmacro MUI_LANGUAGE English

# Installer attributes
OutFile geco_setup.exe
InstallDir $PROGRAMFILES\Geco
CRCCheck on
XPStyle on
ShowInstDetails show
VIProductVersion 1.0.0.0
VIAddVersionKey ProductName Geco
VIAddVersionKey ProductVersion "${VERSION}"
VIAddVersionKey FileVersion "${VERSION}"
VIAddVersionKey FileDescription ""
VIAddVersionKey LegalCopyright ""
InstallDirRegKey HKLM "${REGKEY}" Path
ShowUninstDetails hide

# Installer sections
Section -Main SEC0000
    SetOutPath $INSTDIR
    SetOverwrite on
  #  File target\igesture-geco-1.2-SNAPSHOT\tray_icon.png
    File target\igesture-geco-1.2-SNAPSHOT\about.html
    File target\igesture-geco-1.2-SNAPSHOT\gecoConfig.xml
    File target\igesture-geco-1.2-SNAPSHOT\geco_exe.exe
    File target\igesture-geco-1.2-SNAPSHOT\geco.properties
  #  File target\igesture-geco-1.2-SNAPSHOT\gecoProperties.properties.bak
    File target\igesture-geco-1.2-SNAPSHOT\ms_application_gestures.xml
    File target\igesture-geco-1.2-SNAPSHOT\ms_gestures_mapping.xml
    File target\igesture-geco-1.2-SNAPSHOT\rubineconfiguration.xml
    SetOutPath $INSTDIR\bin
    File /r target\igesture-geco-1.2-SNAPSHOT\bin\*
    SetOutPath $INSTDIR\lib
    File /r target\igesture-geco-1.2-SNAPSHOT\lib\*
    SetOutPath $INSTDIR\gestureSets
    File /r target\igesture-geco-1.2-SNAPSHOT\gestureSets\*
    SetOutPath $INSTDIR\log
#    File /r target\igesture-geco-1.2-SNAPSHOT\log\*
#    SetOutPath $INSTDIR
    File target\igesture-geco-1.2-SNAPSHOT\bin\run.bat
    WriteRegStr HKLM "${REGKEY}\Components" Main 1
SectionEnd

Section -post SEC0001
    WriteRegStr HKLM "${REGKEY}" Path $INSTDIR
    SetOutPath $INSTDIR
    WriteUninstaller $INSTDIR\uninstall.exe
    !insertmacro MUI_STARTMENU_WRITE_BEGIN Application
    SetOutPath $SMPROGRAMS\$StartMenuGroup
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Uninstall $(^Name).lnk" $INSTDIR\uninstall.exe
    SetOutPath $INSTDIR\bin
    CreateShortcut "$SMPROGRAMS\$StartMenuGroup\Run $(^Name).lnk" $INSTDIR\bin\run.bat
    !insertmacro MUI_STARTMENU_WRITE_END
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayName "$(^Name)"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayVersion "${VERSION}"
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" DisplayIcon $INSTDIR\uninstall.exe
    WriteRegStr HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" UninstallString $INSTDIR\uninstall.exe
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoModify 1
    WriteRegDWORD HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)" NoRepair 1
SectionEnd

# Macro for selecting uninstaller sections
!macro SELECT_UNSECTION SECTION_NAME UNSECTION_ID
    Push $R0
    ReadRegStr $R0 HKLM "${REGKEY}\Components" "${SECTION_NAME}"
    StrCmp $R0 1 0 next${UNSECTION_ID}
    !insertmacro SelectSection "${UNSECTION_ID}"
    GoTo done${UNSECTION_ID}
next${UNSECTION_ID}:
    !insertmacro UnselectSection "${UNSECTION_ID}"
done${UNSECTION_ID}:
    Pop $R0
!macroend

# Uninstaller sections
Section /o -un.Main UNSEC0000
    Delete /REBOOTOK $INSTDIR\run.bat
    RmDir /r /REBOOTOK $INSTDIR\log
    RmDir /r /REBOOTOK $INSTDIR\gestureSets
    RmDir /r /REBOOTOK $INSTDIR\lib
    RmDir /r /REBOOTOK $INSTDIR\bin
    Delete /REBOOTOK $INSTDIR\rubineconfiguration.xml
    Delete /REBOOTOK $INSTDIR\ms_gestures_mapping.xml
    Delete /REBOOTOK $INSTDIR\ms_application_gestures.xml
 #   Delete /REBOOTOK $INSTDIR\gecoProperties.properties.bak
    Delete /REBOOTOK $INSTDIR\geco.properties
    Delete /REBOOTOK $INSTDIR\geco_exe.exe
    Delete /REBOOTOK $INSTDIR\gecoConfig.xml
    Delete /REBOOTOK $INSTDIR\config2.xml
    Delete /REBOOTOK $INSTDIR\about.html
  #  Delete /REBOOTOK $INSTDIR\tray_icon.png
    DeleteRegValue HKLM "${REGKEY}\Components" Main
SectionEnd

Section -un.post UNSEC0001
    DeleteRegKey HKLM "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\$(^Name)"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\Uninstall $(^Name).lnk"
    Delete /REBOOTOK "$SMPROGRAMS\$StartMenuGroup\$(^Name).lnk"
    Delete /REBOOTOK $INSTDIR\uninstall.exe
    DeleteRegValue HKLM "${REGKEY}" StartMenuGroup
    DeleteRegValue HKLM "${REGKEY}" Path
    DeleteRegKey /IfEmpty HKLM "${REGKEY}\Components"
    DeleteRegKey /IfEmpty HKLM "${REGKEY}"
    RmDir /REBOOTOK $SMPROGRAMS\$StartMenuGroup
    RmDir /REBOOTOK $INSTDIR
    Push $R0
    StrCpy $R0 $StartMenuGroup 1
    StrCmp $R0 ">" no_smgroup
no_smgroup:
    Pop $R0
SectionEnd

# Installer functions
Function .onInit
    InitPluginsDir
FunctionEnd

Function LaunchGeco
    SetOutPath $INSTDIR\bin
  ExecShell "" $INSTDIR\bin\run.bat
FunctionEnd

# Uninstaller functions
Function un.onInit
    ReadRegStr $INSTDIR HKLM "${REGKEY}" Path
    !insertmacro MUI_STARTMENU_GETFOLDER Application $StartMenuGroup
    !insertmacro SELECT_UNSECTION Main ${UNSEC0000}
FunctionEnd

