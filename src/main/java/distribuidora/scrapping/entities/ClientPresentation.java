package distribuidora.scrapping.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class ClientPresentation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	private Boolean selectable;
	private Boolean pdfShowChild;
	@ManyToOne
	@JoinColumn(name = "client_id")
	Client client;
	@ManyToOne
	@JoinColumn(name = "presentation_id")
	Presentation presentation;
}
