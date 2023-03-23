package org.galatea.starter.domain;

import java.util.Date;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IexHistoricalPrices {
  private float close;
  private float high;
  private float low;
  private float open;
  private String symbol;
  private Integer volume;
  private Date date;
}
