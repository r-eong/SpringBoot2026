package com.green;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AddressController {
//	heap 메모리에 주소 데이터를 담을 리스트
	private List<AddressDTO> addressList = new ArrayList<>();
	
//	1. 주소록 화면
	@GetMapping("/address")
	public String list(Model model) {
		model.addAttribute("list", addressList);
		return "address-list";
	}
	
//	2. 주소 추가
	@PostMapping("/add-address")
	public String addr(AddressDTO adto) {
//		ArrayList 에 삽입
		addressList.add(adto);
		
//		현재 url은 add-address인데 address로 이사함
		return "redirect:/address";
	}
}
