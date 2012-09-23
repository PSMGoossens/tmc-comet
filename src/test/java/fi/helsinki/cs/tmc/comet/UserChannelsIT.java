package fi.helsinki.cs.tmc.comet;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.cometd.bayeux.Channel;
import org.cometd.bayeux.ChannelId;
import org.cometd.bayeux.Message;
import org.cometd.bayeux.client.ClientSessionChannel;
import org.cometd.client.BayeuxClient;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserChannelsIT extends AbstractTmcCometTest {
    @Test
    public void testReviewAvailable() throws Exception {
        testSimplePubSub("/broadcast/tmc/user/" + TestUtils.TEST_USER + "/review-available");
    }
    
    @Test
    public void testNotAbleToSubscribeToChannelsOwnedByOthers() throws Exception {
        testCannotSub("/broadcast/tmc/user/a_different_user/review-available");
    }
     
    /*
        final String channel = "/broadcast/tmc/user/a_different_user/review-available";
        BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
        Message msg;
        
        BayeuxClient frontClient = TestUtils.connectAsFrontend();
        
        TestUtils.addEnqueueingListener(queue, frontClient.getChannel(Channel.META_SUBSCRIBE));
        
        frontClient.getChannel(channel).subscribe(emptyMessageListener);
        msg = notNull("No message received", queue.poll(3, TimeUnit.SECONDS));
        assertEquals(new ChannelId(Channel.META_SUBSCRIBE), msg.getChannelId());
        assertFalse(msg.isSuccessful());
        assertEquals("403:you don't have access to this channel:create_denied", msg.get(Message.ERROR_FIELD));
    }
    
    @Test
    public void testNotAbleToSubscribeToChannelsOwnedByOthers() throws Exception {
        final String channel = "/broadcast/tmc/user/a_different_user/review-available";
        BlockingQueue<Message> queue = new LinkedBlockingQueue<Message>();
        Message msg;
        
        BayeuxClient frontClient = TestUtils.connectAsFrontend();
        BayeuxClient backClient = TestUtils.connectAsBackend();
        
        TestUtils.addEnqueueingListener(queue, backClient.getChannel(Channel.META_SUBSCRIBE));
        backClient.getChannel(channel).subscribe(emptyMessageListener); // Ensure the channel is created
        msg = notNull("No message received", queue.poll(3, TimeUnit.SECONDS));
        assertEquals(new ChannelId(Channel.META_SUBSCRIBE), msg.getChannelId());
        assertTrue(msg.isSuccessful());
        
        TestUtils.addEnqueueingListener(queue, frontClient.getChannel(Channel.META_SUBSCRIBE));
        frontClient.getChannel(channel).subscribe(emptyMessageListener);
        msg = notNull("No message received", queue.poll(3, TimeUnit.SECONDS));
        assertEquals(new ChannelId(Channel.META_SUBSCRIBE), msg.getChannelId());
        assertFalse(msg.isSuccessful());
        assertEquals("403:you don't have access to this channel:subscribe_denied", msg.get(Message.ERROR_FIELD));
    }
    * */
}
