package com.dj.web.admin;

import com.dj.po.Skill;
import com.dj.po.User;
import com.dj.service.SkillService;
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
public class SkillController {
    @Autowired
    private SkillService skillService;

    @GetMapping("/skills")
    public String skills(@PageableDefault(size = 6,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                          Model model,HttpSession session){
        model.addAttribute("page",skillService.listSkill(pageable,session));
        return "admin/skills";
    }

    @GetMapping("/skills/input")
    public String input(Model model){
        model.addAttribute("skill",new Skill());
        return "admin/skills-input";
    }

    @PostMapping("/skills")
    public String post(@Valid Skill skill, BindingResult result, RedirectAttributes attributes, HttpSession session){
        User user= (User) session.getAttribute("user");
        Skill skill1 = skillService.getSkillByNameAndUser(skill.getName(),user.getId());
        if(skill1!=null){
            result.rejectValue("name","nameError","不能添加重复的技术栈");
        }
        if(result.hasErrors()){
            return "admin/skills-input";
        }
        skill.setUser((User)session.getAttribute("user"));
        Skill s = skillService.saveSkill(skill);
        if(s == null){
            //提示操作失败
            attributes.addFlashAttribute("message","新增失败");
        }else{
            attributes.addFlashAttribute("message","新增成功");
        }
        return "redirect:/admin/skills";
    }

    @GetMapping("/skills/{id}/input")
    public String editInput(@PathVariable Long id,Model model){
        model.addAttribute("skill",skillService.getSkillById(id));
        return "admin/skills-input";
    }

    @PostMapping("/skills/{id}")
    public String editPost(@Valid Skill skill,BindingResult result,@PathVariable Long id,RedirectAttributes attributes,HttpSession session){
        User user= (User) session.getAttribute("user");
        Skill skill1 = skillService.getSkillByNameAndUser(skill.getName(),user.getId());
        if(skill1!=null){
            result.rejectValue("name","nameError","不能重复添加技术栈");
        }
        if(result.hasErrors()){
            return "admin/skills-input";
        }
        skill.setUser((User)session.getAttribute("user"));
        Skill s = skillService.updateSkill(id,skill);
        if(s == null){
            //提示操作失败
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/skills";
    }

    @GetMapping("/skills/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        skillService.deleteSkill(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/skills";
    }

}
