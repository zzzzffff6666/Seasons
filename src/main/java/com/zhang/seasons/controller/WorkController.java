package com.zhang.seasons.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhang.seasons.bean.Buy;
import com.zhang.seasons.bean.Style;
import com.zhang.seasons.bean.Work;
import com.zhang.seasons.http.APIMsg;
import com.zhang.seasons.http.Result;
import com.zhang.seasons.service.BuyService;
import com.zhang.seasons.service.StyleService;
import com.zhang.seasons.service.UserService;
import com.zhang.seasons.service.WorkService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Map;

@Slf4j
@RestController
public class WorkController {
    private static final int STYLE_PAGE_AMOUNT = 20;
    private static final int STYLE_RECOMMEND_AMOUNT = 10;
    private static final int BUY_PAGE_AMOUNT = 40;

    @Autowired
    private UserService userService;
    @Autowired
    private StyleService styleService;
    @Autowired
    private WorkService workService;
    @Autowired
    private BuyService buyService;

    // Style 部分

    @PostMapping("/style")
    public Result insertStyle(@RequestParam("name") String name, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (styleService.isManageMax(uid)) return Result.error(APIMsg.MANAGE_MAX_ERROR);
        Style style = new Style();
        style.setManager(uid);
        style.setName(name);
        style.setUpdated(new Timestamp(System.currentTimeMillis()));
        boolean suc = styleService.insertStyle(style);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    @DeleteMapping("/style")
    public Result deleteStyle(@RequestParam("sid") int sid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (uid != styleService.selectStyleManager(sid)) return Result.error(APIMsg.AUTH_ERROR);
        boolean suc = styleService.deleteStyle(sid, uid);
        return suc ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @PutMapping("/style/manager")
    public Result updateStyleManager(@RequestParam("sid") int sid, @RequestParam("manager") int manager,
                                     HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (uid != styleService.selectStyleManager(sid)) return Result.error(APIMsg.AUTH_ERROR);
        boolean suc = styleService.updateStyleManager(sid, manager, uid);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @PutMapping("/style/name")
    public Result updateStyleName(@RequestParam("sid") int sid, @RequestParam("name") String name,
                                  HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (uid != styleService.selectStyleManager(sid)) return Result.error(APIMsg.AUTH_ERROR);
        boolean suc = styleService.updateStyleName(sid, name);
        return suc ? Result.success() : Result.error(APIMsg.UPDATE_ERROR);
    }

    @PutMapping("/style/erase/timely")
    public Result eraseStyleTimelyNum() {
        styleService.eraseStyleTimelyNum();
        return Result.success();
    }

    @GetMapping("/style/{sid}")
    public Result selectStyle(@PathVariable("sid") int sid) {
        return Result.success(styleService.selectStyle(sid));
    }

    @GetMapping({"/style/list/name/{name}", "/style/list/name/{name}/{page}"})
    public Result searchStyleByName(@PathVariable("name") String name,
                                    @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, STYLE_PAGE_AMOUNT);
        PageInfo<Style> list = new PageInfo<>(styleService.searchStyleByName(name));
        return Result.success(list);
    }

    @GetMapping({"/style/list/manager/{manager}", "/style/list/manager/{manager}/{page}"})
    public Result selectStyleByManager(@PathVariable("manager") int manager,
                                       @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, STYLE_PAGE_AMOUNT);
        PageInfo<Style> list = new PageInfo<>(styleService.selectStyleByManager(manager));
        return Result.success(list);
    }

    @GetMapping("/style/work/num")
    public Result selectStyleByWorkNum() {
        return Result.success(styleService.selectStyleByWorkNum(STYLE_RECOMMEND_AMOUNT));
    }

    @GetMapping("/style/weekly/num")
    public Result selectStyleByWeeklyNum() {
        return Result.success(styleService.selectStyleByWeeklyNum(STYLE_RECOMMEND_AMOUNT));
    }

    @GetMapping("/style/daily/num")
    public Result selectStyleByDailyNum() {
        return Result.success(styleService.selectStyleByDailyNum(STYLE_RECOMMEND_AMOUNT));
    }

    @GetMapping({"/style/all", "/style/all/{page}"})
    public Result selectAllStyle(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, STYLE_PAGE_AMOUNT);
        PageInfo<Style> list = new PageInfo<>(styleService.selectAllStyle());
        return Result.success(list);
    }

    // Work 部分



    // Buy 部分

    @Transactional
    @PostMapping("/buy/balance")
    public Result insertBuyWithBalance(@RequestParam Map<String, Object> params, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        int wid = (int) params.get("wid");
        Work work = workService.selectWork(wid);
        float coin = userService.selectUserCoin(uid);
        String way = params.get("way").toString();
        int type = (int) params.get("type");
        if (type == 0 && coin < work.getPrice()) return Result.error(APIMsg.BALANCE_NOT_ENOUGH_ERROR);
        if (type == 1 && coin < work.getPriceBiz()) return Result.error(APIMsg.BALANCE_NOT_ENOUGH_ERROR);
        Buy buy = new Buy();
        buy.setUid(uid);
        buy.setWid(wid);
        buy.setPrice(type == 0 ? work.getPrice() : work.getPriceBiz());
        buy.setType(type);
        buy.setWay(way);
        buy.setCreated(new Timestamp(System.currentTimeMillis()));
        boolean suc = buyService.insertBuy(buy);
        suc &= userService.updateUserCoin(uid, 0 - buy.getPrice());
        suc &= userService.updateUserCoin(work.getUid(), buy.getPrice() * 0.9f);
        return suc ? Result.success() : Result.error(APIMsg.INSERT_ERROR);
    }

