package com.nowhealth.mobile.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowhealth.mobile.constants.LudiConstants;
import com.nowhealth.mobile.entity.BaseInfor;
import com.nowhealth.mobile.service.PaymentInforService;



@Controller
@RequestMapping("/paymentInfor")
public class PaymentInforController {
	private static final Logger logger = Logger.getLogger(PaymentInforController.class);
	
	@Autowired
	private PaymentInforService paymentInforService;
	/**
	 * 点击支付按钮后调用后台
	 */
	@RequestMapping("/paymentApplication.do")
	public String paymentApplication(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap){		
		BaseInfor baseinfor = paymentInforService.paymentApplication(request);
		logger.info("返回成功1");
		modelMap.addAttribute("baseinfor",baseinfor);
		return LudiConstants.PAYMENT;		
	}
	
	/**
	 * 分享支付二维码给朋友支付(代付/扫码支付)
	 * @param request
	 * @return
	 */
	@RequestMapping("sharedFriendPay.do")
	public String wechatSweepPay(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap){
		logger.info("进入到代付生成二维码！");
		String result = null;
		try {
			result = paymentInforService.parsePayQRcode(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("result", result);
		modelMap.addAttribute("isShare","1");
		return LudiConstants.QRCODE;
	}
	
	/**
	 * 扫码支付实际调用微信公众号支付实现（扫码支付不能长按识别支付）
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("sweepPayByWechat.do")
	public String sweepPayByWechat(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap){
		logger.info("扫码支付通过公众号支付完成生成代付二维码！");
		String result = null;
		try {
			result = paymentInforService.sweepPayQRcode(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		modelMap.addAttribute("result", result);
		return LudiConstants.QRCODE;
	}
	
	/**
	 * 微信公众号支付
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("wechatPay.do")
	@ResponseBody
	public Map<String,String> wechatPay(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap){
		logger.info("微信公众号支付！");
		Map<String,String> map = paymentInforService.wechatPay(request);
		logger.info("返回的map集合为："+map.toString());
		return map;
	}
	
	/**
	 * ***bug---下单账号与支付账号不一致，不能支付----***
	 * 扫码或者长按识别二维码调用微信公众号支付
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("wechatPayToSweep.do")
	@ResponseBody
	public Map<String,String> wechatPayToSweep(HttpServletRequest request,
			HttpServletResponse response,ModelMap modelMap){
		logger.info("微信公众号扫码和长按识别支付！");
		Map<String,String> map = paymentInforService.wechatPayToSweep(request);
		logger.info("返回的map集合为："+map.toString());
		return map;
	}
	/**
	 * 选择银行卡支付生成签名
	 */
	@RequestMapping("generateSignature.do")
	@ResponseBody
	public String generateSignatur(HttpServletRequest request,
			HttpServletResponse response){
		logger.info("选择银行卡支付,生成签名开始......");
		String signatrue = paymentInforService.generateSignature(request,response);
		logger.info("生成的签名的返回结果为："+signatrue);
		return signatrue;
	}
	
	
	/**
	 * 选择银行卡支付前（向通联注册用户获取返回的userid）
	 */
	@RequestMapping("getTLuserid.do")
	@ResponseBody
	public String getTLuserid(HttpServletRequest request,
			HttpServletResponse response){
		logger.info("向通联注册用户，获取返回的userid......");
		String tltUserid = paymentInforService.getTltUserid(request,response);
		logger.info("获取通联返回的userid为："+tltUserid);
		return tltUserid;
	}
}
