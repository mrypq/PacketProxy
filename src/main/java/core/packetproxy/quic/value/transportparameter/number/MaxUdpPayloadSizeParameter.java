package packetproxy.quic.value.transportparameter.number;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import packetproxy.quic.value.VariableLengthInteger;
import packetproxy.quic.value.transportparameter.TransportParameter;

import java.nio.ByteBuffer;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Value
public class MaxUdpPayloadSizeParameter extends TransportParameter {
    static public final long ID = 0x3;
    long value;

    public MaxUdpPayloadSizeParameter(ByteBuffer buffer) {
        super(buffer);
        value = VariableLengthInteger.parse(super.parameterValue).getValue();
    }

    public MaxUdpPayloadSizeParameter(long value) {
        super(ID, VariableLengthInteger.of(value).getBytes().length, VariableLengthInteger.of(value).getBytes());
        this.value = value;
    }

}
