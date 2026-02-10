package com.green;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.green.carproduct.CarProcuctService;
import com.green.carproduct.CarProductDTO;

@Controller
public class HomeController {
	@Autowired
	CarProcuctService carproductservice;
	
//	localhost:8090 또는 localhost:8090/ 을 함께 사용 가능하게
	@GetMapping({"", "/"})
	public String home(Model model) {
		System.out.println("HomeController - home 실행");
		
		List<CarProductDTO> carList = carproductservice.getAllCarProduct();
//									  ┖> '1', 'Veyron', '2000000000', 'Bugatti', '1.jpg', '고성능 슈퍼카입니다.'
//										 위의 자료를 DB에서 꺼내와 List - ArrayList 배열로 저장한다는 뜻
		
//		carList 를 model.addAttribute 에 담아서 home.html 로 내보낸다
//		단, model 은 한 번 담아보내면 다른 페이지로 이동해도 자료를 가지고 갈 수 없다.
		model.addAttribute("carList", carList);
		
		return "home";
	}
}
