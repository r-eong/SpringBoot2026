package com.green.carproduct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.green.carproduct.mapper.CarProductMapper;

@Service
public class CarProcuctService {
//	의존객체 삽입
//	을 안 하면 carproduct-mapper.xml 의 SQL 문을 사용할 수 없음
	@Autowired
	CarProductMapper carproductmapper;
	
//	CarProductMapper 의 메소드 복붙
	public List<CarProductDTO> getAllCarProduct(){
		System.out.println("CarProcuctService - getAllCarProduct 실행");
		
		return carproductmapper.getAllCarProduct();
	}
}
