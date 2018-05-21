package com.pporzuczek.rster.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true,
        value = {"id"})
public class ReadResult {

    @Id
    @GeneratedValue
    private Long id;
    private String html;
    private int words;
}