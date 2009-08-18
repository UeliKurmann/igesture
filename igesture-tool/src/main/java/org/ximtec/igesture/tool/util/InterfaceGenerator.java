package org.ximtec.igesture.tool.util;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.ximtec.igesture.tool.view.testbench.TestbenchView;


public class InterfaceGenerator {

	private static final String ARRAY = "[]";
	private static final String COMMA = ",";
	private static final String QMARK = "?";
	private static final String AND = "&";
	private static final String A_OPEN = "<";
	private static final String A_CLOSE = ">";
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
	private static final String EXTENDS = "extends";
	private static final String THROWS = "throws";
	private static final String BLANK = " ";
	private static final String SEMICOLON = ";";
	private static final String NL = "\n";

	private static final List<String> declaredGenericTypes = new LinkedList<String>();
	private static int declaredGenericTypesInsertPosition;

	public static void main(String[] args) {
		System.out.println(generateInterface(TestbenchView.class));
	}

	public static StringBuilder generateInterface(Class<?> type) {
		StringBuilder sb = new StringBuilder();

		sb.append("// automatically generated code");
		sb.append(NL);
		sb.append(PACKAGE + BLANK + type.getPackage().getName() + SEMICOLON);
		sb.append(NL);
		sb.append(NL);
		sb.append(PUBLIC + BLANK + INTERFACE + BLANK + INTERFACE_PREFIX
				+ type.getSimpleName() + BLANK);
		declaredGenericTypesInsertPosition = sb.length();
		sb.append(CB_OPEN);
		sb.append(NL);
		sb.append(NL);
		for (Method method : type.getMethods()) {

			if (method.isSynthetic() || method.isBridge()
					|| (Modifier.isStatic(method.getModifiers())) || (Modifier.isFinal(method.getModifiers()))) {
				continue;
			}

			generateMethodDefinition(sb, method);

			sb.append(NL);
			sb.append(NL);
		}
		sb.append(CB_CLOSE);

		if (!declaredGenericTypes.isEmpty()) {
			Collections.reverse(declaredGenericTypes);
			sb.insert(declaredGenericTypesInsertPosition, A_CLOSE + BLANK);
			boolean last = true;
			for (String typeName : declaredGenericTypes) {
				if (last) {
					last = false;
				} else {
					sb
							.insert(declaredGenericTypesInsertPosition, COMMA
									+ BLANK);
				}
				sb.insert(declaredGenericTypesInsertPosition, typeName);
			}
			sb.insert(declaredGenericTypesInsertPosition, A_OPEN);
		}
		return sb;
	}

	private static void generateMethodDefinition(StringBuilder sb, Method method) {
		sb.append(BLANK + BLANK + PUBLIC + BLANK);

		TypeVariable<Method>[] typeParameters = method.getTypeParameters();
		if (typeParameters.length > 0) {
			sb.append(A_OPEN);
			boolean firstParam = true;
			for (TypeVariable<Method> typeVar : typeParameters) {
				if (firstParam) {
					firstParam = false;
				} else {
					sb.append(COMMA).append(BLANK);
				}
				sb.append(typeVar.getName());
				if (typeVar.getBounds() != null) {
					boolean first = true;
					for (Type bound : typeVar.getBounds()) {
						if (bound != Object.class
								|| typeVar.getBounds().length > 1) {
							if (first) {
								first = false;
								sb.append(BLANK).append(EXTENDS);
							} else {
								sb.append(BLANK).append(AND);
							}
							sb.append(BLANK);
							writeType(sb, bound);
						}
					}
				}
			}
			sb.append(A_CLOSE).append(BLANK);
		}

		writeType(sb, method.getGenericReturnType());

		sb.append(BLANK + method.getName() + B_OPEN);

		int i = 1;
		for (Type parameterType : method.getGenericParameterTypes()) {
			if (i > 1) {
				sb.append(COMMA).append(BLANK);
			}
			writeType(sb, parameterType);
			sb.append(BLANK).append("arg").append(i);
			i++;
		}
		sb.append(B_CLOSE);

		boolean first = true;
		for (Type exceptionType : method.getGenericExceptionTypes()) {
			if (first) {
				sb.append(BLANK).append(THROWS).append(BLANK);
				first = false;
			} else {
				sb.append(COMMA).append(BLANK);
			}
			writeType(sb, exceptionType);
		}

		sb.append(SEMICOLON);
	}

	private static void writeType(StringBuilder sb, Type type) {

		if (type instanceof Class<?>) {
			Class<?> typeClass = (Class<?>) type;

			if (typeClass.isArray()) {
				Class<?> componentType = typeClass.getComponentType();
				sb.append(componentType.getName().replace('$','.')).append(ARRAY);
			} else {
				sb.append(typeClass.getName().replace('$','.'));
			}
		} else if (type instanceof TypeVariable<?>) {
			TypeVariable<?> typeVar = (TypeVariable<?>) type;

			Object genericDeclaration = typeVar.getGenericDeclaration();
			if (genericDeclaration instanceof Method) {
			} else {
				if (!declaredGenericTypes.contains(typeVar.getName())) {
					declaredGenericTypes.add(typeVar.getName());
				}
			}
			sb.append(typeVar.getName());
		} else if (type instanceof GenericArrayType) {
			GenericArrayType arrayType = (GenericArrayType) type;

			Type genericComponentType = arrayType.getGenericComponentType();
			writeType(sb, genericComponentType);
			sb.append(ARRAY);
		} else if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;

			sb.append(paramType);
		}
	}
}
