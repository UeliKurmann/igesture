/*
 * @(#)XMLTool.java   1.0   Dec 26, 2006
 *
 * Author       :   Ueli Kurmann, kurmannu@ethz.ch
 *
 * Purpose      : 	Provides static methods with XML import/export 
 * 					functionality
 *
 * -----------------------------------------------------------------------
 *
 * Revision Information:
 *
 * Date             Who         Reason
 *
 * 26.12.2006       ukurmann    Initial Release
 *
 * -----------------------------------------------------------------------
 *
 * Copyright 1999-2007 ETH Zurich. All Rights Reserved.
 *
 * This software is the proprietary information of ETH Zurich.
 * Use is subject to license terms.
 * 
 */

package org.ximtec.igesture.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.sigtec.jdom.JdomDocument;
import org.sigtec.jdom.id.Factory;
import org.sigtec.util.FileHandler;
import org.ximtec.igesture.batch.BatchProcessContainer;
import org.ximtec.igesture.batch.BatchResultSet;
import org.ximtec.igesture.batch.core.BatchAlgorithm;
import org.ximtec.igesture.batch.jdom.JdomBatchResultSet;
import org.ximtec.igesture.configuration.Configuration;
import org.ximtec.igesture.configuration.jdom.JdomConfiguration;
import org.ximtec.igesture.core.GestureClass;
import org.ximtec.igesture.core.GestureSet;
import org.ximtec.igesture.core.TestSet;
import org.ximtec.igesture.core.jdom.JdomGestureClass;
import org.ximtec.igesture.core.jdom.JdomGestureSet;
import org.ximtec.igesture.core.jdom.JdomTestSet;
import org.xml.sax.InputSource;

public class XMLTools {

	public static final String ROOT_TAG = "sets";

	/**
	 * Imports a GestureSet
	 * 
	 * @param file
	 *            the XML file
	 * @return a list of GestureSets
	 */
	@SuppressWarnings("unchecked")
	public static List<GestureSet> importGestureSet(File file) {
		final List<GestureSet> sets = new ArrayList<GestureSet>();

		final Document document = importDocument(file);
		final List<Element> algorithmElements = document.getRootElement()
				.getChildren(JdomGestureSet.ROOT_TAG);
		for (final Element setElement : algorithmElements) {
			sets.add((GestureSet) JdomGestureSet.unmarshal(setElement));
		}

		return sets;
	}

	/**
	 * Exports a GestureSet
	 * 
	 * @param set
	 *            the GestureSet to be exported
	 * @param file
	 *            the XML file
	 */
	public static void exportGestureSet(GestureSet set, File file) {
		final List<GestureSet> list = new ArrayList<GestureSet>();
		list.add(set);
		exportGestureSet(list, file);
	}

	/**
	 * Exports a list of GestureSets
	 * 
	 * @param sets
	 *            the GestureSets to be exported
	 * @param file
	 *            the XML file
	 */
	public static void exportGestureSet(List<GestureSet> sets, File file) {

		final JdomDocument igestureDocument = new JdomDocument(ROOT_TAG);
		final HashSet<GestureClass> hashSet = new HashSet<GestureClass>();

		for (final GestureSet set : sets) {
			for (final GestureClass gestureClass : set.getGestureClasses()) {
				hashSet.add(gestureClass);
			}
		}

		for (final GestureClass gestureClass : hashSet) {
			igestureDocument.attach(new JdomGestureClass(gestureClass));
		}

		for (final GestureSet set : sets) {
			igestureDocument.attach(new JdomGestureSet(set));
		}

		FileHandler.writeFile(file.getPath(), igestureDocument.toXml());
	}

	/**
	 * Exports a TestSet
	 * 
	 * @param testSet
	 *            the TestSet to be exported
	 * @param file
	 *            the XML file
	 */
	public static void exportTestSet(TestSet testSet, File file) {
		final List<TestSet> list = new ArrayList<TestSet>();
		list.add(testSet);
		exportTestSet(list, file);
	}

	/**
	 * Exports a list of TestSets
	 * 
	 * @param testSetList
	 *            the list of TestSets to be exported
	 * @param file
	 *            the XML file
	 */
	public static void exportTestSet(List<TestSet> testSetList, File file) {

		final JdomDocument igestureDocument = new JdomDocument(ROOT_TAG);

		for (final TestSet set : testSetList) {
			igestureDocument.attach(new JdomTestSet(set));
		}

		FileHandler.writeFile(file.getPath(), igestureDocument.toXml());
	}

	/**
	 * Imports a List of TestSets
	 * 
	 * @param file
	 *            the XML File
	 * @return a list of GestureSets
	 */
	@SuppressWarnings("unchecked")
	public static List<TestSet> importTestSet(File file) {
		final List<TestSet> sets = new ArrayList<TestSet>();
		final Document document = importDocument(file);

		final List<Element> testSetElements = document.getRootElement()
				.getChildren(JdomTestSet.ROOT_TAG);
		for (final Element setElement : testSetElements) {
			sets.add((TestSet) JdomTestSet.unmarshal(setElement));
		}

		return sets;
	}

