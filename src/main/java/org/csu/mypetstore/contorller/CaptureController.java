package org.csu.mypetstore.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.imageio.ImageIO;

@Controller
public class CaptureController {

    public void capture(Model model){
       /* CaptchaUtil util = CaptchaUtil.Instance();
        // 将验证码输入到session中，用来验证
        String code = util.getString();
        model.addAttribute("code", code);
        // 输出打web页面
        ImageIO.write(util.getImage(), "jpg", response.getOutputStream());*/
    }
}
