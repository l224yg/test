package com.li.seckill.controller;

import com.li.seckill.access.AccessLimit;
import com.li.seckill.domain.MiaoshaOrder;
import com.li.seckill.domain.MiaoshaUser;
import com.li.seckill.domain.User;
import com.li.seckill.rabbitmq.MQSender;
import com.li.seckill.rabbitmq.MiaoshaMessage;
import com.li.seckill.redis.GoodsKey;
import com.li.seckill.redis.MiaoshaKey;
import com.li.seckill.redis.OrderKey;
import com.li.seckill.redis.RedisService;
import com.li.seckill.result.CodeMsg;
import com.li.seckill.result.Result;
import com.li.seckill.service.GoodsService;
import com.li.seckill.service.MiaoshaService;
import com.li.seckill.service.OrderService;
import com.li.seckill.service.UserService;
import com.li.seckill.vo.GoodsVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther Liyg
 * @Date 2018/10/30
 */
//实现InitializingBean接口可以实现再系统完成初始化之后做一些事情.
@Slf4j
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private UserService userService;
    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private MiaoshaService miaoshaService;
    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;

    //减少访问redis的网络传输开销,本地缓存售罄与否的信息
    private Map<Long,Boolean> localOverMap= new HashMap<Long,Boolean>();

    @RequestMapping("/path/{path}/do_miaosha/{goodsId}")
    @ResponseBody
    public Result doMiaosha(Model model,User user,
                            @PathVariable("goodsId") Long goodsId,
                            @PathVariable("path") String path) {
        if (user == null) {
            return Result.NOTLOGIN;
        }
        //采用秒杀接口优化之后

        //验证path
        boolean check=miaoshaService.checkPath(user,goodsId,path);
        if(!check){
            return Result.ILLEGAL_REQUEST;
        }
        //先查看内存标记,减少redis访问网络开销
        boolean over=localOverMap.get(goodsId);
        if(over){
            return Result.MIAOSHAOVER_ERROR;
        }

        //预减库存
        Long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock, "" + goodsId);

        //判断库存量
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.MIAOSHAOVER_ERROR;
        }

        //判断是否已经秒杀到了
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);

        //判断是否重复秒杀
        if (order != null) {
            return Result.MIAOSHAREPEATED_ERROR;
        }

        //消息队列
        MiaoshaMessage message = new MiaoshaMessage();
        message.setUser(user);
        message.setGoodsId(goodsId);
        sender.sendMiaoshaMessage(message);

        return Result.success(0); //排队中
    }


    /**
     * 响应客户端的轮询请求,查看是否成功生成秒杀订单
     * 返回orderId :成功
     * 返回-1 :秒杀失败
     * 返回0 : 排队中
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result miaoshaResult(Model model, User user, @RequestParam("goodsId")long goodsId){
        model.addAttribute("user", user);
        if(user==null){
            return Result.NOTLOGIN;
        }

        long result=miaoshaService.getMiaoshaResult(user.getId(),goodsId);

        return Result.success(result);
    }

    /**
     * 数据初始化
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        //系统初始化之后秒杀数据准备
        List<GoodsVo> goodsVoList=goodsService.listGoodsVo();
        if(goodsVoList==null || goodsVoList.size()==0){
            return;
        }
        for (GoodsVo goodsVo : goodsVoList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        }
    }



    @AccessLimit(seconds=5, maxCount=5, needLogin=true)
    @RequestMapping(value="/path", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaPath(HttpServletRequest request, User user,
                                         @RequestParam("goodsId")long goodsId,
                                         @RequestParam(value="verifyCode", defaultValue="0")int verifyCode
    ) {
        if(user == null) {
            return Result.NOTLOGIN;
        }
        boolean check = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
        if(!check) {
            return Result.ILLEGAL_REQUEST;
        }
        String path  =miaoshaService.createMiaoshaPath(user, goodsId);
        return Result.success(path);
    }


    @RequestMapping(value="/verifyCode", method=RequestMethod.GET)
    @ResponseBody
    public Result<String> getMiaoshaVerifyCod(HttpServletResponse response, User user,
                                              @RequestParam("goodsId")long goodsId) throws IOException {
        if(user == null) {
            return Result.NOTLOGIN;
        }
        try {
            BufferedImage image  = miaoshaService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        }catch(Exception e) {
            e.printStackTrace();
            return Result.MIAOSHAFAIL_ERROR;
        }
    }


    /**
     * 数据重置接口,仅用于开发调试
     */
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset(Model model){
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        for (GoodsVo goodsVo : goodsVoList) {
            //重置库存
            goodsVo.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goodsVo.getId(), 10);
            localOverMap.put(goodsVo.getId(), false);
        }

        redisService.delete(OrderKey.getMiaoshaOrderByUidGid);
        redisService.delete(MiaoshaKey.isGoodsOver);
        miaoshaService.reset(goodsVoList);
        return Result.success(true);
    }
}
