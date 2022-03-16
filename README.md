
	"messageConfigs": [
		{
			"entityId": "Patient-0",
			"pid": "PID0",
			"dataType": "NUMERIC",
			"paramConfigs": [
				{
					"paramName": "MDC_ECG_HEART_RATE",
					"valueSelector": "RANDOM",
					"valueSeparator": "NUMERIC.values.separator"
				},
				{
					"paramName": "MDC_ECG_AMPL_ST_I",
					"valueSelector": "RANDOM",
					"valueSeparator": "NUMERIC.values.separator"
				}
			],
			"timeInterval": 2000
		},
		{
			"entityId": "Patient-0",
			"pid": "PID0",
			"dataType": "WAVEFORM",
			"paramConfigs": [
				{
					"paramName": "MDC_ECG_HEART_RATE",
					"valueSelector": "SEQUENTIAL",
					"valueSeparator": "WAVEFORM.values.separator",
               		"sequentialDataBlockLength":1
				}
			],
			"timeInterval": 250
		}
	]
}