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
public class AckDelayExponentParameter extends TransportParameter {
    static public final long ID = 0xa;
    long value;

    public AckDelayExponentParameter(ByteBuffer buffer) {
        super(buffer);
        value = VariableLengthInteger.parse(super.parameterValue).getValue();
    }

    public AckDelayExponentParameter(long value) {
        super(ID, VariableLengthInteger.of(value).getBytes().length, VariableLengthInteger.of(value).getBytes());
        this.value = value;
    }

}
