package com.frapee.basic.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "simple") 
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
/**
 * Entity (store class) to support simple data (Giving the string just a name and add an id)
 * It will be stored in a database
 * Validation for the database has been added as well
 */
public class SimpleEntity {

    private static final int MAX_FIELD_LENGTH = 50;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private int id;

    @Column(name = "name", nullable = false, length = MAX_FIELD_LENGTH)
    @Size(max = MAX_FIELD_LENGTH)
    @NotNull(message = "Name cannot be null")
    private String name;

}
