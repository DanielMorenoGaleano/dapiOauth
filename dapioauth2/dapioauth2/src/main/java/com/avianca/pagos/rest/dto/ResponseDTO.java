package com.avianca.pagos.rest.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonAutoDetect
@JsonSerialize
public class ResponseDTO implements Serializable {

		@JsonProperty
	    private String code;
		
	    @JsonProperty
	    private String error;
	    
	    
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getError() {
			return error;
		}
		public void setError(String error) {
			this.error = error;
		}

}
