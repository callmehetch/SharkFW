package net.sharkfw.asip.engine;

import net.sharkfw.asip.ASIPBaseTest;
import net.sharkfw.asip.ASIPInterest;
import net.sharkfw.asip.ASIPSpace;
import net.sharkfw.asip.TestKP;
import net.sharkfw.knowledgeBase.PeerSTSet;
import net.sharkfw.knowledgeBase.PeerSemanticTag;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;
import net.sharkfw.peer.J2SESharkEngine;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by j4rvis on 12/15/16.
 */
public class ASIPSessionTest extends ASIPBaseTest{

    PeerSemanticTag sender;
    PeerSemanticTag receiverPeer;

    PeerSTSet peers;

    String rawInput = "Hello ASIP.";
    TestKP testKPA;
    TestKP testKPB;
    J2SESharkEngine engineA;
    J2SESharkEngine engineB;
    PeerSemanticTag peerA;
    PeerSemanticTag peerB;


    @Before
    public void setUp() throws Exception {

        super.setUp();

        peers = InMemoSharkKB.createInMemoPeerSTSet();

        sender = peers.createPeerSemanticTag("SENDER", "www.si1.de", "tcp://addr1.de");
        receiverPeer = peers.createPeerSemanticTag("RECEIEVER", "www.si2.de", "tcp://addr2.de");

        engineA = new J2SESharkEngine();
        testKPA = new TestKP(engineA, "Port A");
        testKPA.setText("Pong");

        engineB = new J2SESharkEngine();
        testKPB = new TestKP(engineB, "Port B");
        testKPB.setText("Ping");

        peerA = InMemoSharkKB.createInMemoPeerSemanticTag("Peer A", "www.peer-a.de", "tcp://localhost:7070");
        peerB = InMemoSharkKB.createInMemoPeerSemanticTag("Peer B", "www.peer-b.de", "tcp://localhost:7071");

        engineA.setEngineOwnerPeer(peerA);
        engineB.setEngineOwnerPeer(peerB);

        engineB.startTCP(7071);
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        engineB.stopTCP();
    }


    @Test
    public void testSerializationASIPSession() throws Exception {
        ASIPInterest space = InMemoSharkKB.createInMemoASIPInterest(topics, types, peerA, peers, peers, null, null, ASIPSpace.DIRECTION_INOUT);

        ASIPOutMessage outMessage = engineA.createASIPOutMessage(peerB.getAddresses(), peerA, peerB, null, null, null, null, 10);

        outMessage.expose(space);

        Thread.sleep(10000);
    }
}
