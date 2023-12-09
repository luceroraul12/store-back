package distribuidora.scrapping.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
