package rais.friendmanagement.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rais.friendmanagement.dao.PersonRepo;
import rais.friendmanagement.validation.EmailRegexValidator;

/**
 *
 * @author Muhammad Rais Rahim <rais.gowa@gmail.com>
 */
@Service
@Slf4j
public class UpdatesServiceImpl implements UpdatesService {

    @Autowired
    private PersonRepo personRepo;
    @Autowired
    private FriendConnectionService friendConnectionService;
    @Autowired
    private BlockingService blockingService;
    @Autowired
    private SubscriptionService subscriptionService;
    @Autowired
    private EmailRegexValidator emailRegexValidator;

    @Override
    public List<String> retrieveAllRecipients(String sender, String text) {
        log.debug("[retrieveAllRecipients]-sender={}, text={}", sender, text);
        Set<String> set = new HashSet();
        // add all connected friends
        List<String> friends = friendConnectionService.retrieveFriends(sender);
        log.debug("[retrieveAllRecipients]-sender={}, friends={}", sender, friends);
        set.addAll(friends);
        // add all subscribers
        List<String> subscribers = subscriptionService.retrieveSubscribers(sender);
        log.debug("[retrieveAllRecipients]-sender={}, subscribers={}", sender, subscribers);
        set.addAll(subscribers);
        // add all mentions
        Set<String> mentions = findMentionsEmail(text);
        log.debug("[retrieveAllRecipients]-sender={}, mentions={}", sender, mentions);
        mentions.stream()
                .filter(personRepo::existsByEmail)
                .forEach(set::add);
        // exclude all blocks
        List<String> blocks = blockingService.getAllEmailsWhichAreBlockingTheEmailTarget(sender);
        log.debug("[retrieveAllRecipients]-sender={}, blocks={}", sender, blocks);
        set.removeAll(blocks);

        log.debug("[retrieveAllRecipients]-sender={}, recipients={}", sender, set);
        return new ArrayList(set);
    }

    private Set<String> findMentionsEmail(String text) {
        Matcher m = emailRegexValidator.getEmailPattern().matcher(text);
        Set<String> set = new LinkedHashSet<>();
        while (m.find()) {
            set.add(m.group());
        }
        return set;
    }

}
