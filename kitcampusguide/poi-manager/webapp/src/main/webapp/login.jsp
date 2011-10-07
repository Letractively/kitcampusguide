<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
	<soap:Body>
		<ns2:ExecuteRequest
			xmlns:ns2="http://cm.tm.kit.edu/kitcampusguide/poiservice/">
			<ns2:SelectRequestComplexType>
				<findByNamesLike>
				<name>%</name></findByNamesLike>
			</ns2:SelectRequestComplexType>
		</ns2:ExecuteRequest>
	</soap:Body>
</soap:Envelope>