package com.davidauz.library_module.entity.mssqlentity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MOVIES")
public class Movie {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrementing columns
	@Column
	private String movie_id;

	@Column
	private String movie_title;

	@Column
	private String movie_description;
}
