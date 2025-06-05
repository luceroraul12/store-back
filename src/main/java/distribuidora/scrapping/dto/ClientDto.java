package distribuidora.scrapping.dto;

import lombok.Data;

@Data
public class ClientDto {
	private Integer id;
	private String name;
	private String phone;
	private String phoneLink;
	private String address;
	private String addressLink;
	private String instagram;
	private String instagramLink;
	private String facebook;
	private String facebookLink;
	private String filenameLogo;
}
