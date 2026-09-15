package distribuidora.scrapping.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CashRegisterSessionDto {
	private Integer id;
	private Integer clientId;
	private Date openingDate;
	private Date closingDate;
	private Double initialAmount;
	private Double countedAmount;
	private String status;
	private String openedBy;
	private String closedBy;
}
