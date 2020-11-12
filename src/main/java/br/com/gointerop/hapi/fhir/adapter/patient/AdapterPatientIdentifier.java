package br.com.gointerop.hapi.fhir.adapter.patient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hl7.fhir.helper.HelperCodeableConcept;
import org.hl7.fhir.r4.model.Identifier;
import org.hl7.fhir.r4.model.Identifier.IdentifierUse;

import br.com.gointerop.hapi.fhir.adapter.Adapter;
import br.com.gointerop.hapi.fhir.adapter.IAdapter;
import br.com.gointerop.hapi.fhir.mapper.MapperPatient;

public final class AdapterPatientIdentifier extends Adapter<List<Identifier>> {
	private static IAdapter<List<Identifier>> instance;
	
	public static IAdapter<List<Identifier>> getInstance() {
		if (AdapterPatientIdentifier.instance == null) {
			AdapterPatientIdentifier.instance = new AdapterPatientIdentifier();
		}

		return AdapterPatientIdentifier.instance;
	}

	@Override
	public List<Identifier> mapRow(ResultSet rs, int rownumber) throws SQLException {
		List<Identifier> retVal = new ArrayList<Identifier>();

		int indexTax = -1;
		String valueTax = null;
		
		try {
			 indexTax = rs.findColumn(MapperPatient.identifier);
		} catch (SQLException e) {}
		
		if (indexTax > -1) valueTax = rs.getString(indexTax);
		
		if(valueTax != null) {
			Identifier identifier = new Identifier();
			identifier.setUse(IdentifierUse.OFFICIAL);
			identifier.setType(HelperCodeableConcept.create("http://www.saude.gov.br/fhir/r4/CodeSystem/BRTipoIdentificador", "TAX"));
			identifier.setValue(valueTax);
			retVal.add(identifier);
		}
		
		return retVal;
	}
}
