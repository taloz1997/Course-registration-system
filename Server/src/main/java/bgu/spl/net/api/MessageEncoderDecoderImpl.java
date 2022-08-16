package bgu.spl.net.api;

import bgu.spl.net.impl.BGRSServer.command.BGRScommands.*;
import bgu.spl.net.impl.BGRSServer.command.Command;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageEncoderDecoderImpl implements MessageEncoderDecoder<Command> {
    private byte[] bytes = new byte[1 << 10];
    private int len = 0;
    private Short opcode = -1;
    private String userName = null;
    private String password = null;
    private short courseNum = -1;
    private int zeroCounter = 0;


    @Override
    public Command decodeNextByte(byte nextByte) {
        if (opcode == -1) {
            bytes[len++] = nextByte;
            if (len == 2) {
                opcode = bytesToShort(bytes);
                len = 0;
                if (opcode != 4 && opcode != 11 )
                    return null;
            }
        }
        if (opcode == 1 || opcode == 2 || opcode == 3 || opcode == 8) {
            if (nextByte != '\0') {
                pushByte(nextByte);
                return null;
            } else
                zeroCounter++;
            if (zeroCounter == 1) {
                userName = popString();
                if(opcode != 8)
                    return null;
            } else if (zeroCounter == 2) {
                password = popString();

            }
        } else if (opcode == 5 || opcode == 6 || opcode == 7 || opcode == 9 || opcode == 10) {
            if (courseNum == -1) {
                bytes[len++] = nextByte;
                if (len == 2) {
                    courseNum = bytesToShort(bytes);
                } else
                    return null;
            }
        }

            switch (opcode) {

                case 1:
                    return new ADMINREGcommand(userName, password);
                case 2:
                    return new STUDENTREGcommand(userName, password);
                case 3:
                    return new LOGINcommand(userName, password);
                case 4:
                    return new LOGOUTcommand();
                case 5:
                    return new COURSEREGcommand(courseNum);
                case 6:
                    return new KDAMCHECKcommand(courseNum);
                case 7:
                    return new COURSESTATcommand(courseNum);
                case 8:
                    return new STUDENTSTATcommand(userName);
                case 9:
                    return new ISREGISTEREDcommand(courseNum);
                case 10:
                    return new UNREGISTERcommand(courseNum);
                case 11:
                    return new MYCOURSEScommand();
            }
            return null;


        }


        @Override
        public byte[] encode (Command message){
            opcode = message.getOpcode();
            byte[] currOpcode = shortToBytes(opcode);
            String optional = message.getOptionalByString();
            byte[] output;
            if (message instanceof ERRcommand) {
                byte[] ERRopcode = shortToBytes((short) 13);
                output = merge(ERRopcode, currOpcode);
            } else {  //ACK command
                byte[] ACKopcode = shortToBytes((short) 12);
                byte[] optionalArr = new byte[0];
                if (optional != null)
                    optionalArr = optional.getBytes(StandardCharsets.UTF_8);
                output = merge(ACKopcode, currOpcode);
                output = merge(output, optionalArr);
            }
            clear();
            return output;
        }

        private byte[] merge ( byte[] a1, byte[] a2){
            int lengthMerged = a1.length + a2.length;
            byte[] output = new byte[lengthMerged];
            for (int i = 0; i < a1.length; i++) {
                output[i] = a1[i];
            }
            for (int i = a1.length; i < lengthMerged; i++) {
                output[i] = a2[i - a1.length];
            }
            return output;
        }

        private short bytesToShort ( byte[] byteArr){
            short result = (short) ((byteArr[0] & 0xff) << 8);
            result += (short) (byteArr[1] & 0xff);
            return result;
        }

        private String popString () {
            String result = new String(bytes, 0, len, StandardCharsets.UTF_8);
            len = 0;
            return result;
        }

        private void pushByte ( byte nextByte){
            if (len >= bytes.length) {
                bytes = Arrays.copyOf(bytes, len * 2);
            }
            bytes[len++] = nextByte;
        }

        private byte[] shortToBytes ( short num){
            byte[] bytesArr = new byte[2];
            bytesArr[0] = (byte) ((num >> 8) & 0xFF);
            bytesArr[1] = (byte) (num & 0xFF);
            return bytesArr;
        }

        private void clear () {
            opcode = -1;
            courseNum = -1;
            len = 0;
            zeroCounter = 0;
            bytes = new byte[1 << 10];
        }
    }

