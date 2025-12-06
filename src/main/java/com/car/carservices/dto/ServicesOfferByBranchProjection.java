package com.car.carservices.dto;

/**
 * Projection for native query result mapping.
 * The method names must match the column aliases in the query.
 */
public interface ServicesOfferByBranchProjection {

    Long getServiceId();

    String getServiceName();
}
