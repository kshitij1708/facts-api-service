package com.example.facts.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Fact {
  private String id;
  private String text;
  private String source;
  private String sourceUrl;
  private String language;
  private String permalink;
}