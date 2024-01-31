package org.example.forum.service.Discussions;

import org.example.forum.service.Discussions.persistence.DiscussionCommentEntity;
import org.example.forum.service.Discussions.persistence.DiscussionCommentRepository;
import org.example.forum.service.Discussions.persistence.DiscussionEntity;
import org.example.forum.service.Users.UserService;
import org.example.forum.service.Users.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiscussionCommentService {

    private final DiscussionCommentRepository commentRepository;

    private UserService userService;
    private DiscussionService discussionService;

    @Autowired
    public DiscussionCommentService(DiscussionCommentRepository commentRepository,
                                    UserService userService,
                                    DiscussionService discussionService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.discussionService = discussionService;
    }

    public List<DiscussionCommentEntity> getCommentsByDiscussionId(Long discussionId) {
        return commentRepository.findByDiscussionIdOrderByCommentDateAsc(discussionId);
    }

    public void deleteByDiscussionId(Long discussionId) {
        commentRepository.deleteByDiscussionId(discussionId);
    }
    public void addReplyToDiscussion(Long userId, Long discussionId, String reply) {
        if (userId == null) {
            return;
        }

        UserEntity user = userService.findById(userId);
        DiscussionEntity discussion = discussionService.getDiscussionById(discussionId);
        DiscussionCommentEntity replyEntity = new DiscussionCommentEntity();
        replyEntity.setCommentText(reply);
        replyEntity.setCommentDate(new Date());
        replyEntity.setUser(user);
        replyEntity.setDiscussion(discussion);

        commentRepository.save(replyEntity);
    }
}
