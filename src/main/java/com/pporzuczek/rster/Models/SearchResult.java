package com.pporzuczek.rster.Models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true,
        value = {"id", "source"})
//@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchResult {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String source;
    private String href;
}