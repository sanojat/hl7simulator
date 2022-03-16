package myhl7simulator;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hl7.learn.simulator.model.HL7MessageConfigModel;
import com.hl7.learn.simulator.model.Hl7MessageConfig;
import com.hl7.learn.simulator.model.ParameterConfig;
import com.hl7.learn.simulator.type.DataType;
import com.hl7.learn.simulator.type.NumericParameter;
import com.hl7.learn.simulator.type.ParamValueSelector;

public class DataTest {
	static ObjectMapper mapper = new ObjectMapper();

	public static void main(String[] args) throws JsonProcessingException {

		Hl7MessageConfig m = new Hl7MessageConfig();
		m.setEntityId("Patient-0");
		m.setPid("PID0");
		m.setDataType(DataType.NUMERIC);
		m.setTimeInterval(10);
		m.setParamConfigs(new ArrayList<ParameterConfig>());
		ParameterConfig config = new ParameterConfig();
		config.setParamName(NumericParameter.MDC_ECG_HEART_RATE.name());
		config.setValueSelector(ParamValueSelector.RANDOM);
		config.setValueSeparator("NUMERIC.values.separator");
		m.getParamConfigs().add(config);
		HL7MessageConfigModel mc = new HL7MessageConfigModel();
		mc.setMessageConfigs(new ArrayList<Hl7MessageConfig>());
		mc.getMessageConfigs().add(m);
		ObjectMapper om = new ObjectMapper();
		try {
			System.out.println(om.writeValueAsString(mc));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
}
