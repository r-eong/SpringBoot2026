package com.green.controller;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ReserveController {
	
	@GetMapping("/movie/reserve")
	public String reserveForm() {
		System.out.println("reserveForm()");
		return "reserveForm";
	}
	
	@GetMapping("/movie/reserveConfirm")
	public ModelAndView printReserve(
			@RequestParam("title") String title,
			@RequestParam("new_date") String new_date,
			@RequestParam("new_time") String new_time,
			@RequestParam("people") int people,
			@RequestParam("name") String name
			) {
		System.out.println("printReserve()");
		
		ModelAndView mNv = new ModelAndView();
		mNv.setViewName("reservePrint");
		
//		SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat sd2 = new SimpleDateFormat("HH:mm:ss");
		
		mNv.addObject("title", title);
		mNv.addObject("new_date", new_date);
		mNv.addObject("new_time", new_time);
		mNv.addObject("people", people);
		mNv.addObject("name", name);
		
		return mNv;
	}
}
