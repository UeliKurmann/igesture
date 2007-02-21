<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="batchResultSet">
		<html>
			<head>
				<title>iGesture - Batch Process Report</title>
			</head>
			<body>
				<h1>iGesture - Batch Process Report</h1>
				Date: <xsl:call-template name="currentTime" /> 
				<xsl:apply-templates select="batchResult">
					<xsl:sort select="2 * ./precision * ./recall div (./precision + ./recall)"
						order="descending" data-type="number" />
				</xsl:apply-templates>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template name="currentTime" 
              xmlns:java="http://xml.apache.org/xslt/java">
  		<xsl:value-of select="java:java.util.Date.new()"/>
	</xsl:template>

	<xsl:template match="batchResult">
		<h2>
			<xsl:value-of select="./configuration/algorithm/@name" />
		</h2>
		<table border="1">
			<tr>
				<td>Correct</td>
				<td>Error</td>
				<td>Reject Correct</td>
				<td>Reject Error</td>
				<td>Samples total</td>
				<td>Noise</td>
			</tr>
			<tr>
				<td>
					<xsl:value-of select="numberOfCorrects" />
				</td>
				<td>
					<xsl:value-of select="numberOfErrors" />
				</td>
				<td>
					<xsl:value-of select="numberOfRejectCorrect" />
				</td>
				<td>
					<xsl:value-of select="numberOfRejectError" />
				</td>
				<td>
					<xsl:value-of select="numberOfSamples" />
				</td>
				<td>
					<xsl:value-of select="numberOfNoise" />
				</td>
			</tr>
		</table>
		<p> </p>
		<table border="1">
			<tr>
				<td>Precision</td>
				<td>Recall</td>
				<td>F-measure</td>
				<td>Running Time</td>
			</tr>
			<tr>
				<td>
					<xsl:value-of select="precision" />
				</td>
				<td>
					<xsl:value-of select="recall" />
				</td>
				<td>
					<xsl:value-of select="2 * ./precision * ./recall div (./precision + ./recall)" />
				</td>
				<td>
					<xsl:value-of select="runningTime" />
				</td>
			</tr>
		</table>
		<xsl:apply-templates select="configuration/algorithm" />
		<p/>
		<table border="1">
			<tr>
				<td>GestureClass</td>
				<td>Correct</td>
				<td>Error</td>
				<td>Reject Correct</td>
				<td>Reject Error</td>
			</tr>
			<xsl:apply-templates select="./classStatistic" />
		</table>

	</xsl:template>

	<xsl:template match="classStatistic">
		<tr>
			<td>
				<xsl:value-of select="@name" />
			</td>
			<td>
				<xsl:value-of select="numberOfCorrects" />
			</td>
			<td>
				<xsl:value-of select="numberOfErrors" />
			</td>
			<td>
				<xsl:value-of select="numberOfRejectCorrect" />
			</td>
			<td>
				<xsl:value-of select="numberOfRejectError" />
			</td>
		</tr>
	</xsl:template>


	<xsl:template match="configuration/algorithm">
		Configuration Parameter
		<table border="1">
			<xsl:apply-templates select="parameter" />
		</table>
	</xsl:template>

	<xsl:template match="parameter">
		<tr>
			<td>
				<xsl:value-of select="@name" />
			</td>
			<td>
				<xsl:value-of select="." />
			</td>
		</tr>
	</xsl:template>

</xsl:stylesheet>
