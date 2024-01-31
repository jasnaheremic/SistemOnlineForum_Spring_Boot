package org.example.forum.controller;

import jakarta.servlet.http.HttpSession;
import org.example.forum.service.Discussions.DiscussionCommentService;
import org.example.forum.service.Discussions.DiscussionService;
import org.example.forum.service.Discussions.persistence.DiscussionCommentEntity;
import org.example.forum.service.Discussions.persistence.DiscussionEntity;
import org.example.forum.service.Users.UserService;
import org.example.forum.service.Users.persistence.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class DiscussionController {
    private final DiscussionService discussionService;
    private final DiscussionCommentService discussionCommentService;

    private final UserService userService;

    @Autowired
    public DiscussionController(DiscussionService discussionService,
                                DiscussionCommentService discussionCommentService,
                                UserService userService) {
        this.discussionService = discussionService;
        this.discussionCommentService = discussionCommentService;
        this.userService = userService;
    }


    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        dashboardLoadUser(model, session);
        List<DiscussionEntity> discussions = discussionService.getAllDiscussions();
        model.addAttribute("discussions", discussions);
        return "dashboard";
    }

    private void dashboardLoadUser(Model model, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");

        if (userId != null) {
            UserEntity user = this.userService.findById(userId);
            model.addAttribute("message", "Dobrodosli: " + user.getUsername());

            if ("admin".equals(user.getRole())) {
                model.addAttribute("admin", true);
            } else {
                model.addAttribute("admin", false);
            }
        } else {
            model.addAttribute("admin", false);
        }
    }

    @GetMapping("/discussion/{id}")
    public String getDiscussionById(@PathVariable Long id, Model model, HttpSession session) {
        DiscussionEntity discussion = discussionService.getDiscussionById(id);
        List<DiscussionCommentEntity> discussionComments = discussionCommentService.getCommentsByDiscussionId(id);

        if (discussionComments != null) {
            Long userId = (Long) session.getAttribute("userId");
            if (userId != null) {
                UserEntity user = this.userService.findById(userId);
                if ("admin".equals(user.getRole()) || "user".equals(user.getRole())) {
                    model.addAttribute("activateReply", true);
                }
            } else {
                model.addAttribute("activateReply", false);
            }

            model.addAttribute("discussionNotFound", false);
            model.addAttribute("discussion", discussion);
            model.addAttribute("discussionComments", discussionComments);
        } else {
            model.addAttribute("discussionNotFound", true);
        }

        return "discussion";
    }

    @PostMapping("/discussion/{id}/reply")
    public String replyToDiscussion(@PathVariable Long id,
                                    @RequestParam("replyContent") String replyContent,
                                    HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        discussionCommentService.addReplyToDiscussion(userId, id, replyContent);
        return "redirect:/discussion/" + id;
    }

    @PostMapping("/discussion/add")
    public String createDiscussion(String title, String context,
                                   HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/dashboard";
        }

        UserEntity user = userService.findById(userId);
        if (!"admin".equals(user.getRole())) {
            return "redirect:/dashboard";
        }

        discussionService.createDiscussion(title, context);
        return "redirect:/dashboard";
    }

    @GetMapping("/discussion/delete/{id}")
    public String deleteDiscussion(@PathVariable Long id, Model model, HttpSession session) {
        dashboardLoadUser(model, session);
        discussionService.deleteDiscussionById(id);
        return "redirect:/dashboard";
    }

}
