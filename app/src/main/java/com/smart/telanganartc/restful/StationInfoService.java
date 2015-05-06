package com.smart.telanganartc.restful;

import java.util.List;

import com.smart.telanganartc.vo.StationVO;

import retrofit.http.GET;

public interface StationInfoService {

	@GET("/apsrtc/stations")
	List<StationVO> getStations();
	
}