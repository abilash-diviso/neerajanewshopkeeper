package com.diviso.graeshoppe.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.client.store.model.*;


public interface StoreQueryService {
	
	public Page<Review> findAllReviews(String storeId, Pageable pageable);
	
	public Page<UserRating> findAllUserRatings(String storeId, Pageable pageable);
	
	public Store findStoreByRegNo(String regNo);
	
	/**
	 * @param id
	 * @return
	 */
	public Page<DeliveryInfo> findDeliveryInfoByStoreId(Long id);
	
	/**
	 * @param storeId
	 * @param pageable
	 * @return
	 */
	List<Type> findAllDeliveryTypesByStoreId(String storeId);
	
	/**
	 * @param regNo
	 * @return
	 */
	public List<StoreType> findAllStoreTypesByStoreId(String regNo);
	
	/**
	 * @param regNo
	 * @return
	 */
	public Page<Banner> findBannersByStoreId(String regNo);

	List<Banner> findAllBannersByStoreId(String regNo);


}
