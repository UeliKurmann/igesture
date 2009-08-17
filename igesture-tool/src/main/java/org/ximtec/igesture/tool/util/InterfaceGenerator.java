package org.ximtec.igesture.tool.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.ximtec.igesture.tool.view.MainView;

public class InterfaceGenerator {

  private static final String ARRAY = "[]";
  private static final String COMMA = ",";
  private static final String B_OPEN = "(";
  private static final String B_CLOSE = ")";
  private static final String CB_CLOSE = "}";
  private static final String CB_OPEN = "{";
  private static final String AB_CLOSE = "]";
  private static final String AB_OPEN = "[";
  private static final String INTERFACE_PREFIX = "I";
  private static final String PACKAGE = "package";
  private static final String PUBLIC = "public";
  private static final String INTERFACE = "interface";
  private static final String BLANK = " ";
  private static final String SEMICOLON = ";";
  private static final String NL = "\n";

  public static void main(String[] args) {
    System.out.println(generateInterface(MainView.class));
  }

  public static StringBuilder generateInterface(Class<?> type) {
    StringBuilder sb = new StringBuilder();

    sb.append("// automatically generated code");
    sb.append(NL);
    sb.append(PACKAGE + BLANK + type.getPackage().getName() + SEMICOLON);
    sb.append(NL);
    sb.append(NL);
    sb.append(PUBLIC + BLANK + INTERFACE + BLANK + INTERFACE_PREFIX + type.getSimpleName() + CB_OPEN);
    sb.append(NL);
    for (Method method : type.getMethods()) {

      if (method.isSynthetic() || method.isBridge() || (Modifier.isFinal(method.getModifiers()))) {
        continue;
      }

      generateMethodDefinition(sb, method);
      
      sb.append(NL);
      sb.append(NL);
    }
    sb.append(CB_CLOSE);

    return sb;
  }

  private static void generateMethodDefinition(StringBuilder sb, Method method) {
    sb.append(BLANK + BLANK + PUBLIC + BLANK);

    extractReturnType(sb, method);

    sb.append(BLANK + method.getName() + B_OPEN);
    if (method.getParameterTypes().length > 0) {
      int i = 1;
      for (Class<?> parameterType : method.getParameterTypes()) {
        if (parameterType.isArray()) {
          sb.append(parameterType.getName().substring(2) + ARRAY + BLANK + "arg" + i + COMMA);
        } else {
          sb.append(parameterType.getName() + BLANK + "arg" + i + COMMA);
        }
        i++;
      }
      sb.deleteCharAt(sb.length() - 1);
    }
    sb.append(B_CLOSE + SEMICOLON);
  }

  private static void extractReturnType(StringBuilder sb, Method method) {

    Class<?> returnType = method.getReturnType();

    if (returnType.isArray()) {
      sb.append(generateArrayTypeName(returnType));
    } else {
      sb.append(returnType.getName());
    }
    
 
  }

  private static String generateArrayTypeName(Class<?> arrayType) {
    char[] binaryName = arrayType.getName().toCharArray();
    binaryName = Arrays.copyOfRange(arrayType.getName().toCharArray(), 0, binaryName.length - 1);
    // count the number of preceding [ characters
    int arrayDepth = 0;
    for (int i = 0; i < binaryName.length; i++) {
      if (AB_OPEN.charAt(0) == binaryName[i]) {
        arrayDepth++;
      } else {
        break;
      }
    }

    String typeName = null;
    switch (binaryName[arrayDepth]) {
    case 'L':
      typeName = new String(binaryName, arrayDepth + 1, binaryName.length - arrayDepth - 1);
      break;
    case 'Z':
      typeName = "boolean";
      break;
    case 'B':
      typeName = "byte";
      break;
    case 'C':
      typeName = "char";
      break;
    case 'D':
      typeName = "double";
      break;
    case 'F':
      typeName = "float";
      break;
    case 'I':
      typeName = "I";
      break;
    case 'J':
      typeName = "long";
      break;
    case 'S':
      typeName = "short";
      break;
    default:
      throw new IllegalStateException("should not happen... ;-)");
    }

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < arrayDepth; i++) {
      sb.append(ARRAY);
    }

    return typeName + sb.toString();
  }

}
