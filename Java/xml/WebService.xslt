<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<xsl:template match="/interface">

<html>
<head>
<style type="text/css">
	body {
		font-family: arial;
	}
	table, th, td {
		text-align: left;
	}
	table {
		border: 2px solid black;
		padding: 2px;
	}
	th, td {
		padding: 10px;
		border: 1px solid red;
	}
	th {
		background-color: blue;
		color: white;
		font-weight: bold;
	}
	.method {
		font-weight: bold;
	}
</style>
</head>

<body>
	<h1><xsl:value-of select="@name" /></h1>
	<table>
	<tr>
		<th>Method</th>
		<th>Return type</th>
		<th>Input parameters</th>
	</tr>
	<xsl:for-each select="abstract_method">
	<tr>
		<td class="method"><xsl:value-of select="@name" /></td>
		<td><xsl:value-of select="return" /></td>
		<td>
		<xsl:for-each select="arguments">
			<xsl:for-each select="parameter">
				<xsl:value-of select="current()" />:
				<xsl:value-of select="@type" />
				<xsl:if test="following-sibling::parameter">,&#160;</xsl:if>
			</xsl:for-each>
		</xsl:for-each>				
		</td>
	</tr>
	</xsl:for-each>
	</table>
</body>
</html>
</xsl:template>
</xsl:stylesheet>