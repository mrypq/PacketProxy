/*
 * Copyright 2022 DeNA Co., Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package packetproxy.quic.value.frame;

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.Value;
import packetproxy.quic.value.SimpleBytes;
import packetproxy.quic.value.Token;
import packetproxy.quic.value.VariableLengthInteger;

import java.nio.ByteBuffer;
import java.util.List;

/*
https://www.rfc-editor.org/rfc/rfc9000.html#section-19.7

NEW_TOKEN Frame {
  Type (i) = 0x07,
  Token Length (i),
  Token (..),
}
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class NewTokenFrame extends Frame {

    static public final byte TYPE = 0x7;

    static public List<Byte> supportedTypes() {
        return ImmutableList.of(TYPE);
    }

    Token token;

    static public NewTokenFrame parse(byte[] bytes) {
        return NewTokenFrame.parse(ByteBuffer.wrap(bytes));
    }

    static public NewTokenFrame parse(ByteBuffer buffer) {
        byte type = buffer.get();
        assert(type == TYPE);
        long tokenLength = VariableLengthInteger.parse(buffer).getValue();
        if (tokenLength == 0) {
            System.err.println("NewTokenFrame: error: FRAME_ENCODING_ERROR");
        }
        Token token = Token.of(SimpleBytes.parse(buffer, tokenLength).getBytes());

        return new NewTokenFrame(token);
    }

    @Override
    public byte[] getBytes() {
        return new byte[]{ TYPE };
    }

    @Override
    public boolean isAckEliciting() {
        return true;
    }

}
