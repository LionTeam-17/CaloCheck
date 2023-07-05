package calocheck.boundedContext.notification.controller;

import calocheck.base.rq.Rq;
import calocheck.boundedContext.notification.entity.Notification;
import calocheck.boundedContext.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final Rq rq;
    private final NotificationService notificationService;

    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    public String showList(Model model) {

        List<Notification> notifications = notificationService.findByPost_Member(rq.getMember());

        // 알림 페이지 방문 시에 ReadDate가 null인 것들 현재시각으로 갱신
        notificationService.updateReadDate(notifications);

        notificationService.deleteNotification(notifications);

        model.addAttribute("notifications", notifications);

        return "usr/notification/list";
    }
}
