package com.green.carproduct.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.green.carproduct.CarProductDTO;

@Mapper
public interface CarProductMapper {
//	carProduct 모두를 검색하는 메소드
//	getAllCarProduct 메소드는 carproduct-mapper.xml 로 연결해서 sql문 작성
	public List<CarProductDTO> getAllCarProduct();
}
