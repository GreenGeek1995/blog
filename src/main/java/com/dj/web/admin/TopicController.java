package com.dj.web.admin;

import com.dj.po.Topic;
import com.dj.po.User;
import com.dj.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/topics")
    public String topics(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                          Model model,HttpSession session){
        model.addAttribute("page",topicService.listTopic(pageable,session));
        return "admin/topics";
    }

    @GetMapping("/topics/input")
    public String input(Model model){
        model.addAttribute("topic",new Topic());
        return "admin/topics-input";
    }

    @PostMapping("/topics")
    public String post(@Valid Topic topic, BindingResult result, RedirectAttributes attributes, HttpSession session){
        User user= (User) session.getAttribute("user");
        Topic topic1 = topicService.getTopicByNameAndUser(topic.getName(),user.getId());
        if(topic1!=null){
            result.rejectValue("name","nameError","不能添加重复的主题");
        }
        if(result.hasErrors()){
            return "admin/topics-input";
        }
        topic.setUser((User)session.getAttribute("user"));
        Topic t = topicService.saveTopic(topic);
        if(t == null){
            //提示操作失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/topics";
    }

    @GetMapping("/topics/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("topic",topicService.getTopicById(id));
        return "admin/topics-input";
    }

    @PostMapping("/topics/{id}")
    public String editPost(@Valid Topic topic,BindingResult result,@PathVariable Long id,RedirectAttributes attributes,HttpSession session){
        User user= (User) session.getAttribute("user");
        Topic topic1 = topicService.getTopicByNameAndUser(topic.getName(),user.getId());
        if(topic1!=null){
            result.rejectValue("name","nameError","不能重复添加主题");
        }
        if(result.hasErrors()){
            return "admin/topics-input";
        }
        topic.setUser((User)session.getAttribute("user"));
        Topic t = topicService.updateTopic(id,topic);
        if(t == null){
            //提示操作失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/topics";
    }

    @GetMapping("/topics/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        topicService.deleteTopic(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/topics";
    }

}
