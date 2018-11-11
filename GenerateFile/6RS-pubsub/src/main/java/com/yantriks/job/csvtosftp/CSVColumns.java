package com.yantriks.job.csvtosftp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@Builder
public class CSVColumns {
  
  private String messageType;
  private String groupType;
  private String groupID;
  private String pickID;
  private String containerID;
  private String containerType;
  private String sourceLocation;
  private String destinationLocation;
  private String unitOfMeasureQuantity;
  private String unitOfMeasure;
  private String eachQuantity;
  private String productID;
  private String lotID;
  private String identifiers;
  private String name;
  private String description;
  private String image;
  private String length;
  private String width;
  private String height;
  private String weight;
  private String dimensionUnitOfMeasure;
  private String weightUnitOfMeasure;
  private String expectedShippingDate;
  private String data;
  

}
