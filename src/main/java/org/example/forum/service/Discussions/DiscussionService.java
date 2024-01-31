package org.example.forum.service.Discussions;

import org.example.forum.service.Discussions.persistence.DiscussionCommentRepository;
import org.example.forum.service.Discussions.persistence.DiscussionEntity;
import org.example.forum.service.Discussions.persistence.DiscussionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiscussionService {

    private final DiscussionRepository discussionRepository;
    private final DiscussionCommentRepository commentRepository;

    @Autowired
    public DiscussionService(DiscussionRepository discussionRepository,
                             DiscussionCommentRepository commentRepository) {
        this.discussionRepository = discussionRepository;
        this.commentRepository = commentRepository;
    }

    public List<DiscussionEntity> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    public DiscussionEntity getDiscussionById(Long id) {
        Optional<DiscussionEntity> optionalDiscussion = discussionRepository.findById(id);
        return optionalDiscussion.orElse(null);
    }

    public void deleteDiscussionById(Long id) {
        commentRepository.deleteByDiscussionId(id);
        discussionRepository.deleteById(id);
    }

    public DiscussionEntity createDiscussion(String title, String context) {
        DiscussionEntity discussion = new DiscussionEntity();
        discussion.setTitle(title);
        discussion.setContext(context);

        return discussionRepository.save(discussion);
    }
}

