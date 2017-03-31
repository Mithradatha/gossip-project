package com.cse4232.gossip.helper.asn;

import net.ddp2p.ASN1.*;

//Peer ::= [APPLICATION 2] IMPLICIT SEQUENCE {name UTF8String, port INTEGER, ip PrintableString}

public class Peer extends ASNObj {

    private static final byte TAG_AP2 = Encoder.buildASN1byteType(Encoder.CLASS_APPLICATION, Encoder.PC_CONSTRUCTED, (byte) 2);
    public static final byte TAG = 98;

    private String name;
    private int port;
    private String ip;

    public Peer() {}

    public Peer(String name, int port, String ip) {
        this.name = name;
        this.port = port;
        this.ip = ip;
    }

    public String getName() { return name; }

    public int getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public Encoder getEncoder() {
        Encoder e = new Encoder().initSequence();
        e.addToSequence(new Encoder(name, Encoder.TAG_UTF8String));
        e.addToSequence(new Encoder(port));
        e.addToSequence(new Encoder(ip, Encoder.TAG_PrintableString));
        e.setASN1Type(TAG_AP2);
        return e;
    }

    @Override
    public Object decode(Decoder decoder) throws ASN1DecoderFail {
        Decoder d = decoder.getContent();
        name = d.getFirstObject(true).getString();
        port = d.getFirstObject(true).getInteger().intValue();
        ip = d.getFirstObject(true).getString();
        if (d.getTypeByte() != 0) throw new ASN1DecoderFail("Wrong Decoder");
        return this;
    }

    @Override
    public ASNObj instance() throws CloneNotSupportedException {
        return new Peer();
    }
}