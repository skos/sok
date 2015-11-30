package pl.gda.pg.ds.sok.entities;

import org.hibernate.annotations.ColumnTransformer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	@NotNull
	@Column(columnDefinition = "inet default '127.0.0.1'")
	@ColumnTransformer(write="CAST(? AS inet)")
	private String ip;
	@NotNull
	@Column(columnDefinition = "timestamp with time zone default now()")
	private Date registrationDate;
	@OneToMany(mappedBy="candidate",cascade=CascadeType.PERSIST)
	private List<AnswerHistory> answers = new ArrayList<>();
	
	public Candidate() {
	}
	
	public Candidate(String name, String email, String token, String ip) {
		this.name = name;
		this.email = email;
		this.token = token;
		this.ip = ip;
		this.registrationDate = new Date();
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
