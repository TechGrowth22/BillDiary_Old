<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
	<xsl:template match="employees">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
			<fo:layout-master-set>
				<fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
					<fo:region-body/>
				</fo:simple-page-master>
			</fo:layout-master-set>
			<fo:page-sequence master-reference="simpleA4">
				<fo:flow flow-name="xsl-region-body">
					<fo:block>
					<fo:external-graphic src="url('file:.//src/main/resources/images/pay-bills logo.jpg')" content-height="scale-to-fit" height="1.00in"  content-width="2.00in" padding-left="150pt"/>
					</fo:block>
					<fo:block font-size="16pt" font-weight="bold" space-after="5mm" >Company Name: <xsl:value-of select="companyname"/>
					
					</fo:block>
					
					<fo:block>
					<fo:inline font-size="12pt"  >Name:<fo:inline font-weight="bold" ><xsl:value-of select="NameOfClient"/></fo:inline>
					</fo:inline>
					<fo:inline font-size="12pt"  padding-left="200pt"  text-align="right" > Date:<fo:inline font-weight="bold"> <xsl:value-of select="date"/> </fo:inline>
					</fo:inline>
					</fo:block>
					
					<fo:block font-size="12pt" space-after="5mm" margin-top="0.8cm"> Address:<fo:inline font-weight="bold"> <xsl:value-of select="Address"/></fo:inline>
					</fo:block>
					<fo:block font-size="12pt" space-after="5mm">Mobile no:<fo:inline font-weight="bold"> <xsl:value-of select="phone"/></fo:inline>
					</fo:block>
					<fo:block>
					<fo:inline font-size="12pt" font-weight="bold"  >Products Purchased:-
					</fo:inline>
					<fo:inline font-size="12pt" font-weight="bold" padding-left="200pt" text-align="right" >InvoiceNo:
					</fo:inline>
					</fo:block>
					
					<fo:block font-size="10pt">
						<fo:table table-layout="fixed" width="100%" border-collapse="collapse" margin-top="0.6cm">    
							<fo:table-column border-width="2px" border-style="solid" column-width="4cm" margin-top="0.6cm" >Id</fo:table-column>
							<fo:table-column border-width="2px" border-style="solid" column-width="4cm">Name</fo:table-column> 
							<fo:table-column border-width="2px" border-style="solid" column-width="3cm">Quantity</fo:table-column>	
							<fo:table-column border-width="2px" border-style="solid" column-width="3cm">Amount Per Quantity</fo:table-column>
							<fo:table-column border-width="2px" border-style="solid" column-width="3cm">Total</fo:table-column>
							<fo:table-header>
							<fo:table-cell><fo:block>Id</fo:block></fo:table-cell>
							<fo:table-cell><fo:block>Name</fo:block></fo:table-cell>
							<fo:table-cell><fo:block>Quantity</fo:block></fo:table-cell>
							<fo:table-cell><fo:block>Amount per Quantity</fo:block></fo:table-cell>
							<fo:table-cell><fo:block>Total</fo:block></fo:table-cell>
							</fo:table-header>
							<fo:table-body>
								<xsl:apply-templates select="employee"/>
							</fo:table-body>
							
						</fo:table>
					</fo:block>
					<fo:block font-size="12pt"  space-after="5mm" padding="8pt" margin-left="305pt">Total Amount:<fo:inline font-weight="bold"><xsl:value-of select="total"/>Rs</fo:inline>
					</fo:block>
					<fo:block font-size="12pt"  space-after="5mm" padding="8pt" margin-left="305pt">Discount:<fo:inline font-weight="bold"><xsl:value-of select="discount"/>%</fo:inline>
					</fo:block>
					<fo:block font-size="12pt"  space-after="5mm" padding="8pt"  margin-left="305pt">Total Amount to be paid:<fo:inline font-weight="bold"><xsl:value-of select="Totalafterdiscount"/>Rs</fo:inline>
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>
	<xsl:template match="employee">
		<fo:table-row border-width="2px" border-style="solid" padding="10pt">   
			
			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="id"/>
				</fo:block>
			</fo:table-cell>

			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="name"/>
				</fo:block>
			</fo:table-cell>   
			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="Quantity"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="amtperquantity"/>
				</fo:block>
			</fo:table-cell>
			<fo:table-cell>
				<fo:block>
					<xsl:value-of select="total"/>
				</fo:block>
			</fo:table-cell>
		</fo:table-row>
		
					
	</xsl:template>
</xsl:stylesheet>