    // 微信支付（暂时未实现）
    @PostMapping("/buy/wx_pay")
    public Result insertBuyWithWXPay() {
        return Result.error(APIMsg.INSERT_ERROR);
    }

    @Transactional
    @DeleteMapping("/buy")
    @RequiresPermissions("buy:*")
    public Result deleteBuy(@RequestParam("uid") int uid, @RequestParam("wid") int wid) {
        Buy buy = buyService.selectBuy(uid, wid);
        if (buy.getType() == 0) return Result.error(APIMsg.COMMON_REFUND_ERROR);
        else {
            long now = System.currentTimeMillis();
            if (now - buy.getCreated().getTime() > 7 * 1000 * 3600 * 24) {
                return Result.error(APIMsg.BUSINESS_REFUND_ERROR);
            }
        }
        boolean suc = buyService.deleteBuy(uid, wid);
        suc &= userService.updateUserCoin(uid, buy.getPrice() * 0.9f);
        suc &= userService.updateUserCoin(workService.selectWork(wid).getUid(), 0 - (buy.getPrice() * 0.9f));
        return suc ? Result.success() : Result.error(APIMsg.DELETE_ERROR);
    }

    @GetMapping("/buy/{wid}")
    public Result selectBuy(@PathVariable("wid") int wid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        return Result.success(buyService.selectBuy(uid, wid));
    }

    @GetMapping("/buy-admin/{uid}/{wid}")
    public Result selectBuyAsAdmin(@PathVariable("uid") int uid, @PathVariable("wid") int wid) {
        return Result.success(buyService.selectBuy(uid, wid));
    }

    @GetMapping({"/buy/list/uid", "/buy/list/uid/{page}"})
    public Result selectBuyByUid(@PathVariable(value = "page", required = false) Integer page, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (page == null) page = 1;
        PageHelper.startPage(page, BUY_PAGE_AMOUNT);
        PageInfo<Buy> list = new PageInfo<>(buyService.selectBuyByUid(uid));
        return Result.success(list);
    }

    @GetMapping({"/buy-admin/list/uid/{uid}", "/buy-admin/list/uid/{uid}/{page}"})
    public Result selectBuyByUidAsAdmin(@PathVariable("uid") int uid,
                                        @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, BUY_PAGE_AMOUNT);
        PageInfo<Buy> list = new PageInfo<>(buyService.selectBuyByUid(uid));
        return Result.success(list);
    }

    @GetMapping({"/buy/list/wid/{wid}", "/buy/list/wid/{wid}/{page}"})
    public Result selectBuyByWid(@PathVariable("wid") int wid,
                                 @PathVariable(value = "page", required = false) Integer page,
                                 HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (uid != workService.selectWorkUid(wid)) return Result.error(APIMsg.AUTH_ERROR);
        if (page == null) page = 1;
        PageHelper.startPage(page, BUY_PAGE_AMOUNT);
        PageInfo<Buy> list = new PageInfo<>(buyService.selectBuyByWid(wid));
        return Result.success(list);
    }

    @GetMapping({"/buy-admin/list/wid/{wid}", "/buy-admin/list/wid/{wid}/{page}"})
    public Result selectBuyByWidAsAdmin(@PathVariable("wid") int wid,
                                 @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, BUY_PAGE_AMOUNT);
        PageInfo<Buy> list = new PageInfo<>(buyService.selectBuyByWid(wid));
        return Result.success(list);
    }

    @GetMapping({"/buy/list/time", "/buy/list/time/{page}"})
    public Result selectBuyByTime(@RequestParam("start") String start, @RequestParam("end") String end,
                                  @PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        Timestamp startTime = Timestamp.valueOf(start);
        Timestamp endTime = Timestamp.valueOf(end);
        PageHelper.startPage(page, BUY_PAGE_AMOUNT);
        PageInfo<Buy> list = new PageInfo<>(buyService.selectBuyByTime(startTime, endTime));
        return Result.success(list);
    }

    @GetMapping({"/buy/all", "/buy/all/{page}"})
    public Result selectAllBuy(@PathVariable(value = "page", required = false) Integer page) {
        if (page == null) page = 1;
        PageHelper.startPage(page, BUY_PAGE_AMOUNT);
        PageInfo<Buy> list = new PageInfo<>(buyService.selectAllBuy());
        return Result.success(list);
    }

    @GetMapping("/buy/sell/{wid}")
    public Result selectWorkSell(@PathVariable("wid") int wid, HttpSession session) {
        int uid = (int) session.getAttribute("uid");
        if (uid != workService.selectWorkUid(wid)) return Result.error(APIMsg.AUTH_ERROR);
        return Result.success(buyService.selectWorkSell(wid));
    }
}
