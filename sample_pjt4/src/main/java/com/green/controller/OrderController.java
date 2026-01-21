package com.green.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class OrderController {
//	주문 입력폼
	@GetMapping("/product/order")
	public String orderForm() {
		System.out.println("orderForm()");
		return "orderForm";
	}
	
//	주문 확인페이지
	@GetMapping("/product/orderResult")
	public ModelAndView orderRes(
			@RequestParam("productName") String productName,
			@RequestParam("price") int price,
			@RequestParam("quantity") int quantity,
			@RequestParam("ordererName") String ordererName
			) {
		System.out.println("orderRes()");
		
		ModelAndView mNv = new ModelAndView();
		
		mNv.setViewName("orderResult");
		
		mNv.addObject("productName", productName);
		mNv.addObject("price", price);
		mNv.addObject("quantity", quantity);
		mNv.addObject("ordererName", ordererName);
		int total = price*quantity;
		mNv.addObject("total", total);
		
		System.out.println(productName + price + quantity + ordererName + total);
		
		return mNv;
	}
}
