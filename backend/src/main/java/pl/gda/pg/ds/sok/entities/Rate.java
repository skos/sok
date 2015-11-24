package pl.gda.pg.ds.sok.entities;

import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "rates")
public class Rate implements Serializable {

	private static final long serialVersionUID = 5045398735786695881L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(columnDefinition = "smallint default 1")
	private int rate;
	@NotNull
	@Type(type="text")
	private String comment;
	@NotNull
	@Column(columnDefinition = "inet default '127.0.0.1'")
	@ColumnTransformer(write="CAST(? AS inet)")
	private String ip;
	@NotNull
	@Column(columnDefinition = "timestamp with time zone default now()")
	private Date date;
	@ManyToOne
	private AnswerHistory answer;

	public Rate() {
	}

	public Rate(int rate, String comment, AnswerHistory answer, String ip) {
		this.rate = rate;
		this.comment = comment;
		this.answer = answer;
		this.ip = ip;
		this.date = new Date();
	}

	public Long getId() {
		return id;
	}

	public int getRate() {
		return rate;
	}

	public String getComment() {
		return comment;
	}
}
