<?xml version="1.0" ?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">
	 
		 
	<xsl:output method="text" indent="no" />

	<xsl:template match="batchResultSet">
		<xsl:apply-templates select="batchResult">
			<xsl:sort
				select="2 * ./precision * ./recall div (./precision + ./recall)"
				order="descending" data-type="number" />
			<xsl:sort select="./precision" />
		</xsl:apply-templates>
	</xsl:template>


	<xsl:template match="batchResult">
		<xsl:apply-templates select="configuration/algorithm" />

		\subsubsection*{Absolute values}
		\begin{tabularx}{400pt}{|X|X|l|l|l|X|} \hline

		\textbf{Correct} &amp; \textbf{Error} &amp; \textbf{Reject
		Correct} &amp; \textbf{Reject Error} &amp; \textbf{Samples
		total} &amp; \textbf{Noise} \\ \hline

		<xsl:value-of select="numberOfCorrects" />
		&amp;
		<xsl:value-of select="numberOfErrors" />
		&amp;
		<xsl:value-of select="numberOfRejectCorrect" />
		&amp;
		<xsl:value-of select="numberOfRejectError" />
		&amp;
		<xsl:value-of select="numberOfSamples" />
		&amp;
		<xsl:value-of select="numberOfNoise" />
		\\ \hline \end{tabularx}

		\subsubsection*{Absolute values per gesture class}

		\begin{tabularx}{400pt}{|l|X|X|l|l|} \hline \textbf{Gesture
		Class} &amp; \textbf{Correct} &amp; \textbf{Error} &amp;
		\textbf{Reject Correct} &amp; \textbf{Reject Error} \\

		<xsl:apply-templates select="./classStatistic">
			<xsl:sort select="@name" />
		</xsl:apply-templates>

		\hline \end{tabularx}

		\subsubsection*{Key figures} \begin{tabularx}{400pt}{|X|X|X|}
		\hline \textbf{Precision} &amp; \textbf{Recall} &amp;
		\textbf{F-measure} \\ \hline
		<xsl:value-of select="precision" />
		&amp;
		<xsl:value-of select="recall" />
		&amp;
		<xsl:value-of
			select="2 * ./precision * ./recall div (./precision + ./recall)" />
		\\ \hline \end{tabularx}

		---END---
	</xsl:template>

	<xsl:template match="classStatistic">
		\hline
		<xsl:value-of select="@name" />
		&amp;
		<xsl:value-of select="numberOfCorrects" />
		&amp;
		<xsl:value-of select="numberOfErrors" />
		&amp;
		<xsl:value-of select="numberOfRejectCorrect" />
		&amp;
		<xsl:value-of select="numberOfRejectError" />
		\\
	</xsl:template>


	<xsl:template match="configuration/algorithm">

		\subsubsection*{Configuration} \begin{tabularx}{400pt}{|l|X|}
		\hline \textbf{Parameter} &amp; \textbf{Value} \\
		<xsl:apply-templates select="parameter" />
		\hline \end{tabularx}
	</xsl:template>

	<xsl:template match="parameter">
		\hline
		<xsl:value-of select="@name" />
		&amp;
		<xsl:value-of select="." />
		\\
	</xsl:template>

</xsl:stylesheet>
