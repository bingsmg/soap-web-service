package com.sboss.hexing.consumer.dto.base;

import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

/**
 * @author weibb
 */
@Data
public class AuthCred {
    @XmlAttribute
    private String opName;
    @XmlAttribute
    private String password;
}
