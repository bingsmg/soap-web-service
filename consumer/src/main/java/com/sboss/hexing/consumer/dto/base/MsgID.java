package com.sboss.hexing.consumer.dto.base;

import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

/**
 * @author weibb
 */
@Data
public class MsgID {
    @XmlAttribute
    private String dateTime;
    @XmlAttribute
    private String uniqueNumber;
}
