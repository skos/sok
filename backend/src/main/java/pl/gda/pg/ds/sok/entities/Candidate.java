package pl.gda.pg.ds.sok.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "candidates")
public class Candidate implements Serializable  {
	
	private static final long serialVersionUID = 7453611484976104100L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Size(max = 64)
	private String name;
	@NotNull
	@Size(max = 64)
	@Column(unique = true)
	private String email;
	@NotNull
	@Column(unique = true)
	private String token;
	@OneToMany(mappedBy="candidate",cascade=CascadeType.PERSIST)
	private List<Answer> answers = new ArrayList<Answer>();
	
	public Candidate() {
	}
	
	public Candidate(String name, String email, String token) {
		this.name = name;
		this.email = email;
		this.token = token;
	}
	
	public Candidate(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getToken() {
		return token;
	}
}