	/**
	 * Imports a Configuration
	 * 
	 * @param file
	 *            the XML file
	 * @return the Configuration
	 */
	public static Configuration importConfiguration(File file) {
		Configuration configuration = new Configuration();
		final Document document = importDocument(file);

		configuration = (Configuration) JdomConfiguration.unmarshal(document
				.getRootElement());

		return configuration;
	}

	/**
	 * Exports a Configuration
	 * 
	 * @param configuration
	 *            the Configuration to be exported
	 * @param file
	 *            the XML file
	 */
	public static void exportConfiguration(Configuration configuration,
			File file) {

		final JdomDocument ipaperDocument = new JdomDocument(
				new JdomConfiguration(configuration));

		FileHandler.writeFile(file.getPath(), ipaperDocument.toXml());
	}

	/**
	 * Exports a BatchResultSet
	 * 
	 * @param resultSet
	 *            the BatchResultSet to be exported
	 * @param file
	 *            the XML file
	 */
	public static void exportBatchResultSet(BatchResultSet resultSet, File file) {
		FileHandler.writeFile(file.getPath(), XMLTools
				.exportBatchResultSet(resultSet));
	}

	/**
	 * Exports a BatchResultSet to its XML representation
	 * 
	 * @param resultSet
	 *            the BatchResultSet to be exported
	 * @return the String containing the XML representation
	 */
	public static String exportBatchResultSet(BatchResultSet resultSet) {
		final JdomDocument document = new JdomDocument(new JdomBatchResultSet(
				resultSet));
		return document.toXml();
	}

	/**
	 * Imports an XML Document
	 * 
	 * @param file
	 *            the XML file
	 * @return the JDOM Document
	 */
	private static Document importDocument(File file) {
		Document document = null;
		try {
			final InputStream inputStream = new FileInputStream(file);
			final SAXBuilder builder = new SAXBuilder(false);
			builder.setFactory(new Factory());
			builder.setIgnoringElementContentWhitespace(true);
			final InputSource is = new InputSource(inputStream);
			document = builder.build(is);
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final JDOMException e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * Imports an XML File and builds up the object tree. The result is a list
	 * of BatchAlgorithm instances.
	 * 
	 * @param file
	 *            the XML file to import
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static BatchProcessContainer importBatchProcessContainer(File file) {
		final BatchProcessContainer container = new BatchProcessContainer();

		final Document document = importDocument(file);

		for (final Element elem : (List<Element>) document.getRootElement()
				.getChildren(BatchAlgorithm.ROOT_TAG)) {
			container.addAlgorithm(BatchAlgorithm.unmarshal(elem));
		}

		for (final Element set : (List<Element>) document.getRootElement()
				.getChildren(JdomGestureSet.ROOT_TAG)) {
			container.addGestureSet((GestureSet) JdomGestureSet.unmarshal(set));
		}

		return container;
	}

	/**
	 * transform
	 * 
	 * @author Ueli Kurmann
	 * @version 1.0
	 * @param xmlDocument
	 *            (String)
	 * @param xslFile
	 *            (String)
	 * @return xmlDocument (String)
	 */
	public static String transform(String xmlDocument, String xslFile)
			throws TransformerException, TransformerConfigurationException,
			TransformerFactoryConfigurationError {

		final StringReader xmlSR = new StringReader(xmlDocument);
		final StreamSource xmlIn = new StreamSource(xmlSR);

		final StringWriter xmlSW = new StringWriter();
		final StreamResult xmlOut = new StreamResult(xmlSW);

		FileReader xslSR = null;
		try {
			xslSR = new FileReader(new File(xslFile));
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		}

		final TransformerFactory tFactory = TransformerFactory.newInstance();
		final Transformer transformer = tFactory
				.newTransformer(new StreamSource(xslSR));

		transformer.transform(xmlIn, xmlOut);

		return xmlSW.toString();
	}

	/**
	 * xsl transformation
	 * @param xmlDocument xml document (String)
	 * @param xsl xsl document (InputStream)
	 * @return
	 * @throws TransformerException
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 */
	public static String transform(String xmlDocument, InputStream xsl)
			throws TransformerException, TransformerConfigurationException,
			TransformerFactoryConfigurationError {

		final StringReader xmlSR = new StringReader(xmlDocument);
		final StreamSource xmlIn = new StreamSource(xmlSR);

		final StringWriter xmlSW = new StringWriter();
		final StreamResult xmlOut = new StreamResult(xmlSW);

		StreamSource xslSR = new StreamSource(xsl);

		final TransformerFactory tFactory = TransformerFactory.newInstance();
		final Transformer transformer = tFactory
				.newTransformer(xslSR);

		transformer.transform(xmlIn, xmlOut);

		return xmlSW.toString();
	}
}
