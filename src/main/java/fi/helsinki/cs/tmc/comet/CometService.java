package fi.helsinki.cs.tmc.comet;

import fi.helsinki.cs.tmc.comet.acl.DenyPublishIfNotBackend;
import fi.helsinki.cs.tmc.comet.acl.RequireWellKnownGlobalChannelName;
import fi.helsinki.cs.tmc.comet.acl.RequireUserOwnsChannel;
import org.cometd.annotation.Configure;
import org.cometd.annotation.Service;
import org.cometd.bayeux.server.ConfigurableServerChannel;
import org.cometd.server.authorizer.GrantAuthorizer;

/**
 * Configures frontent channels.
 */
@Service
public class CometService {
    //TODO: /broadcast/tmc/global/admin-msg
    //TODO: /broadcast/tmc/global/course-updated
    //TODO: /broadcast/tmc/user/<username>/review-available (req authorization)
    //TODO: document the above in a readme
    //TODO: tests
    
    @Configure("/broadcast/tmc/global/**")
    public void confGlobalChannels(ConfigurableServerChannel channel) {
        channel.setPersistent(true);
        channel.addAuthorizer(new RequireWellKnownGlobalChannelName());
        channel.addAuthorizer(new DenyPublishIfNotBackend());
        channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
    }
    
    @Configure("/broadcast/tmc/user/**")
    public void confUserChannels(ConfigurableServerChannel channel) {
        channel.setPersistent(true);
        channel.addAuthorizer(new RequireUserOwnsChannel());
        channel.addAuthorizer(new DenyPublishIfNotBackend());
        channel.addAuthorizer(GrantAuthorizer.GRANT_ALL);
    }
}
