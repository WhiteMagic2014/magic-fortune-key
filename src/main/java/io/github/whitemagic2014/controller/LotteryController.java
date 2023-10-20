package io.github.whitemagic2014.controller;


import io.github.whitemagic2014.util.DateFormatUtil;
import io.github.whitemagic2014.util.MagicMd5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class LotteryController {


    @GetMapping("")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView("index");
        return mv;
    }


    @RequestMapping("/trans")
    @ResponseBody
    public String lottery(String cafumima) {
        return "大乐透:\n" + dlt(cafumima) +
                "\n\n双色球:\n" + ssq(cafumima) +
                "    排列3: " + pl3(cafumima);
    }


    private String dlt(String cafumima) {
        String dstr = DateFormatUtil.sdfv2.format(new Date());
        // 初始化球
        List<Integer> front = new ArrayList<>();
        for (int i = 1; i <= 35; i++) {
            front.add(i);
        }
        List<Integer> back = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            back.add(i);
        }
        int[] tmp = new int[7];
        String str = MagicMd5.getMd5String(cafumima + dstr);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 对每个字符的ASCII值进行一些计算，然后将结果存储在result数组中
            tmp[i % 7] += c;
        }
        StringBuilder sb = new StringBuilder("前: ");
        for (int i = 0; i < 7; i++) {
            int r;
            if (i <= 4) {
                r = tmp[i] % (front.size());
                sb.append(front.get(r)).append(" ");
                front.remove(r);
                if (i == 4) {
                    sb.append("\n后: ");
                }
            } else {
                r = tmp[i] % (back.size());
                sb.append(back.get(r)).append(" ");
                back.remove(r);
            }
        }
        return sb.toString();
    }

    private String ssq(String cafumima) {
        String dstr = DateFormatUtil.sdfv2.format(new Date());
        // 初始化球
        List<Integer> front = new ArrayList<>();
        for (int i = 1; i <= 33; i++) {
            front.add(i);
        }
        List<Integer> back = new ArrayList<>();
        for (int i = 1; i <= 16; i++) {
            back.add(i);
        }
        int[] tmp = new int[7];
        String str = MagicMd5.getMd5String(cafumima + dstr);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 对每个字符的ASCII值进行一些计算，然后将结果存储在result数组中
            tmp[i % 7] += c;
        }
        StringBuilder sb = new StringBuilder("红: ");
        for (int i = 0; i < 7; i++) {
            int r;
            if (i <= 5) {
                r = tmp[i] % (front.size());
                sb.append(front.get(r)).append(" ");
                front.remove(r);
                if (i == 5) {
                    sb.append("\n蓝: ");
                }
            } else {
                r = tmp[i] % (back.size());
                sb.append(back.get(r)).append(" ");
                back.remove(r);
            }
        }
        return sb.toString();
    }


    private String pl3(String cafumima) {
        String dstr = DateFormatUtil.sdfv2.format(new Date());

        int[] tmp = new int[3];
        String str = MagicMd5.getMd5String(cafumima + dstr);
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 对每个字符的ASCII值进行一些计算，然后将结果存储在result数组中
            tmp[i % 3] += c;
        }

        StringBuilder sb = new StringBuilder();
        for (int x : tmp) {
            sb.append(x % 10).append(" ");
        }
        return sb.toString();
    }


}
