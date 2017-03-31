package com.cse4232.gossip.helper.asn;

import net.ddp2p.ASN1.*;
import net.ddp2p.common.config.DD;

// PeersAnswer ::= [1] EXPLICIT SEQUENCE OF Peer

public class PeersAnswer extends ASNObj {

    private final static byte TAG_CS1 = Encoder.buildASN1byteType(Encoder.TAG_SEQUENCE, Encoder.PC_CONSTRUCTED, (byte) 1);
    public static final byte TAG = 33;

    private Peer[] peers;

    public PeersAnswer() {}

    public PeersAnswer(Peer[] peers) {
        this.peers = peers;
    }

    public Peer[] getPeers() { return peers; }

    @Override
    public Encoder getEncoder() {
        return new Encoder().initSequence().addToSequence(Encoder.getEncoder(peers)).setASN1Type(TAG_CS1);
    }

    @Override
    public Object decode(Decoder decoder) throws ASN1DecoderFail {
        Decoder d = decoder.getContent();
        peers = d.getFirstObject(true).getSequenceOf(Peer.TAG, new Peer[0], new Peer());
        if (d.getTypeByte() != 0) throw new ASN1DecoderFail("Wrong Decoder");
        return this;
    }
}