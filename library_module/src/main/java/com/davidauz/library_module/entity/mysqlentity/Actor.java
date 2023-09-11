package com.davidauz.library_module.entity.mysqlentity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="ACTORS")
public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto-incrementing columns
	@Column
	private Long actor_id;

	@Column(nullable=false, unique=true)
	private String actor_name;

	@Column
	private String actor_comment;
}
