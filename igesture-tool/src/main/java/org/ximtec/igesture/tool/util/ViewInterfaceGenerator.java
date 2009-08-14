package org.ximtec.igesture.tool.util;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.ximtec.igesture.tool.view.MainView;

public class ViewInterfaceGenerator {

  private static final String SEMICOLON = ";";
  private static final String NL = "\n";

  public static void main(String[] args) {
    System.out.println(generateInterface(MainView.class));
  }

  public static StringBuilder generateInterface(Class<?> type) {
    StringBuilder sb = new StringBuilder();

    sb.append("// automatically generated code");
    sb.append(NL);
    sb.append("package " + type.getPackage().getName() + SEMICOLON);
    sb.append(NL);
    sb.append(NL);
    sb.append("public interface I" + type.getSimpleName() + "{");
    sb.append(NL);
    for (Method method : type.getMethods()) {

      if (method.isSynthetic() || method.isBridge() || (Modifier.isFinal(method.getModifiers()))) {
        continue;
      }

      sb.append("  public ");
      if(method.getReturnType().isArray()){
        sb.append(method.getReturnType().getName().substring(2).replace(';', '[')+"]");
      }else{
        sb.append(method.getReturnType().getName());
      }
      sb.append(" " + method.getName() + "(");
      int i = 1;
      if (method.getParameterTypes().length > 0) {
        for (Class<?> parameterType : method.getParameterTypes()) {
          if (parameterType.isArray()) {
            sb.append(parameterType.getName().substring(2) + "[] p" + i + ",");
          } else {
            sb.append(parameterType.getName() + " p" + i + ",");
          }
          i++;
        }
        sb.deleteCharAt(sb.length() - 1);
      }
      sb.append(");");
      sb.append(NL);
      sb.append(NL);
    }
    sb.append("}");

    return sb;
  }

}
